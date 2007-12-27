package com.gsoft.doganapt.data;

import com.gsoft.doganapt.data.adapters.MovimentoIvaAdapter;
import com.gtsoft.utils.sql.IDatabase2;

public class MovimentoIVA extends Movimento {
	
	Double valoreEuro;
	Double valoreDollari; 
	
	public Double getValoreEuro() {
		return valoreEuro;
	}
	public void setValoreEuro(Double v) {
		this.valoreEuro = v;
	}
	public Double getValoreDollari() {
		return valoreDollari;
	}
	public void setValoreDollari(Double v) {
		this.valoreDollari = v;
	}
	
	public static synchronized MovimentoIvaAdapter newAdapter() throws Exception {		
		return new MovimentoIvaAdapter() ;
	}
	public static synchronized MovimentoIvaAdapter newAdapter(IDatabase2 db) throws Exception {		
		return new MovimentoIvaAdapter(db) ;
	}
	
	public MovimentoIVA clone() {
		
		MovimentoIVA m = new MovimentoIVA();
		
		m.setIdMerce(idmerce); 
		m.setData(data); 
		m.setIdStallo(idstallo);
		m.setIdConsegna(idconsegna);
		m.setIsScarico(isScarico);
		m.setIsRettifica(isRettifica);

		m.setDocumento(documento) ;
		m.setIsLocked(Boolean.FALSE);
		
		return m ;
		
	}
}
