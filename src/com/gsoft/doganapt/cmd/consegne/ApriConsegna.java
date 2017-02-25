package com.gsoft.doganapt.cmd.consegne;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.data.Consegna;
import com.gsoft.doganapt.data.Documento;
import com.gsoft.doganapt.data.Movimento;
import com.gsoft.doganapt.data.MovimentoIVA;
import com.gsoft.doganapt.data.Stallo;
import com.gsoft.doganapt.data.StalloConsegna;
import com.gsoft.doganapt.data.adapters.ConsegnaAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoDoganaleAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoIvaAdapter;
import com.gsoft.doganapt.data.adapters.StalloAdapter;
import com.gsoft.doganapt.data.adapters.StalloConsegnaAdapter;
import com.gsoft.pt_movimentazioni.data.MovimentoQuadrelliAdapter;
import com.gsoft.pt_movimentazioni.utils.PtMovimentazioniImporter;
import com.gtsoft.utils.common.FormattedDate;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.exception.ParameterException;
import com.gtsoft.utils.http.servlet.GtServlet;


public class ApriConsegna extends VelocityCommand {

	protected static String TEMPLATE = "consegne/list.vm" ;

	StalloAdapter sAdp = null ;

	Integer idConsegna = null;
	Consegna consegna = null;
	FormattedDate data = null;

	Double valoreDollari = null;
	Double valoreEuro = null;
	Double valoreTestp = null;
	Double tassoCambio = null;

	Double sommaSeccoTotale = null;

	Double valoreUnitarioUSD = null;
	Double valoreUnitarioEuro = null;
	Double valoreUnitarioTestp = null;

	private StalloConsegna stalloConsegna;

	private StalloConsegnaAdapter stalloConsegnaAdapter;

	public ApriConsegna ( GtServlet callerServlet) {
		super(callerServlet);
	}

	@Override
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {

		idConsegna = getIntParam("id", true);
		consegna = ConsegnaAdapter.get(idConsegna);
		data = getDateParam("data", true);

		String note = getParam("note", false);

		Documento documento = getDocumento();
		Documento documentoPV = getDocumentoPV();

		valoreDollari = getDoubleParam("valoreDollari", false);
		valoreTestp = getDoubleParam("valoreTestp", false);
		tassoCambio = getDoubleParam("tasso", false);


		ctx.put( ContextKeys.OBJECT , consegna ) ;

		if ( consegna != null && getBooleanParam(Strings.EXEC) ) {

			MovimentoQuadrelliAdapter qAdp = new MovimentoQuadrelliAdapter(PtMovimentazioniImporter.getInstance().getAccessDB());
			ArrayList<String> codiciStalli = qAdp.getCodiciStalli(consegna, data);

			Stallo s = null ;
			sAdp = Stallo.newAdapter() ;

			for (String codiceStallo : codiciStalli) {
				s = (Stallo) sAdp.getByCodice( codiceStallo ) ;

				if ( s != null ) {
					if ( s.getIdConsegnaAttuale() == null) {
						assegnaStallo(s, consegna);
					}
					else {
						Consegna attuale = ConsegnaAdapter.get(s.getIdConsegnaAttuale());

						if ( attuale == null || attuale.isChiusa() ) {
							assegnaStallo(s, consegna);
						} else { /*
							ctx.put( "list" ,  new ConsegnaAdapter().getNonChiuse( ) ) ;
							throw new UserException("Attenzione, lo stallo " + s  + " non è libero !") ;
						 */
						}
					}
				}
			}

			Movimento movApertura = null;
			MovimentoDoganaleAdapter movDoganaleAdp = new MovimentoDoganaleAdapter();
			MovimentoIvaAdapter movIvaAdp = new MovimentoIvaAdapter();

			if ( consegna.isChiusa() ) {
				//				c.setDataChiusura(null);
				//				Consegna.newAdapter().update(c);
			}
			else {
				movApertura = consegna.getIter()
						.getImporter(
								movDoganaleAdp,
								movIvaAdp ,
								qAdp
								).apriConsegna(consegna, data, documento, documentoPV, note);
			}

//			if ( movApertura instanceof MovimentoIVA ) {

				stalloConsegnaAdapter = new StalloConsegnaAdapter();

				initStalloConsegnaApertura(movApertura.getData());

				if ( movApertura != null ) {

					System.out.println("Apertura Consegna: " + idConsegna + " - Secco Apertura " + movApertura.getSecco() );

					double seccoTotale = 0d;


					Double pesoPolizza = consegna.getPesopolizza();
					if ( pesoPolizza  != null && pesoPolizza.doubleValue() > 0 ) {
						// se ho il peso polizza posso usare quello
						seccoTotale = consegna.calcolaSecco( pesoPolizza.doubleValue() );
					} else {
						// se non ce l'ho utilizzo il peso del momento dell'apertura
						seccoTotale = movApertura.getSecco().doubleValue();
					}

					stalloConsegna.initValoriUnitari(seccoTotale);
					stalloConsegnaAdapter.update(stalloConsegna);
				   
					if ( movApertura instanceof MovimentoIVA ) {
				    	stalloConsegna.assegnaValori((MovimentoIVA) movApertura);
				    	movIvaAdp.update(movApertura);
				    } else {
				    	movDoganaleAdp.update(movApertura);
				    }


				}
//			}


			consegna.setDataChiusura(null);
			Consegna.newAdapter().update(consegna);

			ctx.put( "list" ,  new ConsegnaAdapter().getNonChiuse( ) ) ;

			ctx.put("result", Boolean.TRUE ) ;

			response.sendRedirect(".consegne?id=" + idConsegna );

		}
		return null ;
	}

	private Documento getDocumento() throws ParameterException {
		String doc = getParam("doc", false);
		String doc_num = getParam("doc_num", false);
		FormattedDate doc_data = getDateParam("doc_data", false);

		return Documento.getDocumento( doc, doc_data , doc_num );
	}
	private Documento getDocumentoPV() throws ParameterException {

		String docPV = getParam("docIVA", false);
		String docPV_num = getParam("docIVA_num", false);
		FormattedDate docPV_data = getDateParam("docIVA_data", false);

		return Documento.getDocumento( docPV, docPV_data , docPV_num );
	}


	protected void assegnaStallo(Stallo s, Consegna c ) throws IOException, SQLException {
		// E' necessario nonstante
		//		verrà fatta anche in fase di popola Stalli
		ConsegnaAdapter.assegnaStalloAConsegna(c, s);
	}

	protected void initStalloConsegnaApertura(FormattedDate formattedDate) throws Exception {

		stalloConsegna = StalloConsegna.getNew(formattedDate);
		stalloConsegna.setIdConsegna(idConsegna);
		stalloConsegna.setIsInLiberaPratica(Boolean.FALSE);

		stalloConsegna.setValoreDollari( valoreDollari );
		stalloConsegna.setValoreEuro( stalloConsegna.getValoreArrotondatoEuro( valoreDollari / tassoCambio ));
		stalloConsegna.setValoreTesTp( valoreTestp);
		stalloConsegna.setTassoEuroDollaro( tassoCambio );

		stalloConsegna.setIdStallo(StalloConsegnaAdapter.ID_STALLO_APERTURA);

		Long id = Long.valueOf( stalloConsegnaAdapter.create(stalloConsegna).toString() );
		stalloConsegna.setId(Integer.valueOf(id.intValue()) );
	}

	@Override
	public VelocityCommand clone() {
		return  new ApriConsegna(callerServlet);
	}

	@Override
	public String getTemplateName() {
		return TEMPLATE;
	}
}