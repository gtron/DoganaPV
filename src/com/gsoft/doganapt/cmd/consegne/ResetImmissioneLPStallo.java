package com.gsoft.doganapt.cmd.consegne;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.data.Stallo;
import com.gsoft.doganapt.data.adapters.StalloAdapter;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;


public class ResetImmissioneLPStallo extends VelocityCommand {
		
	public ResetImmissioneLPStallo ( GtServlet callerServlet) {
		super(callerServlet);
	}
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {
		
		if ( getBooleanParam(Strings.EXEC) ) {
			
			Integer id = getIntParam("id", true);
			StalloAdapter adp = new StalloAdapter() ;
			
			Stallo s = (Stallo) adp.getByKey(id);
			
			s.setImmessoInLiberaPratica(false);
			
			if ( getBooleanParam("exec" ))
				adp.update(s);
			
			response.sendRedirect(".consegne?cmd=edit&id=" + getIntParam("idC", true));
			response.flushBuffer();
			
		}
		
		return null;
	}


	public VelocityCommand clone() {
		return  new ResetImmissioneLPStallo(this.callerServlet);
	}

	public String getTemplateName() {
		return "consegne/view.vm" ;
	}

}