package com.gsoft.doganapt.cmd.utenti;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.cmd.Login;
import com.gsoft.doganapt.data.Utente;
import com.gsoft.doganapt.data.adapters.UtenteAdapter;
import com.gsoft.doganapt.data.adapters.UtenteAdapter.Fields;
import com.gtsoft.utils.common.BeanAdapter2;
import com.gtsoft.utils.common.BeanEditor;
import com.gtsoft.utils.data.Field;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.exception.ParameterException;
import com.gtsoft.utils.http.exception.RequiredParameterException;
import com.gtsoft.utils.http.servlet.GtServlet;


public class EditUtente extends BeanEditor {

	protected static String TEMPLATE = "utenti/" + DEFAULT_TEMPLATE;

	public EditUtente ( GtServlet callerServlet) {
		super(callerServlet);
	}

	@Override
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {

		Utente logged = Login.getUtenteLogged(req);

		UtenteAdapter adp = (UtenteAdapter) getAdapter();

		Integer selectedId = getIntParam( "id" , false);

		Template t = null ;
		try {
			Utente e = null ;
			if ( selectedId != null ) {
				e = (Utente) adp.getByKey(selectedId);

				if ( e == null || logged.getLevel() < e.getLevel() )
					throw new Exception("Access Denied");

			} else {
				Field fieldLevel = adp.getField(Fields.LIVELLO);
				Integer level = getIntParam( adp.getHttpFieldName(fieldLevel.getId()), true);
				if ( level > logged.getLevel() )
					throw new ParameterException("level", fieldLevel , new Exception("Livello non valido"));
			}




			t = super.exec(req, resp, ctx);

			if ( getBooleanParam(Strings.EXEC) ) {
				if ( e != null ) {
					Login.logAction("Modifica dell'utente '" + e.getUsername()+"' ("+e.getId()+ ")", req);
				} else {
					e = (Utente) ctx.get(ContextKeys.OBJECT);
					if ( e == null ) {
						e = new Utente();
						e.setUsername("???");
						e.setId(0);
					}
					Login.logAction("Creazione dell'utente '" + e.getUsername()+"' ("+e.getId()+ ")", req);
				}
			}
		} catch (RequiredParameterException e2) {
			ctx.put("param_error", e2);
		} catch (ParameterException e2) {
			ctx.put("param_error", e2);
		}


		return t ;

	}
	@Override
	public VelocityCommand clone() {
		return  new EditUtente(callerServlet);
	}

	@Override
	public BeanAdapter2 getAdapter() {
		return new UtenteAdapter();
	}

	@Override
	public String getTemplateName() {
		return TEMPLATE;
	}
}