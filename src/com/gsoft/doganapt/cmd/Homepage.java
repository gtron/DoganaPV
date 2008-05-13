package com.gsoft.doganapt.cmd;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.data.Stallo;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;

public class Homepage extends VelocityCommand {

	protected static final String HOMEPAGE = "homepage.vm" ;
	protected static String PRINT = "stalli/print.vm";
	
//	private static final String ID = "id" ; 
	
	public Homepage ( GtServlet callerServlet) {
		super(callerServlet);
	}

	public VelocityCommand clone() {
		return  new Homepage(this.callerServlet);
	}

	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {
		
		HttpSession s =  req.getSession(false);
		
		if ( s == null )  return null ;
		Boolean logged = (Boolean) s.getAttribute("logged") ;
		if ( logged != Boolean.TRUE ) {
			resp.sendRedirect(".main");
		}
		
		ctx.put("isAdmin", s.getAttribute("admin") ) ;

		ctx.put("stalli", Stallo.newAdapter().getAll("id") );
		
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
		if ( getBooleanParam("p") )
			return PRINT ;
		
		return HOMEPAGE ;
	}
	
	

	
}
