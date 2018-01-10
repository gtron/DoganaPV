package com.gsoft.doganapt.data;

import java.io.Serializable;
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
import com.gsoft.doganapt.data.adapters.StalloConsegnaAdapter;
import com.gtsoft.utils.common.FormattedDate;
import com.gtsoft.utils.common.ModelBean2;
import com.gtsoft.utils.sql.IDatabase2;
@SuppressWarnings({"serial","unchecked"})
public class Consegna extends ModelBean2 implements Serializable {

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
	public void setId(final Integer id) {
		this.id = id;
	}
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(final Integer numero) {
		this.numero = numero;
	}
	public Integer getNumeroPartitario() {
		return numPartitario;
	}
	public void setNumeroPartitario(final Integer numero) {
		numPartitario = numero;
	}
	public Integer getIdmerce() {
		return idmerce;
	}
	public void setIdmerce(final Integer idmerce) {
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
	public void setIdIter(final Integer iter) {
		idIter = iter;
	}
	public FormattedDate getDataCreazione() {
		return dataCreazione;
	}
	public void setDataCreazione(final FormattedDate datacreazione) {
		dataCreazione = datacreazione;
	}
	public FormattedDate getDataChiusura() {
		return dataChiusura;
	}
	public void setDataChiusura(final FormattedDate d) {
		dataChiusura = d;
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
	public void setPesoFinalePortoCarico(final Boolean b) {
		if ( b == null ) {
			pesofinaleportocarico = Boolean.FALSE;
		} else {
			pesofinaleportocarico = b;
		}
	}

	public Double getPesopolizza() {
		return pesopolizza;
	}
	public void setPesopolizza(final Double pesopolizza) {
		this.pesopolizza = pesopolizza;
	}
	public String getProvenienza() {
		return provenienza;
	}
	public void setProvenienza(final String provenienza) {
		this.provenienza = provenienza;
	}
	public String getOrigine() {
		return origine;
	}
	public void setOrigine(final String origine) {
		this.origine = origine;
	}
	public String getMezzo() {
		return mezzo;
	}
	public void setMezzo(final String mezzo) {
		this.mezzo = mezzo;
	}
	public String getRegimedoganale() {
		return regimedoganale;
	}
	public void setRegimedoganale(final String regimedoganale) {
		this.regimedoganale = regimedoganale;
	}
	public String getCodicenc() {
		return codicenc;
	}
	public void setCodicenc(final String codicenc) {
		this.codicenc = codicenc;
	}
	public String getPosizione() {
		return posizione;
	}
	public void setPosizione(final String p) {
		posizione = p;
	}
	public Double getTassoCambio() {
		return tassoCambio;
	}
	public void setTassoCambio(final Double tassoCambio) {
		this.tassoCambio = tassoCambio;
	}
	public Double getTassoUmidita() {
		return tassoUmidita * 100 ;
	}
	public void setValoreUnitario(final Double v) {
		valoreUnitario = v;
	}
	public Double getValoreUnitario() {
		return valoreUnitario ;
	}
	public void setIsValutaEuro(final Boolean v) {
		isValutaEuro = v;
	}
	public Boolean getIsValutaEuro() {
		return isValutaEuro ;
	}

	public void updateValore( final MovimentoIVA m ) {

		StalloConsegnaAdapter scAdp = new StalloConsegnaAdapter();
		try {
			StalloConsegna sc = scAdp.getByMovimento(m);
			if ( sc != null ) {
				sc.updateValore(m);
			} else {
				Double z = Double.valueOf(0d);

				m.setValoreDollari(z);
				m.setValoreEuro(z);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ;
		}
	}

	protected void updateValoreDaValoriConsegna( final MovimentoIVA m ) {

		double valore = 0d;
		Double valUnitario = getValoreUnitario();

		if ( valUnitario  != null &&  m.getSecco() == null ) {

			valore = new Double(
					Math.round( 100000000 * m.getSecco().doubleValue() * valUnitario.doubleValue() ) / 100000000
					) ;

			if ( ! isValutaEuro.booleanValue() ) {
				m.setValoreDollari( valore );

				if ( tassoCambio == null ) return ;

				valore = valore / tassoCambio.doubleValue() ;
			}
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
	public Double calcolaSecco( final Double umido ) {
		if ( umido != null)
			return new Double(
					Math.round(
							umido.doubleValue() *
							Math.round(  100000000 - 100000000 * tassoUmidita.doubleValue() ) / 100000000  ) );

		return null ;
	}


	public Documento getPrimoDocumento(final boolean iva) throws Exception {

		final Vector<?> v = getRegistro(iva, false, false) ;
		Documento d = null ;
		if ( v != null && v.size() > 0 ) {
			d = (( Movimento ) v.firstElement()).getDocumento();
		}

		return d ;
	}

	@SuppressWarnings("unchecked")
	public Documento getPrimoDocumento(final boolean iva, Stallo s) throws Exception {

		final Vector<Movimento> v = getRegistro(iva, false, false) ;
		int idStallo = s.getId().intValue();
		Stallo stalloMov = null;

		if ( v != null && v.size() > 0 ) {
			for ( Movimento m : v ) {
				stalloMov = m.getStallo() ;
				if ( stalloMov != null && stalloMov.getId().intValue() == idStallo )
					return m.getDocumento();
			}
		}

		return null ;
	}

	public Documento getDocumentoPrimoMovimento(final boolean iva, Stallo s) throws Exception {

		MovimentoAdapter adp = null ;

		if ( iva ) {
			adp = new MovimentoIvaAdapter();
		} else {
			adp = new MovimentoDoganaleAdapter();
		}

		Vector<?> list = adp.getByConsegna(true, id, s.getId(), "data" , null );

		for( Object o : list )
			return ((Movimento) o).getDocumento();

		return null ;
	}

	public ArrayList<Object> getStalli() throws Exception {


		stalli = StalloAdapter.getByConsegna(getId());

		if( isChiusa() && (
				stalli == null || stalli.size() < 1 ) ) {
			stalli = getStalliRegistrati();
		}

		return stalli;
	}

	public ArrayList<Object> getStalliRegistrati() throws Exception {
		if ( stalliRegistrati == null ) {
			stalliRegistrati = getIter().getStalli(getId());
		}
		return stalliRegistrati;
	}

	public ArrayList<Object> getStalliRelazionati() throws Exception {

		ArrayList<Object> list = new ArrayList<Object>(3);

		list.addAll(getStalliRegistrati());

		for ( Object i : getStalli() ) {
			if ( ! list.contains(i) ) {
				list.add(i);
			}
		}

		return list;
	}


	public static synchronized ConsegnaAdapter newAdapter() throws Exception {
		return new ConsegnaAdapter() ;
	}
	public static synchronized ConsegnaAdapter newAdapter(final IDatabase2 db) throws Exception {
		return new ConsegnaAdapter(db) ;
	}

	public Vector getRegistroIva( final boolean soloRegistrati ) throws Exception {
		return getRegistro( true,  soloRegistrati , true);
	}
	public Vector getRegistroDoganale( final boolean soloRegistrati ) throws Exception {
		return getRegistro( false, soloRegistrati , true);
	}

	public Vector getRegistro(final boolean iva,  final boolean soloRegistrati , final boolean dontcache) throws Exception {
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
			if (registroIva  == null ) {
				registroIva = adp.getRegistro(soloRegistrati, this);
			}
			return registroIva ;
		}

		if ( registroDoganale == null ) {
			registroDoganale  = adp.getRegistro(soloRegistrati, this);
		}

		return registroDoganale ;

	}

	public Vector<?> getPartitario() throws Exception {
		return getPartitario(null, null);
	}

	public Vector<?> getPartitario(Integer idStallo) throws Exception {
		return getPartitario(idStallo, null);
	}

	public Vector<?> getPartitario(Integer idStallo, Integer minNumero) throws Exception {

		Vector<?> dog = new MovimentoDoganaleAdapter().getByConsegna(true, id, idStallo, "idstallo, data" , null );
		final Vector iva = new MovimentoIvaAdapter().getByConsegna(true, id, idStallo, "idstallo, data" , null );

		if ( dog != null && dog.size() >  0 ) {
			if ( iva != null && iva.size() > 0 ) {
				dog.addAll(iva);
				Collections.sort(dog, new PartitarioSorter());
			}
		}
		else if ( iva != null && iva.size() > 0 ) {
			dog = iva ;
		}

		if ( minNumero != null && minNumero > 0 ) {
			Movimento m;
			Vector<Movimento> filtered = new Vector<Movimento>(13);
			for( Object o : dog ) {
				m = (Movimento) o;
				if( m.getNumRegistro() >= minNumero ) {
					filtered.add(m);
				}
			}
			return filtered;
		}
		else
			return dog;
	}

	private class PartitarioSorter implements Comparator<Object> {

		public int compare(final Object o1, final Object o2) {
			final Movimento a = (Movimento) o1 ;
			final Movimento b = (Movimento) o2 ;

			if (  a.idstallo != null && b.idstallo != null ) {
				if (a.idstallo > b.idstallo )
					return -1 ;
				else if ( a.idstallo < b.idstallo )
					return 1 ;
			}

			if ( a.data.after(b.data) )
				return 1 ;
			else if ( b.data.after(a.data) )
				return -1 ;

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

	public boolean isIva(final Stallo s) {

		Iter iter = getIter();
		if ( iter.getRegiva() ) {
			if ( iter.getRegdoganale() ) {
				try {
					StalloConsegna sc = getStalloConsegna(s);
					if ( sc != null )
						return sc.getIsInLiberaPratica().booleanValue() ;
					else
						return s.getHasLiberaPratica().booleanValue();
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			} else
				return true;
		}

		return false;
	}
	public void flushRegistri() {
		registroDoganale = null ;
		registroIva= null ;
	}

	public StalloConsegna getStalloConsegna(Stallo s) throws Exception {
		return StalloConsegna.newAdapter().getByKeysIds(s.getId(), id);
	}
	
	public StalloConsegna rettificaValoriUnitari(MovimentoIVA m) throws Exception {

		StalloConsegna sc = getStalloConsegna(m.getStallo());
		
		sc.rettificaValoriUnitari(m);
		
		return sc;

	}

}