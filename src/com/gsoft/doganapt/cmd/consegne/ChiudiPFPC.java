package com.gsoft.doganapt.cmd.consegne;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.data.Consegna;
import com.gsoft.doganapt.data.Movimento;
import com.gsoft.doganapt.data.Stallo;
import com.gsoft.doganapt.data.adapters.ConsegnaAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoIvaAdapter;
import com.gsoft.doganapt.data.adapters.StalloAdapter;
import com.gtsoft.utils.common.FormattedDate;
import com.gtsoft.utils.common.UserException;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;


public class ChiudiPFPC extends VelocityCommand {
	
	protected static String TEMPLATE = "consegne/list.vm" ;
	
	public ChiudiPFPC ( GtServlet callerServlet) {
		super(callerServlet);
	}
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {
		
		Integer id = getIntParam("id", true);
		
		ConsegnaAdapter adp = Consegna.newAdapter();
		Consegna c = (Consegna) adp.getByKey(id);
		
		ctx.put( ContextKeys.OBJECT , c ) ;
		
		if ( c != null && getBooleanParam(Strings.EXEC) ) {
			
			
			
			
			ArrayList<Object> stalli = c.getStalli();
			Stallo s = null ;
			Double giacenza = null ;
			Double giacenzaSecco = null ;
			
			for ( Iterator<Object> i = stalli.iterator() ; i.hasNext() ; ) {
				s = (Stallo) i.next();
				
				giacenza = s.getGiacenzaIva(false) ;
				
				if ( giacenza > 0 ) 
					if ( giacenzaSecco != null ) 
						throw new UserException("Attenzione, giacenza presente in più di uno stallo!" );
					else 
						giacenzaSecco = s.getGiacenzaIva(true);
			}
			
			
			Vector v = c.getRegistro(true, false, true );
			
			Movimento m = (Movimento) v.lastElement() ;
			
			if ( giacenza != null ) {
				m.setUmido( new Double( m.getUmido().doubleValue() + giacenza.doubleValue()  ) );
				m.setSecco( new Double( m.getSecco().doubleValue() + giacenzaSecco.doubleValue()  ) );
			}
			
			new MovimentoIvaAdapter().update(m);
			
			if ( c != null )
				resp.sendRedirect(".consegne?id=" + c.getId());
		}
		return null ;
	}
	

	public VelocityCommand clone() {
		return  new ChiudiPFPC(this.callerServlet);
	}
	
	public String getTemplateName() {
		return TEMPLATE;
	}
}