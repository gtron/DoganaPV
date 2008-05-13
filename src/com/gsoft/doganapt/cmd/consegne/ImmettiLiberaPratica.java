package com.gsoft.doganapt.cmd.consegne;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.data.Consegna;
import com.gsoft.doganapt.data.Documento;
import com.gsoft.doganapt.data.adapters.ConsegnaAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoDoganaleAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoIvaAdapter;
import com.gtsoft.utils.common.FormattedDate;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;


public class ImmettiLiberaPratica extends VelocityCommand {
	
	public ImmettiLiberaPratica ( GtServlet callerServlet) {
		super(callerServlet);
	}
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {
		
		Integer idConsegna = getIntParam("c", true);
		Consegna c = ConsegnaAdapter.get(idConsegna);
		
		ArrayList<Integer> stalli = getIntParams("list", 1);
		
		FormattedDate data = getDateParam("dataLP", true);
		
		String doc = getParam("doc", false);
		String doc_num = getParam("doc_num", false);
		FormattedDate doc_data = getDateParam("doc_data", false);
		
		Documento docDogana = Documento.getDocumento( doc, doc_data , doc_num ); 
		
		String docPV = getParam("docIVA", false);
		String docPV_num = getParam("docIVA_num", false);
		FormattedDate docPV_data = getDateParam("docIVA_data", false);
		
		Documento docIVA = Documento.getDocumento( docPV, docPV_data , docPV_num ); 
		
		
		ctx.put( ContextKeys.OBJECT , c ) ;
		
		if ( c != null && getBooleanParam(Strings.EXEC) ) {
			c.getIter()
				.getImporter(
					new MovimentoDoganaleAdapter(),
					new MovimentoIvaAdapter(), 
					null)
				.immettiLiberaPratica(stalli, data,  docDogana,  docIVA);
		}
		return null ;
	}
	

	public VelocityCommand clone() {
		return  new ImmettiLiberaPratica(this.callerServlet);
	}
	
	public String getTemplateName() {
		return ViewConsegna.TEMPLATE ;
	}
}