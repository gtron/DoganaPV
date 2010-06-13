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

public class LockMdb extends VelocityCommand {

	public LockMdb ( GtServlet callerServlet) {
		super(callerServlet);
	}

	public VelocityCommand clone() {
		return  new LockMdb(this.callerServlet);
	}

	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {
		
		PtMovimentazioniImporter.setLocked( getBooleanParam("lock") );
		
		ctx.put(ContextKeys.RESULT, Boolean.TRUE.toString() );
		
		return null;
	}

	public String getTemplateName() {
		return "void.vm" ;
	}

	
}
