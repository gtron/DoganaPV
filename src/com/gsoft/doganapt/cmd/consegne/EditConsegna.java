package com.gsoft.doganapt.cmd.consegne;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.cmd.Homepage;
import com.gsoft.doganapt.data.Consegna;
import com.gsoft.doganapt.data.Stallo;
import com.gsoft.doganapt.data.adapters.ConsegnaAdapter;
import com.gsoft.doganapt.data.adapters.IterAdapter;
import com.gsoft.doganapt.data.adapters.MerceAdapter;
import com.gsoft.doganapt.data.adapters.StalloAdapter;
import com.gtsoft.utils.common.BeanAdapter2;
import com.gtsoft.utils.common.BeanEditor;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.exception.ParameterException;
import com.gtsoft.utils.http.servlet.GtServlet;


public class EditConsegna extends BeanEditor {

	protected static String TEMPLATE = "consegne/" + DEFAULT_TEMPLATE;

	public EditConsegna ( GtServlet callerServlet) {
		super(callerServlet);
	}
	@Override
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {

		HttpSession s = req.getSession() ;
		if ( s != null ) {
			ctx.put("isAdmin", s.getAttribute("admin") ) ;
		}

		Collection stalli = StalloAdapter.getAllCached();
		ctx.put("merci" ,  MerceAdapter.getAllCached());
		ctx.put("iters" ,  IterAdapter.getAllCached());
		ctx.put("stalli" ,  stalli);

		//		if ( req.getParameter("pesofinaleportocarico")== null )
		//			req.getParameterMap().put("pesofinaleportocarico", "0");

		Template t = super.exec(req, resp, ctx) ;


		Consegna c = (Consegna) ctx.get( ContextKeys.OBJECT );

		if ( c != null ) {
			if ( getBooleanParam(Strings.EXEC) ) {

				String associa = getParam("associa",false);

				if ( associa != null ) {
					if ( ! c.isAperta() || c.isChiusa() )
						throw new Exception("Non si può associare uno stallo a una Consegna Chiusa o non ancora Aperta!");

					Integer idStallo = getIntParam("idNuovoStallo",false);

					Stallo stallo = StalloAdapter.get(idStallo);

					if ( stallo != null || stallo.getIdConsegnaAttuale() != null ) {
						stallo.setIdConsegnaAttuale(c.getId());
						stallo.setIdConsegnaPrenotata(null);
						stallo.setCaricato(0d);
						stallo.setImmessoInLiberaPratica(Boolean.FALSE);

						StalloAdapter sAdp = Stallo.newAdapter();

						sAdp.update(stallo);

						Homepage.purgeCaches();
					} else
						throw new ParameterException("idNuovoStallo");
				} else {
					if ( getParam( "pesofinaleportocarico" , false) == null &&
							! getBooleanParam(Strings.PARTIAL_EDIT)  ) {
						c.setPesoFinalePortoCarico(Boolean.FALSE) ;
						Consegna.newAdapter().update(c);
					}

					//if ( ! c.isAperta() && ! c.isChiusa() )

					if ( Boolean.TRUE.equals( s.getAttribute("admin") ) ) {
						aggiornaStalli(c, stalli);
					}
				}

				ctx.put("result", Boolean.TRUE ) ;

			}
			else if( getBooleanParam("r")) {
				c.setId(null);
				//				FormattedDate d = new FormattedDate();
				//				c.setDataChiusura(d);
				//				c.setDataCreazione(d);

			}
		}
		else {
			ctx.put("nextPartitario", Consegna.newAdapter().getNextNumPartitario() ) ;
		}

		return t ;
	}

	private void aggiornaStalli(Consegna c , Collection stalli ) throws Exception {

		ArrayList<Integer> idStalliAssegnati = getIntParams("stalli", 0);
		//		ArrayList<Integer> idStalliPrenotati = getIntParams("stalliprenotati", 1);
		Stallo s = null ;

		HashMap<Integer,Stallo> toUpdate = new HashMap<Integer,Stallo>(3);

		for ( Iterator i = stalli.iterator() ; i.hasNext() ; ){
			s = ( Stallo ) i.next() ;

			if ( c.isAperta() ) {
				if ( c.getId().equals( s.getIdConsegnaAttuale() ) ) {
					s.setIdConsegnaAttuale(null);
					toUpdate.put(s.getId(), s);
				}
			}
			else if ( ! c.isChiusa() ) {
				if ( c.getId().equals( s.getIdConsegnaPrenotata() ) ) {
					s.setIdConsegnaPrenotata(null);
					toUpdate.put(s.getId(), s);
				}
			}
		}

		if ( idStalliAssegnati != null ) {
			for (Integer integer : idStalliAssegnati) {
				s = StalloAdapter.get(integer) ;

				if ( ! c.isAperta() && ! c.isChiusa() ) {
					s.setIdConsegnaPrenotata(c.getId()) ;
				} else if ( c.isAperta() ) {
					s.setIdConsegnaAttuale(c.getId()) ;
				}

				if ( ! toUpdate.containsKey(s.getId()) )
				{
					toUpdate.put(s.getId(), s);
					//					s.update();
				}
			}
		}

		StalloAdapter adp = Stallo.newAdapter() ;
		for ( Iterator<Stallo> i = toUpdate.values().iterator() ; i.hasNext() ; ){
			s = i.next() ;
			adp.update(s);
		}
	}

	@Override
	public VelocityCommand clone() {
		return  new EditConsegna(callerServlet);
	}

	@Override
	public BeanAdapter2 getAdapter() {
		return new ConsegnaAdapter();
	}

	@Override
	public String getTemplateName() {
		return TEMPLATE;
	}
}