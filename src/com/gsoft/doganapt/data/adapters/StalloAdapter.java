package com.gsoft.doganapt.data.adapters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

import com.gsoft.doganapt.data.Stallo;
import com.gtsoft.utils.common.BeanAdapter2;
import com.gtsoft.utils.common.FormattedDate;
import com.gtsoft.utils.data.Field;
import com.gtsoft.utils.data.FieldSet;
import com.gtsoft.utils.sql.IDatabase2;

import gnu.trove.THashMap;

public class StalloAdapter extends BeanAdapter2 {

	public StalloAdapter() {
		super();
	}
	public StalloAdapter( final IDatabase2 db ) {
		super( db );
	}

	@Override
	public void fillFields(final Object obj) {
		final boolean fill = ( obj != null ) ;

		final Stallo o = (Stallo) obj ;

		if ( fields == null || fields.isDirty() ) {
			fields = new FieldSet(Fields.FIELDSCOUNT);
		}

		fields.add( Fields.ID, Field.Type.INTEGER , (fill)? o.getId() : null , true );

		fields.add( Fields.NOME, Field.Type.STRING , (fill)? o.getNome() : null );
		fields.add( Fields.PARCO, Field.Type.STRING , (fill)? o.getParco() : null );
		fields.add( Fields.NUMERO, Field.Type.INTEGER , (fill)? o.getNumero() : null );
		fields.add( Fields.IDCONSEGNAATTUALE, Field.Type.INTEGER , (fill)? o.getIdConsegnaAttuale() : null );
		fields.add( Fields.IDCONSEGNAPRENOTATA, Field.Type.INTEGER , (fill)? o.getIdConsegnaPrenotata() : null );
		fields.add( Fields.CARICATO, Field.Type.DOUBLE , (fill)? o.getCaricato() : null );
		fields.add( Fields.ATTUALE, Field.Type.DOUBLE , (fill)? o.getAttuale() : null );
		fields.add( Fields.CODICE, Field.Type.STRING , (fill)? o.getCodice() : null );
		fields.add( Fields.LIBERAPRATICA, Field.Type.BOOLEAN , (fill)? o.getImmessoInLiberaPratica() : null );

	}

	public Object getFromFields ( ) {
		return getFromFields ( new Stallo() ) ;
	}
	public Object getFromFields ( final Object obj ) {

		if ( obj == null )
			return null ;

		final Stallo o = (Stallo) obj ;

		o.setId( (Integer) fields.get( Fields.ID).getValue());
		o.setNome( (String) fields.get( Fields.NOME).getValue() );
		o.setParco( (String) fields.get( Fields.PARCO).getValue() );
		o.setNumero( (Integer) fields.get( Fields.NUMERO).getValue() );
		o.setIdConsegnaAttuale( (Integer) fields.get( Fields.IDCONSEGNAATTUALE).getValue() );
		o.setIdConsegnaPrenotata( (Integer) fields.get( Fields.IDCONSEGNAPRENOTATA).getValue() );
		o.setCaricato( (Double) fields.get( Fields.CARICATO).getValue() );
		o.setAttuale( (Double) fields.get( Fields.ATTUALE).getValue() );
		o.setCodice( (String) fields.get( Fields.CODICE).getValue() );
		o.setImmessoInLiberaPratica( (Boolean) fields.get( Fields.LIBERAPRATICA).getValue() );

		return o ;
	}

	public static interface Fields {
		static final int ID = 0;
		static final int NOME = 1;
		static final int PARCO = 2 ;
		static final int NUMERO = 3;
		static final int IDCONSEGNAATTUALE = 4 ;
		static final int IDCONSEGNAPRENOTATA = 5;
		static final int CARICATO = 6;
		static final int ATTUALE = 7;
		static final int CODICE = 8;
		static final int LIBERAPRATICA = 9;

		static final int FIELDSCOUNT = 10;
	}

	private static final String TABLE = "stalli" ;
	private static final String[] fieldNames = {
		"id","nome","parco","numero","idconsegnaattuale","idconsegnaprenotata",
		"caricato","attuale","codice", "liberapratica"
	};
	@Override
	public int getFieldsCount() {
		return Fields.FIELDSCOUNT ;
	}
	public String getHttpFieldName( final int field ) {
		return fieldNames[field] ;
	}
	public String getTableFieldName(final Field f) {
		return fieldNames[f.getId()] ;
	}

	public static String getStaticTable() {
		return TABLE ;
	}
	@Override
	public String getTable() {
		return TABLE ;
	}

	public Object getByCodice(final String codice) throws Exception {

		final Vector<?> list = getWithWhere("codice = '" + codice + "'");

		if ( list.size() > 0 )
			return list.firstElement() ;

		return null ; // throw new DataException("element not found");
	}

	@Override
	public void update(final Object o) throws IOException {
		super.update(o);
		getFields().setDirty(true);
		removeFromCache(o);
	}

