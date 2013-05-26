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
import com.gsoft.doganapt.data.StalloConsegna;
import com.gsoft.doganapt.data.adapters.ConsegnaAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoDoganaleAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoIvaAdapter;
import com.gsoft.doganapt.data.adapters.StalloAdapter;
import com.gsoft.doganapt.data.adapters.StalloConsegnaAdapter;
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
	Stallo stalloDestinazione = null;
	Stallo stalloOrigine = null;
	private boolean isIva;
	private StalloAdapter stalloAdp;
	private Integer idConsegna;

	@Override
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {

		idConsegna = getIntParam("idC", true);
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

				stalloDestinazione = StalloAdapter.get(idStalloDestinazione);

				if ( stalloDestinazione == null ) {
					ctx.put(ContextKeys.ERROR, "Stallo di destinazione non valido! (idStallo: " + idStalloDestinazione + ")");
					return null;
				}
				if ( ! stalloDestinazione.isLibero() ) {
					Consegna c = stalloDestinazione.getConsegna();

					ctx.put(ContextKeys.ERROR, "Lo stallo di destinazione ("+stalloDestinazione+") è occupato dalla consegna " + c.getNumero()  );
					return null;
				}
			}

			data = getDateParam("data", false);
			if ( data == null ) {
				data = new FormattedDate( new FormattedDate().dmyString() );
			}

			documento = Documento.getDocumento( getParam("doc", false), getDateParam("doc_data", false) , getParam("doc_num", false) );

			stalloOrigine = StalloAdapter.get(idStalloOrigine);
			initAdapter(stalloOrigine);

			stalloAdp = new StalloAdapter();

			if ( isTravaso ) {
				doTravaso();
			}
			else {
				doSvuotamento();
			}

			stalloAdp.resetStallo(stalloOrigine);

			response.sendRedirect(".consegne?id=" + idConsegna );
			response.flushBuffer();
		}
		return null ;
	}

	void doTravaso() throws IOException, SQLException, Exception {

		Movimento giacenza = adp.getMovimentoGiacenza(stalloOrigine, consegna);

		if ( giacenza.isEmpty() )
			throw new Exception("Attenzione! Lo stallo di partenza è già vuoto!");

		Movimento scarico = newMovimento();
		scarico.copiaPesiEValoriInvertiti(giacenza);
		scarico.setIsScarico(Boolean.TRUE);
		adp.create(scarico);

		Movimento carico = newMovimento();
		carico.copiaPesiEValoriInvertiti(scarico);
		carico.setIsScarico(Boolean.FALSE);
		carico.setStallo(stalloDestinazione);
		adp.create(carico);

		if ( isIva ) {
			StalloConsegnaAdapter stcAdp = StalloConsegna.newAdapter();
			StalloConsegna stalloConsegna = stcAdp.getByKeysIds(stalloOrigine.getId(), idConsegna);

			if (stalloConsegna != null ) {
				stalloConsegna.setIdStallo(stalloDestinazione.getId());
				stalloConsegna.setId(null);
				stcAdp.create(stalloConsegna);
			}
		}

		stalloDestinazione.setIdConsegnaAttuale(consegna.getId());
		stalloDestinazione.setIdConsegnaPrenotata(null);
		stalloDestinazione.setImmessoInLiberaPratica( stalloOrigine.getImmessoInLiberaPratica() );

		stalloDestinazione.setAttuale(carico.getUmido());
		stalloDestinazione.setCaricato(carico.getUmido());

		stalloAdp.update(stalloDestinazione);
	}

	void doSvuotamento() throws Exception, IOException, SQLException {
		@SuppressWarnings("unchecked")
		Vector<Movimento> list = adp.getByConsegna(false, consegna.getId(), stalloOrigine.getId(), "id DESC LIMIT 1" , "");

		if ( list != null && ! list.isEmpty() ) {
			Movimento ultimoMov = list.firstElement();

			Movimento giacenza = adp.getMovimentoGiacenza(stalloOrigine, consegna);

			if ( ultimoMov != null && ultimoMov.getIsScarico() && ! ultimoMov.getIsRettifica() ) {

				ultimoMov.aggiustaGiacenza(giacenza);

				adp.update(ultimoMov);
			}
			else {
				if ( ! giacenza.isEmpty() ) {
					Movimento scarico = newMovimento();
					scarico.copiaPesiEValoriInvertiti(giacenza);
					adp.create(scarico);
				}
			}
		}
	}

	//	private void ricalcolaValori(Movimento ultimoMov) throws Exception {
	//
	//		if ( ultimoMov instanceof MovimentoIVA ) {
	//			StalloConsegnaAdapter stalloConsegnaAdp = StalloConsegna.newAdapter();
	//			StalloConsegna stalloConsegna = stalloConsegnaAdp.getByKeysIds(ultimoMov.getIdStallo(), ultimoMov.getIdConsegna());
	//
	//			if ( stalloConsegna != null ) {
	//				stalloConsegna.assegnaValori((MovimentoIVA) ultimoMov);
	//			}
	//		}
	//
	//	}

	private Movimento newMovimento() throws Exception {

		Movimento m = adp.newMovimento() ;

		m.setIdConsegna( consegna.getId() );
		m.setIdMerce( consegna.getIdmerce() );
		m.setData(data) ;
		m.setDocumento(documento);
		m.setStallo( stalloOrigine );
		m.setIsLocked(Boolean.FALSE);
		m.setIsRettifica(Boolean.FALSE);

		return m ;
	}

	private Movimento newMovimento_old( Stallo s , Movimento scarico) throws Exception {

		Movimento m = adp.newMovimento() ;

		Movimento mGiacenza = null;
		if ( scarico == null ) {
			//
			// Movimento di svuotamento stallo

			// Ricalcoliamo la giacenza
			mGiacenza = adp.getMovimentoGiacenza(s, consegna);
			m.copiaPesiEValoriInvertiti(mGiacenza);
			//			m.aggiustaGiacenza(giacenza)

			// dovrebbe essere sempre uno scarico,
			// ma se sto svuotando dopo una rettifica
			// potrebbe essere un carico
			m.setIsScarico(mGiacenza.getSecco().intValue() < 0);
		}
		else {
			// Sto travasando e questo è il movimento di carico
			m.setUmido( scarico.getUmido() );
			m.setSecco( scarico.getSecco() );
			m.setIsScarico(false);
		}

		m.setIdConsegna( consegna.getId() );
		m.setIdMerce( consegna.getIdmerce() );
		m.setData(data) ;
		m.setDocumento(documento);
		m.setStallo( s );
		m.setIsLocked(Boolean.FALSE);
		m.setIsRettifica(Boolean.FALSE);

		return m ;
	}

	public void initAdapter(Stallo s)  {
		if ( adp == null ) {
			if ( consegna.isIva(s) ) {
				adp = new MovimentoIvaAdapter();
				isIva = true;
			} else {
				adp = new MovimentoDoganaleAdapter();
				isIva = false;
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