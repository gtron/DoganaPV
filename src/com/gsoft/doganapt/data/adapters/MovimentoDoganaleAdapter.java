package com.gsoft.doganapt.data.adapters;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.gsoft.doganapt.data.Consegna;
import com.gsoft.doganapt.data.Movimento;
import com.gsoft.doganapt.data.MovimentoDoganale;
import com.gsoft.doganapt.data.Stallo;
import com.gtsoft.utils.sql.IDatabase2;

public class MovimentoDoganaleAdapter extends MovimentoAdapter {

	public static Boolean busy = Boolean.FALSE ;

	public MovimentoDoganaleAdapter() {
		super();
	}
	public MovimentoDoganaleAdapter( IDatabase2 db ) {
		super( db );
	}

	@Override
	public Object getFromFields ( ) {
		return getFromFields ( new MovimentoDoganale() ) ;
	}
	private static final String TABLE = "registrodoganale" ;

	public static final String NOTE_CARICO = "Intr. in dep.Dog.";
	public static final String NOTE_SCARICO = "Estr. da dep.Dog.";

	public static String getStaticTable() {
		return TABLE ;
	}
	@Override
	public String getTable() {
		return TABLE ;
	}

	@Override
	public synchronized void doneNextNumRegistro() throws Exception {
		busy = Boolean.FALSE ;
	}
	@Override
	public synchronized Integer getNextNumRegistro() throws Exception {

		int c = 0 ;
		while ( busy.booleanValue() ) {
			Thread.sleep(1000);

			if ( c++  > 10 )
				throw new Exception("Errore nella gestione della concorrenza sul registro") ;
		}

		busy = Boolean.TRUE ;

		StringBuilder sql = new StringBuilder(70)
		.append("SELECT max(numregistro) FROM ")
		.append(getTable())
		.append(" WHERE deleted = 0 ")
		;

		Integer d = Integer.valueOf( 1 ) ;
		Connection conn = db.getConnection() ;
		try {
			PreparedStatement s = conn.prepareStatement(sql.toString()) ;

			ResultSet rs = s.executeQuery() ;

			if ( rs != null && rs.next() ) {
				String str = rs.getString(1);
				if ( str != null ) {
					d = Integer.valueOf( Integer.parseInt(str) + 1 );
				}
			}
		}
		finally {
			db.freeConnection(conn);
		}

		return d;
	}

	@Override
	public Movimento newMovimento() {
		MovimentoDoganale m = new MovimentoDoganale();

		m.setUmido(Double.valueOf(0));
		m.setSecco(Double.valueOf(0));
		m.setIsLocked(Boolean.FALSE);
		m.setIsScarico(Boolean.FALSE);

		return m;
	}

	@Override
	public MovimentoDoganale getMovimentoGiacenza(Stallo s, Consegna c) throws Exception {
		return s.getMovimentoGiacenzaDoganale(this, c);
	}

}