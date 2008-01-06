package com.gsoft.pt_movimentazioni.data;

import java.io.IOException;

import com.gsoft.doganapt.data.adapters.MerceAdapter;
import com.gtsoft.utils.data.Field;
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

	private static final String[] fieldNames = {
		null,"descrizione","codice",null,null
	};
	
	public String getTableFieldName(Field f) {
		return fieldNames[f.getId()] ;
	}
	
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