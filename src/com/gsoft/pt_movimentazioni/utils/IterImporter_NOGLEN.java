package com.gsoft.pt_movimentazioni.utils;

import java.sql.Date;
import java.util.ArrayList;

import com.gsoft.doganapt.data.Consegna;
import com.gsoft.doganapt.data.Documento;
import com.gsoft.doganapt.data.Movimento;
import com.gsoft.doganapt.data.Stallo;
import com.gsoft.doganapt.data.adapters.MovimentoAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoDoganaleAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoIvaAdapter;
import com.gsoft.pt_movimentazioni.data.MovimentoQuadrelliAdapter;
import com.gtsoft.utils.common.FormattedDate;

public class IterImporter_NOGLEN extends IterImporter {
	
/*
 select sum(netto) as Netto, data,  Id_Materiale,  NumConsegna , '%S%' as cliente, 0 as destinazione
	 from Pesate where  
		data = '%D%' and
	    Id_Materiale = '%M%' and NumConsegna = '%C%'  and  Id_Cliente  = '%S%'
	group by data, Id_Materiale, NumConsegna

	 * Originale:
select sum(netto) as PesoNetto, data,  merce,  `num consegna` , '%S%' as cliente, 0 as destinazione
 from `archivio corretto` where  
destinazione <> 7 and
data = #%D%# and
merce = '%M%' and `num consegna` = '%C%'  and 
cliente  = '%S%' and destinazione <> 8 
group by data, merce, `num consegna`

	
	// ------- OUT

	 
	select data   , sum( Netto ) as Netto, Id_Materiale ,  '%S%' as Id_Fornitore 
	FROM (

	select   data   , sum( IIF(Id_Fornitore = '0'  , - netto , netto)) as peso ,  Id_Materiale
	 from Pesate where 
	Id_Materiale = '%M%' and NumConsegna = '%C%'  and
	Id_Fornitore = '%S%' 
	and data =  '%D%'
	group by  data , Id_Fornitore ,  Id_Materiale, NumConsegna , NumDocumento

	) p

	group by data, Id_Materiale

* Originale:

select data   , sum( peso ) as PesoNetto, merce ,  '%S%' as fornitore  ,  0 as destinazione
FROM (

select   data   , sum( IIF(fornitore = '0'  , - netto , netto)) as peso ,  merce
 from `archivio corretto` where 
merce = '%M%' and `num consegna` = '%C%'  and
(  fornitore = '%S%' or ( cliente =  '%S%'  and destinazione = 8 ) )
and data =  #%D%#
group by  data , fornitore ,  merce, `num consegna` , `num documento`

) p

group by data, merce
	 */
	
	
	public IterImporter_NOGLEN( MovimentoDoganaleAdapter m, MovimentoIvaAdapter i, MovimentoQuadrelliAdapter q ) {
		super(m,i,q);
	}

	@Override
	protected void doImport( Consegna c, Stallo s, FormattedDate giorno ) throws Exception {

		// C
		innerImport( false, c, s, giorno , registroDoganale ) ;
		// S
		innerImport( true, c, s, giorno , registroDoganale ) ;

	}

	@Override
	protected FormattedDate getLastData( Consegna c ,  ArrayList<Integer> idStalli) throws Exception {
		FormattedDate lastData = getLastData(c, idStalli, false);

		if ( lastData == null ) {
			FormattedDate d = c.getDataCreazione() ;
			FormattedDate nd =  new FormattedDate( new Date( d.getTime() -  6600000 ) );
			while ( d.dmyString() == nd.dmyString() ) {
				nd = new FormattedDate( d.getTime() -  6600000 );
			}

			lastData = new FormattedDate( nd.ymdString() );

		}
		return lastData;
	}

	//	public void importaPrimoCarico(Consegna c, FormattedDate d ) throws Exception {
	//		// TODO Metodo Vuoto xke l'azione non si applica ( rivedere gerarchia oggetti )
	//
	//	}

	@Override
	public Movimento apriConsegna( Consegna c, FormattedDate d , Documento documento, Documento documentoPV , String note) throws Exception  {
		c.setDataCreazione(d);
		return null;
	}

	@Override
	public MovimentoAdapter getRegistroInPerILP() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MovimentoAdapter getRegistroOutPerILP() {
		// TODO Auto-generated method stub
		return null;
	}

}
