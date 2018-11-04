package com.gsoft.doganapt.cmd;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.pt_movimentazioni.utils.PtMovimentazioniImporter;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;

public class LockMdb extends VelocityCommand {

	public LockMdb ( GtServlet callerServlet) {
		super(callerServlet);
	}

	@Override
	public VelocityCommand clone() {
		return  new LockMdb(this.callerServlet);
	}

	@Override
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {

		boolean lock = getBooleanParam("lock") ;
		PtMovimentazioniImporter.setLocked( lock );

		String unlockResult = "";
		
		if ( ! lock ) {
			unlockResult = "Import Unlocked! ";
			
			ArrayList<String> movimentiSenzaData = PtMovimentazioniImporter.getMovimentiSenzaData(true);
			
			if ( PtMovimentazioniImporter.hasMovimentiSenzaData() ) {
				int numMov = movimentiSenzaData.size();
				unlockResult += " Attenzione: Nel database si sono movimenti senza data! ( " + numMov + " ) IDs: "  ;
				for ( String s : movimentiSenzaData ) {
					if ( numMov > 1 ) 
						unlockResult += "," ;
					unlockResult += s ;
				}
			} else {
				unlockResult += " DataCheck: OK";
			}
		}
		ctx.put(ContextKeys.RESULT, lock ? "Locked:"+ lock + " - DB End. Import Locked!"  : lock + " - " + unlockResult);
		return null;
	}

	@Override
	public String getTemplateName() {
		return "void.vm" ;
	}


}
