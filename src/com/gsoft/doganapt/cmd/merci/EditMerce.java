package com.gsoft.doganapt.cmd.merci;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.data.adapters.MerceAdapter;
import com.gtsoft.utils.common.BeanAdapter2;
import com.gtsoft.utils.common.BeanEditor;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;


public class EditMerce extends BeanEditor {
	
	protected static String TEMPLATE = "merci/" + DEFAULT_TEMPLATE;
	
	String template ;
	
	public EditMerce ( GtServlet callerServlet) {
		super(callerServlet);
		template = TEMPLATE;
	}
	
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {
		
		Template t = super.exec(req,resp,ctx) ;
		
		if ( getBooleanParam("exec" ))
			ctx.put("result", Boolean.TRUE) ;
		
		return t;
	}
	public VelocityCommand clone() {
		return  new EditMerce(this.callerServlet);
	}
	MerceAdapter adapter = null ;
	public BeanAdapter2 getAdapter(  )  {
		if ( adapter == null ) {
			adapter = new MerceAdapter();
		}
		
		return adapter;
	}
	public String getTemplateName() {
		return template;
	}
}