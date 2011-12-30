
package com.gsoft.doganapt.cmd.consegne;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.data.Consegna;
import com.gsoft.doganapt.data.Documento;
import com.gsoft.doganapt.data.Movimento;
import com.gsoft.doganapt.data.Stallo;
import com.gsoft.doganapt.data.adapters.ConsegnaAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoDoganaleAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoIvaAdapter;
import com.gsoft.doganapt.data.adapters.StalloAdapter;
import com.gtsoft.utils.common.FormattedDate;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;


public class TravasaStallo extends VelocityCommand {

	public TravasaStallo ( GtServlet callerServlet) {
		super(callerServlet);
	}

	Consegna consegna = null ;
	FormattedDate data = null ;
	Documento documento = null ;
	MovimentoAdapter adp = null ;
	Stallo destinazione = null;
	Stallo origine = null;

	@Override
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {

		Integer idConsegna = getIntParam("idC", true);
		consegna = ConsegnaAdapter.get(idConsegna);
		ctx.put( ContextKeys.OBJECT , consegna ) ;

		Integer idStalloOrigine= getIntParam("from", true);
		Boolean isTravaso = getBooleanParam("travaso", true);
		Integer idStalloDestinazione= getIntParam("to", isTravaso);

		if ( consegna != null && getBooleanParam(Strings.EXEC) ) {

			if ( isTravaso ) {
				if ( idStalloOrigine.equals(idStalloDestinazione) ) {
					ctx.put(ContextKeys.ERROR, "Origine e destinazione del travaso non possono coincidere! (" + idStalloOrigine + " = " + idStalloDestinazione + ")");
					return null;
				}

				destinazione = StalloAdapter.get(idStalloDestinazione);

				if ( destinazione == null ) {
					ctx.put(ContextKeys.ERROR, "Stallo di destinazione non valido! (idStallo: " + idStalloDestinazione + ")");
					return null;
				}
				if ( ! destinazione.isLibero() ) {
					Consegna c = destinazione.getConsegna();

					ctx.put(ContextKeys.ERROR, "Lo stallo di destinazione ("+destinazione+") è occupato dalla consegna " + c.getNumero()  );
					return null;
				}
			}

			data = getDateParam("data", false);
			if ( data == null ) {
				data = new FormattedDate( new FormattedDate().dmyString() );
			}

			documento = Documento.getDocumento( getParam("doc", false), getDateParam("doc_data", false) , getParam("doc_num", false) );

			origine = StalloAdapter.get(idStalloOrigine);
			initAdapter(origine);

			StalloAdapter stalloAdp = new StalloAdapter();

			Movimento scarico = newMovimento( origine , null );
			if ( isTravaso ) {
				doTravaso(stalloAdp, scarico);
			}
			else {
				doSvuotamento(scarico);
			}

			stalloAdp.resetStallo(origine);

			response.sendRedirect(".consegne?id=" + idConsegna );
			response.flushBuffer();
		}
		return null ;
	}

	void doTravaso(StalloAdapter stalloAdp, Movimento scarico) throws IOException, SQLException, Exception {
		adp.create(scarico);

		Movimento carico = newMovimento(destinazione, scarico);
		if ( scarico.getUmido() > 0 ) {
			adp.create(carico);
		}

		destinazione.setIdConsegnaAttuale(consegna.getId());
		destinazione.setIdConsegnaPrenotata(null);
		destinazione.setImmessoInLiberaPratica( origine.getImmessoInLiberaPratica() );

		destinazione.setAttuale(carico.getUmido());
		destinazione.setCaricato(carico.getUmido());

		stalloAdp.update(destinazione);
	}

	void doSvuotamento(Movimento scarico) throws Exception, IOException, SQLException {
		@SuppressWarnings("unchecked")
		Vector<Movimento> list = adp.getByConsegna(false, consegna.getId(), origine.getId(), "id DESC LIMIT 1" , "");
		Movimento ultimoMov = list.firstElement();

		if ( ultimoMov != null && ultimoMov.getIsScarico() && ! ultimoMov.getIsRettifica() ) {
			ultimoMov.setUmido( ultimoMov.getUmido().doubleValue() + scarico.getUmido().doubleValue() );
			ultimoMov.setSecco( consegna.calcolaSecco(ultimoMov.getUmido()) );
			adp.update(ultimoMov);
		}
		else {
			adp.create(scarico);
		}
	}

	private Movimento newMovimento( Stallo s , Movimento scarico) throws Exception {

		Movimento m = adp.newMovimento() ;
		m.setIdConsegna( consegna.getId() );
		m.setIdMerce( consegna.getIdmerce() );

		m.setIsLocked(false);
		m.setIsRettifica( false );
		m.setData(data) ;
		m.setDocumento(documento);
		m.setStallo( s );

		if ( scarico == null ) {
			m.setUmido( s.getGiacenza(adp, false) );
			m.setSecco( s.getGiacenza(adp, true) );
			m.setIsScarico(true);
		}
		else {
			m.setUmido( scarico.getUmido() );
			m.setSecco( scarico.getSecco() );
			m.setIsScarico(false);
		}

		return m ;
	}

	public void initAdapter(Stallo s)  {
		if ( adp == null ) {
			if ( consegna.isIva(s) ) {
				adp = new MovimentoIvaAdapter();
			} else {
				adp = new MovimentoDoganaleAdapter();
			}
		}
	}

	@Override
	public VelocityCommand clone() {
		return  new TravasaStallo(callerServlet);
	}

	@Override
	public String getTemplateName() {
		return ViewConsegna.TEMPLATE ;
	}
}