package com.gsoft.doganapt.data;

import com.gtsoft.utils.common.FormattedDate;


public class PuntoRipristinoDb {

	Integer id ;

	String nomefile;
	String descrizione;
	FormattedDate data;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNomeFile() {
		return nomefile;
	}
	public void setNomeFile(String nome) {
		nomefile = nome;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public void setData(FormattedDate data) {
		this.data = data;
	}
	public FormattedDate getData() {
		return data;
	}
}
