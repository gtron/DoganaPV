package com.gsoft.doganapt.data.adapters;

import gnu.trove.THashMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

import com.gsoft.doganapt.data.Iter;
import com.gtsoft.utils.common.BeanAdapter2;
import com.gtsoft.utils.data.Field;
import com.gtsoft.utils.data.FieldSet;
import com.gtsoft.utils.sql.IDatabase2;

public class IterAdapter extends BeanAdapter2 {

	public IterAdapter() {
		super();
	}
	public IterAdapter( IDatabase2 db ) {
		super( db );
	}

	public void fillFields(Object obj) { 
		boolean fill = ( obj != null ) ;
		
		Iter o = (Iter) obj ;

		if ( fields == null || fields.isDirty() )
			fields = new FieldSet(Fields.FIELDSCOUNT);
		
		fields.add( Fields.ID, Field.Type.INTEGER , (fill)? o.getId() : null , true );
		
		fields.add( Fields.NOME, Field.Type.STRING , (fill)? o.getNome() : null );
		fields.add( Fields.DESCRIZIONE, Field.Type.STRING , (fill)? o.getDescrizione() : null );
		fields.add( Fields.ISPESOFATTURA, Field.Type.BOOLEAN , (fill)? o.getIspesofattura() : null );
		fields.add( Fields.ISPESOBOLLA, Field.Type.BOOLEAN , (fill)? o.getIspesobolla() : null );
		fields.add( Fields.REGDOGANALE, Field.Type.BOOLEAN , (fill)? o.getRegdoganale() : null );
		fields.add( Fields.REGIVA, Field.Type.BOOLEAN , (fill)? o.getRegiva() : null );
		fields.add( Fields.HASRETTIFICA, Field.Type.BOOLEAN , (fill)? o.getHasrettifica() : null );
		fields.add( Fields.SINGOLICARICHI, Field.Type.BOOLEAN , (fill)? o.getIsSingoliCarichi() : null );
		fields.add( Fields.QUERYIN, Field.Type.STRING , (fill)? o.getQueryin() : null );
		fields.add( Fields.QUERYOUT, Field.Type.STRING , (fill)? o.getQueryout() : null );
	}

	public Object getFromFields ( ) {
		return getFromFields ( new Iter() ) ;
	}
	public Object getFromFields ( Object obj ) {

		if ( obj == null ) {
			return null ;
		}
		
		Iter o = (Iter) obj ;
		
		o.setId( (Integer) fields.get( Fields.ID).getValue());
		o.setNome( (String) fields.get( Fields.NOME).getValue() );
		o.setDescrizione( (String) fields.get( Fields.DESCRIZIONE).getValue() );
		o.setIspesofattura( (Boolean) fields.get( Fields.ISPESOFATTURA).getValue() );
		o.setIspesobolla( (Boolean) fields.get( Fields.ISPESOBOLLA).getValue() );
		o.setRegdoganale( (Boolean) fields.get( Fields.REGDOGANALE).getValue() );
		o.setRegiva( (Boolean) fields.get( Fields.REGIVA).getValue() ); 
		o.setHasrettifica( (Boolean) fields.get( Fields.HASRETTIFICA).getValue() );
		o.setIsSingoliCarichi( (Boolean) fields.get( Fields.SINGOLICARICHI).getValue() );
		o.setQueryin( (String) fields.get( Fields.QUERYIN).getValue() );
		o.setQueryout( (String) fields.get( Fields.QUERYOUT).getValue() );
		
		return o ;
	}
	
	public static interface Fields {
		static final int ID = 0;
		static final int NOME = 1;
		static final int DESCRIZIONE = 2 ;
		static final int ISPESOFATTURA = 3;
		static final int ISPESOBOLLA = 4 ;
		static final int REGDOGANALE = 5;
		static final int REGIVA = 6;
		static final int HASRETTIFICA = 7;
		static final int SINGOLICARICHI = 8;
		static final int QUERYIN = 9;
		static final int QUERYOUT = 10;
		
		static final int FIELDSCOUNT = 11;
	}
	
	private static final String TABLE = "iter" ;
	private static final String[] fieldNames = {
		"id","nome","descrizione","ispesofattura","ispesobolla", "regdoganale",
		"regiva","hasrettifica","singolicarichi","queryin","queryout"
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
		cache.remove(((Iter)o).getId());
	}
	public Vector<?> getAll(String orderby) throws Exception {
		Vector<?> list = super.getAll(orderby);
		Iter a = null ;
		for( Iterator<?> i = list.iterator(); i.hasNext(); ) {
			a = (Iter) i.next();
			cache.put(a.getId(), a);
		}
		return list;
	}
	
	public static final THashMap cache = new THashMap(7);
	public static Iter get(Integer id) {
		return get( id, false ) ;
	}	
	public static Iter get(Integer id, boolean updateCache) {
		Iter a = null ; 
		
		a = (Iter) cache.get(id) ;
		
		if ( a == null ) {
			try {
				a = (Iter) Iter.newAdapter().getByKey(id);
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
	@SuppressWarnings("unchecked")
	public static ArrayList<Object> getAllCached() throws Exception {
		if ( cache.size() < 1 ) {
			Iter.newAdapter().getAll("id");
		}
		ArrayList<Object> list = new ArrayList<Object>(cache.size());
		list.addAll(cache.values());
		
		Collections.reverse(list);
		
		return list; 
	}
}