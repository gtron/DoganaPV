package com.gsoft.pt_movimentazioni.utils;

import java.util.Iterator;
import java.util.Vector;

import com.gsoft.doganapt.data.Merce;
import com.gsoft.doganapt.data.adapters.MerceAdapter;
import com.gsoft.pt_movimentazioni.data.MerceQuadrelliAdapter;
import com.gtsoft.utils.AccessDb2024;
import com.gtsoft.utils.common.ConfigManager;

public class PtMerciImporter {

	MerceAdapter adp = null ;
	MerceQuadrelliAdapter quadAdp = null ;
	AccessDb2024 adb = null ;
	
	private static PtMerciImporter instance = null ;
	
	public static PtMerciImporter getInstance(){
		
		if ( instance == null )
			instance = new PtMerciImporter();
		
		return instance ;
	}
	
	PtMerciImporter() {
		
		String f = ConfigManager.getProperty("anagrafiche.filename") ;
		if ( f != null ) {
			adb = new AccessDb2024(f,null,null); // "sa", "QuaBil") ;
			
			quadAdp = new MerceQuadrelliAdapter(adb) ;
			adp = new MerceAdapter() ;
		}
	}
	
	public AccessDb2024 getAccessDB() {
		return adb;
	}
	
	public synchronized void doImport() throws Exception {
		
		Vector<Object> listQ = quadAdp.getAll();
		
//		adp.clearTable() ;
		
		// Merce m  = null ;
		Merce mQ = null ;
		
		for ( Iterator<Object> iQ = listQ.iterator() ; iQ.hasNext() ; ) {
			mQ = (Merce) iQ.next() ;
			
//			m = MerceAdapter.get(id)
			mQ.setId(null);
			adp.create(mQ);
			
		}
	}
	
}
