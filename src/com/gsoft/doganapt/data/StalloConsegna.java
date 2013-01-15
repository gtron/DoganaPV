package com.gsoft.doganapt.data;

import java.io.Serializable;

import com.gsoft.doganapt.data.adapters.StalloConsegnaAdapter;
import com.gtsoft.utils.common.ConfigManager;
import com.gtsoft.utils.common.FormattedDate;
import com.gtsoft.utils.common.ModelBean2;
import com.gtsoft.utils.sql.IDatabase2;

@SuppressWarnings("serial")
public class StalloConsegna extends ModelBean2 implements Serializable, Cloneable {

	Integer id ;
	Integer idConsegna ;
	Integer idStallo ;
	Boolean isInLiberaPratica;
	Double valoreEuro;
	Double valoreDollari;
	Double valoreUnitarioEuro;
	Double valoreUnitarioDollari;
	Double tassoEuroDollaro;

	Double aliquotaIva = null ;

	Double valoreTesTp;
	Double valoreUnitarioTesTp;


	FormattedDate dataImmissione;

	private static Double aliquotaAttuale = null;

	private static final int PRECISIONE_EURO = 100 ;
	private static final int PRECISIONE_DOLLARI = 100 ;

	private static final int PRECISIONE_TASSO_CAMBIO = 10000 ;
	private static final int PRECISIONE_ALIQUOTA_IVA = 100 ; // 0,21 (2 decimali)
	private static final String CONFIG_KEY_ALIQUOTA_IVA = "aliquotaIva";
	private static final String ALIQUOTA_IVA_FALLBACK = "0.21";

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
		if ( valoreEuro == null ) {
			this.valoreEuro = null;
			return;
		}
		this.valoreEuro = 1d * Math.round( PRECISIONE_EURO * valoreEuro ) / PRECISIONE_EURO;
	}
	public Double getValoreDollari() {
		return valoreDollari;
	}
	public void setValoreDollari(Double valoreDollari) {
		if ( valoreDollari == null ) {
			this.valoreDollari = null;
			return;
		}

		this.valoreDollari = 1d * Math.round( PRECISIONE_DOLLARI * valoreDollari ) / PRECISIONE_DOLLARI;
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
	public Double getValoreTesTp() {
		return valoreTesTp;
	}
	public void setValoreTesTp(Double valoreTesTp) {
		if ( valoreTesTp == null ) {
			this.valoreTesTp = null;
			return;
		}

		this.valoreTesTp = 1d * Math.round( PRECISIONE_EURO * valoreTesTp ) / PRECISIONE_EURO;
	}
	public Double getValoreUnitarioTesTp() {
		return valoreUnitarioTesTp;
	}
	public void setValoreUnitarioTesTp(Double valoreUnitarioTesTp) {
		this.valoreUnitarioTesTp = valoreUnitarioTesTp;
	}
	public static synchronized StalloConsegnaAdapter newAdapter() throws Exception {
		return new StalloConsegnaAdapter() ;
	}
	public static synchronized StalloConsegnaAdapter newAdapter(final IDatabase2 db) throws Exception {
		return new StalloConsegnaAdapter(db) ;
	}

	public Double getAliquotaIva() {
		if ( aliquotaIva == null ) {
			aliquotaIva = getAliquotaIvaAttuale();
		}

		return aliquotaIva;
	}
	public void setAliquotaIva(Double aliquotaIva) {
		this.aliquotaIva = aliquotaIva;
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

		assegnaValori(m);
	}

	public void assegnaValori(MovimentoIVA miva) {

		double valoreDollari = getValoreArrotondato(
				// peso secco in kg * valore di un kg in USD
				getValoreUnitarioDollari() * miva.getSecco().doubleValue()
				, PRECISIONE_DOLLARI );

		miva.setValoreDollari( Double.valueOf(valoreDollari));

		double valoreEuroNetto = getValoreArrotondato(
				valoreDollari / getTassoEuroDollaro().doubleValue()
				, PRECISIONE_EURO);

		miva.setValoreNetto( Double.valueOf(valoreEuroNetto));

		double valoreTestp = getValoreArrotondato(
				getValoreUnitarioTesTp().doubleValue() * miva.getSecco().doubleValue()
				, PRECISIONE_EURO);

		miva.setValoreTestp( valoreTestp );

		double sommaNetta = valoreEuroNetto + valoreTestp;
		miva.setValoreEuro(sommaNetta);

		double iva = getValoreArrotondato( sommaNetta * getAliquotaIva(), PRECISIONE_EURO);
		miva.setValoreIva(iva);


	}

	private double getValoreArrotondato(double val , int precisione ) {
		return 1d * Math.round( precisione * val) / precisione;
	}

	public static StalloConsegna getNew() {
		StalloConsegna sc = new StalloConsegna();
		sc.setAliquotaIva( getAliquotaIvaAttuale() );
		return sc ;
	}

	public static Double getAliquotaIvaAttuale() {

		if ( aliquotaAttuale == null ) {

			String aIva = ConfigManager.getProperty(CONFIG_KEY_ALIQUOTA_IVA);

			if ( aIva == null ) {
				aIva = ALIQUOTA_IVA_FALLBACK;
			}

			Double aliquota = Double.valueOf(aIva.replace(',','.'));

			long tmp = Math.round(
					aliquota.doubleValue() * PRECISIONE_ALIQUOTA_IVA );

			aliquotaAttuale = Double.valueOf( 1d * tmp / PRECISIONE_ALIQUOTA_IVA );

		}

		return aliquotaAttuale ;

	}
	public StalloConsegna initValori(Double pesoSecco) {
		double secco = pesoSecco.doubleValue();
		setValoreDollari(getValoreArrotondato( secco * getValoreUnitarioDollari().doubleValue(), PRECISIONE_DOLLARI) );
		setValoreEuro(getValoreArrotondato( secco * getValoreUnitarioEuro().doubleValue(), PRECISIONE_EURO) );
		setValoreTesTp(getValoreArrotondato( secco * getValoreUnitarioTesTp().doubleValue(), PRECISIONE_EURO) );

		return this;
	}

	public void initValoriUnitari( double sommaSeccoTotale ) throws Exception {

		if ( valoreDollari == null || valoreDollari < 1 )
			throw new Exception("Valore in USD non specificato!");

		if ( valoreTesTp == null || valoreTesTp < 1 )
			throw new Exception("Valore del TesTP non specificato!");

		if ( sommaSeccoTotale < 1 )
			throw new Exception("Valore del peso sommaSeccoTotale non specificato!");


		double valoreDollari = getValoreDollari().doubleValue();
		double tassoCambio = getTassoEuroDollaro().doubleValue();

		setValoreUnitarioDollari( valoreDollari / sommaSeccoTotale );
		setValoreUnitarioEuro( valoreDollari / tassoCambio );

		setValoreUnitarioTesTp( getValoreTesTp().doubleValue() / sommaSeccoTotale );
	}

	@Override
	public StalloConsegna clone() throws CloneNotSupportedException {

		return (StalloConsegna) super.clone();
	}

}

