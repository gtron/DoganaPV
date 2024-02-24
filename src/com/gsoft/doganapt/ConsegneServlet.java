package com.gsoft.doganapt;

//import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.cmd.consegne.ApriConsegna;
import com.gsoft.doganapt.cmd.consegne.ChiudiConsegna;
import com.gsoft.doganapt.cmd.consegne.ChiudiPFPC;
import com.gsoft.doganapt.cmd.consegne.EditConsegna;
import com.gsoft.doganapt.cmd.consegne.ImmettiLiberaPratica;
import com.gsoft.doganapt.cmd.consegne.ListConsegne;
import com.gsoft.doganapt.cmd.consegne.PopolaStalli;
import com.gsoft.doganapt.cmd.consegne.PrintConsegna;
import com.gsoft.doganapt.cmd.consegne.Rettifica;
import com.gsoft.doganapt.cmd.consegne.TravasaStallo;
import com.gsoft.doganapt.cmd.consegne.ViewConsegna;
import com.gsoft.doganapt.cmd.movimenti.DeleteMovimento;
import com.gsoft.doganapt.cmd.movimenti.EditGruppoMovimenti;
import com.gsoft.doganapt.cmd.movimenti.EditMovimento;
import com.gsoft.doganapt.cmd.movimenti.NuovoMovimento;
import com.gsoft.framework.TooledServlet;
//import com.gtsoft.utils.LogManager;

public class ConsegneServlet extends TooledServlet {

	public ConsegneServlet() {
		super();

		addCommand( Commands.DEFAULT ,
				new ViewConsegna( this ) ) ;

		addCommand( Commands.PRINT ,
				new PrintConsegna( this ) ) ;

		addCommand(Commands.EDIT ,
				new EditConsegna(this) ) ;

		addCommand(Commands.EDIT_FATTURA ,
				new EditConsegna(this) ) ;

		addCommand(Commands.APRI ,
				new ApriConsegna(this) ) ;

		addCommand(Commands.CHIUDI ,
				new ChiudiConsegna(this) ) ;

		addCommand(Commands.CHIUDI_PFPC ,
				new ChiudiPFPC(this) ) ;

		addCommand(Commands.LIST ,
				new ListConsegne(this) ) ;

		addCommand(Commands.EDIT_MOVIMENTO ,
				new EditMovimento(this) );

		addCommand(Commands.NEW_MOVIMENTO ,
				new NuovoMovimento(this) );

		addCommand(Commands.EDIT_MOVIMENTO_GROUP ,
				new EditGruppoMovimenti(this) );

		addCommand(Commands.IMMETTILP ,
				new ImmettiLiberaPratica(this) );

		addCommand(Commands.POPOLA_STALLI ,
				new PopolaStalli(this) );

		addCommand( Commands.DELETE_MOVIMENTO,
				new DeleteMovimento( this ) ) ;
		addCommand( Commands.TRAVASA_STALLO,
				new TravasaStallo( this ) ) ;
		addCommand( Commands.RETTIFICA,
				new Rettifica( this ) ) ;
	}

//	@Override
//	public void init() throws ServletException {
//		super.init();
//		LogManager.initLog(getServletConfig());
//	}

	public static interface Commands {
		public static final String DEFAULT = "" ;
		public static final String EDIT = "edit" ;
		public static final String PRINT = "print" ;
		public static final String EDIT_FATTURA = "editfattura" ;
		public static final String APRI = "apri" ;
		public static final String CHIUDI = "chiudi" ;
		public static final String CHIUDI_PFPC = "chiudiPFPC" ;
		public static final String LIST = "list" ;
		public static final String EDIT_MOVIMENTO = "editmovimento" ;
		public static final String NEW_MOVIMENTO = "newmovimento" ;
		public static final String EDIT_MOVIMENTO_GROUP = "editgroup" ;
		public static final String DELETE_MOVIMENTO = "delete" ;
		public static final String TRAVASA_STALLO = "travasa" ;

		public static final String POPOLA_STALLI = "popola" ;
		public static final String RETTIFICA = "rettifica" ;
		public static final String IMMETTILP = "immettiLP" ;

	}

	@Override
	protected Template handleRequest(final HttpServletRequest request, final HttpServletResponse response, final Context ctx) throws Exception {

		final HttpSession s =  request.getSession(false);
		Boolean logged = null ;

		if ( s != null ) {
			logged = (Boolean) s.getAttribute("logged") ;
		}

		if ( logged != Boolean.TRUE ) {
			response.sendRedirect(".main");
			response.flushBuffer();
			return null ;
		}
		return super.handleRequest(request, response, ctx) ;
	}
}

