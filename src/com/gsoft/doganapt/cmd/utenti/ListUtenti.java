package com.gsoft.doganapt.cmd.utenti;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.cmd.Login;
import com.gsoft.doganapt.data.Utente;
import com.gsoft.doganapt.data.adapters.UtenteAdapter;
import com.gtsoft.utils.common.BeanAdapter2;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;


public class ListUtenti extends VelocityCommand {
	protected static String TEMPLATE = "utenti/list.vm";

	public ListUtenti ( GtServlet callerServlet) {
		super(callerServlet);
	}
	@Override
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {

		Utente u = Login.getUtenteLogged(req);

		ctx.put( "list",  new UtenteAdapter().getListWithLevel(u.getLevel())) ;

		return null ;
	}


	@Override
	public VelocityCommand clone() {
		return  new ListUtenti(callerServlet);
	}

	public BeanAdapter2 getAdapter() {
		return new UtenteAdapter();
	}

	@Override
	public String getTemplateName() {
		return TEMPLATE;
	}
}