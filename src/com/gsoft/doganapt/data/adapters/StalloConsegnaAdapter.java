package com.gsoft.doganapt.data.adapters;

import com.gsoft.doganapt.data.Consegna;
import com.gsoft.doganapt.data.Movimento;
import com.gsoft.doganapt.data.Stallo;
import com.gsoft.doganapt.data.StalloConsegna;
import com.gtsoft.utils.common.BeanAdapter2;
import com.gtsoft.utils.common.FormattedDate;
import com.gtsoft.utils.data.Field;
import com.gtsoft.utils.data.FieldSet;
import com.gtsoft.utils.sql.IDatabase2;

public class StalloConsegnaAdapter extends BeanAdapter2 {

	public StalloConsegnaAdapter() {
		super();
	}
	public StalloConsegnaAdapter( IDatabase2 db ) {
		super( db );
	}

	@Override
	public void fillFields(Object obj) {
		boolean fill = ( obj != null ) ;

		StalloConsegna o = (StalloConsegna) obj ;

		if ( fields == null || fields.isDirty() ) {
			fields = new FieldSet(Fields.FIELDSCOUNT);
		}

		fields.add( Fields.ID, Field.Type.INTEGER , (fill)? o.getId() : null , true );

		fields.add( Fields.IDCONSEGNA, Field.Type.INTEGER , (fill)? o.getIdConsegna() : null );
		fields.add( Fields.IDSTALLO, Field.Type.INTEGER , (fill)? o.getIdStallo() : null );
		fields.add( Fields.ISINLIBERAPRATICA, Field.Type.INTEGER , (fill)? o.getIsInLiberaPratica() : null );
		fields.add( Fields.VALOREEURO, Field.Type.DOUBLE , (fill)? o.getValoreEuro() : null );
		fields.add( Fields.VALOREDOLLARI, Field.Type.DOUBLE , (fill)? o.getValoreDollari() : null );
		fields.add( Fields.VALOREUNITARIOEURO, Field.Type.DOUBLE , (fill)? o.getValoreUnitarioEuro() : null );
		fields.add( Fields.VALOREUNITARIODOLLARI, Field.Type.DOUBLE , (fill)? o.getValoreUnitarioDollari() : null );
		fields.add( Fields.TASSOEURODOLLARO, Field.Type.DOUBLE , (fill)? o.getTassoEuroDollaro() : null );

		fields.add( Fields.DATAIMMISSIONELP, Field.Type.DATE , (fill)? o.getDataImmissione() : null );

	}

	public Object getFromFields ( ) {
		return getFromFields ( new StalloConsegna() ) ;
	}
	public Object getFromFields ( Object obj ) {

		if ( obj == null )
			return null ;

		StalloConsegna o = (StalloConsegna) obj ;

		o.setId( (Integer) fields.get( Fields.ID).getValue());
		o.setIdConsegna( (Integer) fields.get( Fields.IDCONSEGNA).getValue() );
		o.setIdStallo( (Integer) fields.get( Fields.IDSTALLO).getValue() );
		o.setIsInLiberaPratica( (Boolean) fields.get( Fields.ISINLIBERAPRATICA).getValue() );
		o.setValoreEuro( (Double) fields.get( Fields.VALOREEURO).getValue() );
		o.setValoreDollari( (Double) fields.get( Fields.VALOREDOLLARI).getValue() );
		o.setValoreUnitarioEuro( (Double) fields.get( Fields.VALOREUNITARIOEURO).getValue() );
		o.setValoreUnitarioDollari( (Double) fields.get( Fields.VALOREUNITARIODOLLARI).getValue() );
		o.setTassoEuroDollaro( (Long) fields.get( Fields.TASSOEURODOLLARO).getValue() );
		o.setDataImmissione( (FormattedDate) fields.get( Fields.DATAIMMISSIONELP).getValue() );

		return o ;
	}

	public static interface Fields {
		static final int ID = 0;
		static final int IDCONSEGNA = 1 ;
		static final int IDSTALLO = 2;
		static final int ISINLIBERAPRATICA = 3;
		static final int VALOREEURO = 4 ;
		static final int VALOREDOLLARI = 5 ;
		static final int VALOREUNITARIOEURO = 6;
		static final int VALOREUNITARIODOLLARI = 7;
		static final int TASSOEURODOLLARO  = 8;
		static final int DATAIMMISSIONELP  = 9;

		static final int FIELDSCOUNT = 10;
	}

	private static final String TABLE = "stalliconsegna" ;
	private static final String[] fieldNames = {
		"id",
		"idConsegna",
		"idStallo",
		"liberaPratica",
		"valoreEuro" ,
		"valoreUsd",
		"valoreUnitarioEuro",
		"valoreUnitarioUSD",
		"tassoEURUSD",
		"dataImmissioneLP"
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

	public StalloConsegna assegnaStallo(Consegna c, Stallo s) {

		StalloConsegna sc = new StalloConsegna();

		sc.setIdConsegna(c.getId());
		sc.setIdStallo(s.getId());

		sc.setTassoEuroDollaro(c.getTassoCambio());

		if ( c.getIsValutaEuro() ) {
			sc.setValoreEuro(c.getValoreUnitario());
			sc.setValoreDollari(c.getValoreUnitario() / c.getTassoCambio() );
		} else {
			sc.setValoreDollari(c.getValoreUnitario());
			sc.setValoreEuro(c.getValoreUnitario() * c.getTassoCambio() );
		}

		sc.setIsInLiberaPratica(Boolean.FALSE);

		return sc ;
	}

	public StalloConsegna getByStallo(Stallo s) throws Exception {
		StalloConsegna stalloConsegna = new StalloConsegna();
		stalloConsegna.setIdConsegna(s.getIdConsegnaAttuale());
		stalloConsegna.setIdStallo(s.getId());

		return (StalloConsegna) getByKeys(stalloConsegna);

	}

	public StalloConsegna getByMovimento(Movimento m) throws Exception {
		StalloConsegna stalloConsegna = new StalloConsegna();
		stalloConsegna.setIdConsegna(m.getIdConsegna());
		stalloConsegna.setIdStallo(m.getIdStallo());

		return (StalloConsegna) getByKeys(stalloConsegna);

	}

}