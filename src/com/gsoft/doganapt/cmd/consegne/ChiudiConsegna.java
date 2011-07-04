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
		
		Integer idStallo = getIntParam("idS", false);
		
		ConsegnaAdapter adp = Consegna.newAdapter();
		Consegna c = (Consegna) adp.getByKey(id);
		
		ctx.put( ContextKeys.OBJECT , c ) ;
		
		if ( c != null && getBooleanParam(Strings.EXEC) ) {
			
			c.setDataChiusura(new FormattedDate()) ;
			
			if ( idStallo != null ) {
				liberaStalli( c, idStallo ) ;
				
				// return success(Results.STALLO);
				ctx.put(ContextKeys.RESULT , Boolean.TRUE ) ;
				ctx.put( ContextKeys.MESSAGE , "Stallo liberato correttamente") ;
				return null ;
			}

			liberaStalli( c, idStallo ) ;

			adp.update(c);
			
			// return success(Results.CONSEGNA);
			
			ctx.put(ContextKeys.RESULT , Boolean.TRUE ) ;
			
			ctx.put( "list" ,  new ConsegnaAdapter().getAll( ) ) ;
			
			ctx.put( ContextKeys.MESSAGE , "Consegna chiusa correttamente") ;
		}
		return null ;
	}
	
	private void liberaStalli(Consegna c, Integer idStallo) throws Exception {
		Stallo s ;
		StalloAdapter sadp = new StalloAdapter();
		
		for ( Iterator<?> i = c.getStalli().iterator() ; i.hasNext() ; ){
			s = ( Stallo ) i.next() ;
			
			if ( idStallo == null || idStallo.equals(s.getId()) ) {
				s.setIdConsegnaAttuale(null);
				s.setImmessoInLiberaPratica(Boolean.FALSE);
				sadp.update(s);
			}
		}
	}
	public VelocityCommand clone() {
		return  new ChiudiConsegna(this.callerServlet);
	}
	
	public String getTemplateName() {
		return TEMPLATE;
	}
}