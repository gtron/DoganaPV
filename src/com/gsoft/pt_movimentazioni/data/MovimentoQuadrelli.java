package com.gsoft.pt_movimentazioni.data;


import java.io.Serializable;

import com.gsoft.doganapt.data.Merce;
import com.gtsoft.utils.common.FormattedDate;
import com.gtsoft.utils.sql.IDatabase2;


@SuppressWarnings("serial")
public class MovimentoQuadrelli implements Serializable {

	Integer id ;
	Double numeroProgressivo = null ;
	String codiceCliente= null;
	FormattedDate data= null;
	FormattedDate dataPesatura= null;
	FormattedDate dataScarico= null;
	FormattedDate dataFormulario= null;
	String descrizione= null;
	Integer destinazione= null;
	String codiceFornitore = null;
	Merce merce= null;
	String codiceMerce= null;
	Integer codiceNave= null;
	Double netto = null ;
	String consegna= null;
	String documento= null;
	Integer numeroCataste = 0 ;

	Integer pesoFattura= null;
	Integer pesoPartenza= null;
	String codiceVettore  = null;

	String note = null ;
	FormattedDate data_em_form = null ;

	/**
	 * Gets the current value of data
	 * @return Current value of data
	 */
	public FormattedDate getData() {
		return data;
	}

	public String getNote() {
		return note;
	}
	public void setNote(final String note) {
		this.note = note;
	}

	public FormattedDate getData_em_form() {
		return data_em_form;
	}

	public void setData_em_form(final FormattedDate data_em_form) {
		this.data_em_form = data_em_form;
	}

	/**
	 * Sets the value of data
	 * @param data New value for data
	 */
	public void setData(final FormattedDate data) {
		this.data=data;
	}

	/**
	 * Gets the current value of dataPesatura
	 * @return Current value of dataPesatura
	 */
	public FormattedDate getDataPesatura() {
		return dataPesatura;
	}

	/**
	 * Sets the value of dataPesatura
	 * @param dataPesatura New value for dataPesatura
	 */
	public void setDataPesatura(final FormattedDate dataPesatura) {
		this.dataPesatura=dataPesatura;
	}

	public FormattedDate getDataFormulario() {
		return dataFormulario;
	}
	public void setDataFormulario(final FormattedDate dataFormulario) {
		this.dataFormulario = dataFormulario;
	}
	public FormattedDate getDataScarico() {
		return dataScarico;
	}
	public void setDataScarico(final FormattedDate dataScarico) {
		this.dataScarico = dataScarico;
	}
	/**
	 * Gets the current value of descrizione
	 * @return Current value of descrizione
	 */
	public String getDescrizione() {
		return descrizione;
	}

	/**
	 * Sets the value of descrizione
	 * @param descrizione New value for descrizione
	 */
	public void setDescrizione(final String descrizione) {
		this.descrizione=descrizione;
	}

	/**
	 * Gets the current value of netto
	 * @return Current value of netto
	 */
	public Double getNetto() {
		return netto;
	}

	/**
	 * Sets the value of netto
	 * @param netto New value for netto
	 */
	public void setNetto(final Double netto) {
		this.netto=netto;
	}

	public void setNetto(final String netto) {
		this.netto= new Double( netto ).doubleValue();
	}

	/**
	 * Gets the current value of consegna
	 * @return Current value of consegna
	 */
	public String getConsegna() {
		return consegna;
	}

	/**
	 * Sets the value of consegna
	 * @param consegna New value for consegna
	 */
	public void setConsegna(final String consegna) {
		this.consegna=consegna;
	}

	/**
	 * Gets the current value of documento
	 * @return Current value of documento
	 */
	public String getDocumento() {
		return documento;
	}

	/**
	 * Sets the value of documento
	 * @param documento New value for documento
	 */
	public void setDocumento(final String documento) {
		this.documento=documento;
	}

	/**
	 * Gets the current value of numeroCatastale
	 * @return Current value of numeroCatastale
	 */
	public Integer getNumeroCataste() {
		return numeroCataste;
	}

	/**
	 * Sets the value of numeroCatastale
	 * @param numeroCatastale New value for numeroCatastale
	 */
	public void setNumeroCataste(final Integer numeroCataste) {
		this.numeroCataste=numeroCataste;
		if ( this.numeroCataste == null ) {
			this.numeroCataste = new Integer(0);
		}

	}

