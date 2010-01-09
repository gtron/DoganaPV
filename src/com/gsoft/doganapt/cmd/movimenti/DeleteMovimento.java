package com.gsoft.doganapt.cmd.movimenti;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.data.adapters.MovimentoAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoDoganaleAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoIvaAdapter;
import com.gtsoft.utils.common.BeanAdapter2;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;


public class DeleteMovimento extends VelocityCommand {
		
	public DeleteMovimento ( GtServlet callerServlet) {
		super(callerServlet);
	}
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {
		
		if ( getBooleanParam(Strings.EXEC) ) {
			
			getAdapter();
			
			Integer id = getIntParam("id", true);
			
			if ( getBooleanParam("exec" ))
				adapter.setDeleted(id) ;
			
			response.sendRedirect(".consegne?id=" + getIntParam("idC", true));
			
		}
		
		return null;
	}


	public VelocityCommand clone() {
		return  new DeleteMovimento(this.callerServlet);
	}
	MovimentoAdapter adapter = null ;
	
	public BeanAdapter2 getAdapter() throws Exception  {
		if ( adapter == null ) {
			if ( "1".equals( getParam("iva", false) ) )
				adapter = new MovimentoIvaAdapter();
			else 
				adapter = new MovimentoDoganaleAdapter();
		}
		
		return adapter;
	}
	public String getTemplateName() {
		return "consegne/view.vm" ;
	}

}