package com.gsoft.doganapt.data.adapters;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import com.gsoft.doganapt.data.Consegna;
import com.gsoft.doganapt.data.Documento;
import com.gsoft.doganapt.data.Merce;
import com.gsoft.doganapt.data.Movimento;
import com.gsoft.doganapt.data.Stallo;
import com.gtsoft.utils.common.BeanAdapter2;
import com.gtsoft.utils.common.FormattedDate;
import com.gtsoft.utils.common.UserException;
import com.gtsoft.utils.data.Field;
import com.gtsoft.utils.data.FieldSet;
import com.gtsoft.utils.sql.IDatabase2;

public abstract class MovimentoAdapter extends BeanAdapter2 {

	public MovimentoAdapter() {
		super();
	}
	public MovimentoAdapter( IDatabase2 db ) {
		super( db );
	}

	public void fillFields(Object obj) { 
		boolean fill = ( obj != null ) ;
		
		Movimento o = (Movimento) obj ;

		if ( fields == null || fields.isDirty() )
			fields = new FieldSet( getFieldsCount() );
		
		fields.add( Fields.ID, Field.Type.INTEGER , (fill)? o.getId() : null , true );
		
		fields.add( Fields.IDMERCE, Field.Type.INTEGER , (fill)? o.getIdMerce() : null );
		fields.add( Fields.IDCONSEGNA, Field.Type.INTEGER , (fill)? o.getIdConsegna() : null );
		fields.add( Fields.DATA, Field.Type.DATE , (fill)? o.getData() : null );
		fields.add( Fields.IDSTALLO, Field.Type.INTEGER , (fill)? o.getIdStallo() : null );
		fields.add( Fields.ISSCARICO, Field.Type.BOOLEAN , (fill)? o.getIsScarico() : null );
		fields.add( Fields.ISRETTIFICA, Field.Type.BOOLEAN , (fill)? o.getIsRettifica() : null );
		fields.add( Fields.SECCO, Field.Type.DOUBLE , (fill)? o.getSecco() : null );
		fields.add( Fields.UMIDO, Field.Type.DOUBLE , (fill)? o.getUmido() : null );
		fields.add( Fields.NUMREGISTRO, Field.Type.LONG , (fill)? o.getNumRegistro() : null );
		fields.add( Fields.LOCKED, Field.Type.BOOLEAN , (fill)? o.getIsLocked() : null );
		
		fields.add( Fields.NOTE, Field.Type.STRING , (fill)? o.getNote() : null );
		
		Documento d = null ;
		if ( fill) d = o.getDocumento();
		boolean myfill = fill && d != null ;
		fields.add( Fields.DOC_TIPO, Field.Type.STRING , (myfill)? d.getTipo() : null );
		fields.add( Fields.DOC_DATA, Field.Type.DATE , (myfill )? d.getData() : null );
		fields.add( Fields.DOC_NUMERO, Field.Type.STRING , (myfill)? d.getNumero() : null );
		
		d = null ;
		if ( fill) d = o.getDocumentoPV();
		
		myfill = fill && d != null ;
		fields.add( Fields.DOCPV_TIPO, Field.Type.STRING , (myfill)? d.getTipo() : null );
		fields.add( Fields.DOCPV_DATA, Field.Type.DATE , (myfill)? d.getData() : null );
		fields.add( Fields.DOCPV_NUMERO, Field.Type.STRING , (myfill)? d.getNumero() : null );
		
	}

