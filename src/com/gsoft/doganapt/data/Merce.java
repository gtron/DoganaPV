package com.gsoft.doganapt.data;

import com.gsoft.doganapt.data.adapters.MerceAdapter;
import com.gtsoft.utils.common.ModelBean2;
import com.gtsoft.utils.sql.IDatabase2;

public class Merce extends ModelBean2 {
	
	Integer id ;
	
	String nome; 	
	String colore; 
	Integer idIterDefault;
	String codiceQuadrelli;
	
	
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
		this.idIterDefault = iterDefault;
	}
	public String getCodiceQuadrelli() {
		return codiceQuadrelli;
	}
	
	public String toString() {
		return this.nome ;
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
