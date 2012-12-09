package com.gsoft.pt_movimentazioni.utils;

import java.sql.Date;
import java.util.ArrayList;

import com.gsoft.doganapt.data.Consegna;
import com.gsoft.doganapt.data.Documento;
import com.gsoft.doganapt.data.Stallo;
import com.gsoft.doganapt.data.adapters.MovimentoAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoDoganaleAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoIvaAdapter;
import com.gsoft.pt_movimentazioni.data.MovimentoQuadrelliAdapter;
import com.gtsoft.utils.common.FormattedDate;

public class IterImporter_NOGLEN extends IterImporter {

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
	public void apriConsegna( Consegna c, FormattedDate d , Documento documento, Documento documentoPV , String note) throws Exception  {
		c.setDataCreazione(d);
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
