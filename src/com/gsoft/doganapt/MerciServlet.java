package com.gsoft.doganapt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.cmd.merci.ImportMerci;
import com.gsoft.doganapt.cmd.merci.ListMerci;
import com.gsoft.framework.TooledServlet;

public class MerciServlet extends TooledServlet {

	public MerciServlet() { 
		super(); 
		
		addCommand( Commands.DEFAULT ,
				new ListMerci( this ) ) ;
		
		addCommand( Commands.DOIMPORT ,
				new ImportMerci( this ) ) ;
		
	}

	public static interface Commands {
		public static final String DEFAULT = "" ;
		public static final String DOIMPORT = "import" ;
	}
	
	protected Template handleRequest(HttpServletRequest request, HttpServletResponse response, Context ctx) throws Exception {

		HttpSession s =  request.getSession(false);
		Boolean logged = null ; 
		
		if ( s != null )
			logged = (Boolean) s.getAttribute("logged") ;
		
		if ( logged != Boolean.TRUE ) {
			response.sendRedirect(".main");
		}
		return super.handleRequest(request, response, ctx) ;
	}
}
