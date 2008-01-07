package com.gsoft.doganapt.cmd.registri;

import java.util.ArrayList;

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


public class PreparaRegistro extends VelocityCommand {
	
	private final static Integer ROWS_PER_PAGE = new Integer(20);
	
	public PreparaRegistro ( GtServlet callerServlet) {
		super(callerServlet);
	}
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {

		Integer from = getIntParam("f", false);
		Integer to = getIntParam("t", true);
		
		int num = to.intValue() -  from.intValue();
		
		ArrayList<Integer> list = new ArrayList<Integer>(num) ;
		int t = to.intValue();
		
		for ( int i = from.intValue() ; i <= t  ; i++  ) {
		
			list.add( new Integer ( i )) ;
			
		}
		ctx.put("list", list );
		
		return null;
	}


	public VelocityCommand clone() {
		return  new PreparaRegistro(this.callerServlet);
	}
	MovimentoAdapter adapter = null ;

	public String getTemplateName() {
		return "registri/prepara.vm" ;
	}
}