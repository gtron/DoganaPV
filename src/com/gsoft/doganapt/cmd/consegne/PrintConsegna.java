package com.gsoft.doganapt.cmd.consegne;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.data.adapters.ConsegnaAdapter;
import com.gtsoft.utils.common.BeanAdapter2;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;


public class PrintConsegna extends VelocityCommand {
	
	protected static String TEMPLATE = "consegne/print.vm";
	public static String ID = "id";
	
	public PrintConsegna ( GtServlet callerServlet) {
		super(callerServlet);
	}
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {
		
		Integer id = getIntParam(ID, true);

		ctx.put(ContextKeys.OBJECT, getAdapter().getByKey(id) ) ;

		return null ;
	}

	public VelocityCommand clone() {
		return  new PrintConsegna(this.callerServlet);
	}
	
	public BeanAdapter2 getAdapter() {
		return new ConsegnaAdapter();
	}

	public String getTemplateName() {
		return TEMPLATE;
	}
}