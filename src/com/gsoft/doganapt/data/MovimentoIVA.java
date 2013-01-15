package com.gsoft.doganapt.data;

import com.gsoft.doganapt.data.adapters.MovimentoIvaAdapter;
import com.gtsoft.utils.sql.IDatabase2;

public class MovimentoIVA extends Movimento {

	Double valoreEuro;
	Double valoreDollari;
	Double valoreNetto;
	Double valoreTestp;
	Double valoreIva;

	String posizioneDoganale ;

	public Double getValoreEuro() {
		return valoreEuro;
	}
	public void setValoreEuro(final Double v) {
		valoreEuro = v;
	}
	public Double getValoreDollari() {
		return valoreDollari;
	}
	public void setValoreDollari(final Double v) {
		valoreDollari = v;
	}


	public Double getValoreNetto() {
		return valoreNetto;
	}
	public void setValoreNetto(Double valoreNetto) {
		this.valoreNetto = valoreNetto;
	}
	public Double getValoreTestp() {
		return valoreTestp;
	}
	public void setValoreTestp(Double valoreTestp) {
		this.valoreTestp = valoreTestp;
	}
	public Double getValoreIva() {
		return valoreIva;
	}
	public void setValoreIva(Double valoreIva) {
		this.valoreIva = valoreIva;
	}
	public String getPosizioneDoganale() {
		return posizioneDoganale;
	}
	public void setPosizioneDoganale(final String posizioneDoganale) {
		this.posizioneDoganale = posizioneDoganale;
	}

	public static synchronized MovimentoIvaAdapter newAdapter() throws Exception {
		return new MovimentoIvaAdapter() ;
	}
	public static synchronized MovimentoIvaAdapter newAdapter(final IDatabase2 db) throws Exception {
		return new MovimentoIvaAdapter(db) ;
	}

	@Override
	public MovimentoIVA clone() {

		final MovimentoIVA m = new MovimentoIVA();

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


		m.setIsLocked(Boolean.FALSE);

		return m ;



	}
}
