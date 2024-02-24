package com.gsoft.doganapt.cmd.movimenti;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.cmd.Homepage;
import com.gsoft.doganapt.data.Consegna;
import com.gsoft.doganapt.data.Movimento;
import com.gsoft.doganapt.data.MovimentoIVA;
import com.gsoft.doganapt.data.StalloConsegna;
import com.gsoft.doganapt.data.adapters.ConsegnaAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoDoganaleAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoIvaAdapter;
import com.gsoft.doganapt.data.adapters.StalloConsegnaAdapter;
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

		try {
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

			Consegna consegna = ConsegnaAdapter.get(idConsegna);

			ctx.put("consegna" , consegna );



			Boolean canRicalcoloSC = Boolean.FALSE;

			if ( isIva ) {
				FormattedDate d = adp.getLastDate(consegna, null, true);

				if( d == null ) {
					canRicalcoloSC = Boolean.TRUE;
				}

				ctx.put("canRicalcoloSC", canRicalcoloSC );
			}


			if ( getBooleanParam(Strings.EXEC) ) {

				getParam("posdoganale", true);
				getParam("codprovenienza", true);

				adp.fillFromRequest(req, getBooleanParam(Strings.PARTIAL_EDIT)) ;


				double u = getDoubleParam("u0_", true ).doubleValue() ;
				u -= getDoubleParam("u1_", true ).doubleValue();

				double s = getDoubleParam("s0_" , true ).doubleValue() ;
				s -= getDoubleParam("s1_" , true ).doubleValue();

				double v0 = 0 ,v1  = 0, t0  = 0,iva0  = 0 ;
				if ( isIva ) {
					v0 = getDoubleParam("v0_"  , true ).doubleValue() ;
					v1 = getDoubleParam("v1_"  , true ).doubleValue() ;
					t0 = getDoubleParam("t0_"  , true ).doubleValue() ;

					iva0 = getDoubleParam("iva0_"  , true ).doubleValue() ;

				}

				Object id = adp.create(null);

				m = (Movimento) adp.getByKey(id);

				m.setIsRettifica(getBooleanParam("isrettifica"));

				if ( getBooleanParam("isrettifica") ) {
					m.setIsRettifica(true);
				}

				if ( isIva ) {
					MovimentoIVA miva = ((MovimentoIVA) m);

					miva.setValoreNetto(v0)  ;
					miva.setValoreTestp(t0)  ;
					miva.setValoreIva(iva0)  ;

					miva.setValoreEuro(miva.getValoreNetto() + miva.getValoreTestp());
					miva.setValoreDollari( v1 )  ;

				}


				m.setUmido(Double.valueOf(u));
				m.setSecco(Double.valueOf(s));

				adp.update(m);


				if ( isIva && canRicalcoloSC && getBooleanParam("doRicalcoloSC") && m.getIsRettifica() ) {
					StalloConsegnaAdapter stalloConsegnaAdp = StalloConsegna.newAdapter();
					StalloConsegna sc = stalloConsegnaAdp .getByKeysIds( m.getIdStallo(), idConsegna);

					MovimentoIVA miva = ((MovimentoIVA) m);

					Movimento mGiacenza = adp.getMovimentoGiacenza(m.getStallo(), consegna);
					if ( sc != null ) {
						double sommaSecco = mGiacenza.getSecco().doubleValue();

						double valoreDollari = sc.getValoreDollari().doubleValue();
						double valoreEuro = sc.getValoreEuro().doubleValue();
						double valoreTestTp = sc.getValoreTesTp().doubleValue();

						if ( m.isScaricoONegativo() ) {
							valoreDollari -= miva.getValoreDollari();
							valoreEuro -= miva.getValoreEuro();
							valoreTestTp -= miva.getValoreTestp();
						} else {
							valoreDollari += miva.getValoreDollari();
							valoreEuro += miva.getValoreEuro();
							valoreTestTp += miva.getValoreTestp();
						}

						sc.setValoreDollari( valoreDollari);
						sc.setValoreTesTp( valoreTestTp);
						sc.setValoreEuro( valoreEuro );

						sc.setValoreUnitarioDollari( valoreDollari / sommaSecco);
						sc.setValoreUnitarioTesTp( valoreTestTp / sommaSecco);
						sc.setValoreUnitarioEuro( valoreEuro / sommaSecco );

						stalloConsegnaAdp.update(sc);

						Homepage.purgeCaches();
					}
				}

				resp.sendRedirect(".consegne?id=" + idConsegna );

			}

			ctx.put(ContextKeys.OBJECT , m);

			return t;
		} catch ( final Exception e) {
			ctx.put("err" ,  e );
		}

		return null;

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