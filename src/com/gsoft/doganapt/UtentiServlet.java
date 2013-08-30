package com.gsoft.doganapt;

//import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.cmd.Login;
import com.gsoft.doganapt.cmd.utenti.EditUtente;
import com.gsoft.doganapt.cmd.utenti.ListUtenti;
import com.gsoft.doganapt.data.Utente;
import com.gsoft.framework.TooledServlet;
//import com.gtsoft.utils.LogManager;

@SuppressWarnings("serial")
public class UtentiServlet extends TooledServlet {

	public UtentiServlet() {
		super();

		addCommand( Commands.DEFAULT ,
				new ListUtenti( this ) ) ;

		addCommand(Commands.EDIT ,
				new EditUtente(this) ) ;

	}

	//	@Override
	//	public void init() throws ServletException {
	//		super.init();
	//		LogManager.initLog(getServletConfig());
	//	}

	public static interface Commands {
		public static final String DEFAULT = "" ;
		public static final String EDIT = "edit" ;
		public static final String LIST = "list" ;
	}

	@Override
	protected Template handleRequest(final HttpServletRequest request, final HttpServletResponse response, final Context ctx) throws Exception {
		if ( ! Login.hasLevel(request, Utente.Levels.ADMIN ) ) {
			response.sendRedirect(".main");
			response.flushBuffer();
			return null ;
		}
		return super.handleRequest(request, response, ctx) ;
	}
}

