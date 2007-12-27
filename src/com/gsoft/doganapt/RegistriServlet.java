package com.gsoft.doganapt;

import com.gsoft.doganapt.cmd.registri.DeregistraMovimento;
import com.gsoft.doganapt.cmd.registri.RegistraMovimento;
import com.gsoft.doganapt.cmd.registri.Stampa;
import com.gsoft.doganapt.cmd.registri.ViewRegistro;
import com.gsoft.framework.TooledServlet;

public class RegistriServlet extends TooledServlet {

	public RegistriServlet() { 
		super(); 
		
		addCommand( Commands.DEFAULT ,
				new ViewRegistro( this ) ) ;
		
		addCommand( Commands.ASSEGNAREG ,
				new RegistraMovimento( this ) ) ;
		
		addCommand( Commands.DEREGISTRA ,
				new DeregistraMovimento( this ) ) ;
		
		addCommand( Commands.STAMPA,
				new Stampa( this ) ) ;
		
	}
	
	
	public static interface Commands {
		public static final String DEFAULT = "" ;
		public static final String STAMPA = "stampa" ;
		public static final String ASSEGNAREG = "assegnaReg" ;
		public static final String DEREGISTRA = "deregistra" ;
	}
}
