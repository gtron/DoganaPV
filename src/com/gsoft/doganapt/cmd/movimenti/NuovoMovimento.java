package com.gsoft.doganapt.cmd.movimenti;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.data.Movimento;
import com.gsoft.doganapt.data.MovimentoIVA;
import com.gsoft.doganapt.data.adapters.ConsegnaAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoDoganaleAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoIvaAdapter;
import com.gtsoft.utils.common.BeanAdapter2;
import com.gtsoft.utils.common.FormattedDate;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;


public class NuovoMovimento extends VelocityCommand {

	protected static String TEMPLATE = "movimenti/new.vm";

	String template ;
	boolean isIva = false ;

	public NuovoMovimento ( GtServlet callerServlet) {
		super(callerServlet);
		template = TEMPLATE;
	}
	@Override
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {

		HttpSession sx = req.getSession(false) ;
		Boolean logged = null ;
		if ( sx != null ) {
			logged = (Boolean) sx.getAttribute("logged") ;
		}

		if ( logged != Boolean.TRUE ) {
			resp.sendRedirect(".main");
		}
		ctx.put("isAdmin", sx.getAttribute("admin") ) ;

		Template t = null ;
		isIva =  "1".equals( getParam("iva", false) ) ;
		ctx.put("isIva", isIva) ;

		Integer idConsegna = getIntParam("idC", true );

		MovimentoAdapter adp = (MovimentoAdapter) getAdapter() ;
		Movimento m = adp.newMovimento() ;
		m.setIdConsegna(idConsegna);

		m.setData(new FormattedDate());
		ctx.put("consegna" , ConsegnaAdapter.get(idConsegna) );
		ctx.put(ContextKeys.OBJECT , m)	;

		if ( getBooleanParam(Strings.EXEC) ) {
			adp.fillFromRequest(req, getBooleanParam(Strings.PARTIAL_EDIT)) ;


			double u = getDoubleParam("u0_"  , false ).doubleValue() ;
			double s = getDoubleParam("s0_"  , true ).doubleValue() ;

			double v0 = 0 ,v1  = 0 ;
			if ( isIva ) {
				v0 = getDoubleParam("v0_"  , true ).doubleValue() ;
				v1 = getDoubleParam("v1_"  , true ).doubleValue() ;
			}
			Object id = adp.create(null);

			m = (Movimento) adp.getByKey(id);


			u -= getDoubleParam("u1_" , true ).doubleValue();

			s -= getDoubleParam("s1_" , true ).doubleValue();

			if ( isIva ) {
				((MovimentoIVA) m).setValoreEuro(v0);
				((MovimentoIVA) m).setValoreDollari(v1);
			}

			m.setUmido(new Double(u));
			m.setSecco(new Double(s));


			adp.update(m);

			resp.sendRedirect(".consegne?id=" + idConsegna );

		}

		return t;
	}


	@Override
	public VelocityCommand clone() {
		return  new NuovoMovimento(callerServlet);
	}
	MovimentoAdapter adapter = null ;
	public BeanAdapter2 getAdapter(  )  {
		if ( adapter == null ) {
			if ( isIva ) {
				adapter = new MovimentoIvaAdapter();
			} else {
				adapter = new MovimentoDoganaleAdapter();
			}
		}

		return adapter;
	}
	@Override
	public String getTemplateName() {
		return template;
	}
}