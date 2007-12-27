package com.gsoft.doganapt.data;

import com.gsoft.doganapt.data.adapters.MovimentoAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoDoganaleAdapter;
import com.gtsoft.utils.sql.IDatabase2;

public class MovimentoDoganale extends Movimento {
	
	public static synchronized MovimentoAdapter newAdapter() throws Exception {		
		return new MovimentoDoganaleAdapter() ;
	}
	public static synchronized MovimentoAdapter newAdapter(IDatabase2 db) throws Exception {		
		return new MovimentoDoganaleAdapter(db) ;
	}

}
