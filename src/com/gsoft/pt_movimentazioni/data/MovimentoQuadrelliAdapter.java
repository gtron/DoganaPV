package com.gsoft.pt_movimentazioni.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

import com.gsoft.doganapt.data.Consegna;
import com.gsoft.doganapt.data.Stallo;
import com.gtsoft.utils.common.BeanAdapter2;
import com.gtsoft.utils.common.ConfigManager;
import com.gtsoft.utils.common.FormattedDate;
import com.gtsoft.utils.data.Field;
import com.gtsoft.utils.data.FieldSet;
import com.gtsoft.utils.sql.IDatabase2;

public class MovimentoQuadrelliAdapter extends BeanAdapter2 {

	static private Boolean readFromCache = false;
	static private Boolean writeCache = false;

	public MovimentoQuadrelliAdapter() {
		super();

		readFromCache  = new Boolean(ConfigManager.getProperty("movimentazioni.readFromCache")) ;
		writeCache  = new Boolean(ConfigManager.getProperty("movimentazioni.writeCache")) ;
	}
	public MovimentoQuadrelliAdapter( IDatabase2 db ) {
		super( db );

		readFromCache  = new Boolean(ConfigManager.getProperty("movimentazioni.readFromCache")) ;
		writeCache  = new Boolean(ConfigManager.getProperty("movimentazioni.writeCache")) ;
	}

	@Override
	public void fillFields(Object obj) {

		boolean fill = ( obj != null ) ;

		MovimentoQuadrelli o = (MovimentoQuadrelli) obj ;

		if ( fields == null || fields.isDirty() ) {
			fields = new FieldSet(Fields.FIELDSCOUNT);
		}

		fields.add( Fields.DATAPESATURA, Field.Type.DATE , (fill)? o.getId() : null , true );

		fields.add( Fields.DATA, Field.Type.DATE , (fill)? o.getData().dmyString() : null );
		fields.add( Fields.ORA, Field.Type.DATE , (fill)? o.getData().hmsString() : null );
		fields.add( Fields.NUMEROPROGRESSIVO, Field.Type.INTEGER , (fill)? o.getNumeroProgressivo() : null );
		fields.add( Fields.NETTO, Field.Type.DOUBLE , (fill)? o.getNetto() : null );
		fields.add( Fields.CLIENTE, Field.Type.STRING , (fill)? o.getCodiceCliente() : null );
		fields.add( Fields.FORNITORE, Field.Type.STRING , (fill)? o.getCodiceFornitore() : null );
		fields.add( Fields.VETTORE, Field.Type.STRING , (fill)? o.getCodiceVettore() : null );
		fields.add( Fields.DESTINAZIONE, Field.Type.STRING , (fill)? o.getDestinazione() : null );
		fields.add( Fields.DESCRIZIONE, Field.Type.STRING , (fill)? o.getDescrizione() : null );
		fields.add( Fields.MERCE, Field.Type.STRING , (fill)? o.getCodiceMerce() : null );
		fields.add( Fields.NAVE, Field.Type.STRING , (fill)? o.getCodiceNave() : null );
		fields.add( Fields.NUMEROCONSEGNA, Field.Type.STRING , (fill)? o.getConsegna() : null );
		fields.add( Fields.NUMERODOCUMENTO, Field.Type.STRING , (fill)? o.getDocumento() : null );

		fields.add( Fields.PESOFATTURA, Field.Type.INTEGER , (fill)? o.getPesoFattura() : null );
		fields.add( Fields.PESOPARTENZA, Field.Type.INTEGER , (fill)? o.getPesoPartenza() : null );
		fields.add( Fields.NUMEROCATASTE, Field.Type.INTEGER , (fill)? o.getNumeroCataste() : null );

		fields.add( Fields.DATA_EM_FORM, Field.Type.DATE , (fill)? o.getData_em_form() : null );
		fields.add( Fields.NOTE, Field.Type.STRING , (fill)? o.getNote() : null );

		fields.add( Fields.MIN_DATA , Field.Type.DATE ,null );

	}

