package com.gsoft.doganapt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.cmd.registri.DeregistraMovimento;
import com.gsoft.doganapt.cmd.registri.PreparaRegistro;
import com.gsoft.doganapt.cmd.registri.RegistraMovimento;
import com.gsoft.doganapt.cmd.registri.SistemaNumerazione;
import com.gsoft.doganapt.cmd.registri.Stampa;
import com.gsoft.doganapt.cmd.registri.ViewDaRegistrare;
import com.gsoft.doganapt.cmd.registri.ViewRegistro;
import com.gsoft.framework.TooledServlet;

public class RegistriServlet extends TooledServlet {

	public RegistriServlet() { 
		super(); 
		
		addCommand( Commands.DEFAULT ,
				new ViewRegistro( this ) ) ;
		
		addCommand( Commands.DAREGISTRARE ,
				new ViewDaRegistrare( this ) ) ;
		
		addCommand( Commands.ASSEGNAREG ,
				new RegistraMovimento( this ) ) ;
		
		addCommand( Commands.DEREGISTRA ,
				new DeregistraMovimento( this ) ) ;
		
		addCommand( Commands.STAMPA,
				new Stampa( this ) ) ;
		
		addCommand( Commands.PREPARA,
				new PreparaRegistro( this ) ) ;
		
		addCommand( Commands.CHECK,
				new SistemaNumerazione( this ) ) ;
		
	}
	
	protected Template handleRequest(HttpServletRequest request, HttpServletResponse response, Context ctx) throws Exception {

		HttpSession s =  request.getSession(false);
		Boolean logged = null ; 
		
		if ( s != null )
			logged = (Boolean) s.getAttribute("logged") ;
		
		if ( logged != Boolean.TRUE ) {
			response.sendRedirect(".main");
			response.flushBuffer() ;
			return null ;
		}
		return super.handleRequest(request, response, ctx) ;
	}
	
	
	public static interface Commands {
		public static final String DEFAULT = "" ;
		public static final String DAREGISTRARE = "dareg" ;
		public static final String STAMPA = "stampa" ;
		public static final String ASSEGNAREG = "assegnaReg" ;
		public static final String DEREGISTRA = "deregistra" ;
		public static final String PREPARA = "prepara" ;
		public static final String CHECK = "check" ;
	}
}
