package com.gsoft.doganapt.cmd;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;

public class Riepilogo extends VelocityCommand {

	protected static final String HOMEPAGE = "homepage.vm" ;
//	private static final String ID = "id" ; 
	
	public Riepilogo ( GtServlet callerServlet) {
		super(callerServlet);
	}

	public VelocityCommand clone() {
		return  new Riepilogo(this.callerServlet);
	}

	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {
		
//		BeanAdapter2 adp = Evento.newAdapter();
//		ctx.put( ContextKeys.LIST , adp.getAll() ) ;
//		
//		Integer selectedId = getParameter.getInt(ID, req, false);
//		
//		if ( selectedId != null ) {
//			ctx.put( ContextKeys.OBJECT , adp.getByKey(selectedId) ) ;
//		}
		
		return null;
	}

	public String getTemplateName() {
		return HOMEPAGE ;
	}

	
}
