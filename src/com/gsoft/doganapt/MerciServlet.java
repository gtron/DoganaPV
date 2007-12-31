package com.gsoft.doganapt;

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
}
