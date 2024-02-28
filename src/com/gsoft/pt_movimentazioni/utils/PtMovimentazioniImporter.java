package com.gsoft.pt_movimentazioni.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import com.gsoft.doganapt.cmd.Login;
import com.gsoft.doganapt.data.Consegna;
import com.gsoft.doganapt.data.adapters.MovimentoDoganaleAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoIvaAdapter;
import com.gsoft.pt_movimentazioni.data.MovimentoQuadrelliAdapter;
import com.gtsoft.utils.SQLServerDB;
import com.gtsoft.utils.common.FormattedDate;

public class PtMovimentazioniImporter {

	MovimentoIvaAdapter ivaAdp = null ;
	MovimentoDoganaleAdapter dogAdp = null ;
	MovimentoQuadrelliAdapter quadAdp = null ;
	SQLServerDB adb = null ;
	
	static boolean locked = false;
	static ArrayList<String> movimentiSenzaData = null;
	
	private static PtMovimentazioniImporter instance = null ;
	
	public static PtMovimentazioniImporter getInstance(){
		
		if ( instance == null && ! locked )
			instance = new PtMovimentazioniImporter();
		
		return instance ;
	}
	
	public static void freeInstance() throws Exception{
		if ( instance != null ){
			instance.quadAdp.end();
			instance = null;
		}
	}
	
	public static void setLocked(boolean b) throws Exception {
		if ( b ){
			freeInstance();
		}
		locked = b ;
	}
	
	public static boolean hasMovimentiSenzaData() throws Exception{
		ArrayList<String> list = getMovimentiSenzaData(false);
		return ( list != null && list.size() > 0 ) ;
	}
	
	public static ArrayList<String> getMovimentiSenzaData() throws Exception {
		return getMovimentiSenzaData(false);
	}
	
	public static ArrayList<String> getMovimentiSenzaData(boolean forceCheck) throws Exception {
		
		if ( forceCheck || null == movimentiSenzaData ) {
			PtMovimentazioniImporter i = PtMovimentazioniImporter.getInstance();
			i.checkMovimentiSenzaData();
		}
		
		return movimentiSenzaData;
	}
		
	PtMovimentazioniImporter() {
		
		ivaAdp = new MovimentoIvaAdapter() ;
		dogAdp = new MovimentoDoganaleAdapter() ;
		
		getQuadrelliAdapter();
	
	}

	private void getQuadrelliAdapter() {
		
		if (quadAdp != null) {
			try {
				freeInstance();
			} catch (Exception e) {
				Login.debug(e, "DBQuadrelli" );
				e.printStackTrace();
			}
		}
		
		quadAdp = new MovimentoQuadrelliAdapter(getSQLServerDB()) ;
	}
	
	public ArrayList<String> checkMovimentiSenzaData() {
		
		try {
			movimentiSenzaData = new MovimentoQuadrelliAdapter(getSQLServerDB()).getMovimentiSenzaData();
		} catch (Exception e) {
			movimentiSenzaData = new ArrayList<String>(1);
			Login.debug(e, "PTImporter");
		}
		
		return movimentiSenzaData;
	}
	
	public SQLServerDB getSQLServerDB() {
		
		if ( adb == null ) {			
			adb = new SQLServerDB() ;
		}
		return adb;
	}
	
	public synchronized void importTo( FormattedDate to ) throws Exception {
		
		Vector<?> consegne = Consegna.newAdapter().getNonChiuse();
		
		Consegna c ;
		IterImporter importer ;
		// Per tutte le consegne ... 
		for ( Iterator<?> i = consegne.iterator(); i.hasNext() ; ) {
			c = (Consegna) i.next();
			
			importer = c.getIter().getImporter(dogAdp,ivaAdp, quadAdp);
			
			importer.importTo(c, to, null );
		}
			
	}
	
	public synchronized void importTo( Consegna c , FormattedDate to , ArrayList<Integer> idStalli ) throws Exception {
		
		IterImporter importer ;
			
		importer = c.getIter().getImporter(dogAdp,ivaAdp, quadAdp);
		
		importer.importTo(c, to, idStalli);
			
	}
}
