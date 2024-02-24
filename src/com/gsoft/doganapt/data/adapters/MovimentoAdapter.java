package com.gsoft.doganapt.data.adapters;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import com.gsoft.doganapt.cmd.Login;
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

	public static final String COD_POSIZIONEDOGANALE_NAZ = "4500";
	public static final String COD_POSIZIONEDOGANALE_ENAZ = "4571";
	public static final String COD_POSIZIONEDOGANALE_EXT = "7100";
	public static final String COD_PROVENIENZA_MAGDOG = "MAGDOG";
	public static final String COD_PROVENIENZA_PORTO = "PORTO";
	public static final String COD_PROVENIENZA_PORTOVESME = "PV";
	public static final String COD_POSIZIONEDOGANALE_COM = "0000";
	public static final String NOTE_SCARICO = "Estr. da dep.IVA";
	public static final String NOTE_CARICO =  "Intr. in dep.IVA";


	public MovimentoAdapter() {
		super();
	}
	public MovimentoAdapter( final IDatabase2 db ) {
		super( db );
	}

	@Override
	public void fillFields(final Object obj) {
		final boolean fill = ( obj != null ) ;

		final Movimento o = (Movimento) obj ;

		if ( fields == null || fields.isDirty() ) {
			fields = new FieldSet( getFieldsCount() );
		}

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
		if ( fill) {
			d = o.getDocumento();
		}
		boolean myfill = fill && d != null ;
		fields.add( Fields.DOC_TIPO, Field.Type.STRING , (myfill)? d.getTipo() : null );
		fields.add( Fields.DOC_DATA, Field.Type.DATE , (myfill )? d.getData() : null );
		fields.add( Fields.DOC_NUMERO, Field.Type.STRING , (myfill)? d.getNumero() : null );

		d = null ;
		if ( fill) {
			d = o.getDocumentoPV();
		}

		myfill = fill && d != null ;
		fields.add( Fields.DOCPV_TIPO, Field.Type.STRING , (myfill)? d.getTipo() : null );
		fields.add( Fields.DOCPV_DATA, Field.Type.DATE , (myfill)? d.getData() : null );
		fields.add( Fields.DOCPV_NUMERO, Field.Type.STRING , (myfill)? d.getNumero() : null );
		fields.add( Fields.POSIZIONE_DOGANALE, Field.Type.STRING , (fill)? o.getCodPosizioneDoganale() : null );
		fields.add( Fields.PROVENIENZA, Field.Type.STRING , (fill)? o.getCodProvenienza() : null );
	}

	public abstract Object getFromFields ( ) ;

	@Override
	public void update() throws IOException {
		if( (Boolean) getField(Fields.LOCKED).getValue() ) {
			System.out.println("Errore: Tentativo di modificare un movimento stampato.");
			throw new IOException("Il Movimento è stato stampato e non puo' essere modificato!");
		}
		super.update();
	}

	public void updateCommonFields ( final Movimento o ) {
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

		o.setCodPosizioneDoganale( (String) fields.get( Fields.POSIZIONE_DOGANALE).getValue() );

		o.setCodProvenienza( (String) fields.get( Fields.PROVENIENZA).getValue() );

	}
	public Object getFromFields ( final Object obj ) {

		if ( obj == null )
			return null ;

		final Movimento o = (Movimento) obj ;

		o.setId( (Integer) fields.get( Fields.ID).getValue());
		o.setIdMerce( (Integer) fields.get( Fields.IDMERCE).getValue() );
		o.setIdConsegna( (Integer) fields.get( Fields.IDCONSEGNA).getValue() );
		o.setData( (FormattedDate) fields.get( Fields.DATA).getValue() );
		o.setIdStallo( (Integer) fields.get( Fields.IDSTALLO).getValue() );
		o.setSecco( (Double) fields.get( Fields.SECCO).getValue() );
		o.setUmido( (Double) fields.get( Fields.UMIDO).getValue() );

		o.setCodPosizioneDoganale( (String) fields.get( Fields.POSIZIONE_DOGANALE).getValue() );
		o.setCodProvenienza( (String) fields.get( Fields.PROVENIENZA).getValue() );

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
		static final int POSIZIONE_DOGANALE = 18;
		static final int PROVENIENZA = 19;

		static final int FIELDSCOUNT = 20;
	}

	private static final String TABLE = "" ;
	private final String[] fieldNames = {
			"id","idmerce","idconsegna","data","idstallo","isscarico","isrettifica", "secco",
			"umido","numregistro","locked",
			"doctype","docnum","docdate",
			"docpvtype","docpvnum","docpvdate",
			"note", "posdoganale", "codprovenienza"
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
	public abstract String getTable() ;

	protected String getFieldsRegistro(final String prefix) {
		String list = "%id, %idmerce, %idconsegna, %data, %idstallo, %isscarico, %isrettifica, sum(%secco) as secco, sum(%umido) as umido, %numregistro, %doctype, %docnum, %docdate, %docpvtype, %docpvnum, %docpvdate, %note, %posdoganale, %codprovenienza, %locked ";

		if ( prefix != null ) {
			list = list.replaceAll("%", prefix) ;
		} else {
			list = list.replaceAll("%", "") ;
		}

		return list ;
	}
	
	public Movimento getLast( final Consegna c , final Stallo stallo, final boolean onlyScarichi, final boolean onlyRegistered ) throws Exception {

		final StringBuilder sql = new StringBuilder(70)
		.append("SELECT " )
		.append(getFieldsRegistro(null)) 
		.append(" FROM ")
		.append(getTable());

		sql.append(" WHERE deleted = 0 AND idconsegna = ?  ");

		if ( onlyScarichi ) {
			sql.append(" AND isscarico = 1 ");
		}

		if ( stallo != null  ) {
			sql.append(" AND idstallo = ? ");
		}
		
		if ( onlyRegistered ) {
			sql.append(" AND numregistro > 0 ") ;
		}
		
		sql.append(" GROUP BY numregistro, data, isscarico , isrettifica")
		   .append(" ORDER BY data DESC LIMIT 1 ");

		Movimento m = null;
		
//		Login.debug("\nLast: " + sql + "\n idConsegna:" + c.getId() + " idStallo:" + stallo.getId() );
		
		final Connection conn = db.getConnection() ;
		try {
			final PreparedStatement s = conn.prepareStatement(sql.toString());
			
			s.setInt(1, c.getId().intValue()) ;
			if ( stallo != null ) {
				s.setInt(2, stallo.getId());
			}
			
			final ResultSet rs = s.executeQuery() ;

			if ( rs != null && rs.next() ) {
				m = (Movimento) getFromRS(rs);
			}
		}
		finally {
			db.freeConnection(conn);
		}

		return m;
	}

	public FormattedDate getLastDate( final Consegna c , final ArrayList<Integer> idStalli, final boolean onlyScarichi ) throws Exception {
		final StringBuilder sql = new StringBuilder(70)
		.append("SELECT max(data) FROM ")
		.append(getTable());

		sql.append(" WHERE deleted = 0 AND idconsegna = ?  ");

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

			for ( final Integer ids : idStalli ) {
				stalli.add(StalloAdapter.get(ids));
			}
		}
		if ( stalli != null && stalli.size() > 0 ) {

			sql.append(" AND ");

			sql.append(" idstallo in ( ");
			for ( final Iterator<Object> i = stalli.iterator() ; i.hasNext() ;) {
				sql.append( ((Stallo)i.next()).getId() ) ;

				if ( i.hasNext() ) {
					sql.append( "," ) ;
				}
			}
			sql.append( ")") ;
		}

		FormattedDate d = null ;
		final Connection conn = db.getConnection() ;
		try {
			final PreparedStatement s = conn.prepareStatement(sql.toString()) ;

			//			s.setBoolean(1, scarico);

			s.setInt(1, c.getId().intValue()) ;

			final ResultSet rs = s.executeQuery() ;

			if ( rs != null && rs.next() ) {
				final String str = rs.getString(1);
				if ( str != null ) {
					d = new FormattedDate( str );
				}
			}
		}
		finally {
			db.freeConnection(conn);
		}
		return d;
	}

	public Vector<Movimento> getByStallo(final Integer id, final String order, final String limit ) throws Exception {
		return getByConsegna( false , null, id ,order, limit);
	}
	public Vector<Movimento> getByConsegna( final boolean onlyRegistered, final Integer idConsegna, final Integer idStallo, final String order, final String limit ) throws Exception {
		final StringBuilder sb = new StringBuilder(50);

		sb.append(" deleted = 0 " );
		if ( idConsegna != null ) {
			sb.append("AND idconsegna =")
			.append( idConsegna.toString() ) ;
		}
		if ( idStallo != null ) {
			sb.append(" AND idstallo = ")
			.append(idStallo.toString()) ;
		}

		if ( onlyRegistered ) {
			//			if ( idStallo != null || idConsegna != null )
			//				sb.append(" AND ") ;
			sb.append(" AND numregistro > 0 ") ;
		}
		return getWithWhere( sb.toString() ,order, limit);
	}

	public Vector<?> getByNumeroRegistro(final Integer num ) throws Exception {
		return getWithWhere( " deleted = 0 AND numregistro =" + num );
	}

	public void setDeleted( final Integer id ) throws Exception {

		Connection conn = null;

		final StringBuilder sql = new StringBuilder(70)
		.append("update ")
		.append(getTable())
		.append(" set deleted = 1 where id = " + id )
		// .append ( "  AND numregistro = null ")
		;
		try {
			conn = db.getConnection() ;
			db.executeNonQuery(sql.toString(), conn) ;
		}
		catch ( final Exception e ) {
			throw e ;
		}
		finally {
			db.freeConnection(conn) ;
		}
	}

	public void unregister( final Long numreg ) throws Exception {

		Connection conn = null;

		final StringBuilder sql = new StringBuilder(70)
		.append("update ")
		.append(getTable())
		.append(" set numregistro = null where deleted = 0 AND numregistro = " + numreg )
		;
		try {
			conn = db.getConnection() ;
			db.executeNonQuery(sql.toString(), conn) ;

			shiftRegistro( true, Integer.valueOf( numreg.intValue() ), conn ) ;
		}
		catch ( final Exception e ) {
			throw e ;
		}
		finally {
			db.freeConnection(conn) ;
		}

	}

	private void shiftRegistro(  final boolean down , final Integer from , final Connection conn ) {

		final StringBuilder sql = new StringBuilder(70)
		.append("update ")
		.append(getTable())
		.append(" set numregistro = numregistro ") ;

		if ( down ) {
			sql.append( " - 1 where deleted = 0 AND numregistro > ") ;
		}
		else {
			sql.append( " + 1 where deleted = 0 AND numregistro >= ") ;
		}
		sql.append( from ) ;

		db.executeNonQuery(sql.toString(), conn) ;

	}

	public ArrayList<Merce> getMerci() throws Exception {
		final StringBuilder sql = new StringBuilder(70)
		.append("SELECT * FROM ")
		.append(getTable()).append(" WHERE deleted = 0 GROUP BY idmerce ")
		;
		ArrayList<Merce> list = null ;
		final Connection conn = db.getConnection() ;
		try {

			final PreparedStatement s = conn.prepareStatement(sql.toString()) ;
			final Vector<Movimento> ids = getByStatment(s);

			if ( ids != null && ids.size() > 0 ) {
				list = new ArrayList<Merce>(ids.size());
				for ( final Iterator<Movimento> i = ids.iterator(); i.hasNext() ; ) {
					list.add( MerceAdapter.get( i.next().getIdMerce() )  );
				}
			}
		}
		finally {
			db.freeConnection(conn);
		}
		return list ;
	}

	public Vector<Integer>  getIdMerci() throws Exception {
		final StringBuilder sql = new StringBuilder(70)
		.append("SELECT distinct idmerce FROM ")
		.append(getTable()).append(" WHERE deleted = 0 AND numregistro > 0 ")
		;
		final Connection conn = db.getConnection() ;
		Vector<Integer> list = null ;
		try {
			final PreparedStatement s = conn.prepareStatement(sql.toString()) ;

			final ResultSet rs = s.executeQuery() ;
			list = new Vector<Integer>(rs.getFetchSize()) ;

			while( rs.next() ) {
				list.add( (Integer) rs.getObject(1) );
			}
		}
		finally {
			db.freeConnection(conn);
		}
		return list ;
	}

	public Vector<Movimento> getRegistro( final Boolean soloRegistrati , final Consegna c ) throws Exception {
		return getRegistro( false, soloRegistrati , c,null,null,null,null,null,null,null,null );
	}
	public Vector<Movimento> getRegistro( final Boolean soloRegistrati , final Consegna c,
			final Integer page,final Integer rows,final Hashtable<String,String> orderColumn,
			final Integer numero,final FormattedDate dal,final FormattedDate al,final Integer idMerce,final Integer numConsegna ) throws Exception {

		return getRegistro( false, soloRegistrati , c,	page,rows,orderColumn,
				numero, dal, al,idMerce, numConsegna );

	}
	public Vector<Movimento> getRegistro( final Boolean ignoreNumRegistro , final Boolean soloRegistrati , final Consegna c,
			final Integer page,final Integer rows,final Hashtable<String,String> orderColumn,
			final Integer numero,final FormattedDate dal,final FormattedDate al,final Integer idMerce,final Integer numConsegna ) throws Exception {

		final StringBuilder sql = new StringBuilder(70)
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
				for (final String nc : nomeColonna) {
					final boolean orderForFieldConsegna= nc!=null && nc.startsWith("C.");
					final boolean orderForFieldMerce= nc!=null && nc.startsWith("M.");
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
		if ( soloRegistrati || c != null || numero != null || numConsegna != null || idMerce != null ) {
			sql.append(" WHERE R.deleted = 0 ");
		}

		if ( soloRegistrati ) {
			sql.append(" AND numregistro > 0 ");
			//			if ( c != null )
			//				sql.append(" AND ");
		}

		if( c != null ) {
			sql.append(" AND idconsegna = ? ");
		}

		if( numero != null ) {
			sql.append(" AND numregistro  = ").append(numero);
		}

		if( numConsegna != null ) {
			sql.append(" AND C.numero  = ").append(numConsegna);
		}

		if( idMerce != null ) {
			sql.append(" AND R.idmerce  = ").append(idMerce).append(" ");
		}

		if( dal != null ) { 
			sql.append(" AND data >= '").append(dal.fullString()).append("' ");
		}
		
		if( al!=null){
			sql.append(" AND data <= '").append(al.fullString()).append("' ");
		}
		sql.append(" GROUP BY ");

		if ( c == null  || ! soloRegistrati ) {

			if ( this instanceof MovimentoIvaAdapter ) {
				sql.append("if( isscarico, idstallo , numregistro) ,");
			}
		}
		sql.append(" numregistro, ");

		if ( ! soloRegistrati || ignoreNumRegistro ) {
			sql.append("data,");
		}

		sql.append( " isscarico , isrettifica");

		sql.append(" ORDER BY ");


		if(nomeColonna!=null && nomeColonna.size() > 0){
			String nc;
			boolean first=true;
			for (final Iterator<String> iterator = nomeColonna.iterator(); iterator.hasNext();) {
				if(!first) {
					sql.append(",");
				}
				nc = iterator.next();
				sql.append(nc);
				final String ascDesc= orderColumn.get(nc);
				if(ascDesc!=null) {
					sql.append(" ").append(ascDesc);
				}
				first=false;
			}
		}
		else {
			if( ! ignoreNumRegistro ) {
				sql.append( " numregistro, ") ;
			}
			sql.append( "data, isrettifica, isscarico, idstallo " );
		}

		if(page!=null && rows!=null){
			sql.append(" LIMIT ").append((page - 1) *  rows).append(",").append(rows);
		} else if ( rows != null ) {
			sql.append(" LIMIT ").append(rows);
		}
		
		Vector<Movimento> list = null ;

		final Connection conn = db.getConnection() ;
		try {
			//			System.out.println(sql);

			final PreparedStatement s = conn.prepareStatement(sql.toString()) ;
			if( c != null ) {
				s.setInt(1, c.getId().intValue()) ;
			}

			list = getByStatment(s);
		} catch (Exception e ) {
			e.printStackTrace();
		}
		finally {
			db.freeConnection(conn);
		}

		return list ;
	}

	public Vector<Movimento> getDaStampare( final Integer from , final Integer num ) throws Exception {

		final StringBuilder sql = new StringBuilder(70)
		.append("SELECT ")
		.append(getFieldsRegistro("R."))
		.append(" FROM ")
		.append(getTable())
		.append(" R ");


		sql.append(" WHERE deleted = 0 ");

		if ( from != null ) {
			sql.append(" AND numregistro >= ?");
		}

		sql.append(" GROUP BY numregistro ORDER BY numregistro , data, isrettifica, idstallo " );

		if( num!=null ){
			sql.append(" LIMIT ").append(num);
		}
		Vector<Movimento> list = null ;

		final Connection conn = db.getConnection() ;
		try {

			final PreparedStatement s = conn.prepareStatement(sql.toString()) ;
			if( from != null ) {
				s.setInt(1, from.intValue()) ;
			}

			list = getByStatment(s);


		}
		finally {
			db.freeConnection(conn);
		}

		return list ;
	}

	public Integer confermaStampa( final Integer from , final Integer to ) throws Exception {

		final StringBuilder sql = new StringBuilder(70)
		.append(" UPDATE ")
		.append(getTable())
		.append( " SET locked = 1 WHERE deleted = 0 AND numregistro >= ? AND numregistro <=  ?");

		Integer ret = null ;
		final Connection conn = db.getConnection() ;
		try {

			final PreparedStatement s = conn.prepareStatement(sql.toString()) ;

			s.setInt(1, from.intValue()) ;
			s.setInt(2, to.intValue()) ;

			ret = Integer.valueOf( s.executeUpdate() );


		}
		finally {
			db.freeConnection(conn);
		}

		return ret ;
	}

	/*
	 * Tira fuori i movimenti da registrare raggruppandoli in base anche all'iter
	 */
	public Vector<Movimento> getDaRegistrare2( final Consegna c , final FormattedDate data ) throws Exception {

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
		final Integer idConsegna = c == null ? null : c.getId() ;

		final StringBuilder sql = new StringBuilder(120)
		.append("SELECT * FROM ")
		.append(getTable())
		.append(" r INNER JOIN consegne c ON r.idconsegna = c.idconsegna " )
		.append(" INNER JOIN iter i ON c.iter = i.id  WHERE deleted = 0 AND numregistro IS NULL ")
		;

		if( idConsegna != null ) {
			sql.append(" AND r.idconsegna = ? ");
		}

		if( data != null ) {
			sql.append("AND r.data = ? ");
		}


		sql.append(
				" group by case when i.singolicarichi = 1 then r.id else r.data end , r.idconsegna, r.isscarico , r.isrettifica")
				.append(" order by data, isrettifica, idstallo" );

		Vector<Movimento> list = null ;

		final Connection conn = db.getConnection() ;
		try {

			int pos = 1 ;
			final PreparedStatement s = conn.prepareStatement(sql.toString()) ;

			if( idConsegna != null ) {
				s.setInt(pos++, idConsegna.intValue()) ;
			}

			if( data != null ) {
				s.setDate(pos++, new Date(data.getTime()) ) ;
			}

			list = getByStatment(s);


		}
		finally {
			db.freeConnection(conn);
		}

		return list ;
	}

	public Vector<Movimento> getDaRegistrare( final Integer idConsegna , final FormattedDate data ) throws Exception {
		return getDaRegistrare( idConsegna , data, null);
	}
	public Vector<Movimento> getDaRegistrareByIdStallo(final Integer idStallo) throws Exception {
		return getDaRegistrare( null, null, idStallo);
	}
	@SuppressWarnings("unchecked")
	public Vector<Movimento> getDaRegistrare( final Integer idConsegna , final FormattedDate data, final Integer idStallo ) throws Exception {

		final StringBuilder sql = new StringBuilder(70)
		.append("SELECT * FROM ")
		.append(getTable())
		.append(" WHERE deleted = 0 AND numregistro is null ");
		;

		if( idConsegna != null ) {
			sql.append(" AND idconsegna = ? ");
		}
		
		if( idStallo != null ) {
			sql.append(" AND idstallo = ? ");
		}

		if( data != null ) {
			sql.append("AND data = ? ");
		}


		sql.append( " order by data, isrettifica, isscarico, idstallo" );

		Vector<Movimento> list = null ;

		final Connection conn = db.getConnection() ;
		try {

			int pos = 1 ;
			final PreparedStatement s = conn.prepareStatement(sql.toString()) ;

			if( idConsegna != null ) {
				s.setInt(pos++, idConsegna.intValue()) ;
			}
			
			if( idStallo != null ) {
				s.setInt(pos++, idStallo.intValue()) ;
			}

			if( data != null ) {
				s.setDate(pos++, new Date(data.getTime()) ) ;
			}

			list = (Vector<Movimento>) getByStatment(s);


		}
		finally {
			db.freeConnection(conn);
		}

		return list ;
	}

	public Vector<Movimento> getPartitario( final Consegna c ) throws Exception {

		final String sql = "SELECT id, idmerce, idconsegna, data, idstallo, cast(isscarico as binary ) as isscarico, isrettifica, secco, umido, numregistro, doctype, docnum, docdate, docpvtype, docpvnum, docpvdate, note, posdoganale, locked, 0 , 0 " +
				" FROM where deleted = 0 AND idconsegna = ? union all select * from registroiva where idconsegna = ? order by idstallo, data ";


		Vector<Movimento> list = null ;

		final Connection conn = db.getConnection() ;
		try {

			final PreparedStatement s = conn.prepareStatement(sql.toString()) ;

			s.setInt(1, c.getId().intValue()) ;
			s.setInt(2, c.getId().intValue()) ;

			list = getByStatment(s);

		}
		finally {
			db.freeConnection(conn);
		}

		return list ;
	}

	public ArrayList<Integer> getIdStalli(final Integer idConsegna ) throws Exception {

		final String sql = "SELECT distinct idstallo FROM " + getTable() +
				" where deleted = 0 AND idconsegna = ? and idstallo is not null  order by idstallo ";

		ArrayList<Integer> list = null ;

		final Connection conn = db.getConnection() ;
		try {

			final PreparedStatement s = conn.prepareStatement(sql.toString()) ;

			s.setInt(1, idConsegna.intValue()) ;
			ResultSet rs = null ;

			rs = s.executeQuery() ;

			list = new ArrayList<Integer>(rs.getFetchSize()) ;

			while( rs.next() ) {
				list.add( (Integer) rs.getObject(1) );
			}

		}
		finally {
			db.freeConnection(conn);
		}

		return list ;
	}

	public Vector<?> getGroup(  final Integer idConsegna , final FormattedDate d , final boolean scarico, final boolean rettifica) throws Exception {

		return getWithWhere(" deleted = 0 AND idconsegna = " + idConsegna.toString()
				+ " AND data = '" + d.ymdString() + "'"
				+ " AND isrettifica = '" + (rettifica ? "1":"0") + "'"
				+ " AND isscarico = '" + (scarico ? "1":"0") + "'"
				) ;
	}

	public Vector<?> getByData( final Integer idConsegna , final FormattedDate d ) throws Exception {
		return getWithWhere(" deleted = 0 AND idconsegna = " + idConsegna.toString() + " AND data = '" + d.ymdString() + "'" ) ;
	}

	public final static Integer ID_STALLO_CARICO_APERTURA = Integer.valueOf(0);
	public static final String NOTE_RETTIFICA_PESO = "Rettifica Peso";

	public Map<Integer, Movimento> getCarichiInizialiPerStallo(final Consegna consegna) throws Exception {

		/*
		 * carica i carichi attualmente presenti, quello senza stallo sarà quello che ancora devo distribuire
		   popolerà un array di stalli che hanno già i carichi, confronterò il totale con quello che mi dice quadrelli
		   se è inferiore lo attualizzo scontandolo dal movimento originario
		   se il movimento ha il num di registro non si tocca
		 */

		Map<Integer, Movimento> out = new HashMap<Integer, Movimento>(7);

		final Vector<Movimento> movimenti = getByConsegna(false, consegna.getId(), null, "isscarico asc,isrettifica desc", "");

		Integer idStallo = null ;
		for ( final Movimento m : movimenti ) {
			idStallo = m.getIdStallo();

			if ( ! m.getIsScarico() && ! m.getIsRettifica() ) {
				if ( idStallo == null ) {
					// è il carico di apertura ( non ha stallo e il suo umido è quanto resta da assegnare agli stalli )
					idStallo = ID_STALLO_CARICO_APERTURA;
				}
				out.put(idStallo, m);
			}
		}
		return out;
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
	public synchronized boolean checkIntegrity( final boolean doFix ) throws Exception {

		final Vector<Movimento> list = getRegistro( true , true , null,null,null,null,null,null,null,null,null );

		Movimento m ;
		int currNum = 0 ;

		Iterator<Movimento> i = list.iterator() ;
		while ( i.hasNext()  ) {
			m = i.next() ;

			if ( ! m.getIsLocked() ) {
				break ;
			}

			currNum = m.getNumRegistro().intValue() ;

		}
		//		throw new Exception();
		if( currNum == 0 ) {
			i = list.iterator() ;
		}

		final Connection conn = db.getConnection() ;

		boolean ret = false ;

		try {
			while ( i.hasNext() ) {
				m = i.next() ;

				currNum ++;

				if ( doFix ) {
					final Integer num = Integer.valueOf( currNum ) ;

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
		catch ( final Exception e ) {
			throw new UserException(e) ;
		}
		finally {
			db.freeConnection(conn);
		}
		return ret ;
	}

	private void updateNumRegistro(final Movimento m, final Integer newNum , final Connection cn ) throws Exception  {

		final StringBuilder sql = new StringBuilder()
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

		final PreparedStatement p = cn.prepareStatement(sql.toString()) ;
		p.setLong(1, m.getId() ) ;

		p.setInt(2, newNum ) ;
		p.executeUpdate();
	}

	public abstract Movimento getMovimentoGiacenza(Stallo s, Consegna consegna) throws Exception;

	public boolean isIva() {
		return false;
	}
	
	public Integer getIdConsegnaInStalloAllaData(Integer idStallo, FormattedDate data) throws Exception {

		Integer idC = null;
		
		final String sql = 
				" SELECT distinct c.idconsegna FROM " + getTable() + " r inner join " + ConsegnaAdapter.getStaticTable() + " c using(idconsegna) \n " +
				" WHERE r.data < '" + data.ymdString() + " 23:59:59' and ( datachiusura > '" + data.ymdString() + "' or datachiusura is null )  \n "
						+ " AND r.idStallo = " + idStallo + " order by r.data desc, r.id desc " ;

		final Connection conn = db.getConnection() ;
		try {

			Vector<?> x = executeScalarQuery(sql);
			
			if ( ! x.isEmpty() ) {
				idC = (Integer) x.firstElement();
			}
		}
		finally {
			db.freeConnection(conn);
		}

		return idC ;
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