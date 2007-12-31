package com.gsoft.doganapt.cmd.merci;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.data.adapters.ConsegnaAdapter;
import com.gsoft.doganapt.data.adapters.MerceAdapter;
import com.gtsoft.utils.common.BeanAdapter2;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;


public class ListMerci extends VelocityCommand {
	protected static String TEMPLATE = "merci/list.vm";
	
	
	public ListMerci ( GtServlet callerServlet) {
		super(callerServlet);
	}
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {
		
		
		ctx.put( "merci" , MerceAdapter.getAllCached()) ;
		
		return null ;
	}
	

	public VelocityCommand clone() {
		return  new ListMerci(this.callerServlet);
	}
	
	public BeanAdapter2 getAdapter() {
		return new ConsegnaAdapter();
	}

	public String getTemplateName() {
		return TEMPLATE;
	}
}