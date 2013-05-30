package com.gsoft.doganapt.data.adapters;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gsoft.doganapt.data.Consegna;
import com.gsoft.doganapt.data.Movimento;
import com.gsoft.doganapt.data.MovimentoIVA;
import com.gsoft.doganapt.data.Stallo;
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

	@Override
	public Object getFromFields ( ) {
		return getFromFields ( new MovimentoIVA() ) ;
	}
	private static final String TABLE = "registroiva" ;

	public static String getStaticTable() {
		return TABLE ;
	}
	@Override
	public String getTable() {
		return TABLE ;
	}

	@Override
	public boolean isIva() {
		return true;
	}

	@Override
	public void fillFields(Object obj) {
		boolean fill = ( obj != null ) ;

		super.fillFields(obj);

		MovimentoIVA o = (MovimentoIVA) obj ;

		fields.add( Fields.VALORE_DOLLARI, Field.Type.DOUBLE , (fill)? o.getValoreDollari() : null );
		fields.add( Fields.VALORE_EURO, Field.Type.DOUBLE , (fill)? o.getValoreEuro() : null );
		fields.add( Fields.POSIZIONE_DOGANALE, Field.Type.STRING , (fill)? o.getCodPosizioneDoganale() : null );

		fields.add( Fields.VALORE_NETTO, Field.Type.DOUBLE , (fill)? o.getValoreNetto() : null );
		fields.add( Fields.VALORE_TESTP, Field.Type.DOUBLE , (fill)? o.getValoreTestp() : null );
		fields.add( Fields.VALORE_IVA, Field.Type.DOUBLE , (fill)? o.getValoreIva() : null );
	}

	@Override
	public Object getFromFields ( Object obj ) {

		MovimentoIVA o = (MovimentoIVA) super.getFromFields(obj) ;

		o.setValoreDollari( (Double) fields.get( Fields.VALORE_DOLLARI).getValue() );
		o.setValoreEuro( (Double) fields.get( Fields.VALORE_EURO).getValue() );
		o.setCodPosizioneDoganale( (String) fields.get( Fields.POSIZIONE_DOGANALE).getValue() );

		o.setValoreNetto( (Double) fields.get( Fields.VALORE_NETTO).getValue() );
		o.setValoreTestp( (Double) fields.get( Fields.VALORE_TESTP).getValue() );
		o.setValoreIva( (Double) fields.get( Fields.VALORE_IVA).getValue() );

		return o ;
	}

	public static interface Fields {

		static final int FIELDSHIFT = MovimentoAdapter.Fields.FIELDSCOUNT - 1;

		static final int VALORE_EURO = FIELDSHIFT + 1 ;
		static final int VALORE_DOLLARI = FIELDSHIFT + 2;
		static final int POSIZIONE_DOGANALE = FIELDSHIFT + 3;

		static final int VALORE_NETTO = FIELDSHIFT + 4;
		static final int VALORE_TESTP = FIELDSHIFT + 5;
		static final int VALORE_IVA = FIELDSHIFT + 6;

		static final int FIELDSCOUNT = FIELDSHIFT + 7;
	}

	private static final String[] fieldNames = {
		"valoreeuro","valoredollari", "posdoganale" , "valorenetto", "valoretestp", "valoreiva"
	};

	@Override
	public int getFieldsCount() {
		return Fields.FIELDSCOUNT ;
	}

	@Override
	public String getHttpFieldName( int f ) {
		int field = f - Fields.FIELDSHIFT  - 1;
		if ( field >= 0 && field < Fields.FIELDSCOUNT )
			return fieldNames[field] ;

		return super.getHttpFieldName(f);
	}
	@Override
	public String getTableFieldName(Field f) {
		int field = f.getId() - Fields.FIELDSHIFT - 1 ;
		if ( field >= 0 && field < Fields.FIELDSCOUNT )
			return fieldNames[field] ;

		return super.getTableFieldName(f);
	}

	@Override
	protected String getFieldsRegistro(String prefix) {
		String list = "%id, %idmerce, %idconsegna, %data, %idstallo, %isscarico, %isrettifica, sum(%secco) as secco, sum(%umido) as umido, %numregistro, %doctype, %docnum, %docdate, %docpvtype, %docpvnum, %docpvdate, %note, %posdoganale, %locked," +
				" sum(%valoreeuro) as valoreeuro, sum(%valoredollari) as valoredollari, sum(%valorenetto) as valorenetto, sum(%valoretestp) as valoretestp, sum(%valoreiva) as valoreiva ";

		if ( prefix != null ) {
			list = list.replaceAll("%", prefix) ;
		} else {
			list = list.replaceAll("%", "") ;
		}

		return list ;
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
		Thread.sleep(1500);
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
				if ( str != null ) {
					d = new Integer( Integer.parseInt(str) + 1 );
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
		MovimentoIVA m = new MovimentoIVA();

		m.setIsLocked(Boolean.FALSE);
		m.setIsScarico(Boolean.FALSE);

		Double zero = Double.valueOf(0);

		m.setUmido(zero);
		m.setSecco(zero);

		m.setValoreEuro(zero);
		m.setValoreDollari(zero);
		m.setValoreTestp(zero);
		m.setValoreNetto(zero);
		m.setValoreIva(zero);

		return m;
	}

	@Override
	public Object create(Object o) throws IOException , SQLException{
		MovimentoIVA m = (MovimentoIVA) o;

		if ( m != null ) {
			Consegna c = m.getConsegna();
			if ( c != null ) {
				if (m.getValoreEuro() == null ||
						m.getValoreEuro().intValue() == 0 && m.getSecco().intValue() != 0 ) {
					c.updateValore(m);
				}

				/* Sostituito ...
				 * ora lo specificano manualmente
				 * Eventualmente si puo' usare il campo per le comunitarie
				 * ma in questo caso va riabiliato il campo nell'editing della consegna
				 * 
				if ( m.getPosizioneDoganale() == null ) {
					m.setPosizioneDoganale(c.getPosizione());
				}
				 */
			}
		}


		Integer id = Integer.valueOf(((Number) super.create(m)).intValue());

		if ( m != null  ) {
			m.setId(id);
		}

		return id;

	}

	@Override
	public MovimentoIVA getMovimentoGiacenza(Stallo s, Consegna c) throws Exception {
		return s.getMovimentoGiacenzaIva(this, c);
	}

}