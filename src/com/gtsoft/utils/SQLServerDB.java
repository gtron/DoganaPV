package com.gtsoft.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

import com.gtsoft.utils.sql.IDatabase;
import com.gtsoft.utils.sql.IDatabase2;
import com.javaexchange.dbConnectionBroker.DbConnectionBroker;

/**
 * @author Gtron
 */
public class SQLServerDB extends IDatabase2 {

	private String connectionLogFile = "DbConnectionBroker_SQLServerDB.log";

	private String server = "sql";
	private String dbname = "Movimentazioni";
	private String port = "1433";
	private String user = "sa";
	private String pwd = "Password123";

	public SQLServerDB(String f) {
		server = f;
	}

	public SQLServerDB(String s, String db, String port, String u, String p) {
		server = s;
		dbname = db;
		this.port = port;
		user = u;
		pwd = p;
	}
	public SQLServerDB() {
	}

	public int getDbType() {
		return IDatabase.DBType.ACCESS;
	}

	public DbConnectionBroker getBroker() throws IOException {
		
		String connectionUrl = 
				"jdbc:sqlserver://"+ server + ":"+ port + 
				";DatabaseName=" + dbname +
				";user=sa;password="+ pwd +";encrypt=false;trust_server_certificate=true;";
		
		System.out.println("GETTING DB SQLServerDB to:" + connectionUrl);
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			
			Connection con = DriverManager.getConnection(connectionUrl);
			
			System.out.println("connected! " + con.getSchema());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new DbConnectionBroker(
				"com.microsoft.sqlserver.jdbc.SQLServerDriver",
				connectionUrl, 
				user, pwd, 1, 1,
				connectionLogFile, 1.0);
	}

	public String getIdentitySql() {
		return "";
	}
}

