package com.gsoft.doganapt.data;

import java.util.ArrayList;
import java.util.Iterator;

import com.gsoft.doganapt.data.adapters.IterAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoDoganaleAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoIvaAdapter;
import com.gsoft.doganapt.data.adapters.StalloAdapter;
import com.gsoft.pt_movimentazioni.data.MovimentoQuadrelliAdapter;
import com.gsoft.pt_movimentazioni.utils.IterImporter;
import com.gsoft.pt_movimentazioni.utils.IterImporter_COM;
import com.gsoft.pt_movimentazioni.utils.IterImporter_EXT;
import com.gsoft.pt_movimentazioni.utils.IterImporter_GLEN;
import com.gsoft.pt_movimentazioni.utils.IterImporter_NOGLEN;
import com.gtsoft.utils.common.FormattedDate;
import com.gtsoft.utils.common.ModelBean2;
import com.gtsoft.utils.sql.IDatabase2;

public class Iter extends ModelBean2 {
	
	Integer id ;
	
	String nome; 
	String descrizione; 
	Boolean ispesofattura;
	Boolean ispesobolla; 
	Boolean regdoganale; 
	Boolean regiva; 
	Boolean hasrettifica;
	Boolean singoliCarichi ;
	String queryin; 
	String queryout;
	
	
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
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public Boolean getIspesofattura() {
		return ispesofattura;
	}
	public void setIspesofattura(Boolean ispesofattura) {
		this.ispesofattura = ispesofattura;
	}
	public Boolean getIspesobolla() {
		return ispesobolla;
	}
	public void setIspesobolla(Boolean s) {
		this.ispesobolla = s;
	}
	public Boolean getIsSingoliCarichi() {
		return singoliCarichi;
	}
	public void setIsSingoliCarichi(Boolean s) {
		this.singoliCarichi = s;
	}
	public Boolean getRegdoganale() {
		return regdoganale;
	}
	public void setRegdoganale(Boolean regdoganale) {
		this.regdoganale = regdoganale;
	}
	public Boolean getRegiva() {
		return regiva;
	}
	public void setRegiva(Boolean regiva) {
		this.regiva = regiva;
	}
	public Boolean getHasrettifica() {
		return hasrettifica; // dovrebbe non essere + utile 
	}
	public void setHasrettifica(Boolean hasrettifica) {
		this.hasrettifica = hasrettifica;
	}
	public String getQueryin() {
		return queryin;
	}
	public void setQueryin(String queryin) {
		this.queryin = queryin;
	}
	public String getQueryout() {
		return queryout;
	}
	public void setQueryout(String queryout) {
		this.queryout = queryout;
	}
	
	public String toString() {
		return this.nome ;
	}
	
	public static synchronized IterAdapter newAdapter() throws Exception {		
		return new IterAdapter() ;
	}
	public static synchronized IterAdapter newAdapter(IDatabase2 db) throws Exception {		
		return new IterAdapter(db) ;
	}
	
//	final static String PARCHI = "'101','102','103','104','105','106','107','108','109','110','111','112','113','114','115','116','904','905','906','907','908','909','910','911','912','931','932','933'";
	
	
	public String getQueryin( Consegna c, Stallo s, FormattedDate data ) {
		
		// Da cambiare la gestione dei parchi ... sono nella consegna 

		return queryin
			.replaceAll("%C%", c.getNumero().toString() )
//			.replaceAll("%SS%", PARCHI )
			.replaceAll("%S%", ( s != null ) ? s.codice : "" )
			.replaceAll("%ANDS%", ( s != null ) ? " and cliente  = '"+
					s.codice + "' " : "" )
			.replaceAll("%M%", c.getMerce().getCodiceQuadrelli() )
			.replaceAll("%D%", (data != null)? data.ymdString() : "1900-01-01" ) 
			;
		
	}
	public String getQueryout( Stallo s, FormattedDate data ) {
		
		return queryout
			.replaceAll("%C%", s.getConsegna().getNumero().toString() )
//			.replaceAll("%SS%", PARCHI )
			.replaceAll("%S%", ( s != null ) ? s.codice : "" )
			.replaceAll("%M%", s.getConsegna().getMerce().getCodiceQuadrelli() )
			.replaceAll("%D%", (data != null)? data.ymdString() : "1900-01-01" ) ;
		
	}
	
	public IterImporter getImporter(MovimentoDoganaleAdapter dog, MovimentoIvaAdapter iva,
			MovimentoQuadrelliAdapter q) {
		
		switch ( id ) {
			case 1 :
				return new IterImporter_EXT(dog, iva, q) ;
				
			case 2 :
				return new IterImporter_COM(dog, iva, q) ;
				
			case 3 :
				return new IterImporter_GLEN(dog, iva, q) ;
				
			case 4 :
				return new IterImporter_NOGLEN(dog, iva, q) ;
		}
		
		return null ;
	}
	
	public ArrayList<Object> getStalli( Integer idConsegna) throws Exception {
		ArrayList<Object> list = null ;
		ArrayList<Integer> id = null ;
		
		if ( regdoganale ) 
			id = MovimentoDoganale.newAdapter().getIdStalli(idConsegna) ;
		else 
			id = MovimentoIVA.newAdapter().getIdStalli(idConsegna) ;
		
		
		if ( id != null ) {
			list = new ArrayList<Object>(id.size());
			
			for( Iterator<Integer> i = id.iterator(); i.hasNext(); ) 
				list.add(StalloAdapter.get(i.next()));
		}
		
		return list ;
	}
}
