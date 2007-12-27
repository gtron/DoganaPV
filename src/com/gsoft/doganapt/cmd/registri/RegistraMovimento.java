package com.gsoft.doganapt.cmd.registri;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.data.Iter;
import com.gsoft.doganapt.data.Movimento;
import com.gsoft.doganapt.data.adapters.ConsegnaAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoDoganaleAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoIvaAdapter;
import com.gtsoft.utils.common.BeanAdapter2;
import com.gtsoft.utils.common.FormattedDate;
import com.gtsoft.utils.common.UserException;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;


public class RegistraMovimento extends VelocityCommand {
		
	public RegistraMovimento ( GtServlet callerServlet) {
		super(callerServlet);
	}
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {
		
		if ( getBooleanParam(Strings.EXEC) ) {
			
			Integer idConsegna = getIntParam("c", true);
			Iter iter = ConsegnaAdapter.get(idConsegna).getIter() ;
			
			getAdapter();
			
			if ( iter.getIsSingoliCarichi() ) {
				ArrayList<Integer> listId = getIntParams("list", 0);
				registraPerId(listId, idConsegna, iter);
			}
			else { 
				ArrayList<FormattedDate> listDate = getDateParams("list", 0);
				registraPerData(listDate, idConsegna, iter);
			}

			resp.sendRedirect(".consegne?id=" + idConsegna);
		}
		

		return null;
	}


	public VelocityCommand clone() {
		return  new RegistraMovimento(this.callerServlet);
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
	
	private void registraPerData( ArrayList<FormattedDate> list , Integer idConsegna, Iter iter ) throws Exception {
		
		Integer newNum = null ;
		Movimento m = null ;
		Vector movimenti = null ;
		
		FormattedDate oldData = null ;
		Boolean oldScarico = null;
		Boolean oldRettifica = null ;
		
		for ( Iterator<FormattedDate> i = list.iterator() ; i.hasNext(); ) {
			
			movimenti = adapter.getDaRegistrare(idConsegna, i.next() ) ;
			
			
			if ( movimenti != null ) {
				for ( Iterator im = movimenti.iterator() ; im.hasNext(); ) {
					
					m = (Movimento) im.next();
					
					
					if ( oldData == null )
						newNum = registraNuovoNum(m, adapter);
					
					else if ( ! oldData.dmyString().equals( m.getData().dmyString() ) )
						newNum = registraNuovoNum(m, adapter);
					
					else if ( oldScarico.booleanValue() != m.getIsScarico().booleanValue() )
						newNum = registraNuovoNum(m, adapter);
					
					else if ( oldRettifica.booleanValue() != m.getIsRettifica().booleanValue() )
						newNum = registraNuovoNum(m, adapter);
							
						
					oldData = m.getData() ;
					oldScarico = m.getIsScarico();
					oldRettifica = m.getIsRettifica();
					
				
					m.setNumRegistro( new Long(newNum ) );
					adapter.update(m);
			
				
//					if ( m.isAppenaRegistrato() )
//						m.getStallo().notifyRegistrazione(m, ! i.hasNext() );
				}
			}
		}
	}
	
	private void registraPerId( ArrayList<Integer> list , Integer idConsegna, Iter iter ) throws Exception {
		
		Integer newNum = null ;
		
		Movimento m = null ;

		boolean canDoRettifica = true ;
		boolean canDoMovimento = true ;
		
		FormattedDate oldData = null ;
		
		for ( Iterator<Integer> i = list.iterator() ; i.hasNext(); ) {
			
			m = (Movimento) adapter.getByKey(i.next()) ;
			
			if ( m.getNumRegistro() == null) {
			
				if ( iter.getIsSingoliCarichi() || 
						oldData == null || 
						oldData.getTime() != m.getData().getTime() ) {
					newNum = registraNuovoNum(m, adapter);
					
					oldData = m.getData() ;
				}
				else {
					m.setNumRegistro( new Long(newNum ) );
					adapter.update(m);	
				}
			
				if ( canDoRettifica && m.getIsRettifica() ) {
					canDoMovimento = false ;
				}
				else if ( canDoMovimento && ! m.getIsRettifica() ) {
					canDoRettifica = false ;
				}
				else {
					continue ;
				}

				if ( m.isAppenaRegistrato() )
					m.getStallo().notifyRegistrazione(m, ! i.hasNext() );
				
			}
			else {
				throw new UserException("Il movimento con ID:" + m.getId() + 
						" è già stato registrato sul registro IVA ! ") ;
			}
		}
	}
}