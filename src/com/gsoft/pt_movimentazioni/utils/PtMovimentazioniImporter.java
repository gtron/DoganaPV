package com.gsoft.pt_movimentazioni.utils;

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
	
	private static PtMovimentazioniImporter instance = null ;
	
	public static PtMovimentazioniImporter getInstance(){
		
		if ( instance == null )
			instance = new PtMovimentazioniImporter();
		
		return instance ;
	}
	
	PtMovimentazioniImporter() {
		
		String fileMovimentazioni = ConfigManager.getProperty("movimentazioni.filename") ;
		
		adb = new AccessDB(fileMovimentazioni) ;

		ivaAdp = new MovimentoIvaAdapter() ;
		dogAdp = new MovimentoDoganaleAdapter() ;
		
		quadAdp = new MovimentoQuadrelliAdapter(adb) ;
	
	}
	
	public AccessDB getAccessDB() {
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
			
			importer.importTo(c, to);
		}
			
	}
	
	public synchronized void importTo( Consegna c , FormattedDate to ) throws Exception {
		
		IterImporter importer ;
			
		importer = c.getIter().getImporter(dogAdp,ivaAdp, quadAdp);
		
		importer.importTo(c, to);
			
	}

//	public synchronized void old_importTo( FormattedDate to ) throws Exception {
//
//		if ( to == null )
//			to = new FormattedDate() ;
//		
//		Vector consegne = Consegna.newAdapter().getAperte();
//		
//		Consegna c = null ;
//		
//		FormattedDate dataLastCarico = null ;
//		FormattedDate dataLastScarico = null;
//		FormattedDate giornoCorrente = null ;
//		
//		Stallo s = null;
//
//		// TODO : Se la data del carico è null guardare la data del primo carico di quadrelli
//		try {
//			dataLastCarico = movAdp.getLastDate(false, null) ;
//			dataLastScarico = movAdp.getLastDate(true, null) ;
//			
//			GregorianCalendar gc = new GregorianCalendar(Locale.ITALIAN);
//			
//			if ( dataLastCarico == null && dataLastScarico == null ) {
////				giornoCorrente = new FormattedDate( "01/01/" + new FormattedDate().getAnno() + " 00:00:00" );
//				giornoCorrente = new FormattedDate( "01/01/2006 00:00:00" );
//				to = new FormattedDate( "01/01/2007 00:00:00" );
//				
//				gc.setTime(giornoCorrente);
//				gc.add(GregorianCalendar.DAY_OF_YEAR, -1) ;
//				giornoCorrente = new FormattedDate( gc.getTime() );
//			}
//			else if ( dataLastCarico == null ) {
//				giornoCorrente = dataLastScarico ;
//			}
//			else if ( dataLastScarico == null ) {
//				giornoCorrente = dataLastCarico ;
//			}
//			else if ( dataLastScarico.after(dataLastCarico) ) {
//				giornoCorrente = dataLastScarico ;
//			}
//			else  {
//				giornoCorrente = dataLastCarico ;
//			}
//
//			
//			gc.setTime(giornoCorrente) ;
//			
//			boolean carichiSingoli = false ;
//			while ( giornoCorrente.before(to) ) {
//				gc.add(GregorianCalendar.DAY_OF_YEAR, 1) ;
//				giornoCorrente = new FormattedDate(gc.getTime());
//				
//				// Per tutte le consegne ... 
//				for ( Iterator i = consegne.iterator(); i.hasNext() ; ) {
//					c = (Consegna) i.next();
//					
//					carichiSingoli = ( c.getIter().getIspesofattura() || c.getIter().getIspesobolla() );
//					// E tutti gli stalli di ciascuna consegna ...
//					for ( Iterator is = c.getStalli().iterator() ; is.hasNext() ; ) {
//
//						s = (Stallo) is.next() ;
//					
//						if ( carichiSingoli && 
//								( dataLastCarico == null || giornoCorrente.before(dataLastCarico) ) ) {
//							importMovimenti( quadAdp.get(false, giornoCorrente, s), c, false, null);
//						}
//						if ( dataLastScarico == null || giornoCorrente.before(dataLastScarico) ) {
//							importMovimenti( quadAdp.get(true, giornoCorrente, s), c, true, null );
//						}
//					}
//				}
//			}
//		}
//		catch ( Exception e ) {
//			throw e ;
//		}
//		finally {
//			
////			quadAdp.end() ;
//		}
//		
//	}
	
	
	
	
	
	
}
