package com.gsoft.doganapt.cmd;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.pt_movimentazioni.utils.PtMovimentazioniImporter;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;

public class LockMdb extends VelocityCommand {

	public LockMdb ( GtServlet callerServlet) {
		super(callerServlet);
	}

	@Override
	public VelocityCommand clone() {
		return  new LockMdb(this.callerServlet);
	}

	@Override
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {

		boolean b = getBooleanParam("lock") ;
		PtMovimentazioniImporter.setLocked( b );

		ctx.put(ContextKeys.RESULT, b ? b + " - DB End. Import Locked!"  : b + " - Import Unlocked!");

		return null;
	}

	@Override
	public String getTemplateName() {
		return "void.vm" ;
	}


}