	public Object getFromFields ( ) {
		return getFromFields ( new MovimentoQuadrelli() ) ;
	}
	public Object getFromFields ( Object obj ) {

		if ( obj == null )
			return null ;

		MovimentoQuadrelli o = (MovimentoQuadrelli) obj ;

		o.setDataPesatura( (FormattedDate) fields.get( Fields.DATAPESATURA).getValue() );

		FormattedDate data = (FormattedDate) fields.get(Fields.MIN_DATA).getValue() ;
		if ( data == null ) {
			data = (FormattedDate) fields.get( Fields.DATA).getValue() ;
		}

		o.setData( data ) ;
		if ( o.getData() != null ) {
			FormattedDate ora = (FormattedDate) fields.get( Fields.ORA).getValue() ;
			if ( ora != null) {
				o.setData( new FormattedDate( o.getData().ymdString() + " " + ora.hmsString() ) ) ;
			}

		}

		o.setNumeroProgressivo( (Double) fields.get( Fields.NUMEROPROGRESSIVO).getValue() );
		o.setNetto( (Double) fields.get( Fields.NETTO).getValue() );
		o.setCodiceCliente( (String) fields.get( Fields.CLIENTE).getValue() );
		o.setCodiceFornitore( (String) fields.get( Fields.FORNITORE).getValue() );
		o.setVettore( (String) fields.get( Fields.VETTORE).getValue() );
		o.setDestinazione( (Integer) fields.get( Fields.DESTINAZIONE).getValue() );
		o.setDescrizione( (String) fields.get( Fields.DESCRIZIONE).getValue() );
		o.setMerce( (String) fields.get( Fields.MERCE).getValue() );
		o.setNave( (Integer) fields.get( Fields.NAVE).getValue() );
		o.setConsegna( (String) fields.get( Fields.NUMEROCONSEGNA).getValue() );
		o.setDocumento( (String) fields.get( Fields.NUMERODOCUMENTO).getValue() );

		o.setPesoFattura( (Integer) fields.get( Fields.PESOFATTURA).getValue() );
		o.setNote( (String) fields.get( Fields.NOTE).getValue() );
		o.setNumeroCataste( (Integer) fields.get( Fields.NUMEROCATASTE).getValue() );
		o.setPesoPartenza( (Integer) fields.get( Fields.PESOPARTENZA).getValue() );
		o.setData_em_form( (FormattedDate) fields.get( Fields.DATA_EM_FORM).getValue() );


		return o ;
	}

	public static interface Fields {
		public static final int DATAPESATURA = 0;
		public static final int DATA = 1;
		public static final int ORA = 2;
		public static final int NUMEROPROGRESSIVO = 3;
		public static final int NETTO = 4;
		public static final int CLIENTE = 5;
		public static final int FORNITORE = 6;
		public static final int VETTORE = 7;
		public static final int DESTINAZIONE = 8;
		public static final int DESCRIZIONE = 9;
		public static final int MERCE = 10;
		public static final int NAVE = 11;
		public static final int NUMEROCONSEGNA = 12;
		public static final int NUMERODOCUMENTO = 13;
		public static final int PESOFATTURA = 14;
		public static final int NUMEROCATASTE = 15;
		public static final int PESOPARTENZA = 16;
		public static final int DATA_EM_FORM = 17;
		public static final int NOTE = 18;
		public static final int MIN_DATA = 19;

		static final int FIELDSCOUNT = 20;
	}

