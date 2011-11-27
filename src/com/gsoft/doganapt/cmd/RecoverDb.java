package com.gsoft.doganapt.cmd;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.data.PuntoRipristinoDb;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;

public class RecoverDb extends VelocityCommand {

	Context ctx = null;
	
	public RecoverDb ( GtServlet callerServlet) {
		super(callerServlet);
	}

	public VelocityCommand clone() {
		return  new RecoverDb(this.callerServlet);
	}

	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {
		
		this.ctx = ctx;

		if ( getBooleanParam(Strings.EXEC) ) {
			doRecover();
		}
		else {
			showRecover();
		}
			
		ctx.put(ContextKeys.RESULT, Boolean.TRUE.toString() );
		
		return null;
	}
	
	protected void showRecover() {
		
		Vector<PuntoRipristinoDb> list = FileManager.getRecoverableSnapshots();
		
		ctx.put(ContextKeys.LIST, list );
		
		
	}
	
	protected void doRecover() {
		
	}

	public String getTemplateName() {
		return "recoverdb.vm" ;
	}

	
}
