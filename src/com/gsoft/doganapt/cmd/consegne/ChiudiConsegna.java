package com.gsoft.doganapt.cmd.consegne;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.data.Consegna;
import com.gsoft.doganapt.data.Stallo;
import com.gsoft.doganapt.data.adapters.ConsegnaAdapter;
import com.gsoft.doganapt.data.adapters.StalloAdapter;
import com.gtsoft.utils.common.FormattedDate;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;


public class ChiudiConsegna extends VelocityCommand {
	
	protected static String TEMPLATE = "consegne/list.vm" ;
	
	public ChiudiConsegna ( GtServlet callerServlet) {
		super(callerServlet);
	}
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {
		
		Integer id = getIntParam("id", true);
		
		ConsegnaAdapter adp = Consegna.newAdapter();
		Consegna c = (Consegna) adp.getByKey(id);
		
		ctx.put( ContextKeys.OBJECT , c ) ;
		
		if ( c != null && getBooleanParam(Strings.EXEC) ) {
			
			c.setDataChiusura(new FormattedDate()) ;
			Stallo s ;
			StalloAdapter sadp = new StalloAdapter();
			
			for ( Iterator i = c.getStalli().iterator() ; i.hasNext() ; ){
				s = ( Stallo ) i.next() ;
				s.setIdConsegnaAttuale(null);
				s.setImmessoInLiberaPratica(Boolean.FALSE);
				sadp.update(s);
				
			}
			
			adp.update(c);
			
			ctx.put("result", Boolean.TRUE ) ;
			
			ctx.put( "list" ,  new ConsegnaAdapter().getAll( ) ) ;

		}
		return null ;
	}
	

	public VelocityCommand clone() {
		return  new ChiudiConsegna(this.callerServlet);
	}
	
	public String getTemplateName() {
		return TEMPLATE;
	}
}