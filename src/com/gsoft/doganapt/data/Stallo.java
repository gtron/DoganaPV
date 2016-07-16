package com.gsoft.doganapt.data;

import java.util.Vector;

import com.gsoft.doganapt.data.adapters.ConsegnaAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoDoganaleAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoIvaAdapter;
import com.gsoft.doganapt.data.adapters.StalloAdapter;
import com.gtsoft.utils.common.FormattedDate;
import com.gtsoft.utils.common.ModelBean2;
import com.gtsoft.utils.sql.IDatabase2;

public class Stallo extends ModelBean2 implements Cloneable {

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
	
	FormattedDate dataRiferimento = null;

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
	
	public FormattedDate getDataRiferimento() {
		return dataRiferimento;
	}
	public void setDataRiferimento(FormattedDate dataRiferimento) {
		this.dataRiferimento = dataRiferimento;
	}

	@Override
	public String toString() {
		return parco + " " + numero ;
	}

	public boolean isLibero() {
		return getIdConsegnaAttuale() == null ;
	}

//	public Double getGiacenza(MovimentoAdapter registro, boolean secco, FormattedDate data) throws Exception {
	public Double getGiacenza(MovimentoAdapter registro, boolean secco) throws Exception {
		
		Integer idConsegna = idConsegnaAttuale;
		
//		if ( data != null ) {
//			idConsegna = registro.getIdConsegnaInStalloAllaData(id, data);
//		}
		
		@SuppressWarnings("unchecked")
		Vector<Movimento> v = registro.getByConsegna(true, idConsegna, id, null, null );

		Movimento giacenza = Movimento.getMovimentoRisultante(v, getDataRiferimento());

		if ( secco )
			return giacenza.getSecco();
		else
			return giacenza.getUmido();
	}

	public Double getGiacenzaIva(boolean secco) throws Exception {
		return getGiacenza(new MovimentoIvaAdapter(), secco);
	}
	public Double getGiacenzaDoganale(boolean secco) throws Exception {
		return getGiacenza(new MovimentoDoganaleAdapter(), secco);
	}
	
//	public Double getGiacenzaIva(boolean secco, FormattedDate data) throws Exception {
//		return getGiacenza(new MovimentoIvaAdapter(), secco, data);
//	}
//	public Double getGiacenzaDoganale(boolean secco, FormattedDate data) throws Exception {
//		return getGiacenza(new MovimentoDoganaleAdapter(), secco, data);
//	}

	public MovimentoDoganale getMovimentoGiacenzaDoganale(MovimentoDoganaleAdapter registro, Consegna consegna) throws Exception {
		return (MovimentoDoganale) getMovimentoGiacenza(registro, consegna);
	}
	public MovimentoIVA getMovimentoGiacenzaIva(MovimentoIvaAdapter registro, Consegna consegna) throws Exception {
		return (MovimentoIVA) getMovimentoGiacenza(registro, consegna);
	}

	private Movimento getMovimentoGiacenza(MovimentoAdapter registro, Consegna consegna) throws Exception {

		Movimento mov = registro.newMovimento();

		@SuppressWarnings("unchecked")
		Vector<Movimento> v = registro.getByConsegna(true, consegna.getId() , id, null, null );

		for (Movimento m : v) {
			if ( m.isScaricoONegativo() ) {
				if ( m.getIsRettifica() && m.getSecco().doubleValue() < 0 ) {
					// se è una rettifica negativa va sommato ... credo ...
					mov.aggiungi(m);
				} else {
					mov.togli(m);
				}
			} else {
				mov.aggiungi(m);
			}
		}

		return mov ;
	}


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

	@Override
	public Stallo clone() throws CloneNotSupportedException {
		return (Stallo) super.clone();
	}

}