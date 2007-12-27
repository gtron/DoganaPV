package com.gsoft.pt_movimentazioni.utils;

import java.util.ArrayList;
import java.util.Iterator;

import com.gsoft.doganapt.data.Documento;
import com.gsoft.doganapt.data.Stallo;
import com.gsoft.doganapt.data.adapters.MovimentoDoganaleAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoIvaAdapter;
import com.gsoft.doganapt.data.adapters.StalloAdapter;
import com.gsoft.pt_movimentazioni.data.MovimentoQuadrelliAdapter;
import com.gtsoft.utils.common.FormattedDate;

public class IterImporter_GLEN extends IterImporter {

	public IterImporter_GLEN( MovimentoDoganaleAdapter m, MovimentoIvaAdapter i, MovimentoQuadrelliAdapter q ) {
		super(m,i,q);
	}

	public void immettiLiberaPratica(  ArrayList<Integer> stalli , 
			FormattedDate data , Documento docOut, Documento docIn) throws Exception {
		Stallo s = null ;
		Integer id = null ;

		StalloAdapter stAdp= new StalloAdapter();

		for ( Iterator i = stalli.iterator() ; i.hasNext() ; ){

			id = (Integer) i.next() ;

			s = StalloAdapter.get(id) ;

			if (!  s.getHasLiberaPratica() ) {
				s.immettiInLiberaPratica(data, docOut, docIn, registroIVA, registroIVA ) ;

				s.setImmessoInLiberaPratica(Boolean.TRUE);

				stAdp.update(s);
			}

		}

	}
}
