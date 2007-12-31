package com.gsoft.doganapt.cmd.merci;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.pt_movimentazioni.utils.PtMerciImporter;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;

public class ImportMerci extends VelocityCommand {
	
	public ImportMerci ( GtServlet callerServlet) {
		super(callerServlet);
	}
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {
		
		PtMerciImporter.getInstance().doImport();
		
		return null ;
	}
	

	public VelocityCommand clone() {
		return  new ImportMerci(this.callerServlet);
	}
	
	public VelocityCommand nexVelocityCommand() {
		return  new ListMerci(this.callerServlet);
	}
	
	public String getTemplateName() {
		return null;
	}
}