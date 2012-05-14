package com.gsoft.doganapt.cmd;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.data.Stallo;
import com.gsoft.doganapt.data.adapters.ConsegnaAdapter;
import com.gsoft.doganapt.data.adapters.IterAdapter;
import com.gsoft.doganapt.data.adapters.MerceAdapter;
import com.gsoft.doganapt.data.adapters.StalloAdapter;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;

public class Homepage extends VelocityCommand {

	protected static final String HOMEPAGE = "homepage.vm" ;
	protected static String PRINT = "stalli/print.vm";

	//	private static final String ID = "id" ;

	public Homepage ( GtServlet callerServlet) {
		super(callerServlet);
	}

	@Override
	public VelocityCommand clone() {
		return  new Homepage(callerServlet);
	}

	@Override
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {

		HttpSession s =  req.getSession(false);

		if ( s == null ) {
			resp.sendRedirect(".main");
			resp.flushBuffer();
			return null ;
		}
		Boolean logged = (Boolean) s.getAttribute("logged") ;
		if ( logged != Boolean.TRUE ) {
			resp.sendRedirect(".main");
		}

		ctx.put("isAdmin", s.getAttribute("admin") ) ;

		ctx.put("stalli", Stallo.newAdapter().getAll("parco, numero") );

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


	@Override
	public String getTemplateName() {
		if ( getBooleanParam("p") )
			return PRINT ;

		return HOMEPAGE ;
	}


	public static void purgeCaches() {
		ConsegnaAdapter.clearCache();
		StalloAdapter.clearCache();
		MerceAdapter.clearCache();
		IterAdapter.clearCache();
	}


}