	private static final String TABLE = "`ARCHIVIO CORRETTO`" ;
	private static final String[] fieldNames = {
		"DataPesatura","Data","Ora","Numero Progressivo","PesoNetto","Cliente","Fornitore",
		"Vettore","Destinazione","Descrizione","Merce","Nave","Num Consegna",
		"Num Documento","Peso Fattura","numero cataste","Peso Partenza",
		"Data_Em_Form","Note", "mindata"
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
	public Object create(Object o) throws IOException {
		throw new IOException("Attenzione: creazione non implementata");
	}
	@Override
	public void update(Object o) throws IOException {
		throw new IOException("Attenzione: update non implementata");
	}

	public FormattedDate getMinDataCarico( Consegna c ) throws Exception  {

		FormattedDate d = null ;
		String sql = c.getIter().getQueryin(c, null, null) ;


		String key = FileBasedCacher.getCachedKey(sql);

		if ( key != null && readFromCache )
			return (FormattedDate) FileBasedCacher.get(key);

		Connection conn = null ;
		conn  = db.getConnection();

		Connection cn = db.getConnection();
		try {
			PreparedStatement stmt = cn.prepareStatement(sql);
			Vector list = getByStatment(stmt);

			if ( list != null && list.size() > 0 ) {
				d = ((MovimentoQuadrelli) list.firstElement()).getData();
			}

			if ( key != null && writeCache ) {
				FileBasedCacher.set(key, d);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			db.freeConnection(cn);
		}

		return d ;
	}

	public Vector get(boolean scarichi, FormattedDate data, Stallo s ) throws Exception  {

		String sql  ;
		if ( scarichi) {
			//			c.getIter().getQueryin(c) ;
			sql = s.getConsegna().getIter().getQueryout(s, data) ;
		} else {
			sql = s.getConsegna().getIter().getQueryin(s.getConsegna(), s, data) ;
		}

		String key = FileBasedCacher.getCachedKey(sql);

		if ( key != null && readFromCache )
			return (Vector) FileBasedCacher.get(key);

		Connection cn = db.getConnection();

		try {
			PreparedStatement stmt = cn.prepareStatement(sql);
			Vector res = getByStatment(stmt);

			if ( key != null && writeCache ) {
				FileBasedCacher.set(key, res);
			}

			return res;
		}
		catch (Exception e) {
			e.printStackTrace();
			db.freeConnection(cn);
			return null ;
		}


		// ------------------------------------ WRONG ------
		//
		//		StringBuilder sql = new StringBuilder();
		//		sql.append("SELECT * FROM ").append(getTable()).append( " WHERE " );
		//
		//		if ( data != null ) {
		//			sql.append( " data = #?# AND " ) ;
		//		}
		//
		//		if ( c != null ) {
		//			sql.append( " `Num Consegna` = ? AND Merce = ? AND " ) ;
		//		}
		//
		//		if ( scarichi )
		//			sql.append(" fornitore in ( ");
		//		else
		//			sql.append(" cliente in ( ");
		//
		//		sql.append(PARCHI).append(")");

		//		PreparedStatement ps = db.getConnection().prepareStatement(sql.toString()) ;
		//		ResultSet rs = stmt.getResultSet();
		//		int params = 1 ;
		//		if ( data != null )
		//			ps.setString( params++, data.ymdString() ) ;
		//
		//		if ( c != null ) {
		//			ps.setInt( params++, c.getNumero().intValue() ) ;
		//			ps.setString(params++, c.getIdmerce().toString());
		//		}

		//		 if (rs != null )
		//		        while( rs.next() )
		//		            list.add( getFromRS(rs) );

	}

	//	public MovimentoQuadrelli getLast(boolean scarico, Consegna c ) throws Exception {
	//		StringBuilder sql = new StringBuilder(70)
	//			.append("SELECT top 1 * FROM ")
	//			.append(getTable())
	//			.append(" WHERE isscarico = ?");
	//
	//		if( c != null )
	//			sql.append(" AND idconsegna = ? ");
	//
	//
	//		PreparedStatement s = db.getConnection().prepareStatement(sql.toString()) ;
	//		s.setBoolean(1, scarico);
	//
	////		db.executeQuery(sql, conn);
	//		if( c != null )
	//			s.setInt(2, c.getId().intValue()) ;
	//
	//		ResultSet rs = s.executeQuery() ;
	//
	//		if ( rs != null && rs.next() ) {
	//			return (MovimentoQuadrelli)getFromRS(rs);
	//		}
	//
	//		return null ;
	//	}

	public void end() throws Exception {
		db.end();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<String> getCodiciStalli( Consegna c, FormattedDate data ) throws Exception {

		ArrayList<String> codici = null;

		StringBuilder sql = new StringBuilder(70)
		.append("SELECT cliente FROM ")
		.append(getTable())
		.append(" WHERE `num consegna` = ? AND merce = ? ");

		if ( data != null ) {
			if ( c.getIdIter() == 4 ) {
				sql.append(" and data = ? ");
			} else {
				sql.append(" and data >= ? ");
			}

		}

		sql.append(" group by cliente, data order by data, cliente" ) ;

		String key = FileBasedCacher.getCachedKey(sql);

		if ( key != null && readFromCache )
			return (ArrayList<String>) FileBasedCacher.get(key);

		Connection conn = db.getConnection();

		try {
			PreparedStatement s = conn.prepareStatement(sql.toString()) ;
			s.setString( 1 , c.getNumero().toString()) ;
			s.setString( 2 , c.getMerce().getCodiceQuadrelli() ) ;
			if ( data != null ) {
				s.setDate( 3 , new Date(data.getTime() ) );
			}

			ResultSet rs = s.executeQuery() ;
			String cod = null;
			if ( rs != null ) {
				codici = new ArrayList<String>(5);

				while (rs.next()) {
					cod = rs.getString(1);
					if ( ! codici.contains( cod )) {
						codici.add( cod );
					}
				}
			}

			if ( key != null && writeCache ) {
				FileBasedCacher.set(key, codici);
			}
		}
		finally {
			db.freeConnection(conn) ;
		}
		return codici;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<FormattedDate> getDateDaImportare( Consegna c , FormattedDate fromData ) throws Exception {
		StringBuilder sql = new StringBuilder(70)
		.append("SELECT distinct min(data) FROM ")
		.append(getTable());
		if ( c != null || fromData != null ) {
			sql.append(" where ");
		}
		if( c != null ) {
			sql.append(" `num consegna` = ? ");
		}
		sql.append(" group by  data , merce, `num consegna`  ");

		if ( fromData != null ) {
			sql.append(" having min(data) > ? ");
		}

		String key = FileBasedCacher.getCachedKey(sql);

		if ( key != null && readFromCache )
			return (ArrayList<FormattedDate>) FileBasedCacher.get(key);

		Connection conn = db.getConnection();

		ArrayList<FormattedDate> list = null ;
		try {
			//			ResultSet rs = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
			//           	ResultSet.CONCUR_READ_ONLY).executeQuery(sql.toString());
			//
			//			DatabaseMetaData metadata = conn.getMetaData();
			//            ResultSet schemas = metadata.getCatalogs();
			//            conn.setReadOnly(true);
			//            //schemas.first();
			//            while(schemas.next()){
			//                String schemaName = schemas.getString(1);
			//                System.out.println(schemaName);
			//                //schemas.next();
			//            }
			//            ResultSet rs = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
			//            		ResultSet.CONCUR_READ_ONLY).executeQuery(sql.toString());
			//				PreparedStatement s = conn.prepareStatement(sql.toString()) ;
			//			int n = 1 ;
			//			if( c != null )
			//				s.setString(n++, c.getNumero().toString()) ;
			//
			//			if ( fromData != null )
			//				s.setDate(n++, new Date( fromData.getTime() ) ) ;
			//			 s.executeQuery() ;

			PreparedStatement s = conn.prepareStatement(sql.toString()) ;

			int n = 1 ;
			if( c != null ) {
				s.setString(n++, c.getNumero().toString()) ;
			}

			if ( fromData != null ) {
				s.setDate(n++, new Date( fromData.getTime() ) ) ;
			}

			ResultSet rs = s.executeQuery();
			if ( rs != null ) {
				list = new ArrayList<FormattedDate>(5);
				while (rs.next()) {
					list.add( new FormattedDate(rs.getString(1)) );
				}
			}
			if ( key != null && writeCache ) {
				FileBasedCacher.set(key, list);
			}
		}

		finally {
			db.freeConnection(conn) ;
		}
		return list ;
	}

}