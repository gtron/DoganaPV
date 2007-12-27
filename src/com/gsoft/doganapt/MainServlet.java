package com.gsoft.doganapt;

import com.gsoft.doganapt.cmd.Homepage;
import com.gsoft.doganapt.cmd.ImportMovimenti;
import com.gsoft.doganapt.cmd.ViewStallo;
import com.gsoft.framework.TooledServlet;

public class MainServlet extends TooledServlet {

	public MainServlet() { 
		super(); 
		
		addCommand( Commands.DEFAULT ,
				new Homepage( this ) ) ;
		
		addCommand( Commands.DOIMPORT ,
				new ImportMovimenti( this ) ) ;
		addCommand( Commands.VIEWSTALLO ,
				new ViewStallo( this ) ) ;
		
	}

	public static interface Commands {
		public static final String DEFAULT = "" ;
		public static final String VIEWSTALLO = "viewstallo" ;
		public static final String DOIMPORT = "import" ;
	}
}
