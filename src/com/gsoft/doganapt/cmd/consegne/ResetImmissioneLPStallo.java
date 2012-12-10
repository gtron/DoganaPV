package com.gsoft.doganapt.cmd.consegne;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.data.Consegna;
import com.gsoft.doganapt.data.Movimento;
import com.gsoft.doganapt.data.MovimentoIVA;
import com.gsoft.doganapt.data.Stallo;
import com.gsoft.doganapt.data.StalloConsegna;
import com.gsoft.doganapt.data.adapters.ConsegnaAdapter;
import com.gsoft.doganapt.data.adapters.StalloAdapter;
import com.gsoft.doganapt.data.adapters.StalloConsegnaAdapter;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;


public class ResetImmissioneLPStallo extends VelocityCommand {

	public ResetImmissioneLPStallo ( GtServlet callerServlet) {
		super(callerServlet);
	}
	@Override
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {

		if ( getBooleanParam(Strings.EXEC) ) {

			Integer id = getIntParam("id", true);
			StalloAdapter adp = Stallo.newAdapter() ;
			StalloConsegnaAdapter scAdp = StalloConsegna.newAdapter();

			Stallo s = (Stallo) adp.getByKey(id);
			Consegna consegna = ConsegnaAdapter.get(getIntParam("idC", false));

			if ( consegna == null ) {
				// default: la consegna assegnata allo stallo
				consegna = s.getConsegna();
			}

			if ( getBooleanParam("exec" )) {

				Vector<Movimento> l = MovimentoIVA.newAdapter().getByConsegna(
						false, consegna.getId(), id, null, null);

				if ( l != null && l.size() > 0 )
					throw new Exception(
							"Attenzione: Non è possibile effettuare il reset ILP se il registro IVA relativo allo stallo non è vuoto!");

				s.setImmessoInLiberaPratica(false);
				adp.update(s);

				StalloConsegna sc = scAdp.getByKeyObjects(consegna, s);
				if ( sc != null ) {
					sc.setIsInLiberaPratica(Boolean.FALSE);
					scAdp.update(sc);
				}
			}

			response.sendRedirect(".consegne?cmd=edit&id=" + getIntParam("idC", true));
			response.flushBuffer();

		}

		return null;
	}


	@Override
	public VelocityCommand clone() {
		return  new ResetImmissioneLPStallo(callerServlet);
	}

	@Override
	public String getTemplateName() {
		return "consegne/view.vm" ;
	}

}