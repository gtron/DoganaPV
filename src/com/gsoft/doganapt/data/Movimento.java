package com.gsoft.doganapt.data;

import com.gsoft.doganapt.data.adapters.ConsegnaAdapter;
import com.gtsoft.utils.common.FormattedDate;
import com.gtsoft.utils.common.ModelBean2;

public abstract class Movimento extends ModelBean2 {

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

	@Override
	public abstract Movimento clone() ;
}
