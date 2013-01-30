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
	@Override
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

	@Override
	public void aggiungi(Movimento m) {
		super.aggiungi(m);
		MovimentoIVA mIva = (MovimentoIVA) m;

		setValoreDollari(sommaArrotondata(getValoreDollari(), mIva.getValoreDollari()));
		setValoreEuro(sommaArrotondata(getValoreEuro(), mIva.getValoreEuro()));
		setValoreTestp(sommaArrotondata(getValoreTestp(), mIva.getValoreTestp()));

		setValoreNetto(sommaArrotondata(getValoreNetto(), mIva.getValoreNetto()));
		setValoreIva(sommaArrotondata(getValoreIva(), mIva.getValoreIva()));
	}

	protected Double sommaArrotondata(Double a, Double b) {

		double _a = 0d;
		if ( a != null ) {
			_a = a.doubleValue();
		}

		double _b = 0d;
		if ( b != null ) {
			_b = b.doubleValue();
		}

		return Double.valueOf(  Math.round( 100 * _a + 100 * _b) / 100d );
	}

	@Override
	public void togli(Movimento m) {
		super.togli(m);
		MovimentoIVA mIva = (MovimentoIVA) m;

		setValoreDollari(sottrazioneArrotondata(getValoreDollari(), mIva.getValoreDollari()));
		setValoreEuro(sottrazioneArrotondata(getValoreEuro(), mIva.getValoreEuro()));
		setValoreTestp(sottrazioneArrotondata(getValoreTestp(), mIva.getValoreTestp()));

		setValoreNetto(sottrazioneArrotondata(getValoreNetto(), mIva.getValoreNetto()));
		setValoreIva(sottrazioneArrotondata(getValoreIva(), mIva.getValoreIva()));

		//		setValoreDollari(
		//				Math.round(100 * getValoreDollari().doubleValue()	- 100 * mIva.getValoreDollari().doubleValue() )
		//				/ 100d ) ;
		//		setValoreEuro(
		//				Math.round(100 * getValoreEuro().doubleValue()		- 100 * mIva.getValoreEuro().doubleValue() )
		//				/ 100d ) ;
		//		setValoreTestp(
		//				Math.round(100 * getValoreTestp().doubleValue() 	- 100 * mIva.getValoreTestp().doubleValue() )
		//				/ 100d ) ;
		//
		//		setValoreNetto(
		//				Math.round(100 * getValoreNetto().doubleValue()		- 100 * mIva.getValoreNetto().doubleValue() )
		//				/ 100d ) ;
		//		setValoreIva(
		//				Math.round(100 * getValoreIva().doubleValue() 		- 100 * mIva.getValoreIva().doubleValue() )
		//				/ 100d ) ;

	}

	protected Double sottrazioneArrotondata(Double a, Double b) {

		double _a = 0d;
		if ( a != null ) {
			_a = a.doubleValue();
		}

		double _b = 0d;
		if ( b != null ) {
			_b = b.doubleValue();
		}

		return Double.valueOf(  Math.round( 100 * _a - 100 * _b) / 100d );
	}

	@Override
	public void copiaPesiEValoriInvertiti(Movimento m) {
		super.copiaPesiEValoriInvertiti(m);

		MovimentoIVA mIva = (MovimentoIVA) m;

		setValoreDollari( -1d * mIva.getValoreDollari().doubleValue());
		setValoreEuro(-1d * mIva.getValoreEuro().doubleValue());
		setValoreTestp(-1d * mIva.getValoreTestp().doubleValue()) ;
		setValoreNetto(-1d * mIva.getValoreNetto().doubleValue());
		setValoreIva(-1d * mIva.getValoreIva().doubleValue());
	}

	@Override
	public boolean isEmpty() {
		boolean padre = super.isEmpty();

		double sommaValori =
				Math.abs( 100d * getValoreEuro().doubleValue() ) +
				Math.abs( 100d * getValoreIva().doubleValue() )
				;

		return padre && sommaValori < 1 ;
	}
}
