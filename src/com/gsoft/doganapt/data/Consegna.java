package com.gsoft.doganapt.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import com.gsoft.doganapt.data.adapters.ConsegnaAdapter;
import com.gsoft.doganapt.data.adapters.IterAdapter;
import com.gsoft.doganapt.data.adapters.MerceAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoDoganaleAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoIvaAdapter;
import com.gsoft.doganapt.data.adapters.StalloAdapter;
import com.gtsoft.utils.common.FormattedDate;
import com.gtsoft.utils.common.ModelBean2;
import com.gtsoft.utils.sql.IDatabase2;

public class Consegna extends ModelBean2 {
	
	Integer id ;
	Integer numero;
	Integer numPartitario ;
	Integer idmerce;
	Integer idIter;
	FormattedDate dataCreazione;
	FormattedDate dataChiusura;
	
	Boolean pesofinaleportocarico;
	Double pesopolizza;
	
	String provenienza;
	String origine;
	String mezzo;
	String regimedoganale ;
	String codicenc;
	String posizione;
	
	Double tassoCambio;
	Double tassoUmidita;
	Double valoreUnitario;
	Boolean isValutaEuro;
	
	ArrayList<Object> stalli = null ;
	ArrayList<Object> stalliRegistrati = null ;
	
	Vector registroIva = null;
	Vector registroDoganale = null ;
	
	Merce merce = null ;
	Iter iter = null ;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	public Integer getNumeroPartitario() {
		return numPartitario;
	}
	public void setNumeroPartitario(Integer numero) {
		this.numPartitario = numero;
	}
	public Integer getIdmerce() {
		return idmerce;
	}
	public void setIdmerce(Integer idmerce) {
		this.idmerce = idmerce;
	}
	public Merce getMerce() {
		if ( merce == null && idmerce > 0 ) {
			merce = MerceAdapter.get(idmerce) ;
		}
		return merce ;
	}
	public Integer getIdIter() {
		return idIter;
	}
	public Iter getIter() {
		if ( iter == null && idIter > 0 ) {
			iter = IterAdapter.get(idIter) ;
		}
		return iter ;
	}
	public void setIdIter(Integer iter) {
		this.idIter = iter;
	}
	public FormattedDate getDataCreazione() {
		return dataCreazione;
	}
	public void setDataCreazione(FormattedDate datacreazione) {
		this.dataCreazione = datacreazione;
	}
	public FormattedDate getDataChiusura() {
		return dataChiusura;
	}
	public void setDataChiusura(FormattedDate d) {
		this.dataChiusura = d;
	}
	public boolean isChiusa() {
		return (dataChiusura != null && dataCreazione != null && dataChiusura.after(dataCreazione) );
	}
	public boolean isAperta() {
		return (dataChiusura == null  ); // || dataChiusura.getTime() == dataCreazione.getTime() ????
	}
	
	public boolean isPesoFinalePortoCarico() {
		return pesofinaleportocarico.booleanValue() ;
	}
	public Boolean getPesoFinalePortoCarico() {
		return pesofinaleportocarico;
	}
	public void setPesoFinalePortoCarico(Boolean b) {
		this.pesofinaleportocarico = b;
	}
	
	public Double getPesopolizza() {
		return pesopolizza;
	}
	public void setPesopolizza(Double pesopolizza) {
		this.pesopolizza = pesopolizza;
	}
	public String getProvenienza() {
		return provenienza;
	}
	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}
	public String getOrigine() {
		return origine;
	}
	public void setOrigine(String origine) {
		this.origine = origine;
	}
	public String getMezzo() {
		return mezzo;
	}
	public void setMezzo(String mezzo) {
		this.mezzo = mezzo;
	}
	public String getRegimedoganale() {
		return regimedoganale;
	}
	public void setRegimedoganale(String regimedoganale) {
		this.regimedoganale = regimedoganale;
	}
	public String getCodicenc() {
		return codicenc;
	}
	public void setCodicenc(String codicenc) {
		this.codicenc = codicenc;
	}
	public String getPosizione() {
		return posizione;
	}
	public void setPosizione(String p) {
		this.posizione = p;
	}
	public Double getTassoCambio() {
		return tassoCambio;
	}
	public void setTassoCambio(Double tassoCambio) {
		this.tassoCambio = tassoCambio;
	}
	public Double getTassoUmidita() {
		return tassoUmidita * 100 ;
	}
	public void setValoreUnitario(Double v) {
		this.valoreUnitario = v;
	}
	public Double getValoreUnitario() {
		return valoreUnitario ;
	}
	public void setIsValutaEuro(Boolean v) {
		this.isValutaEuro = v;
	}
	public Boolean getIsValutaEuro() {
		return isValutaEuro ;
	}
	
	public void updateValore( MovimentoIVA m ) {
		
		if ( getValoreUnitario() == null ) return ;
		if ( m.getSecco() == null ) return ;
		
		double valore = new Double( m.getSecco().doubleValue() * getValoreUnitario().doubleValue() ) ;
		
		if ( ! isValutaEuro.booleanValue() ) {
			m.setValoreDollari( valore );
			
			if ( tassoCambio == null ) return ;
			
			valore = valore / tassoCambio.doubleValue() ;
		}
		 
		m.setValoreEuro(valore);
	}
	
	public void setTassoUmidita(Double tassoUmidita) {
		
		if ( tassoUmidita.doubleValue() >= 100 ) {
			System.out.println("Tasso di umidità non valido ( >= 100 )");
			tassoUmidita = new Double(0);
		}
		
		this.tassoUmidita = tassoUmidita / 100 ;
	}
	public Double calcolaSecco( Double umido ) {
		if ( umido != null)
			return new Double( 
					Math.round( 
							umido.doubleValue() * 
							(double) Math.round(  10000 - 10000 * tassoUmidita.doubleValue() ) / 10000  ) );
		
		return null ;
	}
	

	public Documento getPrimoDocumento(boolean iva) throws Exception {

		Vector v = getRegistro(iva, false, false) ;
		Documento d = null ;
		if ( v != null && v.size() > 0 ) {
			d = (( Movimento ) v.firstElement()).getDocumento();
		}
		
		return d ;
	}
	
