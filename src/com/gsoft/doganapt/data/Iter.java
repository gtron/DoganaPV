package com.gsoft.doganapt.data;

import java.util.ArrayList;

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

	public static final int ITER_Extacomunitarie = 1;

	public static final int ITER_Comunitario = 2;

	public static final int ITER_Glencore = 3;

	public static final int ITER_NOGlencor = 4;

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
	public void setId(final Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(final String nome) {
		this.nome = nome;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(final String descrizione) {
		this.descrizione = descrizione;
	}
	public Boolean getIspesofattura() {
		return ispesofattura;
	}
	public void setIspesofattura(final Boolean ispesofattura) {
		this.ispesofattura = ispesofattura;
	}
	public Boolean getIspesobolla() {
		return ispesobolla;
	}
	public void setIspesobolla(final Boolean s) {
		ispesobolla = s;
	}
	public Boolean getIsSingoliCarichi() {
		return singoliCarichi;
	}
	public void setIsSingoliCarichi(final Boolean s) {
		singoliCarichi = s;
	}
	public Boolean getRegdoganale() {
		return regdoganale;
	}
	public void setRegdoganale(final Boolean regdoganale) {
		this.regdoganale = regdoganale;
	}
	public Boolean getRegiva() {
		return regiva;
	}
	public void setRegiva(final Boolean regiva) {
		this.regiva = regiva;
	}
	public Boolean getHasrettifica() {
		return hasrettifica; // dovrebbe non essere + utile
	}
	public void setHasrettifica(final Boolean hasrettifica) {
		this.hasrettifica = hasrettifica;
	}
	public String getQueryin() {
		return queryin;
	}
	public void setQueryin(final String queryin) {
		this.queryin = queryin;
	}
	public String getQueryout() {
		return queryout;
	}
	public void setQueryout(final String queryout) {
		this.queryout = queryout;
	}

	@Override
	public String toString() {
		return nome ;
	}

	public static synchronized IterAdapter newAdapter() throws Exception {
		return new IterAdapter() ;
	}
	public static synchronized IterAdapter newAdapter(final IDatabase2 db) throws Exception {
		return new IterAdapter(db) ;
	}

	//	final static String PARCHI = "'101','102','103','104','105','106','107','108','109','110','111','112','113','114','115','116','904','905','906','907','908','909','910','911','912','931','932','933'";


	public String getQueryin( final Consegna c, final Stallo s, final FormattedDate data ) {

		// Da cambiare la gestione dei parchi ... sono nella consegna

		return queryin
				.replaceAll("%C%", c.getNumero().toString() )
				//			.replaceAll("%SS%", PARCHI )
				.replaceAll("%S%", ( s != null ) ? s.codice : "" )
				.replaceAll("%ANDS%", 
						( s != null ) ? " and cliente  = '" + s.codice + "' " : "" )
						.replaceAll("%M%", c.getMerce().getCodiceQuadrelli() )
						.replaceAll("%D%", (data != null)? data.ymdString() : "1900-01-01" )
						;

	}
	public String getQueryout( final Stallo s, final FormattedDate data ) {

		return queryout
				.replaceAll("%C%", s.getConsegna().getNumero().toString() )
				//			.replaceAll("%SS%", PARCHI )
				// .replaceAll("%S%", ( s != null ) ? s.codice : "" )
				.replaceAll("%S%", s.getCodice() )
				.replaceAll("%M%", s.getConsegna().getMerce().getCodiceQuadrelli() )
				.replaceAll("%D%", (data != null)? data.ymdString() : "1900-01-01" ) ;

	}

	public IterImporter getImporter(final MovimentoDoganaleAdapter dog, final MovimentoIvaAdapter iva,
			final MovimentoQuadrelliAdapter q) {

		switch ( id ) {
		case ITER_Extacomunitarie :
			return new IterImporter_EXT(dog, iva, q) ;

		case ITER_Comunitario :
			return new IterImporter_COM(dog, iva, q) ;

		case ITER_Glencore :
			return new IterImporter_GLEN(dog, iva, q) ;

		case ITER_NOGlencor :
			return new IterImporter_NOGLEN(dog, iva, q) ;
		}

		return null ;
	}

	public ArrayList<Object> getStalli( final Integer idConsegna) throws Exception {
		ArrayList<Object> list = null ;
		ArrayList<Integer> ids = null ;
		ArrayList<Integer> idI = null ;

		if ( regdoganale ) {
			ids = MovimentoDoganale.newAdapter().getIdStalli(idConsegna) ;
		}

		if ( regiva ) {
			idI = MovimentoIVA.newAdapter().getIdStalli(idConsegna) ;

			if ( ids != null && ids.size() > 0  ) {
				ids.addAll(idI);
			} else {
				ids = idI ;
			}
		}



		if ( ids != null ) {
			list = new ArrayList<Object>(ids.size());

			for (final Integer id : ids) {
				final Stallo s = StalloAdapter.get(id);
				if ( ! list.contains(s)) {
					list.add(s);
				}
			}
		}

		return list ;
	}
}
