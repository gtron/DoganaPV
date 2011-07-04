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
	
	public EditMovimento ( GtServlet callerServlet) {
		super(callerServlet);
		template = TEMPLATE;
	}
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {
		
		isIva =  "1".equals( getParam("iva", false) ) ;
		
		ctx.put("isIva", isIva) ;
		
		Collection<?> stalli = StalloAdapter.getAllCached();
		ctx.put("merci" ,  MerceAdapter.getAllCached());
		ctx.put("iters" ,  IterAdapter.getAllCached());
		ctx.put("stalli" ,  stalli);
		
		Template t = super.exec(req, resp, ctx) ; 
		
		Movimento m = (Movimento) ctx.get(ContextKeys.OBJECT);
		
		
		if ( getBooleanParam(Strings.EXEC) ) {

			HashMap<String, String[]> newParams = new HashMap<String, String[]>(1);
			String[] id = {m.getIdConsegna().toString()} ;
			newParams.put( "id", id );
			setNextCommand(
					callerServlet.getCommand(ConsegneServlet.Commands.DEFAULT, request, response) , newParams);
		}
		
		return t;
	}


	public VelocityCommand clone() {
		return  new EditMovimento(this.callerServlet);
	}
	MovimentoAdapter adapter = null ;
	public BeanAdapter2 getAdapter(  )  {
		if ( adapter == null ) {
			if ( isIva )
				adapter = new MovimentoIvaAdapter();
			else 
				adapter = new MovimentoDoganaleAdapter();
		}
		
		return adapter;
	}
	public String getTemplateName() {
		return template;
	}
}