	@Override
	public Vector<Stallo> getAll(final String orderby) throws Exception {
		@SuppressWarnings("unchecked")
		final Vector<Stallo> list = super.getAll(orderby);
		Stallo a = null ;
		for( final Iterator<Stallo> i = list.iterator(); i.hasNext(); ) {
			a = i.next();
			cache.put(a.getId(), a);
			cacheByCodice.put(a.getCodice(), a);
		}
		return list;
	}

	public void resetStallo( Stallo s ) throws IOException {
		s.setAttuale(0d);
		s.setCaricato(0d);
		s.setIdConsegnaAttuale(null);
		s.setIdConsegnaPrenotata(null);
		s.setImmessoInLiberaPratica(false);

		update(s);
	}

	public static final THashMap cache = new THashMap(7);
	public static final THashMap cacheByCodice = new THashMap(7);
	public static Stallo get(final Integer id) {
		return get( id, false ) ;
	}
	public static Stallo get(final Integer id, final boolean updateCache) {
		Stallo a = null ;

		if ( ! updateCache ) {
			a = (Stallo) cache.get(id) ;
		}

		if ( a == null ) {
			try {
				a = (Stallo) Stallo.newAdapter().getByKey(id);
				cache.put(a.getId(), a);
				cacheByCodice.put(a.getCodice(), a);
			}
			catch ( final Exception ex ) {
				ex.printStackTrace();
			}
		}
		return a ;
	}

	public static ArrayList<Object> getAllCached(final boolean refresh ) throws Exception {
		if ( refresh ) {
			clearCache() ;
		}
		return getAllCached();
	}
	public static ArrayList<Object> getAllCached() throws Exception {
		if ( cache.size() < 1 ) {
			Stallo.newAdapter().getAll("id");
		}
		final ArrayList<Object> list = new ArrayList<Object>(cache.size());
		list.addAll(cache.values());
		Collections.reverse(list);

		return list;
	}
	public static void clearCache() {
		cache.clear();
		cacheByCodice.clear();
	}
	public static void removeFromCache(final Object o) {
		cache.remove(o);
		cacheByCodice.remove(o);

		clearCache(); // da cambiare
	}
	public static Stallo getByCodice(final String codice , final boolean updateCache) {
		Stallo a = null ;

		if ( ! updateCache ) {
			a = (Stallo) cacheByCodice.get(codice) ;
		}

		if ( a == null ) {
			try {
				a = (Stallo) Stallo.newAdapter().getByCodice(codice);
				if ( a != null ) {
					cache.put(a.getId(), a);
					cacheByCodice.put(a.getCodice(), a);
				}
			}
			catch ( final Exception ex ) {
				ex.printStackTrace();
			}
		}
		return a ;
	}
	public static ArrayList<Object> getByConsegna(final Integer id) throws Exception {
		Stallo a = null ;

		final ArrayList<Object> list = new ArrayList<Object>(5);

		for (final Object object : getAllCached()) {
			a = (Stallo) object ;

			if (id.equals(a.getIdConsegnaAttuale()) ) {
				list.add(a);
			}
		}

		return list ;
	}
	
	public Vector<Stallo> getAll(FormattedDate data, String order) throws Exception {
		Vector<Stallo> stalliAttuali = getAll(order);
		
		if ( data == null )
			return stalliAttuali ;
		
		return getStalliAllaData(data, stalliAttuali);
		
	}
	
	private Vector<Stallo> getStalliAllaData(FormattedDate data, Vector<Stallo> stalliAttuali)
			throws CloneNotSupportedException, Exception {
		
		MovimentoIvaAdapter registroIva = new MovimentoIvaAdapter();
		MovimentoDoganaleAdapter registroDoganale = new MovimentoDoganaleAdapter();
		
		Vector<Stallo> list = new Vector<Stallo>(stalliAttuali.size());
		Stallo s2;
		
		Integer idC = 0;
		
		for ( Stallo s : stalliAttuali ) {
			
			idC = null ;
			
			s2 = s.clone();
			
			Integer idCIva = registroIva.getIdConsegnaInStalloAllaData(s2.getId(), data);
			
			Integer idCDog = registroDoganale.getIdConsegnaInStalloAllaData(s2.getId(), data);
			
			if ( idCIva != null && idCDog == null) {
				idC = idCIva;
			} else if ( idCIva == null && idCDog != null ) {
				idC = idCDog;
			} else {
				
				if ( idCIva != null && idCDog != null ) {
					
					idC = Math.max( idCIva, idCDog );
					
					if ( idCIva - idCDog != 0 ) { 
						System.out.println("Conflitto! idC:" + idC + " iva:" + idCIva + " dog:" + idCDog );
					}
				}
				
			}

			s2.setIdConsegnaAttuale(idC);
			s2.setDataRiferimento(data);
			
			list.add(s2);

		}
		return list;
	}
}