//	public Double getGiacenza(boolean iva , boolean secco ) throws Exception {
//
//		double sum = 0 ;
//		double val = 0;
//		Vector v = getRegistro(iva, false, false) ;
//		for ( Iterator i = v.iterator() ; i.hasNext() ; ) {
//
//			Movimento m = (Movimento) i.next(); 
//			
//			if ( secco ) 
//				val = m.getSecco().doubleValue() ;
//			else
//				val = m.getUmido().doubleValue() ;
//			
//			if ( m.isScarico ) {
//				sum -= val ;
//			}
//			else 
//				sum += val ;
//		}
//		
//		return sum ;
//	}


	public ArrayList<Object> getStalli() throws Exception {
		
		
		stalli = StalloAdapter.getByConsegna(getId());
			
		if( isChiusa() && (
				stalli == null || stalli.size() < 1 ) )
			stalli = getStalliRegistrati();
	
		return stalli;
	}
	
	public ArrayList<Object> getStalliRegistrati() throws Exception {
		if ( stalliRegistrati == null )
			stalliRegistrati = getIter().getStalli(getId());
		return stalliRegistrati;
	}

	public static synchronized ConsegnaAdapter newAdapter() throws Exception {		
		return new ConsegnaAdapter() ;
	}
	public static synchronized ConsegnaAdapter newAdapter(IDatabase2 db) throws Exception {		
		return new ConsegnaAdapter(db) ;
	}
	
	public Vector getRegistroIva( boolean soloRegistrati ) throws Exception {
		return getRegistro( true,  soloRegistrati , true);
	}
	public Vector getRegistroDoganale( boolean soloRegistrati ) throws Exception {
		return getRegistro( false, soloRegistrati , true);
	}
	
	public Vector getRegistro(boolean iva,  boolean soloRegistrati , boolean dontcache) throws Exception {
		MovimentoAdapter adp = null ;

		if ( dontcache || registroIva == null || registroDoganale == null) {
			if ( iva ) {
				adp = new MovimentoIvaAdapter();
			}
			else {
				adp = new MovimentoDoganaleAdapter();
			}
		}
		
		if ( dontcache )
			return adp.getRegistro(soloRegistrati, this);
		
		if ( iva ) {
			if (registroIva  == null )
				registroIva = adp.getRegistro(soloRegistrati, this);
			return registroIva ;
		}
		
		if ( registroDoganale == null )
			registroDoganale  = adp.getRegistro(soloRegistrati, this);
		
		return registroDoganale ;
	
		
		
	}
	
	
	

	public Vector getPartitario() throws Exception {
		
		Vector dog = new MovimentoDoganaleAdapter().getByConsegna(true, this.id, null, "idstallo, data" , null );
		Vector iva = new MovimentoIvaAdapter().getByConsegna(true, this.id, null, "idstallo, data" , null );
		
		if ( dog != null && dog.size() >  0 ) {
			if ( iva != null && iva.size() > 0 ) {
				dog.addAll(iva);
				Collections.sort(dog, new PartitarioSorter());
			}
		}
		else if ( iva != null && iva.size() > 0 ) {
				dog = iva ;
			}
		
		return dog;
	}
	
	private class PartitarioSorter implements Comparator {

		public int compare(Object o1, Object o2) {
			Movimento a = (Movimento) o1 ;
			Movimento b = (Movimento) o2 ;
			
			if (  a.idstallo != null && b.idstallo != null ) {
				if (a.idstallo > b.idstallo ) 
					return -1 ;
				else if ( a.idstallo < b.idstallo )
					return 1 ;
			}
			
			if ( a.data.after(b.data) ) {
				return 1 ;
			}
			else if ( b.data.after(a.data) ) {
				return -1 ;
			} 
			
			if ( a.isRettifica && ! b.isRettifica ) 
				return -1 ;
			else if ( b.isRettifica && ! a.isRettifica ) 
				return 1 ;
			
			if ( a.isScarico && ! b.isScarico ) 
				return -1 ;
			else if ( b.isScarico && ! a.isScarico ) 
				return 1 ;
			
			return 0 ;
		}
		
	}
}

