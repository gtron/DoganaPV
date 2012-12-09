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
import com.gsoft.doganapt.data.Stallo;
import com.gsoft.doganapt.data.adapters.ConsegnaAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoDoganaleAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoIvaAdapter;
import com.gsoft.doganapt.data.adapters.StalloAdapter;
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

		ctx.put( ContextKeys.OBJECT , consegna ) ;

		if ( consegna != null && getBooleanParam(Strings.EXEC) ) {

			MovimentoQuadrelliAdapter qAdp = new MovimentoQuadrelliAdapter(PtMovimentazioniImporter.getInstance().getAccessDB());
			ArrayList<String> codiciStalli = qAdp.getCodiciStalli(consegna, data);

			Stallo s ;
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

			if ( consegna.isChiusa() ) {
				//				c.setDataChiusura(null);
				//				Consegna.newAdapter().update(c);
			}
			else {
				consegna.getIter()
				.getImporter(
						new MovimentoDoganaleAdapter(),
						new MovimentoIvaAdapter(),
						qAdp
						).apriConsegna(consegna, data, documento, documentoPV, note);
			}

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
		ConsegnaAdapter.assegnaStallo(c, s);
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