package com.gsoft.doganapt.data.adapters;

import gnu.trove.THashMap;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.gsoft.doganapt.data.Consegna;
import com.gtsoft.utils.common.BeanAdapter2;
import com.gtsoft.utils.common.FormattedDate;
import com.gtsoft.utils.data.Field;
import com.gtsoft.utils.data.FieldSet;
import com.gtsoft.utils.sql.IDatabase2;

public class ConsegnaAdapter extends BeanAdapter2 {

	public ConsegnaAdapter() {
		super();
	}
	public ConsegnaAdapter( IDatabase2 db ) {
		super( db );
	}

	public void fillFields(Object obj) { 
		boolean fill = ( obj != null ) ;
		
		Consegna o = (Consegna) obj ;

		if ( fields == null || fields.isDirty() )
			fields = new FieldSet(Fields.FIELDSCOUNT);
		
		fields.add( Fields.ID, Field.Type.INTEGER , (fill)? o.getId() : null , true );
		
		fields.add( Fields.NUMERO, Field.Type.INTEGER , (fill)? o.getNumero() : null );
		fields.add( Fields.IDMERCE, Field.Type.INTEGER , (fill)? o.getIdmerce() : null );
		fields.add( Fields.ITER, Field.Type.INTEGER , (fill)? o.getIdIter() : null );
		fields.add( Fields.DATACREAZIONE, Field.Type.DATE , (fill)? o.getDataCreazione() : null );
		fields.add( Fields.DATACHIUSURA, Field.Type.DATE , (fill)? o.getDataChiusura() : null );
		fields.add( Fields.PESOPOLIZZA, Field.Type.DOUBLE , (fill)? o.getPesopolizza() : null );
		fields.add( Fields.PESOFINALEPORTOCARICO, Field.Type.BOOLEAN , (fill)? o.getPesoFinalePortoCarico() : null );
		fields.add( Fields.PROVENIENZA, Field.Type.STRING , (fill)? o.getProvenienza() : null );
		fields.add( Fields.ORIGINE, Field.Type.STRING , (fill)? o.getOrigine() : null );
		fields.add( Fields.MEZZO, Field.Type.STRING , (fill)? o.getMezzo() : null );
		fields.add( Fields.REGIMEDOGANALE, Field.Type.STRING , (fill)? o.getRegimedoganale() : null );
		fields.add( Fields.CODICENC, Field.Type.STRING , (fill)? o.getCodicenc() : null );
		fields.add( Fields.POSIZIONE, Field.Type.STRING , (fill)? o.getPosizione() : null );
		fields.add( Fields.TASSO_CAMBIO, Field.Type.DOUBLE , (fill)? o.getTassoCambio() : null );
		fields.add( Fields.TASSO_UMIDITA, Field.Type.DOUBLE , (fill)? o.getTassoUmidita() : null );
		
		fields.add( Fields.NUMPARTITARIO, Field.Type.INTEGER , (fill)? o.getNumeroPartitario() : null );
		fields.add( Fields.ISVALUTAEURO, Field.Type.BOOLEAN , (fill)? o.getIsValutaEuro() : null );
		fields.add( Fields.VALOREUNITARIO, Field.Type.DOUBLE , (fill)? o.getValoreUnitario() : null );
		
	}

	public Object getFromFields ( ) {
		return getFromFields ( new Consegna() ) ;
	}
	public Object getFromFields ( Object obj ) {

		if ( obj == null ) {
			return null ;
		}
		
		Consegna o = (Consegna) obj ;
		
		o.setId( (Integer) fields.get( Fields.ID).getValue());
		o.setNumero( (Integer) fields.get( Fields.NUMERO).getValue() );
		o.setIdmerce( (Integer) fields.get( Fields.IDMERCE).getValue() );
		o.setIdIter( (Integer) fields.get( Fields.ITER).getValue() );
		o.setDataCreazione( (FormattedDate) fields.get( Fields.DATACREAZIONE).getValue() );
		o.setDataChiusura( (FormattedDate) fields.get( Fields.DATACHIUSURA).getValue() );
		o.setPesoFinalePortoCarico( (Boolean) fields.get( Fields.PESOFINALEPORTOCARICO).getValue() );
		o.setPesopolizza( (Double) fields.get( Fields.PESOPOLIZZA).getValue() ); 
		o.setProvenienza( (String) fields.get( Fields.PROVENIENZA).getValue() );
		o.setOrigine( (String) fields.get( Fields.ORIGINE).getValue() ); 
		o.setMezzo( (String) fields.get( Fields.MEZZO).getValue() );
		o.setRegimedoganale( (String) fields.get( Fields.REGIMEDOGANALE).getValue() ); 
		o.setCodicenc( (String) fields.get( Fields.CODICENC).getValue() );
		o.setPosizione( (String) fields.get( Fields.POSIZIONE).getValue() ); 
		o.setTassoCambio( (Double) fields.get( Fields.TASSO_CAMBIO).getValue() );
		o.setTassoUmidita( (Double) fields.get( Fields.TASSO_UMIDITA).getValue() );
		
		o.setNumeroPartitario( (Integer) fields.get( Fields.NUMPARTITARIO).getValue() ); 
		o.setIsValutaEuro( (Boolean) fields.get( Fields.ISVALUTAEURO).getValue() );
		o.setValoreUnitario( (Double) fields.get( Fields.VALOREUNITARIO).getValue() );
		
		return o ;
	}
	
