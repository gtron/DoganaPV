package com.gsoft.doganapt.data.adapters;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.gsoft.doganapt.data.Consegna;
import com.gsoft.doganapt.data.Movimento;
import com.gsoft.doganapt.data.MovimentoIVA;
import com.gtsoft.utils.data.Field;
import com.gtsoft.utils.sql.IDatabase2;

public class MovimentoIvaAdapter extends MovimentoAdapter {

	public static Boolean busy = Boolean.FALSE ;
	
	public MovimentoIvaAdapter() {
		super();
	}
	public MovimentoIvaAdapter( IDatabase2 db ) {
		super( db );
	}

	public Object getFromFields ( ) {
		return getFromFields ( new MovimentoIVA() ) ;
	}
	private static final String TABLE = "registroiva" ;
	
	public static String getStaticTable() {
		return TABLE ;
	}
	public String getTable() {
		return TABLE ;
	}

	
	public void fillFields(Object obj) { 
		boolean fill = ( obj != null ) ;
		
		super.fillFields(obj);
		
		MovimentoIVA o = (MovimentoIVA) obj ;

		fields.add( Fields.VALORE_DOLLARI, Field.Type.DOUBLE , (fill)? o.getValoreDollari() : null );
		fields.add( Fields.VALORE_EURO, Field.Type.DOUBLE , (fill)? o.getValoreEuro() : null );
		fields.add( Fields.POSIZIONE_DOGANALE, Field.Type.STRING , (fill)? o.getPosizioneDoganale() : null );
		
	}
	
	public Object getFromFields ( Object obj ) {

		MovimentoIVA o = (MovimentoIVA) super.getFromFields(obj) ;

		o.setValoreDollari( (Double) fields.get( Fields.VALORE_DOLLARI).getValue() );
		o.setValoreEuro( (Double) fields.get( Fields.VALORE_EURO).getValue() );
		o.setPosizioneDoganale( (String) fields.get( Fields.POSIZIONE_DOGANALE).getValue() );
		
		return o ;
	}
	
	public static interface Fields {
		
		static final int FIELDSHIFT = MovimentoAdapter.Fields.FIELDSCOUNT - 1;
		
		static final int VALORE_EURO = FIELDSHIFT + 1 ;
		static final int VALORE_DOLLARI = FIELDSHIFT + 2;
		static final int POSIZIONE_DOGANALE = FIELDSHIFT + 3;
		
		static final int FIELDSCOUNT = FIELDSHIFT + 4;
	}

	private static final String[] fieldNames = {
		"valoreeuro","valoredollari", "posdoganale"
	};
	
	public int getFieldsCount() {
		return Fields.FIELDSCOUNT ;
	}
	
	public String getHttpFieldName( int f ) {
		int field = f - Fields.FIELDSHIFT  - 1;
		if ( field >= 0 && field < Fields.FIELDSCOUNT ) 
			return fieldNames[field] ;
		
		return super.getHttpFieldName(f);
	}
	public String getTableFieldName(Field f) {
		int field = f.getId() - Fields.FIELDSHIFT - 1 ;
		if ( field >= 0 && field < Fields.FIELDSCOUNT ) 
			return fieldNames[field] ;
		
		return super.getTableFieldName(f);
	}
	
	protected String getFieldsRegistro(String prefix) {
		String list = "%id, %idmerce, %idconsegna, %data, %idstallo, %isscarico, %isrettifica, sum(%secco) as secco, sum(%umido) as umido, %numregistro, %doctype, %docnum, %docdate, %docpvtype, %docpvnum, %docpvdate, %note, %posdoganale, %locked, %valoreeuro, %valoredollari ";
			
		if ( prefix != null )
			list = list.replaceAll("%", prefix) ;
		else
			list = list.replaceAll("%", "") ;
		
		return list ;
	}
		
	public synchronized void doneNextNumRegistro() throws Exception {
		busy = Boolean.FALSE ;
	}
	public synchronized Integer getNextNumRegistro() throws Exception {
		
		int c = 0 ;
		while ( busy.booleanValue() ) {
			Thread.sleep(1000);
			
			if ( c++  > 10 ) {
				throw new Exception("Errore nella gestione della concorrenza sul registro") ;
			}
		}
		Thread.sleep(1500);
		busy = Boolean.TRUE ;
		
		StringBuilder sql = new StringBuilder(70)
			.append("SELECT max(numregistro) FROM ")
			.append(getTable())
			;
		
		Integer d = new Integer( 1 ) ;
		Connection conn = db.getConnection() ;
		try {
			PreparedStatement s = conn.prepareStatement(sql.toString()) ;
			
			ResultSet rs = s.executeQuery() ;
	
			if ( rs != null && rs.next() ) {
				String str = rs.getString(1);
				if ( str != null )
					d = new Integer( Integer.parseInt(str) + 1 );
			}
		}
		finally {
			db.freeConnection(conn);
		}
		
		return d;
	}
	
	public Movimento newMovimento() {
		return new MovimentoIVA();
	}
	
	public Object create(Object o) throws IOException {
		MovimentoIVA m = (MovimentoIVA) o;
		
		if ( m != null ) {
			Consegna c = m.getConsegna();
			if ( c != null ) {
				if (m.getValoreEuro() == null) {
					c.updateValore(m);
				}
				
				if ( m.getPosizioneDoganale() == null ) {
					m.setPosizioneDoganale(c.getPosizione());
				}
			}
		}
		return super.create(m);		
	}

}