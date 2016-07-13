package com.gsoft.doganapt.cmd;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.data.Stallo;
import com.gsoft.doganapt.data.adapters.ConsegnaAdapter;
import com.gsoft.doganapt.data.adapters.IterAdapter;
import com.gsoft.doganapt.data.adapters.MerceAdapter;
import com.gsoft.doganapt.data.adapters.StalloAdapter;
import com.gtsoft.utils.ManagerAliquoteIva;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;

public class Homepage extends VelocityCommand {

	protected static final String HOMEPAGE = "homepage.vm" ;
	protected static String PRINT = "stalli/print.vm";

	//	private static final String ID = "id" ;

	protected int getIndex(int max) {
		return (int) Math.round(( Math.random() * 10000 ) % ( max - 1 ) );
	}


	public Homepage ( GtServlet callerServlet) {
		super(callerServlet);
	}

	@Override
	public VelocityCommand clone() {
		return  new Homepage(callerServlet);
	}

	String[][] hombres = {
			{"Gianluca", "0" ,"P" },
			{"Daniele", "0" ,"P"},
			{"Javi", "0" ,"P"},
			{"Sam", "0" ,"P"},
			{"Alex", "0" ,"P"},
			{"Adam", "0" ,"P"},
			{"Roman", "0" ,"P"},
			{"Max", "0" ,"P"},
			{"Isma", "0" ,"P"},
			{"Luca", "0" ,"P"},
			{"Aitor", "0" ,"P"}
	};
	String[][] hombres_2 = {
			{"Ganador 1", "0" ,"P"},
			{"Ganador 2", "0" ,"P"},
			{"Ganador 3", "0" ,"P"},
			{"Ganador 4", "0" ,"P"},
			{"Ganador 5", "0" ,"P"},
			{"6", "0" ,"P"}
	};

	String[][] hombres_3 = {
			{"Ganador 1", "0" ,"P"},
			{"Ganador 2", "0" ,"P"},
			{"Ganador 3", "0" ,"P"},
	};


	String[][] mujeres = {
			{"Maite", "0" ,"P"},
			{"Chiara", "0" ,"P"},
			{"Ilia", "0" ,"P"},
			{"Neus", "0" ,"P"},
			{"Julia", "0" ,"P"},
			{"Elsa", "0" ,"P"},
			{"Kathy", "0" ,"P"},
			{"Ali", "0" ,"P"},
			{"Georgina", "0" ,"P"}
			//			,{"Meri", "0" }
	};

	String[][] mujeres_2 = {
			{"Ganador 1", "0" ,"P"},
			{"Ganador 2", "0" ,"P"},
			{"Ganador 3", "0" ,"P"},
			{"Ganador 4", "0" ,"P"},
			{"5", "0" ,"P"}
	};

	String[][] mujeres_3 = {
			{"Ganador 1", "0" ,"P"},
			{"Ganador 2", "0" ,"P"},
			{"Ganador 3", "0" ,"P"}
	};

	private String[] genera(String[][] in) {
		// StringBuffer out = new StringBuffer();

		int len = in.length;
		String[] out = new String[len];

		int i = 0 ;
		int rand = getIndex(len) ;

		for ( ; i <len ; i++  ) {
			while ( in[rand][1] == "1" ) {
				rand = getIndex(len);
			}

			out[i] = in[rand][0] ;

			in[rand][1] = "1";
		}
		return out;
	}

	public Template exec_torneo(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {

		ctx.put("H", genera(hombres));
		ctx.put("H2", genera(hombres_2));
		ctx.put("H3", genera(hombres_3));

		ctx.put("M", genera(mujeres));
		ctx.put("M2", genera(mujeres_2));
		ctx.put("M3", genera(mujeres_3));


		return null;
	}


	@Override
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {

		HttpSession s =  req.getSession(false);

		if ( s == null ) {
			resp.sendRedirect(".main");
			resp.flushBuffer();
			return null ;
		}
		Boolean logged = (Boolean) s.getAttribute("logged") ;
		if ( logged != Boolean.TRUE ) {
			resp.sendRedirect(".main");
		}
		
		ctx.put("data", getDateParam("data", false));

		ctx.put("isAdmin", s.getAttribute("admin") ) ;

		ctx.put("stalli", Stallo.newAdapter().getAll("parco, numero") );

		//		BeanAdapter2 adp = BeanAdapter2.newAdapter();
		//		ctx.put( ContextKeys.LIST , adp.getAll() ) ;
		//
		//		Integer selectedId = getParameter.getInt(ID, req, false);
		//
		//		if ( selectedId != null ) {
		//			ctx.put( ContextKeys.OBJECT , adp.getByKey(selectedId) ) ;
		//		}
		return null;
	}


	@Override
	public String getTemplateName() {
		if ( getBooleanParam("p") )
			return PRINT ;

		return HOMEPAGE ;
	}


	public static void purgeCaches() {
		ConsegnaAdapter.clearCache();
		StalloAdapter.clearCache();
		MerceAdapter.clearCache();
		IterAdapter.clearCache();
		ManagerAliquoteIva.clearCache();
	}


}
