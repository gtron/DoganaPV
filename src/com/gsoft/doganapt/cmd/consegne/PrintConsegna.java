package com.gsoft.doganapt.cmd.consegne;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.data.Consegna;
import com.gsoft.doganapt.data.Movimento;
import com.gsoft.doganapt.data.MovimentoDoganale;
import com.gsoft.doganapt.data.adapters.ConsegnaAdapter;
import com.gtsoft.utils.common.BeanAdapter2;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;


public class PrintConsegna extends VelocityCommand {

	private static final String PAGINA_PRECEDENTE = "paginaPrecedente";
	private static final String MIN_NUM_REGISTRO = "minNumRegistro";
	private static final String IDSTALLO = "idStallo";

	protected static String TEMPLATE = "consegne/print.vm";
	private static String ID = "id";

	public PrintConsegna ( GtServlet callerServlet) {
		super(callerServlet);
	}
	@Override
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {

		Integer id = getIntParam(ID, true);

		Integer idStallo = getIntParam("idStallo", true);
		Integer minNumRegistro = getIntParam(MIN_NUM_REGISTRO, false);
		Integer paginaPrecedente = getIntParam(PAGINA_PRECEDENTE, false);

		Consegna c = (Consegna) getAdapter().getByKey(id);
		ctx.put(ContextKeys.OBJECT, c);

		if (idStallo > -1) {

			Vector<?> partitario = c.getPartitario(idStallo);

			if ( minNumRegistro != null && minNumRegistro > 0 ) {
				Movimento m;
				Movimento giacenzaIniziale = new MovimentoDoganale();
				giacenzaIniziale.setUmido(0d);
				giacenzaIniziale.setSecco(0d);
				Vector<Movimento> filtered = new Vector<Movimento>(13);
				for( Object o : partitario ) {
					m = (Movimento) o;
					if( m.getNumRegistro() >= minNumRegistro ) {
						filtered.add(m);
					} else {
						giacenzaIniziale.aggiungi(m);
					}
				}
				partitario = filtered;
			}

			ctx.put( "partitario", partitario);

			ctx.put( PAGINA_PRECEDENTE, paginaPrecedente);
		} else {
			ctx.put( "partitario", c.getPartitario());
		}

		return null;
	}

	@Override
	public VelocityCommand clone() {
		return  new PrintConsegna(callerServlet);
	}

	public BeanAdapter2 getAdapter() {
		return new ConsegnaAdapter();
	}

	@Override
	public String getTemplateName() {
		return TEMPLATE;
	}

}