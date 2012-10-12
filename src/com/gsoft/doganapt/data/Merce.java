package com.gsoft.doganapt.data;

import com.gsoft.doganapt.data.adapters.MerceAdapter;
import com.gtsoft.utils.common.ModelBean2;
import com.gtsoft.utils.sql.IDatabase2;

public class Merce extends ModelBean2 {

	Integer id ;

	String nome;
	String descrizione;
	String colore;
	Integer idIterDefault;
	String codiceQuadrelli;
	String codiceTaric;

	String specieColli;

	Boolean hasRegistroIva = null;
	Boolean hasRegistroDog = null;

	public Boolean hasRegistro( Boolean isIva ) {

		return Boolean.TRUE;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String desc) {
		descrizione = desc;
	}
	public String getColore() {
		return colore;
	}
	public void setColore(String colore) {
		this.colore = colore;
	}
	public Integer getIdIterDefault() {
		return idIterDefault;
	}
	public void setIdIterDefault(Integer iterDefault) {
		idIterDefault = iterDefault;
	}
	public String getCodiceQuadrelli() {
		return codiceQuadrelli;
	}
	public String getCodiceTaric() {
		return codiceTaric;
	}
	public void setCodiceTaric(String codiceTaric) {
		this.codiceTaric = codiceTaric;
	}
	public String getSpecieColli() {
		return specieColli;
	}

	public void setSpecieColli(String specieColli) {
		this.specieColli = specieColli;
	}

	@Override
	public String toString() {
		return nome ;
	}

	public void setCodiceQuadrelli(String codiceQuadrelli) {
		this.codiceQuadrelli = codiceQuadrelli;
	}
	public static synchronized MerceAdapter newAdapter() throws Exception {
		return new MerceAdapter() ;
	}
	public static synchronized MerceAdapter newAdapter(IDatabase2 db) throws Exception {
		return new MerceAdapter(db) ;
	}

}