	public abstract Object getFromFields ( ) ;
	
	
	public void updateCommonFields ( Movimento o ) {
		o.setIsScarico( (Boolean) fields.get( Fields.ISSCARICO).getValue() );
		o.setIsRettifica( (Boolean) fields.get( Fields.ISRETTIFICA).getValue() );
		o.setDocumento( 
				Documento.getDocumento( 
						(String) fields.get( Fields.DOC_TIPO).getValue(),
						(FormattedDate) fields.get( Fields.DOC_DATA).getValue(),
						(String) fields.get( Fields.DOC_NUMERO).getValue()
					));
		
		o.setDocumentoPV( 
				Documento.getDocumento( 
						(String) fields.get( Fields.DOCPV_TIPO).getValue(),
						(FormattedDate) fields.get( Fields.DOCPV_DATA).getValue(),
						(String) fields.get( Fields.DOCPV_NUMERO).getValue()
					));
					
		o.setNumRegistro( (Long) fields.get( Fields.NUMREGISTRO).getValue() );
		o.setIsLocked( (Boolean) fields.get( Fields.LOCKED).getValue() );
		
		o.setNote( (String) fields.get( Fields.NOTE).getValue() );
		
		o.setData( (FormattedDate) fields.get( Fields.DATA).getValue() );
		
	}
	public Object getFromFields ( Object obj ) {

		if ( obj == null ) {
			return null ;
		}
		
		Movimento o = (Movimento) obj ;
		
		o.setId( (Integer) fields.get( Fields.ID).getValue());
		o.setIdMerce( (Integer) fields.get( Fields.IDMERCE).getValue() );
		o.setIdConsegna( (Integer) fields.get( Fields.IDCONSEGNA).getValue() );
		o.setData( (FormattedDate) fields.get( Fields.DATA).getValue() );
		o.setIdStallo( (Integer) fields.get( Fields.IDSTALLO).getValue() );
		o.setSecco( (Double) fields.get( Fields.SECCO).getValue() );
		o.setUmido( (Double) fields.get( Fields.UMIDO).getValue() );
		
		updateCommonFields(o);
		
		return o ;
	}
	
	public static interface Fields {
		static final int ID = 0;
		static final int IDMERCE = 1;
		static final int IDCONSEGNA = 2 ;
		static final int DATA = 3;
		static final int IDSTALLO = 4 ;
		static final int ISSCARICO = 5 ;
		static final int ISRETTIFICA = 6;
		static final int SECCO = 7 ;
		static final int UMIDO = 8;
		static final int NUMREGISTRO = 9 ;
		static final int LOCKED = 10 ;
		static final int DOC_TIPO = 11;
		static final int DOC_NUMERO = 12;
		static final int DOC_DATA = 13;
		static final int DOCPV_TIPO = 14;
		static final int DOCPV_NUMERO = 15;
		static final int DOCPV_DATA = 16;
		static final int NOTE = 17;
		
		static final int FIELDSCOUNT = 18;
	}

