package com.gsoft.doganapt.cmd;

import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.data.Stallo;
import com.gsoft.doganapt.data.StalloHomepageHelper;
import com.gsoft.doganapt.data.adapters.ConsegnaAdapter;
import com.gsoft.doganapt.data.adapters.IterAdapter;
import com.gsoft.doganapt.data.adapters.MerceAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoDoganaleAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoIvaAdapter;
import com.gsoft.doganapt.data.adapters.StalloAdapter;
import com.gsoft.pt_movimentazioni.data.MovimentoQuadrelli;
import com.gsoft.pt_movimentazioni.data.MovimentoQuadrelliAdapter;
import com.gsoft.pt_movimentazioni.utils.PtMovimentazioniImporter;
import com.gtsoft.utils.ManagerAliquoteIva;
import com.gtsoft.utils.common.FormattedDate;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;

public class Homepage extends VelocityCommand {

	protected static final String HOMEPAGE = "homepage.vm" ;
	protected static String PRINT = "stalli/print.vm";

	public Homepage ( GtServlet callerServlet) {
		super(callerServlet);
	}

	@Override
	public VelocityCommand clone() {
		return  new Homepage(callerServlet);
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
		
		FormattedDate now = new FormattedDate();
		
		FormattedDate selectedData = getDateParam("data", false);
		if ( selectedData != null && selectedData.after(now) ) {
			selectedData = null;
		}
		
		FormattedDate dataHead = selectedData;
		if ( selectedData == null ) {
			dataHead = now;
		} else {
			selectedData = new FormattedDate(selectedData.ymdString() + " 23:59:59");
		}
		
		ctx.put("data", selectedData);
		ctx.put("dataHead", dataHead);
		
		ctx.put("isAdmin", s.getAttribute("admin") ) ;

		Vector<Stallo> stalli = Stallo.newAdapter().getAll(selectedData , "parco, numero");
		ctx.put("stalli", stalli );

		if ( ! isPrintOutput() && selectedData != null ) {
			MovimentoQuadrelliAdapter qAdp = new MovimentoQuadrelliAdapter(PtMovimentazioniImporter.getInstance().getAccessDB());
			MovimentoDoganaleAdapter dogAdp = new MovimentoDoganaleAdapter() ;
			MovimentoIvaAdapter ivaAdp = new MovimentoIvaAdapter() ;
			
			StalloHomepageHelper stalliHelper = new StalloHomepageHelper(stalli, qAdp, dogAdp, ivaAdp);

			ctx.put("stalliHelper", stalliHelper );
			
		}
		
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
		if ( isPrintOutput() )
			return PRINT ;

		return HOMEPAGE ;
	}

	private boolean isPrintOutput() {
		return getBooleanParam("p");
	}


	public static void purgeCaches() {
		ConsegnaAdapter.clearCache();
		StalloAdapter.clearCache();
		MerceAdapter.clearCache();
		IterAdapter.clearCache();
		ManagerAliquoteIva.clearCache();
	}


}
