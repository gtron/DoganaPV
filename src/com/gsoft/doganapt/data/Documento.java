package com.gsoft.doganapt.data;

import com.gtsoft.utils.common.FormattedDate;

public class Documento {
	
	FormattedDate data;
	String numero = "";
	String tipo = "";
	
	public Documento() {
		super();
	}
	
	public static Documento getDocumento(String tipo, FormattedDate data, String numero ) {
		
		if ( 
				( tipo != null && tipo.length() > 0 ) 
				 || data != null
				 || numero != null  
			) {
			Documento d = new Documento() ; 
			d.setTipo(tipo);
			d.setData(data);
			d.setNumero(numero) ;
			
			return d ;
		}
		return null ;
	}
	
	public FormattedDate getData() {
		return data;
	}
	public void setData(FormattedDate data) {
		this.data = data;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String toString() {
		
		StringBuilder sb = new StringBuilder(25);
		if ( tipo != null ) 
			sb.append(tipo);
		
		if ( numero != null  )
			sb.append(" n.")
				.append(numero);
			
		if ( data != null ) 
			sb.append(" del ")
				.append(data.dmyString());
		
		return sb.toString();
	}
	
}