package com.gsoft.doganapt.cmd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.data.PuntoRipristinoDb;
import com.gtsoft.utils.common.ConfigManager;
import com.gtsoft.utils.common.FormattedDate;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.exception.ParameterException;
import com.gtsoft.utils.http.servlet.GtServlet;

public class RecoverDb extends VelocityCommand {

	Context ctx = null;

	public RecoverDb ( GtServlet callerServlet) {
		super(callerServlet);
	}

	@Override
	public VelocityCommand clone() {
		return  new RecoverDb(callerServlet);
	}

	@Override
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {

		this.ctx = ctx;

		if ( getBooleanParam(Strings.EXEC) ) {
			doRecover();
		}
		else {
			showRecover();
		}

		ctx.put(ContextKeys.RESULT, Boolean.TRUE.toString() );

		return null;
	}

	protected void showRecover() {

		Vector<PuntoRipristinoDb> list = getRecoverableSnapshots();

		ctx.put(ContextKeys.LIST, list );


	}

	private Vector<PuntoRipristinoDb> getRecoverableSnapshots() {

		HashMap<String, PuntoRipristinoDb> list = new  HashMap<String,PuntoRipristinoDb>() ;

		String dir = ConfigManager.getProperty("backups.path") ;

		File f =  new File ( dir ) ;

		FormattedDate backupDay = null ;

		ctx.put("dir", dir);

		Vector<PuntoRipristinoDb> out = null ;

		if ( f.isDirectory() ) {
			File[] backupFiles = f.listFiles(new FileFilter() {
				public boolean accept(File f) {
					return f.isFile() ; }
			});



			File curr = null ;
			String filename , fn ;
			PuntoRipristinoDb backup;
			String key ;
			List<String> keys = new Vector<String>(backupFiles.length) ;

			for (File backupFile : backupFiles) {

				curr = backupFile ;
				filename = curr.getName();
				fn = filename.split("\\.")[0];

				try {
					backupDay = null ;
					String[] nameParts = fn.split("_");
					if ( nameParts.length == 3 ) {
						backupDay = new FormattedDate( nameParts[1] + nameParts[2] , "yyyyMMddHHmm" ) ;
					}
					else if ( nameParts.length == 2 && nameParts[1].startsWith("2")) {
						backupDay = new FormattedDate( nameParts[1] , "yyyyMMdd" ) ;
					}
				}
				catch (Exception e) {
				}

				if ( backupDay == null ){
					backupDay = new FormattedDate( new Date(curr.lastModified())) ;
				}

				key = fn ;


				if ( list.containsKey(key) ) {
					backup = list.get(key) ;
				}
				else {
					backup = new PuntoRipristinoDb();

					keys.add(key);
					backup.setData( backupDay );
					list.put( key, backup ) ;
				}

				if ( filename.endsWith(".sql")) {
					backup.setNomeFile(filename);

					if ( backup.getDescrizione() == null ) {
						backup.setDescrizione("Backup automatico" );
					}

				}
				else if ( filename.endsWith(".txt")) {

					String comment = " - " ;
					String line = null ;
					if ( curr.length() > 0 ) {
						try {
							BufferedReader fr = new BufferedReader( new FileReader ( curr ) ) ;
							while ( ((line = fr.readLine()) != null) ) {
								comment += line;
							}
						}
						catch ( Exception e ) { comment = " N/A " ; }
					}
					else {
						comment = filename.substring( filename.indexOf('_',20) +1  , filename.lastIndexOf('.')) ;
						comment = comment.replaceAll("_", " ");
					}
					backup.setDescrizione(comment);
				}

			}

			Collections.sort( keys );
			Collections.reverse( keys );

			out = new Vector<PuntoRipristinoDb>(keys.size());

			for ( String k : keys ) {
				out.add( list.get(k) );
			}
		}

		return out;
	}

	protected void doRecover() throws ParameterException {
		String dir = ConfigManager.getProperty("backups.path") ;
		String file = getParam("toRecover", true);

		String mysqlCmd = String.format(
				ConfigManager.getProperty("backups.mysql_cmd"),
				ConfigManager.getProperty("mysql.user"),
				ConfigManager.getProperty("mysql.pwd"),
				ConfigManager.getProperty("mysql.host"),
				ConfigManager.getProperty("mysql.dbname"),
				dir ,
				file
				);

		File f = new File ( dir + File.separator + file ) ;

		if ( f.exists() ) {

			String s ;

			Login.logAction("Recovering DB from backup: " + f.getAbsolutePath() , request);
			System.out.println(mysqlCmd);

			try {
				Process p = Runtime.getRuntime().exec( mysqlCmd );

				BufferedReader stdError = new BufferedReader(new
						InputStreamReader(p.getErrorStream()));

				String out = "" ;
				String outError = "" ;

				p.waitFor();
				BufferedReader reader=new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line=reader.readLine();
				while(line!=null)
				{
					out += line ;
					line=reader.readLine();
				}

				ctx.put( "out", out);

				BufferedReader err=new BufferedReader(new InputStreamReader(p.getErrorStream()));
				line=err.readLine();
				while(line!=null)
				{
					outError += line ;
					line=reader.readLine();
				}
				ctx.put( "outError", outError);


			}
			catch(IOException e1) {}
			catch(InterruptedException e2) {}


			Homepage.purgeCaches();

		}
		else {
			ctx.put(ContextKeys.ERROR, "Il file " + f + " non esiste!");
		}

	}

	@Override
	public String getTemplateName() {
		return "recoverdb.vm" ;
	}


}