	private static final String TABLE = "" ;
	private String[] fieldNames = {
		"id","idmerce","idconsegna","data","idstallo","isscarico","isrettifica", "secco",
			"umido","numregistro","locked",
			"doctype","docnum","docdate",
			"docpvtype","docpvnum","docpvdate",
			"note"
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
	public abstract String getTable() ;
	
	protected String getFieldsRegistro(String prefix) {
		String list = "%id, %idmerce, %idconsegna, %data, %idstallo, %isscarico, %isrettifica, sum(%secco) as secco, sum(%umido) as umido, %numregistro, %doctype, %docnum, %docdate, %docpvtype, %docpvnum, %docpvdate, %note, %posdoganale, %locked ";
			
		if ( prefix != null )
			list = list.replaceAll("%", prefix) ;
		else
			list = list.replaceAll("%", "") ;
		
		return list ;
	}
	
	public FormattedDate getLastDate( Consegna c , ArrayList<Integer> idStalli, boolean onlyScarichi ) throws Exception {
		StringBuilder sql = new StringBuilder(70)
			.append("SELECT max(data) FROM ")
			.append(getTable())

//			.append(" WHERE isscarico = ?") 
			;
			
		sql.append(" WHERE idconsegna = ?  ");
		
		
		
		if ( onlyScarichi ) {
			sql.append(" AND ")
				.append(" isscarico = 1 ");
		}
		
		
		ArrayList<Object> stalli ;
		if ( idStalli == null || idStalli.size() < 1 ) {
			stalli = c.getStalli() ;
		}
		else {
			stalli = new ArrayList<Object>(idStalli.size()) ;
			
			for ( Integer ids : idStalli ) {
				stalli.add(StalloAdapter.get(ids));
			}
			
		}
		if ( stalli != null && stalli.size() > 0 ) {
			
			sql.append(" AND ");
			
			sql.append(" idstallo in ( ");
			for ( Iterator i = stalli.iterator() ; i.hasNext() ;) {
				sql.append( ((Stallo)i.next()).getId() ) ;
				
				if ( i.hasNext() )
					sql.append( "," ) ;
			}
			sql.append( ")") ;
		}
		
		FormattedDate d = null ;
		Connection conn = db.getConnection() ;
		try {
			PreparedStatement s = conn.prepareStatement(sql.toString()) ;
			
//			s.setBoolean(1, scarico);
			
			s.setInt(1, c.getId().intValue()) ;
			
			ResultSet rs = s.executeQuery() ;
	
			if ( rs != null && rs.next() ) {
				String str = rs.getString(1);
				if ( str != null )
					d = new FormattedDate( str );
			}
		}
		finally {
			db.freeConnection(conn);
		}
		
		return d;
	}
	
	public Vector getByStallo(Integer id, String order, String limit ) throws Exception {
		return getByConsegna( false , null, id ,order, limit);
	}
	public Vector getByConsegna( boolean onlyRegistered, Integer idConsegna, Integer idStallo, String order, String limit ) throws Exception {
		StringBuilder sb = new StringBuilder(30);
		
		if ( idConsegna != null ) { 
			sb.append("idconsegna =")
				.append( idConsegna.toString() ) ; 
			
			if ( idStallo != null )
				sb.append(" AND ") ;
		}
		if ( idStallo != null )
			sb.append(" idstallo = ")
				.append(idStallo.toString()) ;
		
		if ( onlyRegistered ) { 
			if ( idStallo != null || idConsegna != null )
				sb.append(" AND ") ;
			sb.append(" numregistro > 0 ") ;
		}
		
		return getWithWhere( sb.toString() ,order, limit);
	}
	
	public Vector getByNumeroRegistro(Integer num ) throws Exception {

		return getWithWhere( "numregistro =" + num );
	}
	
	public void unregister( Long id ) throws Exception {
		
		Connection conn = null;
		
		StringBuilder sql = new StringBuilder(70)
		.append("update ")
		.append(getTable())
		.append(" set numregistro = null where numregistro = " + id )
		 ;
		try {
			conn = db.getConnection() ;
			db.executeNonQuery(sql.toString(), conn) ;
		
			shiftRegistro( true, new Integer( id.intValue() ), conn ) ;
			
		}
		catch ( Exception e ) {
			throw e ;
		}
		finally {
			db.freeConnection(conn) ;
		}
		
	}
	
	private void shiftRegistro(  boolean down , Integer from , Connection conn ) {

		StringBuilder sql = new StringBuilder(70)
			.append("update ")
			.append(getTable())
			.append(" set numregistro = numregistro ") ;
		
		
		if ( down ) {
			sql.append( " - 1 where numregistro > ") ;
		}
		else {
			sql.append( " + 1 where numregistro >= ") ;
		}

		sql.append( from ) ;
	
		db.executeNonQuery(sql.toString(), conn) ;
		
	}
	
	public ArrayList<Merce> getMerci() throws Exception {
		StringBuilder sql = new StringBuilder(70)
					.append("SELECT * FROM ")
					.append(getTable()).append(" GROUP BY idmerce ") 
					;
		ArrayList<Merce> list = null ;
		Connection conn = db.getConnection() ;
		try {
			
			PreparedStatement s = conn.prepareStatement(sql.toString()) ;
			Vector ids = getByStatment(s);
			
			if ( ids != null && ids.size() > 0 ) {
				list = new ArrayList<Merce>(ids.size());
				for ( Iterator i = ids.iterator(); i.hasNext() ; ) {
					list.add( MerceAdapter.get( ((Movimento) i.next() ).getIdMerce() )  );
				}
			}
		}
		finally {
			db.freeConnection(conn);
		}
		return list ;
	}
	
	public Vector<Integer>  getIdMerci() throws Exception {
		StringBuilder sql = new StringBuilder(70)
					.append("SELECT distinct idmerce FROM ")
					.append(getTable()).append(" WHERE numregistro > 0 ")  
					;
		Connection conn = db.getConnection() ;
		Vector<Integer> list = null ;
		try {
			
			PreparedStatement s = conn.prepareStatement(sql.toString()) ;
			
			ResultSet rs = s.executeQuery() ;
			
			list = new Vector<Integer>(rs.getFetchSize()) ;
	
			while( rs.next() ) 
				list.add( (Integer) rs.getObject(1) );
			
		}
		finally {
			db.freeConnection(conn);
		}
		return list ;
	}
	
	public Vector getRegistro( Boolean soloRegistrati , Consegna c ) throws Exception {
		return getRegistro( false, soloRegistrati , c,null,null,null,null,null,null,null,null );
	}
	public Vector getRegistro( Boolean soloRegistrati , Consegna c,
			Integer page,Integer rows,Hashtable<String,String> orderColumn,
			Integer numero,FormattedDate dal,FormattedDate al,Integer idMerce,Integer numConsegna ) throws Exception {
		
		return getRegistro( false, soloRegistrati , c,	page,rows,orderColumn,
				 numero, dal, al,idMerce, numConsegna );
	
	}
	public Vector getRegistro( Boolean ignoreNumRegistro , Boolean soloRegistrati , Consegna c,
			Integer page,Integer rows,Hashtable<String,String> orderColumn,
			Integer numero,FormattedDate dal,FormattedDate al,Integer idMerce,Integer numConsegna ) throws Exception {

		StringBuilder sql = new StringBuilder(70)
			.append("SELECT ")
			.append(getFieldsRegistro("R."))
			.append(" FROM ")
			.append(getTable())
			.append(" R ");
		
		boolean writeJoinConsegne=true;
		boolean writeJoinMerce=true;
		
		if(numConsegna!=null){
			sql.append(" INNER JOIN ");
			sql.append(new ConsegnaAdapter().getTable());
			sql.append(" C ON C.idConsegna = R.idconsegna ");
			writeJoinConsegne=false;
		}
		Collection<String> nomeColonna = null;
		if(orderColumn!=null){
			nomeColonna = orderColumn.keySet();
			if(nomeColonna!=null){
				for (Iterator<String> iterator = nomeColonna.iterator(); iterator.hasNext();) {
					String nc = iterator.next();
					boolean orderForFieldConsegna= nc!=null && nc.startsWith("C.");
					boolean orderForFieldMerce= nc!=null && nc.startsWith("M.");
					if(writeJoinConsegne && (orderForFieldConsegna || orderForFieldMerce)){
						sql.append(" INNER JOIN ");
						sql.append(new ConsegnaAdapter().getTable());
						sql.append(" C ON C.idConsegna = R.idconsegna ");
						writeJoinConsegne=false;
					}
					if(orderForFieldMerce && writeJoinMerce){
						sql.append(" INNER JOIN ");
						sql.append(new MerceAdapter().getTable());
						sql.append(" M ON M.id = C.idmerce ");
						writeJoinMerce=false;
					}
					
				}
			}
		}
		if ( soloRegistrati || c != null || numero != null || numConsegna != null || idMerce != null ) 
			sql.append(" WHERE ");
			
		if ( soloRegistrati ) {
			
			sql.append(" numregistro > 0 ");
			
			if ( c != null )
				sql.append(" AND "); 
		}

		if( c != null )
			sql.append(" idconsegna = ? ");
		
		if( numero != null )
			sql.append(" AND numregistro  = ").append(numero);
		
		if( numConsegna != null )
			sql.append(" AND C.numero  = ").append(numConsegna);
		
		if( idMerce != null )
			sql.append(" AND R.idmerce  = ").append(idMerce).append(" ");
		
		if( dal != null && al!=null){
			sql.append(" AND '").append(dal.fullString()).
				append("' <= data AND '").append(al.fullString()).append("' >= data ");
		}
		sql.append(" GROUP BY ");
		
		if ( c == null  ) {
			sql.append(" numregistro, ");
		}
		
		if ( ! soloRegistrati || ignoreNumRegistro ) 
			sql.append("data,");
		
		sql.append( " isscarico , isrettifica");
		
		sql.append(" ORDER BY ");
		
		
		if(nomeColonna!=null && nomeColonna.size() > 0){
			String nc;
			boolean first=true;
			for (Iterator<String> iterator = nomeColonna.iterator(); iterator.hasNext();) {
				 if(!first) sql.append(",");
				 nc = iterator.next();
				 sql.append(nc);
				 String ascDesc= orderColumn.get(nc);
				 if(ascDesc!=null)
					sql.append(" ").append(ascDesc);
				 first=false;
			}	
		}
		else {
			if( ! ignoreNumRegistro ) 
				sql.append( " numregistro, ") ;
			
			sql.append( "data, isrettifica, isscarico, idstallo " );
		}
		
		if(page!=null && rows!=null){
			sql.append(" LIMIT ").append((page - 1) *  rows).append(",").append(rows);
		}
		Vector list = null ;
		
		Connection conn = db.getConnection() ;
		try {
			
			PreparedStatement s = conn.prepareStatement(sql.toString()) ;		
			if( c != null )	
				s.setInt(1, c.getId().intValue()) ;
			
			list = getByStatment(s);
	
			
		}
		finally {
			db.freeConnection(conn);
		}
		
		return list ;
	}
	
	public Vector getDaStampare( Integer from , Integer num ) throws Exception {

		StringBuilder sql = new StringBuilder(70)
			.append("SELECT ")
			.append(getFieldsRegistro("R."))
			.append(" FROM ")
			.append(getTable())
			.append(" R ");
		
		
		if ( from != null) 
			sql.append(" WHERE ");
			
		if ( from != null ) {
			sql.append("numregistro >= ?");
		}
		
		sql.append(" GROUP BY numregistro ORDER BY numregistro , data, isrettifica, idstallo " );
		
		if( num!=null ){
			sql.append(" LIMIT ").append(num);
		}
		Vector list = null ;
		
		Connection conn = db.getConnection() ;
		try {
			
			PreparedStatement s = conn.prepareStatement(sql.toString()) ;		
			if( from != null )	
				s.setInt(1, from.intValue()) ;
			
			list = getByStatment(s);
	
			
		}
		finally {
			db.freeConnection(conn);
		}
		
		return list ;
	}
	
	public Integer confermaStampa( Integer from , Integer to ) throws Exception {

		StringBuilder sql = new StringBuilder(70)
			.append(" UPDATE ")
			.append(getTable())
			.append( " SET locked = 1 WHERE numregistro >= ? AND numregistro <=  ?");
		
		Integer ret = null ;
		Connection conn = db.getConnection() ;
		try {
			
			PreparedStatement s = conn.prepareStatement(sql.toString()) ;		
			
			s.setInt(1, from.intValue()) ;
			s.setInt(2, to.intValue()) ;
			
			ret = new Integer( s.executeUpdate() );
	
			
		}
		finally {
			db.freeConnection(conn);
		}
		
		return ret ;
	}
	
	/*
	 * Tira fuori i movimenti da registrare raggruppandoli in base anche all'iter
	 */
	public Vector getDaRegistrare2( Consegna c , FormattedDate data ) throws Exception {

		/*
		 * 
		 * SELECT
R.id, R.idmerce, R.idconsegna, R.data, R.idstallo, R.isscarico, R.isrettifica, sum(R.secco) as secco,
sum(R.umido) as umido, R.numregistro, R.doctype, R.docnum, R.docdate, R.docpvtype, R.docpvnum, R.docpvdate, R.note,
R.posdoganale, R.locked
FROM registrodoganale R
inner join consegne c on r.idconsegna = c.idconsegna
inner join iter i on c.iter = i.id
WHERE  numregistro is null GROUP BY case when i.singolicarichi = 1 then r.id else r.data end
, idconsegna, isscarico , isrettifica ORDER BY  data, isrettifica, idstallo

		 */
		Integer idConsegna = c == null ? null : c.getId() ;
		
		StringBuilder sql = new StringBuilder(120)
			.append("SELECT * FROM ")
			.append(getTable())
			.append(" r INNER JOIN consegne c ON r.idconsegna = c.idconsegna " )
			.append(" INNER JOIN iter i ON c.iter = i.id  WHERE numregistro IS NULL ")
			 ;
			
		if( idConsegna != null )
			sql.append(" AND r.idconsegna = ? ");
		
		if( data != null )
			sql.append("AND r.data = ? ");
		
		
		sql.append(
				" group by case when i.singolicarichi = 1 then r.id else r.data end , r.idconsegna, r.isscarico , r.isrettifica")
			.append(" order by data, isrettifica, idstallo" );

		Vector list = null ;
		
		Connection conn = db.getConnection() ;
		try {
			
			int pos = 1 ;
			PreparedStatement s = conn.prepareStatement(sql.toString()) ;
			
			if( idConsegna != null )	
				s.setInt(pos++, idConsegna.intValue()) ;
			
			if( data != null )	
				s.setDate(pos++, new Date(data.getTime()) ) ;
			
			list = getByStatment(s);
	
			
		}
		finally {
			db.freeConnection(conn);
		}
		
		return list ;
	}
	
	public Vector getDaRegistrare( Integer idConsegna , FormattedDate data ) throws Exception {

		StringBuilder sql = new StringBuilder(70)
			.append("SELECT * FROM ")
			.append(getTable())
			.append(" WHERE numregistro is null ");
			 ;
			
		if( idConsegna != null )
			sql.append(" AND idconsegna = ? ");
		
		if( data != null )
			sql.append("AND data = ? ");
		
		
		sql.append( " order by data, isrettifica, isscarico, idstallo" );

		Vector list = null ;
		
		Connection conn = db.getConnection() ;
		try {
			
			int pos = 1 ;
			PreparedStatement s = conn.prepareStatement(sql.toString()) ;
			
			if( idConsegna != null )	
				s.setInt(pos++, idConsegna.intValue()) ;
			
			if( data != null )	
				s.setDate(pos++, new Date(data.getTime()) ) ;
			
			list = getByStatment(s);
	
			
		}
		finally {
			db.freeConnection(conn);
		}
		
		return list ;
	}
	
	public Vector getPartitario( Consegna c ) throws Exception {

		String sql = "SELECT id, idmerce, idconsegna, data, idstallo, cast(isscarico as binary ) as isscarico, isrettifica, secco, umido, numregistro, doctype, docnum, docdate, docpvtype, docpvnum, docpvdate, note, posdoganale, locked, 0 , 0 " +
				" FROM where idconsegna = ? union all select * from registroiva where idconsegna = ? order by idstallo, data ";
		
		
		Vector list = null ;
		
		Connection conn = db.getConnection() ;
		try {
			
			PreparedStatement s = conn.prepareStatement(sql.toString()) ;		
			
			s.setInt(1, c.getId().intValue()) ;
			s.setInt(2, c.getId().intValue()) ;
			
			list = getByStatment(s);
	
		}
		finally {
			db.freeConnection(conn);
		}
		
		return list ;
	}
	
	public ArrayList<Integer> getIdStalli(Integer idConsegna ) throws Exception {

		String sql = "SELECT distinct idstallo FROM " + getTable() +
			" where idconsegna = ? and idstallo is not null  order by idstallo ";
		
		ArrayList<Integer> list = null ;
		
		Connection conn = db.getConnection() ;
		try {
			
			PreparedStatement s = conn.prepareStatement(sql.toString()) ;		
			
			s.setInt(1, idConsegna.intValue()) ;
			ResultSet rs = null ;
		
			rs = s.executeQuery() ;
	
			list = new ArrayList<Integer>(rs.getFetchSize()) ;
	
			while( rs.next() ) 
				list.add( (Integer) rs.getObject(1) );
			
		}
		finally {
			db.freeConnection(conn);
		}
		
		return list ;
	}
	 
	public Vector getGroup(  Integer idConsegna , FormattedDate d , boolean scarico, boolean rettifica) throws Exception {
		
		return getWithWhere(" idconsegna = " + idConsegna.toString() 
				+ " AND data = '" + d.ymdString() + "'" 
				+ " AND isrettifica = '" + (rettifica ? "1":"0") + "'" 
				+ " AND isscarico = '" + (scarico ? "1":"0") + "'" 
				) ;
		
	}
	
	public Vector getByData( Integer idConsegna , FormattedDate d ) throws Exception {
		
		return getWithWhere(" idconsegna = " + idConsegna.toString() + " AND data = '" + d.ymdString() + "'" ) ;
		
	}
	
//	double importa( Vector list , Consegna c, Stallo s, FormattedDate data ) throws Exception {
//		
//		Movimento m = null ;
//		double sommaPesoUmido = 0 ;
//
//		if ( list != null )
//			for ( Iterator i = list.iterator() ; i.hasNext();) {
//
//				m = getMovimento(false, (MovimentoQuadrelli) i.next(), c ) ;
//				m.setSecco(c.calcolaSecco(m.getUmido()));
//				m.setIdConsegna(c.getId());
//
//				if ( data != null )
//					m.setData(data);
//				
//				create(m);
//				
//				m.getStallo().notifyMovimento(m, true);
//
//				sommaPesoUmido += m.getUmido().doubleValue() ;
//			}
//
//		return sommaPesoUmido ;
//	}
	
	public abstract void doneNextNumRegistro() throws Exception;
	public abstract Integer getNextNumRegistro() throws Exception;
	public abstract Movimento newMovimento( );
	
	
	public synchronized boolean checkIntegrity() throws Exception  {
		return checkIntegrity(false);
	}
	
	/* *
	 * 
	 * Prende i movimenti da un certo num in poi ignorando il num di registro     
		SELECT *
		FROM registrodoganale R  WHERE  numregistro > 0 
		ORDER BY  data, numregistro , isrettifica, idstallo

		 * 
		 *  Poi cicla sui movimenti e gli cambia la numerazione in modo progressivo 
		 *  ( oppure puo' solo verificare che sia corretta x vedere se ci son buchi x es ) 
		 *   
		 *  verificando se l'iter dice di registrare i singoli carichi oppure no
		 *  e assegnando il num di reg successivo in base alle regole corrette
	 */
	public synchronized boolean checkIntegrity( boolean doFix ) throws Exception {
		
		Vector list = getRegistro( true , true , null,null,null,null,null,null,null,null,null );
		
		Movimento m ;
		int currNum = 0 ;

		Iterator i = list.iterator() ;
		while ( i.hasNext()  ) { 
			m = (Movimento) i.next() ;
			
			if ( ! m.getIsLocked() ) break ;
			
			currNum = m.getNumRegistro().intValue() ;

		}
//		throw new Exception();
		if( currNum == 0 ) 
			i = list.iterator() ;
		
		Connection conn = db.getConnection() ;
		
		boolean ret = false ;
		
		try {
		while ( i.hasNext() ) {
			m = (Movimento) i.next() ;
			
						
			currNum ++;
			
			if ( doFix ) {
				
				Integer num = new Integer( currNum ) ;
				
//				String ids = getIdsFromNumRegistro( m ) ;
				
				
				
				
//					shiftRegistro( false, num , conn  ) ;
					
					updateNumRegistro(m , num, conn);
				
			} 
			else if ( m.getNumRegistro().intValue() != currNum  ) {
				ret = false ;
			}			
		}
			ret = true ;
		}
		catch ( Exception e ) {
			throw new UserException(e) ;
		}
		finally {
			db.freeConnection(conn);
		}
		return ret ;
	}
	
	private void updateNumRegistro(Movimento m, Integer newNum , Connection cn ) throws Exception  {
		
		StringBuilder sql = new StringBuilder()
			.append("UPDATE ").append( getTable())
			.append(" t1 INNER JOIN ").append( getTable())
			.append(" t2 ON t1.").append(fieldNames[Fields.NUMREGISTRO])
			.append(" = t2.").append(fieldNames[Fields.NUMREGISTRO])
			.append(" AND t1.").append(fieldNames[Fields.IDCONSEGNA])
			.append(" = t2.").append(fieldNames[Fields.IDCONSEGNA])
			.append(" AND t1.").append(fieldNames[Fields.ISSCARICO])
			.append(" = t2.").append(fieldNames[Fields.ISSCARICO])
			.append(" AND t1.").append(fieldNames[Fields.ISRETTIFICA])
			.append(" = t2.").append(fieldNames[Fields.ISRETTIFICA])
			.append(" AND t1.").append(fieldNames[Fields.DATA])
			.append(" = t2.").append(fieldNames[Fields.DATA])
			.append(" AND t2.").append(fieldNames[Fields.ID])
			.append(" = ? ")
			.append(" SET t1.").append(fieldNames[Fields.NUMREGISTRO])
			.append(" = ? ");
		
			PreparedStatement p = cn.prepareStatement(sql.toString()) ;
			p.setLong(1, m.getId() ) ;
			
			p.setInt(2, newNum ) ;
			p.executeUpdate();
	}
	
//	private String getIdsFromNumRegistro( Movimento m ) throws Exception {
//		
//		StringBuilder sql = new StringBuilder()
//			.append("SELECT ").append(fieldNames[Fields.ID])
//			.append(" FROM ").append( getTable())
//			.append(" WHERE ").append(fieldNames[Fields.NUMREGISTRO]) 
//			.append(" = ?") ;
//		
//		StringBuilder ret = null ;
//		
//		Connection cn = db.getConnection();
//		try {
//			PreparedStatement s = cn.prepareStatement(sql.toString()) ;
//			
//			s.setLong(1, m.getNumRegistro()) ;
//			
//			ResultSet rs = s.executeQuery() ;
//			
//			if (rs != null )
//				while( rs.next() ) { 
//					
//					if ( ret == null )
//						ret = new StringBuilder("");
//					else 
//						ret.append(",") ;
//					
//					ret.append( rs.getLong(1) );
//				}
//		}
//		catch (Exception e) {
//			throw e ;
//		}
//		finally {
//			db.freeConnection(cn) ;
//		}
//
//		if ( ret == null )
//			return "" ;
//		
//		return ret.toString() ;
//	}
}