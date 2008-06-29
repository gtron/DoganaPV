package com.gsoft.doganapt.cmd.registri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.data.adapters.MovimentoAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoDoganaleAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoIvaAdapter;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;


public class SistemaNumerazione extends VelocityCommand {
		
	public SistemaNumerazione ( GtServlet callerServlet) {
		super(callerServlet);
	}
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {
		
		ctx.put(
				ContextKeys.RESULT, 
				getAdapter().checkIntegrity( getBooleanParam(Strings.EXEC) )
				);
		
		return null ;
	}

	public VelocityCommand clone() {
		return  new SistemaNumerazione(this.callerServlet);
	}
	
	public MovimentoAdapter getAdapter() throws Exception  {
		MovimentoAdapter adapter = null ;
		
//		if ( adapter == null ) {
			if ( "1".equals( getParam("iva", false) ) )
				adapter = new MovimentoIvaAdapter();
			else 
				adapter = new MovimentoDoganaleAdapter();
//		}
		
		return adapter;
	}
	public String getTemplateName() {
		return DefaultTemplates.VOID ;
	}
}