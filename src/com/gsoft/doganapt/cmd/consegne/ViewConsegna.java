package com.gsoft.doganapt.cmd.consegne;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.data.adapters.ConsegnaAdapter;
import com.gsoft.doganapt.data.adapters.StalloAdapter;
import com.gtsoft.utils.common.BeanAdapter2;
import com.gtsoft.utils.common.FormattedDate;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;


public class ViewConsegna extends VelocityCommand {

	protected static String TEMPLATE = "consegne/view.vm";
	public static String ID = "id";

	public ViewConsegna ( GtServlet callerServlet) {
		super(callerServlet);
	}
	@Override
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {

		Integer id = getIntParam(ID, true);

		ctx.put(ContextKeys.OBJECT, getAdapter().getByKey(id) ) ;

		ctx.put("stalli", StalloAdapter.getAllCached());
		ctx.put("now", new FormattedDate().dmyString() );

		return null ;
	}

	@Override
	public VelocityCommand clone() {
		return  new ViewConsegna(callerServlet);
	}

	public BeanAdapter2 getAdapter() {
		return new ConsegnaAdapter();
	}

	@Override
	public String getTemplateName() {
		return TEMPLATE;
	}
}