package com.gsoft.doganapt.data;

import java.io.Serializable;

import com.gsoft.doganapt.data.adapters.StalloConsegnaAdapter;
import com.gtsoft.utils.common.FormattedDate;
import com.gtsoft.utils.common.ModelBean2;
import com.gtsoft.utils.sql.IDatabase2;

@SuppressWarnings("serial")
public class StalloConsegna extends ModelBean2 implements Serializable {

	Integer id ;
	Integer idConsegna ;
	Integer idStallo ;
	Boolean isInLiberaPratica;
	Double valoreEuro;
	Double valoreDollari;
	Double valoreUnitarioEuro;
	Double valoreUnitarioDollari;
	Double tassoEuroDollaro;

	FormattedDate dataImmissione;

	private static final int PRECISIONE_TASSO_CAMBIO = 10000 ;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdConsegna() {
		return idConsegna;
	}
	public void setIdConsegna(Integer idConsegna) {
		this.idConsegna = idConsegna;
	}
	public Integer getIdStallo() {
		return idStallo;
	}
	public void setIdStallo(Integer idStallo) {
		this.idStallo = idStallo;
	}
	public Boolean getIsInLiberaPratica() {
		return isInLiberaPratica;
	}
	public void setIsInLiberaPratica(Boolean isInLiberaPratica) {
		this.isInLiberaPratica = isInLiberaPratica;
	}
	public Double getValoreEuro() {
		return valoreEuro;
	}
	public void setValoreEuro(Double valoreEuro) {
		this.valoreEuro = valoreEuro;
	}
	public Double getValoreDollari() {
		return valoreDollari;
	}
	public void setValoreDollari(Double valoreDollari) {
		this.valoreDollari = valoreDollari;
	}
	public Double getValoreUnitarioEuro() {
		return valoreUnitarioEuro;
	}
	public void setValoreUnitarioEuro(Double valoreUnitarioEuro) {
		this.valoreUnitarioEuro = valoreUnitarioEuro;
	}
	public Double getValoreUnitarioDollari() {
		return valoreUnitarioDollari;
	}
	public void setValoreUnitarioDollari(Double valoreUnitarioDollari) {
		this.valoreUnitarioDollari = valoreUnitarioDollari;
	}
	public FormattedDate getDataImmissione() {
		return dataImmissione;
	}
	public void setDataImmissione(FormattedDate dataImmissione) {
		this.dataImmissione = dataImmissione;
	}

	public static synchronized StalloConsegnaAdapter newAdapter() throws Exception {
		return new StalloConsegnaAdapter() ;
	}
	public static synchronized StalloConsegnaAdapter newAdapter(final IDatabase2 db) throws Exception {
		return new StalloConsegnaAdapter(db) ;
	}


	public void assegnaValori(MovimentoIVA miva) {
		miva.setValoreDollari( getValoreUnitarioDollari() * miva.getSecco().doubleValue());
		miva.setValoreEuro( getValoreUnitarioEuro() * miva.getSecco().doubleValue());
	}
	public void setTassoEuroDollaro(Double tassoCambio) {
		tassoEuroDollaro = tassoCambio;
	}
	public Double getTassoEuroDollaro() {
		return tassoEuroDollaro;
	}
	public void setTassoEuroDollaroDB(Integer tasso) {
		if ( tasso == null ) {
			tassoEuroDollaro = null ;
			return;
		}
		tassoEuroDollaro = new Double( tasso / PRECISIONE_TASSO_CAMBIO );
	}
	public void setTassoEuroDollaroDB(Long tasso) {
		if ( tasso == null ) {
			tassoEuroDollaro = null ;
			return;
		}
		tassoEuroDollaro = new Double( 1.0d * tasso / PRECISIONE_TASSO_CAMBIO );
	}
	public Integer getTassoEuroDollaroDB() {
		if (tassoEuroDollaro == null ) return null;
		return new Double( tassoEuroDollaro.doubleValue() * PRECISIONE_TASSO_CAMBIO  ).intValue();
	}

	public void updateValore(MovimentoIVA m) {
		if ( getValoreUnitarioDollari() == null && getValoreUnitarioEuro() == null ) return ;
		if ( m.getSecco() == null ) return ;

		// VERIFICARE !!!
		double valoreEuro = new Double(
				Math.round( 100000000 * m.getSecco().doubleValue() * getValoreUnitarioEuro().doubleValue() ) / 100000000
				) ;
		double valoreDollari = new Double(
				Math.round( 100000000 * m.getSecco().doubleValue() * getValoreUnitarioDollari().doubleValue() ) / 100000000
				) ;

		m.setValoreDollari( valoreDollari );
		m.setValoreEuro(valoreEuro);
	}

}

