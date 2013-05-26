package com.gsoft.doganapt.data;

import com.gsoft.doganapt.data.adapters.ConsegnaAdapter;
import com.gtsoft.utils.common.FormattedDate;
import com.gtsoft.utils.common.ModelBean2;

public abstract class Movimento extends ModelBean2 {

	public static final String POS_DOGANALE_NAZIONALIZZATA = "NAZ";
	public static final String POS_DOGANALE_EXTRACOMUNITARIA = "EXT";
	public static final String POS_DOGANALE_COMUNITARIA = "COM";

	public static final String REG_DOGANALE_NAZIONALIZZATA = "4571";
	public static final String REG_DOGANALE_EXTRACOMUNITARIA = "7100";
	public static final String REG_DOGANALE_COMUNITARIA = "4500";

	Integer id ;

	Integer idmerce;
	FormattedDate data;
	Integer idstallo;
	Integer idconsegna;
	Stallo stallo ;
	Boolean isScarico = Boolean.FALSE;
	Boolean isRettifica = Boolean.FALSE;
	Double secco;
	Double umido;

	Long numRegistro;

	String note ;

	Boolean isLocked;

	Documento documento ;
	Documento documentoPV ;



	boolean registrazione = false;

	public Integer getId() {
		return id;
	}
	public void setId(final Integer id) {
		this.id = id;
	}
	public Integer getIdConsegna() {
		return idconsegna;
	}
	public void setIdConsegna(final Integer id) {
		idconsegna = id;
	}
	public Consegna  getConsegna () {
		return ConsegnaAdapter.get(idconsegna);
	}
	public Integer getIdMerce() {
		return idmerce;
	}
	public void setIdMerce(final Integer idmerce) {
		this.idmerce = idmerce;
	}
	public FormattedDate getData() {
		return data;
	}
	public void setData(final FormattedDate data) {
		this.data = data;
	}
	public Integer getIdStallo() {
		return idstallo;
	}
	public void setIdStallo(final Integer idstallo) {
		this.idstallo = idstallo;
	}
	public Stallo getStallo() throws Exception {
		if ( stallo == null && idstallo != null ) {
			stallo = (Stallo) Stallo.newAdapter().getByKey(idstallo);
		}
		return stallo;
	}
	public void setStallo(final Stallo s) {
		stallo = s;
		if ( s != null ) {
			idstallo = s.getId();
		}
	}
	public Boolean getIsScarico() {
		return isScarico;
	}
	public void setIsScarico(final Boolean isScarico) {
		this.isScarico = isScarico;
	}
	public Boolean getIsRettifica() {
		return isRettifica;
	}
	public void setIsRettifica(final Boolean isRettifica) {
		this.isRettifica = isRettifica;
	}
	public Double getSecco() {
		return secco;
	}
	public void setSecco(final Double secco) {
		this.secco = secco;
	}
	public Double getUmido() {
		return umido;
	}
	public void setUmido(final Double umido) {
		this.umido = umido;
	}

	public boolean isAppenaRegistrato() {
		return registrazione ;
	}

	public Boolean getIsLocked() {
		return isLocked;
	}
	public void setIsLocked(final Boolean isLocked) {
		this.isLocked = isLocked;
	}
	public Documento getDocumento() {
		return documento;
	}
	public void setDocumento(final Documento documento) {
		this.documento = documento;
	}
	public Documento getDocumentoPV() {
		return documentoPV;
	}
	public void setDocumentoPV(final Documento documento) {
		documentoPV = documento;
	}
	public Long getNumRegistro() {
		return numRegistro;
	}
	public void setNumRegistro(final Long n) {
		if ( numRegistro == null && n != null ) {
			registrazione = true ;
		}

		numRegistro = n;
	}

	public String getPosizioneDoganale() {
		return "";
	}
	public String getRegimeDoganale() {
		return "";
	}

	public String getNote() {
		return note;
	}
	public void setNote(final String note) {
		this.note = note;
	}

	public Integer getTipo() {
		int tipo = 0 ;

		if ( isScarico ) {
			tipo = 1 ;
		}

		if ( isRettifica ) {
			tipo += 10 ;
		}

		return new Integer(tipo);
	}

	public void aggiungi(Movimento m) {
		setUmido( Math.round( 100 * getUmido().doubleValue() + 100 * m.getUmido().doubleValue() ) / 100d ) ;
		setSecco( Math.round( 100 * getSecco().doubleValue() + 100 * m.getSecco().doubleValue() ) / 100d ) ;
	}

	public void togli(Movimento m) {
		setUmido( Math.round( 100 * getUmido().doubleValue() - 100 * m.getUmido().doubleValue() ) / 100d ) ;
		setSecco( Math.round( 100 * getSecco().doubleValue() - 100 * m.getSecco().doubleValue() ) / 100d ) ;
	}

	public void copiaPesiEValoriInvertiti(Movimento m) {
		setUmido( -1d * m.getUmido().doubleValue() );
		setSecco( -1d * m.getSecco().doubleValue() );
	}

	@Override
	public abstract Movimento clone() ;


	public boolean isScaricoONegativo() {
		return getIsScarico() || getSecco().doubleValue() < 0;
	}

	public boolean isEmpty() {
		double sommaPesi =
				Math.abs( getSecco().intValue() ) +
				Math.abs( getUmido().intValue() )
				;
		return sommaPesi < 1 ;
	}

	public void aggiustaGiacenza(Movimento giacenza) {
		if ( sommareValoreGiacente( giacenza.getUmido() ) ) {
			setUmido(sommaArrotondata(getUmido(), giacenza.getUmido()));
		} else {
			setUmido(sottrazioneArrotondata(getUmido(), giacenza.getUmido()));
		}

		if ( sommareValoreGiacente( giacenza.getSecco() ) ) {
			setSecco(sommaArrotondata(getSecco(), giacenza.getSecco()));
		} else {
			setSecco(sottrazioneArrotondata(getSecco(), giacenza.getSecco()));
		}
	}

	protected boolean sommareValoreGiacente( Double v ) {
		return ( v.doubleValue() > 0 && isScaricoONegativo()
				||
				v.doubleValue() < 0 && ! isScaricoONegativo() );
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

}
