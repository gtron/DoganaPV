package com.gsoft.doganapt.data;


public class PuntoRipristinoDb {
	
	Integer id ;
	
	String nomefile; 
	String descrizione; 
	
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
		this.nomefile = nome;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
}
