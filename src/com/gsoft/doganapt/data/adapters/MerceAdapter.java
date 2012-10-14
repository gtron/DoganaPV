package com.gsoft.doganapt.data.adapters;

import gnu.trove.THashMap;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

import com.gsoft.doganapt.data.Merce;
import com.gtsoft.utils.common.BeanAdapter2;
import com.gtsoft.utils.data.Field;
import com.gtsoft.utils.data.FieldSet;
import com.gtsoft.utils.sql.IDatabase2;


public class MerceAdapter extends BeanAdapter2 {

	public MerceAdapter() {
		super();
	}
	public MerceAdapter( IDatabase2 db ) {
		super( db );
	}

	@Override
	public void fillFields(Object obj) {
		boolean fill = ( obj != null ) ;

		Merce o = (Merce) obj ;

		if ( fields == null || fields.isDirty() ) {
			fields = new FieldSet(Fields.FIELDSCOUNT);
		}

		fields.add( Fields.ID, Field.Type.INTEGER , (fill)? o.getId() : null , true );

		fields.add( Fields.NOME, Field.Type.STRING , (fill)? o.getNome() : null );
		fields.add( Fields.DESCRIZIONE, Field.Type.STRING , (fill)? o.getDescrizione() : null );
		fields.add( Fields.CODQUADRELLI, Field.Type.STRING , (fill)? o.getCodiceQuadrelli() : null );
		fields.add( Fields.CODTARIC, Field.Type.STRING , (fill)? o.getCodiceTaric() : null );
		fields.add( Fields.ITERDEFAULT, Field.Type.INTEGER , (fill)? o.getIdIterDefault() : null );
		fields.add( Fields.COLORE, Field.Type.STRING , (fill)? o.getColore() : null );
	}

	public Object getFromFields ( ) {
		return getFromFields ( new Merce() ) ;
	}
	public Object getFromFields ( Object obj ) {

		if ( obj == null )
			return null ;

		Merce o = (Merce) obj ;

		o.setId( (Integer) fields.get( Fields.ID).getValue());
		o.setNome( (String) fields.get( Fields.NOME).getValue() );
		o.setDescrizione( (String) fields.get( Fields.DESCRIZIONE).getValue() );
		o.setCodiceQuadrelli( (String) fields.get( Fields.CODQUADRELLI).getValue() );
		o.setCodiceTaric( (String) fields.get( Fields.CODTARIC).getValue() );
		o.setIdIterDefault( (Integer) fields.get( Fields.ITERDEFAULT).getValue() );
		o.setColore( (String) fields.get( Fields.COLORE).getValue() );

		return o ;
	}

	public static interface Fields {
		static final int ID = 0;
		static final int NOME = 1;
		static final int DESCRIZIONE = 2;
		static final int CODQUADRELLI = 3;
		static final int CODTARIC = 4;
		static final int ITERDEFAULT = 5;
		static final int COLORE = 6 ;

		static final int FIELDSCOUNT = 7;
	}

	private static final String TABLE = "merci" ;
	private static final String[] fieldNames = {
		"id","nome","descrizione","codquadrelli","codtaric","iterdefault","colore"
	};
	@Override
	public int getFieldsCount() {
		return Fields.FIELDSCOUNT ;
	}
	public String getHttpFieldName( int field ) {
		return fieldNames[field] ;
	}
	public String getTableFieldName(Field f) {
		return fieldNames[f.getId()] ;
	}

	public static String getStaticTable() {
		return TABLE ;
	}
	@Override
	public String getTable() {
		return TABLE ;
	}

	@Override
	public Object create(Object o) throws IOException , SQLException{
		Object c = super.create(o);
		clearCache();
		return c ;
	}
	@Override
	public void update(Object o) throws IOException {
		super.update(o);
		clearCache();
	}
	@Override
	public void update() throws IOException {
		super.update();
		clearCache();
	}
	@Override
	public Vector getAll(String orderby) throws Exception {
		Vector list = super.getAll(orderby);
		Merce a = null ;
		for( Iterator i = list.iterator(); i.hasNext(); ) {
			a = (Merce) i.next();
			cache.put(a.getId(), a);
		}
		return list;
	}

	public void clearTable() throws IOException {
		Connection c = db.getConnection();

		try {
			db.executeNonQuery("DROP TABLE zbck_" + getTable(),
					c );}
		catch ( Exception e ) {}
		db.executeNonQuery("CREATE TABLE zbck_" + getTable() +
				" SELECT * FROM "  + getTable() ,
				c );
		db.executeNonQuery("DELETE FROM " + getTable() ,
				c);

		clearCache();
	}

	public static final THashMap cache = new THashMap(101);
	public static Merce get(Integer id) {
		return get( id, false ) ;
	}
	public static Merce get(Integer id, boolean updateCache) {
		Merce a = null ;

		a = (Merce) cache.get(id) ;

		if ( a == null ) {
			try {
				a = (Merce) Merce.newAdapter().getByKey(id);
			}
			catch ( Exception ex ) {
				ex.printStackTrace();
			}
		}
		return a ;
	}
	public static void clearCache() {
		cache.clear();
	}
	public static ArrayList<Object> getAllCached() throws Exception {
		if ( cache.size() < 1 ) {
			Merce.newAdapter().getAll("nome");
		}
		ArrayList<Object> list = new ArrayList<Object>(cache.size());
		list.addAll(cache.values());

		Collections.reverse(list);

		return list;
	}

	public static ArrayList<Object> getAllCachedRegistro(MovimentoAdapter adp ) throws Exception {
		if ( cache.size() < 1 ) {
			Merce.newAdapter().getAll("nome");
		}

		ArrayList<Object> list = new ArrayList<Object>(cache.size());
		Merce m = null ;
		Vector<Integer> ids = adp.getIdMerci();

		for( Iterator i = cache.values().iterator() ; i.hasNext() ; ) {
			m = (Merce)i.next();
			for (Integer integer : ids) {
				if ( integer.equals(m.getId())  ) {
					list.add(m);
					continue;
				}
			}
		}

		return list;
	}
}