package com.gsoft.doganapt.cmd;

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
		
		boolean hasSelectedData = false;
		if ( selectedData != null && selectedData.after(now) ) {
			selectedData = null;
		}
		
		FormattedDate dataHead = selectedData;
		if ( selectedData == null ) {
			dataHead = now;
		} else {
			hasSelectedData = true;
			selectedData = new FormattedDate(selectedData.ymdString() + " 23:59:59");
		}
		
//		String iva = "2016-04-01";
//		String dog = "2016-05-01";
//		
//		int c = iva.compareTo(dog);
//		
//		if ( c > 0 ) 
//			Login.debug("aprile maggiore dog " + c );
//		else if( c < 0 ) 
//			Login.debug("dog maggiore di iva " + c );
//		
//		Login.debug( dataHead.ymdString() + " " + dataHead.ymdString().compareTo("2017-02-05") )
		
		ctx.put("data", selectedData);
		ctx.put("dataHead", dataHead);
		
		ctx.put("isAdmin", s.getAttribute("admin") ) ;

		Vector<Stallo> stalli = Stallo.newAdapter().getAll(selectedData , "parco, numero");
		ctx.put("stalli", stalli );

//		Login.debug("Home" + ( isPrintOutput() ? " P " : " - " ) + ( hasSelectedData ? "hasSelectedData" : " NotSelectedData") );
		if ( ! isPrintOutput() ) {
			
			if ( ! hasSelectedData ) {
				MovimentoQuadrelliAdapter qAdp = new MovimentoQuadrelliAdapter(PtMovimentazioniImporter.getInstance().getSQLServerDB());
				MovimentoDoganaleAdapter dogAdp = new MovimentoDoganaleAdapter() ;
				MovimentoIvaAdapter ivaAdp = new MovimentoIvaAdapter() ;
				StalloHomepageHelper stalliHelper = new StalloHomepageHelper(stalli, qAdp, dogAdp, ivaAdp);
				ctx.put("stalliHelper", stalliHelper );
			}
			
			if ( PtMovimentazioniImporter.hasMovimentiSenzaData() ) {
				ctx.put("movimentiSenzaData", PtMovimentazioniImporter.getMovimentiSenzaData() );
			}
		}
		
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
