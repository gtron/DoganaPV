package com.gsoft.doganapt.cmd.movimenti;

import java.util.Collection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.ConsegneServlet;
import com.gsoft.doganapt.data.Movimento;
import com.gsoft.doganapt.data.adapters.IterAdapter;
import com.gsoft.doganapt.data.adapters.MerceAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoDoganaleAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoIvaAdapter;
import com.gsoft.doganapt.data.adapters.StalloAdapter;
import com.gtsoft.utils.common.BeanAdapter2;
import com.gtsoft.utils.common.BeanEditor;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;


public class EditMovimento extends BeanEditor {

	protected static String TEMPLATE = "movimenti/" + DEFAULT_TEMPLATE;

	String template ;
	boolean isIva = false ;

	public EditMovimento ( final GtServlet callerServlet) {
		super(callerServlet);
		template = TEMPLATE;
	}
	@Override
	public Template exec(final HttpServletRequest req, final HttpServletResponse resp, final Context ctx) throws Exception  {

		isIva =  "1".equals( getParam("iva", false) ) ;

		ctx.put("isIva", isIva) ;

		final Collection<?> stalli = StalloAdapter.getAllCached();
		ctx.put("merci" ,  MerceAdapter.getAllCached());
		ctx.put("iters" ,  IterAdapter.getAllCached());
		ctx.put("stalli" ,  stalli);

		final Template t = super.exec(req, resp, ctx) ;

		final Movimento m = (Movimento) ctx.get(ContextKeys.OBJECT);

		m.getConsegna().flushRegistri();

		if ( getBooleanParam(Strings.EXEC) ) {

			final HashMap<String, String[]> newParams = new HashMap<String, String[]>(1);
			final String[] id = {m.getIdConsegna().toString()} ;
			newParams.put( "id", id );
			setNextCommand(
					callerServlet.getCommand(ConsegneServlet.Commands.DEFAULT, request, response) , newParams);
		}

		return t;
	}


	@Override
	public VelocityCommand clone() {
		return  new EditMovimento(callerServlet);
	}
	MovimentoAdapter adapter = null ;
	@Override
	public BeanAdapter2 getAdapter(  )  {
		if ( adapter == null ) {
			if ( isIva ) {
				adapter = new MovimentoIvaAdapter();
			} else {
				adapter = new MovimentoDoganaleAdapter();
			}
		}

		return adapter;
	}
	@Override
	public String getTemplateName() {
		return template;
	}
}