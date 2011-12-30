package com.gsoft.doganapt.cmd.consegne;

import java.util.ArrayList;
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
import com.gtsoft.utils.common.UserException;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;


public class ChiudiPFPC extends VelocityCommand {

	protected static String TEMPLATE = "consegne/list.vm" ;

	public ChiudiPFPC ( GtServlet callerServlet) {
		super(callerServlet);
	}
	@Override
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

			for (Object object : stalli) {
				s = (Stallo) object;

				giacenza = s.getGiacenzaIva(false) ;

				if ( giacenza.intValue() != 0 ) {
					if ( giacenzaSecco != null )
						throw new UserException("Attenzione, giacenza presente in più di uno stallo!" );

					giacenzaSecco = s.getGiacenzaIva(true);
				}
			}

			if (  giacenza != null && giacenza.intValue() != 0 ) {
				Vector v = c.getRegistro(true, false, true );

				Movimento m = (Movimento) v.lastElement() ;

				if ( ! m.getIsScarico() ) {
					m = m.clone() ;
					m.setIsScarico(true);
					m.setUmido(0d);
					m.setSecco(0d);
				}

				m.setUmido( new Double( m.getUmido().doubleValue() + giacenza.doubleValue()  ) );

				if ( giacenzaSecco != null ) {
					m.setSecco( new Double( m.getSecco().doubleValue() + giacenzaSecco.doubleValue()  ) );
				}

				new MovimentoIvaAdapter().update(m);
				resp.sendRedirect(".consegne?id=" + c.getId());
			}
			else {
				resp.sendRedirect(".consegne?cmd=chiudi&exec=1&id=" + c.getId());
			}
			resp.flushBuffer();
		}
		return null ;
	}


	@Override
	public VelocityCommand clone() {
		return  new ChiudiPFPC(callerServlet);
	}

	@Override
	public String getTemplateName() {
		return TEMPLATE;
	}
}