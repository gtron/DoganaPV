package com.gsoft.doganapt.cmd.registri;

import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.data.Consegna;
import com.gsoft.doganapt.data.Movimento;
import com.gsoft.doganapt.data.adapters.MovimentoAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoDoganaleAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoIvaAdapter;
import com.gtsoft.utils.common.BeanAdapter2;
import com.gtsoft.utils.common.UserException;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;


public class DeregistraMovimento extends VelocityCommand {
		
	public DeregistraMovimento ( GtServlet callerServlet) {
		super(callerServlet);
	}
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {
		
		if ( getBooleanParam(Strings.EXEC) ) {
			
			getAdapter();
			
			Integer num = getIntParam("num", true);
			
			Vector list = adapter.getByNumeroRegistro(num) ;
			
			Movimento m = null ;
			Consegna c = null ;
			
			for ( Iterator i = list.iterator() ; i.hasNext(); ) {
				
				m = (Movimento) i.next() ;
				c = m.getConsegna();
				
				if ( ! m.getIsLocked() ) {
					
					adapter.unregister( m.getNumRegistro()) ;

					// m.getStallo().notifyRegistrazione(m, ! i.hasNext() );
					
				}
				else {
					throw new UserException("Il movimento con ID:" + m.getId() + 
							" è già stato stampato !  Pertanto non si può de-registrare ") ;
				}
	
			}
			
			if ( "1".equals( getParam("iva", false) ) ) {
				response.sendRedirect(".registri?iva=true" ) ;
			}
			else 
				response.sendRedirect(".registri" ) ;
			
		}
		
		return null;
	}


	public VelocityCommand clone() {
		return  new DeregistraMovimento(this.callerServlet);
	}
	MovimentoAdapter adapter = null ;
	
	public BeanAdapter2 getAdapter() throws Exception  {
		if ( adapter == null ) {
			if ( "1".equals( getParam("iva", false) ) )
				adapter = new MovimentoIvaAdapter();
			else 
				adapter = new MovimentoDoganaleAdapter();
		}
		
		return adapter;
	}
	public String getTemplateName() {
		return "consegne/view.vm" ;
	}
	
	static synchronized private Integer registraNuovoNum( Movimento m , MovimentoAdapter adapter ) throws Exception {
		Integer newNum  ;
		try {
			newNum = adapter.getNextNumRegistro();
			m.setNumRegistro( new Long(newNum ) );
			adapter.update(m);
		}
		catch (Exception e ) {
			throw e ;
		}
		finally {
			adapter.doneNextNumRegistro();
		}
		return newNum ;
	}
}