package com.gsoft.doganapt;

import com.gsoft.doganapt.cmd.consegne.ApriConsegna;
import com.gsoft.doganapt.cmd.consegne.ChiudiConsegna;
import com.gsoft.doganapt.cmd.consegne.ChiudiPFPC;
import com.gsoft.doganapt.cmd.consegne.EditConsegna;
import com.gsoft.doganapt.cmd.consegne.ImmettiLiberaPratica;
import com.gsoft.doganapt.cmd.consegne.ListConsegne;
import com.gsoft.doganapt.cmd.consegne.PopolaStalli;
import com.gsoft.doganapt.cmd.consegne.ViewConsegna;
import com.gsoft.doganapt.cmd.movimenti.EditGruppoMovimenti;
import com.gsoft.doganapt.cmd.movimenti.EditMovimento;
import com.gsoft.framework.TooledServlet;

public class ConsegneServlet extends TooledServlet {

	public ConsegneServlet() { 
		super(); 
		
		addCommand( Commands.DEFAULT ,
				new ViewConsegna( this ) ) ;
		
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
		
		addCommand(Commands.EDIT_MOVIMENTO_GROUP , 
				new EditGruppoMovimenti(this) );
		
		addCommand(Commands.IMMETTILP , 
				new ImmettiLiberaPratica(this) );
		
		addCommand(Commands.POPOLA_STALLI , 
				new PopolaStalli(this) );
		
	}

	public static interface Commands {
		public static final String DEFAULT = "" ;
		public static final String EDIT = "edit" ;
		public static final String EDIT_FATTURA = "editfattura" ;
		public static final String APRI = "apri" ;
		public static final String CHIUDI = "chiudi" ;
		public static final String CHIUDI_PFPC = "chiudiPFPC" ;
		public static final String LIST = "list" ;
		public static final String EDIT_MOVIMENTO = "editmovimento" ;
		public static final String EDIT_MOVIMENTO_GROUP = "editgroup" ;
		
		public static final String POPOLA_STALLI = "popola" ;
		public static final String IMMETTILP = "immettiLP" ;
	}
}
