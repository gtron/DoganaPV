package com.gsoft.doganapt.data.adapters;

import gnu.trove.THashMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

import com.gsoft.doganapt.data.Iter;
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

	public void fillFields(Object obj) { 
		boolean fill = ( obj != null ) ;
		
		Merce o = (Merce) obj ;

		if ( fields == null || fields.isDirty() )
			fields = new FieldSet(Fields.FIELDSCOUNT);
		
		fields.add( Fields.ID, Field.Type.INTEGER , (fill)? o.getId() : null , true );
		
		fields.add( Fields.NOME, Field.Type.STRING , (fill)? o.getNome() : null );
		fields.add( Fields.CODQUADRELLI, Field.Type.STRING , (fill)? o.getCodiceQuadrelli() : null );
		fields.add( Fields.ITERDEFAULT, Field.Type.INTEGER , (fill)? o.getIdIterDefault() : null );
		fields.add( Fields.COLORE, Field.Type.STRING , (fill)? o.getColore() : null );
	}

	public Object getFromFields ( ) {
		return getFromFields ( new Merce() ) ;
	}
	public Object getFromFields ( Object obj ) {

		if ( obj == null ) {
			return null ;
		}
		
		Merce o = (Merce) obj ;
		
		o.setId( (Integer) fields.get( Fields.ID).getValue());
		o.setNome( (String) fields.get( Fields.NOME).getValue() );
		o.setCodiceQuadrelli( (String) fields.get( Fields.CODQUADRELLI).getValue() );
		o.setIdIterDefault( (Integer) fields.get( Fields.ITERDEFAULT).getValue() );
		o.setColore( (String) fields.get( Fields.COLORE).getValue() );
		
		return o ;
	}
	
	public static interface Fields {
		static final int ID = 0;
		static final int NOME = 1;
		static final int CODQUADRELLI = 2 ;
		static final int ITERDEFAULT = 3;
		static final int COLORE = 4 ;
		
		static final int FIELDSCOUNT = 5;
	}
	
	private static final String TABLE = "merci" ;
	private static final String[] fieldNames = {
		"id","nome","codquadrelli","iterdefault","colore"
	};
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
	public String getTable() {
		return TABLE ;
	}
	
	public void update(Object o) throws IOException {
		super.update(o);
		cache.remove(((Merce)o).getId());
	}
	public Vector getAll(String orderby) throws Exception {
		Vector list = super.getAll(orderby);
		Merce a = null ;
		for( Iterator i = list.iterator(); i.hasNext(); ) {
			a = (Merce) i.next();
			cache.put(a.getId(), a);
		}
		return list;
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
}