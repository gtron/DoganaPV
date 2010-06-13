package com.gsoft.doganapt;

import com.gsoft.doganapt.cmd.Homepage;
import com.gsoft.doganapt.cmd.ImportMovimenti;
import com.gsoft.doganapt.cmd.Login;
import com.gsoft.doganapt.cmd.ViewStallo;
import com.gsoft.doganapt.cmd.consegne.ResetImmissioneLPStallo;
import com.gsoft.framework.TooledServlet;

public class MainServlet extends TooledServlet {

	public MainServlet() { 
		super(); 
		
		addCommand( Commands.DEFAULT ,
				new Login( this ) ) ;
		
		addCommand( Commands.HOMEPAGE ,
				new Homepage( this ) ) ;
		
		addCommand( Commands.DOIMPORT ,
				new ImportMovimenti( this ) ) ;
		addCommand( Commands.RESETILP ,
				new ResetImmissioneLPStallo( this ) ) ;
		addCommand( Commands.VIEWSTALLO ,
				new ViewStallo( this ) ) ;
		addCommand( Commands.LOCKMDB ,
				new LockMdb( this ) ) ;
	}

	public static interface Commands {
		public static final String DEFAULT = "" ;
		public static final String HOMEPAGE = "home" ;
		public static final String VIEWSTALLO = "viewstallo" ;
		public static final String RESETILP = "resetILP" ;
		public static final String DOIMPORT = "import" ;
		public static final String LOCKMDB = "lockMdb" ;
	}
}
