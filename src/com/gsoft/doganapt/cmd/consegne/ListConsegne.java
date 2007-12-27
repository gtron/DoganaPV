package com.gsoft.doganapt.cmd.consegne;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.data.adapters.ConsegnaAdapter;
import com.gtsoft.utils.common.BeanAdapter2;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;


public class ListConsegne extends VelocityCommand {
	protected static String TEMPLATE = "consegne/list.vm";
	
	public ListConsegne ( GtServlet callerServlet) {
		super(callerServlet);
	}
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {
		
		String orderBy = getParam("o", false);
		if ( orderBy == null ) {
			orderBy = "";
		}
		
		boolean chiuse = getBooleanParam("chiuse") ;
		ctx.put("chiuse", chiuse ) ;
		
		if ( chiuse )
			ctx.put( "list" ,  new ConsegnaAdapter().getChiuse() ) ;
		else
			ctx.put( "list" ,  new ConsegnaAdapter().getNonChiuse() ) ;
		
		return null ;
	}
	

	public VelocityCommand clone() {
		return  new ListConsegne(this.callerServlet);
	}
	
	public BeanAdapter2 getAdapter() {
		return new ConsegnaAdapter();
	}

	public String getTemplateName() {
		return TEMPLATE;
	}
}