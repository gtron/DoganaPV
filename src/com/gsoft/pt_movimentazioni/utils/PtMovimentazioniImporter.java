package com.gsoft.pt_movimentazioni.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import com.gsoft.doganapt.data.Consegna;
import com.gsoft.doganapt.data.adapters.MovimentoDoganaleAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoIvaAdapter;
import com.gsoft.pt_movimentazioni.data.MovimentoQuadrelliAdapter;
import com.gtsoft.utils.common.ConfigManager;
import com.gtsoft.utils.common.FormattedDate;
import com.gtsoft.utils.sql.AccessDB;

public class PtMovimentazioniImporter {

	MovimentoIvaAdapter ivaAdp = null ;
	MovimentoDoganaleAdapter dogAdp = null ;
	MovimentoQuadrelliAdapter quadAdp = null ;
	AccessDB adb = null ;
	
	static boolean locked = false;
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
				e.printStackTrace();
			}
		}
		
		quadAdp = new MovimentoQuadrelliAdapter(getAccessDB()) ;
	}
	
	public AccessDB getAccessDB() {
		if ( adb == null ) {
			String fileMovimentazioni = ConfigManager.getProperty("movimentazioni.filename") ;
			adb = new AccessDB(fileMovimentazioni) ;
		}
		return adb;
	}
	
	public synchronized void importTo( FormattedDate to ) throws Exception {
		
		Vector consegne = Consegna.newAdapter().getNonChiuse();
		
		Consegna c ;
		IterImporter importer ;
		// Per tutte le consegne ... 
		for ( Iterator i = consegne.iterator(); i.hasNext() ; ) {
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
