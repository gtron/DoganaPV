package com.gtsoft.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.gtsoft.utils.sql.IDatabase;
import com.gtsoft.utils.sql.IDatabase2;
import com.javaexchange.dbConnectionBroker.DbConnectionBroker;


public class AccessDb2024 extends IDatabase2 {

	private String connectionLogFile = "DbConnectionBroker_AccessDb.log" ;
		
	private String filename = "/usr/local/tomcat/webapps/ROOT/stor_db03.accdb" ;
	private String user = null;
	private String pwd = null;

	public AccessDb2024(String f ) {
	    filename = f ;
	}
	public AccessDb2024(String f , String u , String p ) {
	    filename = f ;
//	    user = u;
//	    pwd = p ;
	}
	
	public int getDbType() {
	    return IDatabase.DBType.ACCESS ;
	}
	
	public DbConnectionBroker getBroker() throws IOException {
		
		return new DbConnectionBroker("net.ucanaccess.jdbc.UcanaccessDriver",
				"jdbc:ucanaccess://" + filename + ";memory=true",
				null, null ,1,1,
				connectionLogFile,1.0);

	}

	public String getIdentitySql() {
		return "";
	}
}