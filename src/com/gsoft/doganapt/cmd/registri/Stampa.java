package com.gsoft.doganapt.cmd.registri;

import java.util.Vector;

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


public class Stampa extends VelocityCommand {
	
	private final static Integer ROWS_PER_PAGE = new Integer(20);
	
	public Stampa ( GtServlet callerServlet) {
		super(callerServlet);
	}
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {

		ctx.put( "isIva", getBooleanParam("iva", false) );
		ctx.put( "righePerPagina", ROWS_PER_PAGE );
		
		getAdapter();

		Integer num = getIntParam("n", false);
		if ( num == null )
			num = ROWS_PER_PAGE ;
		else
			num = new Integer( ROWS_PER_PAGE.intValue() * num.intValue() ) ;
		
		Integer from = getIntParam("f", false);
		
		if ( getBooleanParam("confirm") ) {
			Integer to = getIntParam("t", true);
			adapter.confermaStampa(from, to);
		}
		else  {
			Vector list = adapter.getDaStampare(from, num) ;
	
			ctx.put("list", list );
		}
		
		return null;
	}


	public VelocityCommand clone() {
		return  new Stampa(this.callerServlet);
	}
	MovimentoAdapter adapter = null ;
	
	public BeanAdapter2 getAdapter() throws Exception  {
		if ( adapter == null ) {
			if ( getBooleanParam("iva", false) ) 
				adapter = new MovimentoIvaAdapter();
			else 
				adapter = new MovimentoDoganaleAdapter();
		}
		
		return adapter;
	}
	public String getTemplateName() {
		return "registri/print.vm" ;
	}
}