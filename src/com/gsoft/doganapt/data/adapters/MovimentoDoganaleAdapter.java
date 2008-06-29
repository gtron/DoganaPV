package com.gsoft.doganapt.data.adapters;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.gsoft.doganapt.data.Movimento;
import com.gsoft.doganapt.data.MovimentoDoganale;
import com.gtsoft.utils.sql.IDatabase2;

public class MovimentoDoganaleAdapter extends MovimentoAdapter {

	public static Boolean busy = Boolean.FALSE ;
	
	public MovimentoDoganaleAdapter() {
		super();
	}
	public MovimentoDoganaleAdapter( IDatabase2 db ) {
		super( db );
	}

	public Object getFromFields ( ) {
		return getFromFields ( new MovimentoDoganale() ) ;
	}
	private static final String TABLE = "registrodoganale" ;
	
	public static String getStaticTable() {
		return TABLE ;
	}
	public String getTable() {
		return TABLE ;
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

		busy = Boolean.TRUE ;
		
		StringBuilder sql = new StringBuilder(70)
			.append("SELECT max(numregistro) FROM ")
			.append(getTable())
			.append(" WHERE deleted = 0 ")
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
		return new MovimentoDoganale();
	}

}