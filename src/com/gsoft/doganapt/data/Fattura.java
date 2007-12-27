package com.gsoft.doganapt.data;


public class Fattura extends Documento {
	
	Boolean isprovvisoria;
	Double valore;
	Double valoredollari;
	
	public Boolean getIsprovvisoria() {
		return isprovvisoria;
	}
	public void setIsprovvisoria(Boolean isprovvisoria) {
		this.isprovvisoria = isprovvisoria;
	}
	public Double getValore() {
		return valore;
	}
	public void setValore(Double valore) {
		this.valore = valore;
	}
	public Double getValoredollari() {
		return valoredollari;
	}
	public void setValoredollari(Double valoredollari) {
		this.valoredollari = valoredollari;
	}


}
