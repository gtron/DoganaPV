package com.gsoft.doganapt.cmd;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.data.Stallo;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;

public class ViewStallo extends VelocityCommand {

	protected static String TEMPLATE = "stalli/view.vm";
	
	private static final String ID = "id" ; 
	
	public ViewStallo ( GtServlet callerServlet) {
		super(callerServlet);
	}

	public VelocityCommand clone() {
		return  new ViewStallo(this.callerServlet);
	}

	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {
		
		Integer id = getIntParam(ID, false);
		ctx.put( ContextKeys.OBJECT , Stallo.newAdapter().getByKey( id ) );
		
//		BeanAdapter2 adp = BeanAdapter2.newAdapter();
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
		return TEMPLATE ;
	}

	
}
