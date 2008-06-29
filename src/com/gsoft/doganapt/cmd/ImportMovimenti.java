package com.gsoft.doganapt.cmd;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.data.Consegna;
import com.gsoft.doganapt.data.adapters.ConsegnaAdapter;
import com.gsoft.pt_movimentazioni.utils.PtMovimentazioniImporter;
import com.gtsoft.utils.common.FormattedDate;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;

public class ImportMovimenti extends VelocityCommand {

	protected static final String HOMEPAGE = "homepage.vm" ;
	private static final String ID = "idConsegna" ; 
	
	public ImportMovimenti ( GtServlet callerServlet) {
		super(callerServlet);
	}

	public VelocityCommand clone() {
		return  new ImportMovimenti(this.callerServlet);
	}

	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {
		
		Integer idConsegna = getIntParam(ID, false);
		
		ArrayList<Integer> stalli = getIntParams("stalli", 0);
		
		FormattedDate finoAl = getDateParam("to", false);
		Consegna c = null ;
		if ( idConsegna != null ) { 
			c = ConsegnaAdapter.get(idConsegna);
			PtMovimentazioniImporter.getInstance().importTo(c, finoAl,stalli);
		}
		else 
			PtMovimentazioniImporter.getInstance().importTo(finoAl);
		
		ctx.put(ContextKeys.MESSAGE, "Importazione effettuata");
		
		if ( c != null )
			resp.sendRedirect(".consegne?id=" + c.getId());
		
		return null;
	}

	public String getTemplateName() {
		return HOMEPAGE ;
	}

	
}