	public static interface Fields {
		static final int ID = 0;
		static final int NUMERO = 1 ;
		static final int IDMERCE = 2;
		static final int ITER = 3;
		static final int DATACREAZIONE = 4 ;
		static final int DATACHIUSURA = 5 ;
		static final int PESOPOLIZZA = 6;
		static final int PESOFINALEPORTOCARICO = 7;
		static final int PROVENIENZA  = 8;
		static final int ORIGINE  = 9;		
		static final int MEZZO = 10;
		static final int REGIMEDOGANALE  = 11;
		static final int CODICENC = 12;
		static final int POSIZIONE = 13;
		static final int TASSO_CAMBIO = 14;
		static final int TASSO_UMIDITA = 15;
		static final int NUMPARTITARIO = 16;
		static final int ISVALUTAEURO = 17;
		static final int VALOREUNITARIO = 18;
		
		static final int FIELDSCOUNT = 19;
	}
	
	private static final String TABLE = "consegne" ;
	private static final String[] fieldNames = {
		"idConsegna","numero","idmerce","iter","datacreazione","datachiusura","pesopolizza","pesofinaleportocarico",
		"provenienza","origine","mezzo","regimedoganale","codicenc","posizione","tassocambio","tassoumidita",
		"numpartitario","isvalutaeuro", "valoreunitario" 
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
	
	
	public void update() throws IOException {
		clearCache();
		super.update();
	}
	public void update(Object o) throws IOException {
		super.update(o);
		cache.remove(((Consegna)o).getId());
	}
	
	@Override
	public Object create(Object o) throws IOException, SQLException {
		Consegna c = (Consegna) o ;
		if ( c!= null ) {
			if ( c.getDataCreazione() == null ) {
				c.setDataCreazione(new FormattedDate()) ;
				c.setDataChiusura(c.getDataCreazione()) ;
			}
		}
		else {
			FormattedDate d = new FormattedDate();
			getFields().get(Fields.DATACREAZIONE).setValue(d) ;
			getFields().get(Fields.DATACHIUSURA).setValue(d) ;
		}

		return super.create(o);
	}
	
	public Vector getAperte() throws Exception {
		
		StringBuilder sql = new StringBuilder(70)
			.append("SELECT * FROM ").append(getTable())
			.append(" WHERE ").append(fieldNames[Fields.DATACHIUSURA]).append(" is null ")
			.append(" ORDER BY ").append(fieldNames[Fields.DATACREAZIONE]) ;
			
		PreparedStatement s = db.getConnection().prepareStatement(sql.toString()) ;
		return getByStatment(s) ;  
		
	}

	public Vector getNonChiuse() throws Exception {
		
		StringBuilder sql = new StringBuilder(70)
			.append("SELECT * FROM ").append(getTable())
			.append(" WHERE ").append(fieldNames[Fields.DATACHIUSURA]).append(" is null ")
			.append(" OR ")
			.append(fieldNames[Fields.DATACREAZIONE]).append(" = ")
			.append(fieldNames[Fields.DATACHIUSURA])
			.append(" ORDER BY ").append(fieldNames[Fields.DATACREAZIONE]) ;
			
		PreparedStatement s = db.getConnection().prepareStatement(sql.toString()) ;
		return getByStatment(s) ;  
		
	}
	
	public Vector getChiuse() throws Exception {
		
		StringBuilder sql = new StringBuilder(70)
			.append("SELECT * FROM ").append(getTable())
			.append(" WHERE ").append(fieldNames[Fields.DATACHIUSURA]).append(" is not null")
			.append(" AND ").append(fieldNames[Fields.DATACREAZIONE]).append(" < ")
			.append(fieldNames[Fields.DATACHIUSURA])
			.append(" ORDER BY ").append(fieldNames[Fields.DATACREAZIONE]) ;
			
		PreparedStatement s = db.getConnection().prepareStatement(sql.toString()) ;
		return getByStatment(s) ;  
		
	}
	
	public Object getNextNumPartitario() throws Exception {
		
		StringBuilder sql = new StringBuilder(70)
			.append("SELECT 1 + max(").append(fieldNames[Fields.NUMPARTITARIO])
			.append(") FROM ").append(getTable())
			;
			
		PreparedStatement s = db.getConnection().prepareStatement(sql.toString()) ;
		
		ResultSet rs = s.executeQuery() ;  
		
		if ( rs != null ) {
			rs.next();
			return rs.getObject(1);
		}
		return null ;
	}

	public static final THashMap cache = new THashMap();
	public static Consegna get(Integer id) {
		if ( id == null ) return  null ;
		return get( id, false ) ;
	}	
	public static Consegna get(Integer id, boolean updateCache) {
		Consegna a = null ; 
		if ( id == null ) return  null ;
		
		a = (Consegna) cache.get(id) ;
		
		if ( a == null ) {
			try {
				a = (Consegna) Consegna.newAdapter().getByKey(id);
				
				if ( a != null && ! a.isChiusa() ) {
					cache.put(a.getId(), a) ;
				}
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
}