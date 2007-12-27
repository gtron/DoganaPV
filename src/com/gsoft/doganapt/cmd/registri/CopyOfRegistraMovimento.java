package com.gsoft.doganapt.cmd.registri;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.ConsegneServlet;
import com.gsoft.doganapt.data.Movimento;
import com.gsoft.doganapt.data.adapters.MovimentoAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoDoganaleAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoIvaAdapter;
import com.gtsoft.utils.common.BeanAdapter2;
import com.gtsoft.utils.common.UserException;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;


public class CopyOfRegistraMovimento extends VelocityCommand {
		
	public CopyOfRegistraMovimento ( GtServlet callerServlet) {
		super(callerServlet);
	}
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {
		
		if ( getBooleanParam(Strings.EXEC) ) {
			
			getAdapter();
			
			try {
				ArrayList<Integer> list = getIntParams("list", 1);

				
				Integer newNum = adapter.getNextNumRegistro();
				
				Movimento m = null ;
	
				boolean canDoRettifica = true ;
				boolean canDoMovimento = true ;
				
				for ( Iterator<Integer> i = list.iterator() ; i.hasNext(); ) {
					
					m = (Movimento) adapter.getByKey(i.next()) ;
					
					if ( m.getNumRegistro() == null) {
					
						if ( canDoRettifica && m.getIsRettifica() ) {
							canDoMovimento = false ;
						}
						else if ( canDoMovimento && ! m.getIsRettifica() ) {
							canDoRettifica = false ;
						}
						else {
							continue ;
						}
						
						m.setNumRegistro( new Long(newNum ) );
						
						adapter.update(m);
						
						if ( m.isAppenaRegistrato() )
							m.getStallo().notifyRegistrazione(m, ! i.hasNext() );
					}
					else {
						throw new UserException("Il movimento con ID:" + m.getId() + 
								" è già stato registrato sul registro IVA ! ") ;
					}
		
				}
			}
			catch( Exception e ) {
				throw e ;
			}
			finally {
				adapter.doneNextNumRegistro();
			}
			
		}
		

		HashMap<String,Object> newParams = new HashMap<String,Object>(1);
		String[] id = { getIntParam("c", true).toString() } ;
		newParams.put( "id", id );
		setNextCommand(
				callerServlet.getCommand(ConsegneServlet.Commands.DEFAULT, request, response) , newParams);
		
		return null;
	}


	public VelocityCommand clone() {
		return  new CopyOfRegistraMovimento(this.callerServlet);
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
		return null ;
	}
}