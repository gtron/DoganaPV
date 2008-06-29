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
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdConsegna() {
		return idconsegna;
	}
	public void setIdConsegna(Integer id) {
		this.idconsegna = id;
	}
	public Consegna  getConsegna () {
		return ConsegnaAdapter.get(idconsegna);
	}
	public Integer getIdMerce() {
		return idmerce;
	}
	public void setIdMerce(Integer idmerce) {
		this.idmerce = idmerce;
	}
	public FormattedDate getData() {
		return data;
	}
	public void setData(FormattedDate data) {
		this.data = data;
	}
	public Integer getIdStallo() {
		return idstallo;
	}
	public void setIdStallo(Integer idstallo) {
		this.idstallo = idstallo;
	}
	public Stallo getStallo() throws Exception {
		if ( stallo == null && idstallo != null )
			stallo = (Stallo) Stallo.newAdapter().getByKey(idstallo);
		return stallo;
	}
	public void setStallo(Stallo s) {
		this.stallo = s;
		if ( s != null )
			this.idstallo = s.getId();
	}
	public Boolean getIsScarico() {
		return isScarico;
	}
	public void setIsScarico(Boolean isScarico) {
		this.isScarico = isScarico;
	}
	public Boolean getIsRettifica() {
		return isRettifica;
	}
	public void setIsRettifica(Boolean isRettifica) {
		this.isRettifica = isRettifica;
	}
	public Double getSecco() {
		return secco;
	}
	public void setSecco(Double secco) {
		this.secco = secco;
	}
	public Double getUmido() {
		return umido;
	}
	public void setUmido(Double umido) {
		this.umido = umido;
	}
	
	public boolean isAppenaRegistrato() {
		return registrazione ;
	}

	public Boolean getIsLocked() {
		return isLocked;
	}
	public void setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
	}
	public Documento getDocumento() {
		return documento;
	}
	public void setDocumento(Documento documento) {
		this.documento = documento;
	}
	public Documento getDocumentoPV() {
		return documentoPV;
	}
	public void setDocumentoPV(Documento documento) {
		this.documentoPV = documento;
	}
	public Long getNumRegistro() {
		return numRegistro;
	}
	public void setNumRegistro(Long n) {
		if ( this.numRegistro == null && n != null )
			registrazione = true ;
		
		this.numRegistro = n;
	}
	
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	public Integer getTipo() {
		int tipo = 0 ;
		
		if ( isScarico ) tipo = 1 ;
		
		if ( isRettifica ) tipo += 10 ;
		
		return new Integer(tipo); 
	}
}
