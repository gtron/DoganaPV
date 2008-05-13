package com.gsoft.doganapt.cmd.consegne;

import java.util.ArrayList;
import java.util.Iterator;

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
import com.gtsoft.utils.http.servlet.GtServlet;


public class ApriConsegna extends VelocityCommand {
	
	protected static String TEMPLATE = "consegne/list.vm" ;
	
	public ApriConsegna ( GtServlet callerServlet) {
		super(callerServlet);
	}
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {
		
		Integer id = getIntParam("id", true);
		
		Consegna c = ConsegnaAdapter.get(id);
		
		String note = getParam("note", false);
		
		FormattedDate data = getDateParam("data", true);
		String doc = getParam("doc", false);
		String doc_num = getParam("doc_num", false);
		FormattedDate doc_data = getDateParam("doc_data", false);
		
		Documento documento = Documento.getDocumento( doc, doc_data , doc_num ); 
		
		String docPV = getParam("docPV", false);
		String docPV_num = getParam("docPV_num", false);
		FormattedDate docPV_data = getDateParam("docPV_data", false);
		
		Documento documentoPV = Documento.getDocumento( docPV, docPV_data , docPV_num ); 
		
		ctx.put( ContextKeys.OBJECT , c ) ;
		
		if ( c != null && getBooleanParam(Strings.EXEC) ) {
			
			MovimentoQuadrelliAdapter qAdp = new MovimentoQuadrelliAdapter(PtMovimentazioniImporter.getInstance().getAccessDB());
			
			ArrayList<String> stalli = qAdp.getCodiciStalli(c, data);
			Stallo s ;
			StalloAdapter sAdp = Stallo.newAdapter() ;
			
			for ( Iterator<String> i = stalli.iterator() ; i.hasNext() ; ){
				s = (Stallo) sAdp.getByCodice( i.next() ) ;
				
				if ( s != null ) {
//					if ( s.getIdConsegnaAttuale() == null) {
						s.setIdConsegnaAttuale(id);
						s.setIdConsegnaPrenotata(null);
						s.setImmessoInLiberaPratica(Boolean.FALSE) ;
						
						s.setAttuale( new Double(0) );
						s.setCaricato( new Double(0) );
						
						sAdp.update(s);
//					}
//					else if ( ! c.getIdIter().equals( 4 )) {
//						
//						ctx.put( "list" ,  new ConsegnaAdapter().getNonChiuse( ) ) ;
//						throw new UserException("Attenzione, lo stallo " + s  + " non è libero !") ;
//					}
				}
			}
			
			if ( c.isChiusa() ) {
//				c.setDataChiusura(null);
//				Consegna.newAdapter().update(c);
			}
			else {
				c.getIter()
					.getImporter(
						new MovimentoDoganaleAdapter(),
						new MovimentoIvaAdapter(), 
						qAdp
						)
						
						.apriConsegna(c, data, documento, documentoPV, note);
			}
			
			
			
			
			c.setDataChiusura(null);
			Consegna.newAdapter().update(c);
			
			ctx.put( "list" ,  new ConsegnaAdapter().getNonChiuse( ) ) ;
			
			ctx.put("result", Boolean.TRUE ) ;
			
			response.sendRedirect(".consegne?id=" + id );

		}
		return null ;
	}
	

	public VelocityCommand clone() {
		return  new ApriConsegna(this.callerServlet);
	}
	
	public String getTemplateName() {
		return TEMPLATE;
	}
}