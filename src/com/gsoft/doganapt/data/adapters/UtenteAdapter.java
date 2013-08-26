package com.gsoft.doganapt.data.adapters;

/*
 * CREATE TABLE  `magazzini`.`users` (
  `id` mediumint(9) NOT NULL AUTO_INCREMENT,
  `username` varchar(120) NOT NULL,
  `password` varchar(120) NOT NULL,
  `email` varchar(120) DEFAULT NULL,
  `active` tinyint(4) DEFAULT NULL,
  `nome` varchar(250) DEFAULT NULL,
  `cognome` varchar(250) DEFAULT NULL,
  `datanascita` datetime DEFAULT NULL,
  `cdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `livello` mediumint(9) DEFAULT NULL,
  `ts` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1
 */
import java.util.Vector;

import com.gsoft.doganapt.data.Utente;
import com.gtsoft.utils.common.BeanAdapter2;
import com.gtsoft.utils.common.FormattedDate;
import com.gtsoft.utils.data.Field;
import com.gtsoft.utils.data.FieldSet;
import com.gtsoft.utils.sql.IDatabase2;

public class UtenteAdapter extends BeanAdapter2 {

	private static String SALT = "P0rtov3SM!";
	private static final String TABLE = "users" ;

	public UtenteAdapter( IDatabase2 db ) {
		super( db );
	}

	public UtenteAdapter() {
		super();
	}

	public Object getFromFields ( Object obj ) {

		Utente o = (Utente) obj ;

		if ( obj == null )
			return null ;

		o.setId( (Integer) fields.get( Fields.ID ).getValue() ) ;
		o.setNome( (String) fields.get( Fields.NOME ).getValue() );
		o.setCognome( (String) fields.get( Fields.COGNOME).getValue() );
		o.setDataNascita( (FormattedDate) fields.get( Fields.DATANASCITA ).getValue() );

		o.setLevel( (Integer) fields.get( Fields.LIVELLO ).getValue() ) ;
		o.setUsername( (String) fields.get( Fields.USERNAME ).getValue() );
		o.setEmail( (String) fields.get( Fields.EMAIL).getValue() );
		o.setCDate( (FormattedDate) fields.get( Fields.CDATE ).getValue() );
		o.setActive( (Integer) fields.get( Fields.ACTIVE ).getValue() );

		return o ;
	}

	public Object getFromFields( ) {
		return getFromFields ( new Utente() ) ;
	}


	@Override
	public void fillFields(Object obj) {
		boolean fill = ( obj != null ) ;

		Utente o = (Utente) obj ;

		if ( fields == null || fields.isDirty() ) {
			fields = new FieldSet(getFieldsCount());
		}

		//			fields.add( Fields.ID, Field.Type.INTEGER , (fill)? o.getId() : null , true );

		fields.add( Fields.ID, Field.Type.INTEGER , (fill)? o.getId() : null, true );
		fields.get(Fields.ID).setMandatory(true);
		fields.add( Fields.NOME, Field.Type.STRING , (fill)? o.getNome() : null );
		fields.add( Fields.COGNOME, Field.Type.STRING , (fill)? o.getCognome() : null  );
		fields.add( Fields.DATANASCITA, Field.Type.DATE , (fill)? o.getDataNascita() : null  );

		fields.add( Fields.LIVELLO, Field.Type.INTEGER , (fill)? o.getLevel() : null );
		fields.add( Fields.USERNAME, Field.Type.STRING , (fill)? o.getUsername() : null  );
		fields.add( Fields.EMAIL, Field.Type.STRING , (fill)? o.getEmail() : null  );
		fields.add( Fields.CDATE, Field.Type.DATE , (fill)? o.getCDate() : null  );
		fields.add( Fields.ACTIVE, Field.Type.INTEGER , (fill)?
				( o.isActive() ? new Integer(1) : new Integer(0) ) : null  );

	}
	public static Utente getUtente(String Utentename, String pwd) {

		if ( Utentename == null || pwd == null )
			return null;

		Utentename = Utentename.trim();
		pwd = pwd.trim();

		if ( Utentename.length() < 2 || pwd.length() < 2 )
			return null;

		try {


			UtenteAdapter adp = Utente.newAdapter();
			String UtenteFieldName = adp.getTableFieldName( adp.getField(Fields.USERNAME) );

			Vector v = adp.getWithWhere( UtenteFieldName +  " = '" + Utentename +
					"' AND password = md5('" + SALT + pwd + "') ");

			if ( v != null && ! v.isEmpty() )
				return (Utente) v.firstElement();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public static interface Fields {

		static final int ID = 0;
		static final int NOME = 1 ;
		static final int COGNOME = 2;
		static final int DATANASCITA = 3;

		static final int USERNAME = 4 ;
		static final int EMAIL = 5 ;
		static final int CDATE = 6;
		static final int ACTIVE = 7;

		static final int LIVELLO = 8;

		static final int FIELDSCOUNT = 9 ;

	}
	private static final String[] fieldNames = {
		"id", "nome", "cognome", "datanascita" , "username", "email", "cdate", "active", "livello"
	};

	@Override
	public String getTable() {
		return TABLE ;
	}

	@Override
	public int getFieldsCount() {
		return Fields.FIELDSCOUNT;
	}

	public String getTableFieldName(Field f) {
		return fieldNames[f.getId()] ;
	}

	public String getHttpFieldName(int f) {
		return fieldNames[f] ;
	}
}