	/**
	 * Gets the current value of numeroProgressivo
	 * @return Current value of numeroProgressivo
	 */
	public Double getNumeroProgressivo() {
		return numeroProgressivo;
	}

	/**
	 * Sets the value of numeroProgressivo
	 * @param numeroProgressivo New value for numeroProgressivo
	 */
	public void setNumeroProgressivo(final Double numeroProgressivo) {
		this.numeroProgressivo=numeroProgressivo;
	}
	public void setNumeroProgressivo( final String numeroProgressivo) {
		try {
			this.numeroProgressivo = new Double( numeroProgressivo );
		}
		catch (final Exception e ) {
			this.numeroProgressivo = new Double(0) ;
		}
	}
	/**
	 * Gets the current value of pesoFattura
	 * @return Current value of pesoFattura
	 */
	public double getPesoFattura() {
		return pesoFattura;
	}
	/**
	 * Sets the value of pesoFattura
	 * @param pesoFattura New value for pesoFattura
	 */
	public void setPesoFattura(final Integer pesoFattura) {
		this.pesoFattura=pesoFattura;
	}
	public void setPesoFattura( final String p ) {
		try {
			pesoFattura = new Integer( p );
		}
		catch (final Exception e ) {
			pesoFattura = null ;
		}
	}

	/**
	 * Gets the current value of pesoPartenza
	 * @return Current value of pesoPartenza
	 */
	public Integer getPesoPartenza() {
		return pesoPartenza;
	}

	/**
	 * Sets the value of pesoPartenza
	 * @param pesoPartenza New value for pesoPartenza
	 */
	public void setPesoPartenza(final Integer pesoPartenza) {
		this.pesoPartenza=pesoPartenza;
	}
	public void setPesoPartenza( final String p ) {
		try {
			pesoPartenza = new Integer( p );
		}
		catch (final Exception e ) {
			pesoPartenza = null ;
		}
	}

	public String getCodiceCliente() {
		return codiceCliente ;
	}
	public void setCodiceCliente( final String codiceCliente ) {
		this.codiceCliente = codiceCliente ;
	}

	//}}

	//{{ DESTINAZIONE
	public Integer getDestinazione() {
		return destinazione;
	}

	/**
	 * Sets the value of destinazione
	 * @param destinazione New value for destinazione
	 */
	public void setDestinazione(final Integer destinazione) {
		this.destinazione=destinazione;
	}
	//}}


	//{{ FORNITORE
	public String getCodiceFornitore() {
		return codiceFornitore;
	}
	public void setCodiceFornitore(final String codFornitore) {
		codiceFornitore = codFornitore;
	}
	//}}

	//{{ MERCE
	public Merce getMerce() throws Exception {
		if ( merce == null ){
			merce = (Merce) Merce.newAdapter().getByKey( codiceMerce ) ;
		}
		return merce;
	}
	public String getCodiceMerce() {
		return codiceMerce;
	}


	/**
	 * Sets the value of merce
	 * @param merce New value for merce
	 */
	public void setMerce(final Merce merce) {
		this.merce=merce;
		codiceMerce = merce.getCodiceQuadrelli() ;
	}

	public void setMerce(final String codiceMerce) {
		this.codiceMerce = codiceMerce ;
		merce = null ;
	}
	//}}


	//{{ NAVE
	/**
	 * Gets the current value of nave
	 * @return Current value of nave
	 */
	public Integer getCodiceNave() {
		return codiceNave;
	}
	/**
	 * Sets the value of nave
	 * @param nave New value for nave
	 */
	public void setNave(final Integer codNave ) {
		codiceNave = codNave;
	}
	//}}


	//{{ VETTORE
	public String getCodiceVettore() {
		return codiceVettore;
	}

	/**
	 * Sets the value of vettore
	 * @param vettore New value for vettore
	 */
	public void setVettore(final String idVettore) {
		codiceVettore = idVettore ;
	}

	//}}

	/**
	 * @return Restituisce il valore di id.
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id Imposta il valore per id .
	 */
	public void setId(final Integer id) {
		this.id = id ;
	}

	public static synchronized MovimentoQuadrelliAdapter newAdapter() throws Exception {
		return new MovimentoQuadrelliAdapter() ;
	}
	public static synchronized MovimentoQuadrelliAdapter newAdapter(final IDatabase2 db) throws Exception {
		return new MovimentoQuadrelliAdapter(db) ;
	}

	public static Integer DESTINAZIONE_RETTIFICA = new Integer(8);
	public static String DESTINAZIONE_RETTIFICA_STRING = "8";

}