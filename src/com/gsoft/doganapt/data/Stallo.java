package com.gsoft.doganapt.data;

import java.util.Iterator;
import java.util.Vector;

import com.gsoft.doganapt.data.adapters.ConsegnaAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoDoganaleAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoIvaAdapter;
import com.gsoft.doganapt.data.adapters.StalloAdapter;
import com.gtsoft.utils.common.FormattedDate;
import com.gtsoft.utils.common.ModelBean2;
import com.gtsoft.utils.sql.IDatabase2;

public class Stallo extends ModelBean2 {

	Integer id ;

	String nome;
	String parco;
	Integer numero ;
	Integer idConsegnaAttuale;
	Integer idConsegnaPrenotata;
	Double caricato;
	Double attuale;
	String codice;
	Boolean immessoInLiberaPratica ;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodice() {
		return codice;
	}
	public void setCodice(String c) {
		codice = c;
	}

	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getParco() {
		return parco;
	}
	public void setParco(String parco) {
		this.parco = parco;
	}
	public Integer getIdConsegnaAttuale() {
		return idConsegnaAttuale;
	}
	public void setIdConsegnaAttuale(Integer i) {
		idConsegnaAttuale = i;
	}
	public Consegna getConsegna() {
		return ConsegnaAdapter.get(idConsegnaAttuale);
	}
	public Integer getIdConsegnaPrenotata() {
		return idConsegnaPrenotata;
	}
	public void setIdConsegnaPrenotata(Integer i) {
		idConsegnaPrenotata = i;
	}
	public Consegna getConsegnaPrenotata() {
		return ConsegnaAdapter.get(idConsegnaPrenotata);
	}
	public Double getCaricato() {
		return caricato;
	}
	public void setCaricato(Double caricato) {
		this.caricato = caricato;
	}
	public Double getAttuale() {
		return attuale;
	}
	public void setAttuale(Double attuale) {
		this.attuale = attuale;
	}

	public Boolean getImmessoInLiberaPratica() {
		return immessoInLiberaPratica;
	}
	public Boolean getHasLiberaPratica(){
		return immessoInLiberaPratica ;
	}
	public void setImmessoInLiberaPratica(Boolean i) {
		immessoInLiberaPratica = i;
	}

	@Override
	public String toString() {
		return parco + " " + numero ;
	}

	public boolean isLibero() {
		return getIdConsegnaAttuale() == null ;
	}

	public Double getGiacenza(MovimentoAdapter registro, boolean secco) throws Exception {
		double sum = 0 ;
		double val = 0;
		@SuppressWarnings("unchecked")
		Vector<Movimento> v = registro.getByConsegna(true, idConsegnaAttuale, id, null, null );

		for (Movimento m : v) {
			if ( secco ) {
				val = m.getSecco().doubleValue() ;
			} else {
				val = m.getUmido().doubleValue() ;
			}
			if ( m.isScarico || m.getSecco().intValue() < 0 ) {
				if( m.getSecco().intValue() < 0 ) {
					sum += val ;
				} else {
					sum -= val ;
				}
			} else {
				sum += val ;
			}
		}
		return sum ;
	}
	public Double getGiacenzaIva(boolean secco) throws Exception {
		return getGiacenza(new MovimentoIvaAdapter(), secco);
	}
	public Double getGiacenzaDoganale(boolean secco) throws Exception {
		return getGiacenza(new MovimentoDoganaleAdapter(), secco);
	}

	//	public Vector getMovimenti(String order, String limit) throws Exception {
	//		return Movimento.newAdapter().getByStallo(this.id, order, limit );
	//	}

	public static synchronized StalloAdapter newAdapter() throws Exception {
		return new StalloAdapter() ;
	}
	public static synchronized StalloAdapter newAdapter(IDatabase2 db) throws Exception {
		return new StalloAdapter(db) ;
	}
	public void notifyMovimento(Movimento m, boolean update) throws Exception {
		if ( m != null ) {
			double amount = 0 ;
			if ( m.getIsScarico() ) {
				amount = -1 * m.getUmido() ;
			}
			else {
				amount = m.getUmido() ;
			}

			attuale = new Double( attuale.doubleValue() + amount ) ;
		}

		if ( update ) {
			Stallo.newAdapter().update(this);
		}
	}
	public void notifyRegistrazione(Movimento m, boolean update) throws Exception {
		if ( m != null ) {
			double amount = 0 ;
			if ( m.getIsScarico() ) {
				amount = -1 * m.getUmido() ;
			}
			else {
				amount = m.getUmido() ;
			}

			caricato = new Double( caricato.doubleValue() + amount ) ;
		}

		if ( update ) {
			Stallo.newAdapter().update(this);
		}
	}
	public void immettiInLiberaPratica( FormattedDate data, Documento doc, Documento docPV,
			MovimentoAdapter registroOut,
			MovimentoAdapter registroIn ) throws Exception {

		@SuppressWarnings("unchecked")
		Vector<Movimento> list = registroOut.getByConsegna( false, idConsegnaAttuale, id, null , null );

		double sommaUmido = 0 ;
		double sommaSecco = 0 ;
		Movimento m = null;

		if ( list != null && list.size() > 0 ) {
			for ( Iterator<Movimento> i = list.iterator() ; i.hasNext() ; ) {
				m = i.next();

				if ( m.getIsScarico() ) {
					sommaUmido -= m.getUmido().doubleValue() ;
					sommaSecco -= m.getSecco().doubleValue() ;
				}
				else {
					sommaUmido += m.getUmido().doubleValue() ;
					sommaSecco += m.getSecco().doubleValue() ;
				}

			}

			//		m = m.clone();
			m.setDocumento(doc);
			m.setDocumentoPV(docPV);
			m.setSecco(sommaSecco);
			m.setUmido(sommaUmido);
			m.setIsScarico(true);
			m.setNumRegistro(null);
			m.setIsRettifica(false);
			m.setData( data ) ;
			m.setId(null);

			registroOut.create(m);

			Movimento miva = registroIn.newMovimento();


			miva.setDocumento(doc);
			miva.setDocumentoPV(docPV);
			miva.setIsScarico(false);
			miva.setSecco(sommaSecco);
			miva.setUmido(sommaUmido);
			miva.setIdMerce(m.getIdMerce());
			miva.setNumRegistro(null);
			miva.setIdStallo(m.getIdStallo());
			miva.setIsRettifica(false);
			miva.setIdConsegna(m.getIdConsegna());
			miva.setData( data ) ;
			miva.setId(null);
			miva.setIsLocked(Boolean.FALSE);

			if( miva instanceof MovimentoIVA ) {
				m.getConsegna().updateValore((MovimentoIVA )miva);
			}

			registroIn.create(miva);
		}

	}
}