package com.gsoft.pt_movimentazioni.utils;

import com.gsoft.doganapt.data.Consegna;
import com.gsoft.doganapt.data.Documento;
import com.gsoft.doganapt.data.adapters.MovimentoAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoDoganaleAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoIvaAdapter;
import com.gsoft.pt_movimentazioni.data.MovimentoQuadrelliAdapter;
import com.gtsoft.utils.common.FormattedDate;

public class IterImporter_COM extends IterImporter {

	public IterImporter_COM( MovimentoDoganaleAdapter m, MovimentoIvaAdapter i, MovimentoQuadrelliAdapter q ) {
		super(m,i,q);
	}

	@Override
	public MovimentoAdapter getRegistroInPerILP() {
		return registroIVA;
	}

	@Override
	public MovimentoAdapter getRegistroOutPerILP() {
		return registroIVA;
	}

	@Override
	public void apriConsegna( Consegna c, FormattedDate d , Documento documento, Documento documentoPV, String note  ) throws Exception  {
		apriConsegna_IVA(c, d, documento, documentoPV, note);
	}

}
