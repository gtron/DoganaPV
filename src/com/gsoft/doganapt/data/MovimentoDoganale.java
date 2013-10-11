package com.gsoft.doganapt.data;

import com.gsoft.doganapt.data.adapters.MovimentoAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoDoganaleAdapter;
import com.gtsoft.utils.sql.IDatabase2;

public class MovimentoDoganale extends Movimento {

	public static synchronized MovimentoAdapter newAdapter() throws Exception {
		return new MovimentoDoganaleAdapter() ;
	}
	public static synchronized MovimentoAdapter newAdapter(final IDatabase2 db) throws Exception {
		return new MovimentoDoganaleAdapter(db) ;
	}

	@Override
	public MovimentoDoganale clone() {

		final MovimentoDoganale m = new MovimentoDoganale();

		m.setId(null);
		m.setNumRegistro(null);

		m.setData(data);
		m.setUmido( umido ) ;
		m.setSecco( secco );

		m.setIdMerce(idmerce);
		m.setIdStallo(idstallo);
		m.setIdConsegna(idconsegna);
		m.setIsScarico(isScarico);
		m.setIsRettifica(isRettifica);

		m.setDocumento(documento) ;
		m.setDocumentoPV(documentoPV);

		m.setCodPosizioneDoganale(codPosizioneDoganale);
		m.setCodProvenienza(codProvenienza);
		m.setNote(note);

		m.setIsLocked(Boolean.FALSE);

		return m ;

	}

}
