package com.gsoft.pt_movimentazioni.data;

import java.io.IOException;

import com.gsoft.doganapt.data.Merce;
import com.gsoft.doganapt.data.adapters.MerceAdapter;
import com.gtsoft.utils.sql.IDatabase2;

public class MerceQuadrelliAdapter extends MerceAdapter {

	public MerceQuadrelliAdapter() {
		super();
	}
	public MerceQuadrelliAdapter( IDatabase2 db ) {
		super( db );
	}
	
	public String getTable() {
		return "tab_merci" ;
	}
	
	public Object getFromFields ( Object obj ) {

		if ( obj == null ) {
			return null ;
		}
		
		Merce o = (Merce) obj ;
		
		o.setId( null );
		o.setNome( (String) fields.get( 1 ).getValue() );
		o.setCodiceQuadrelli( (String) fields.get(  0 ).getValue() );
		
		return o ;
	}
	
	private static final String[] fieldNames = {
		"codice","descrizione"
	};
	
	@Override
	public Object create(Object o) throws IOException {
		throw new IOException("Attenzione: creazione non implementata");
	}
	@Override
	public void update(Object o) throws IOException {
		throw new IOException("Attenzione: update non implementata");
	}

	
	public void end() throws Exception {
		db.end();
	}
	
}