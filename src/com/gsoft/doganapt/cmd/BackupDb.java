package com.gsoft.doganapt.cmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gtsoft.utils.common.ConfigManager;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;

public class BackupDb extends VelocityCommand {

	Context ctx = null;

	public BackupDb ( GtServlet callerServlet) {
		super(callerServlet);
	}

	@Override
	public VelocityCommand clone() {
		return  new BackupDb(callerServlet);
	}

	@Override
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {

		this.ctx = ctx;

		doBackup();

		ctx.put(ContextKeys.RESULT, Boolean.TRUE.toString() );

		return null;
	}

	private void doBackup() {

		//		String dir = ConfigManager.getProperty("backups.path") ;
		//		String baseName = ConfigManager.getProperty("backups.baseName") ;


		String dir = ConfigManager.getProperty("backups.path") ;

		String mysqlCmd = ConfigManager.getProperty("backups.create_cmd");

		Login.logAction("Creating backup into: " + dir , request);

		try {
			Process p = Runtime.getRuntime().exec( mysqlCmd );

			/* BufferedReader stdError = new BufferedReader(new
					InputStreamReader(p.getErrorStream())); */

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
		catch(IOException e1) {

			ctx.put( "outError", "Error : " + e1.getMessage() );

			return ;
		}
		catch(InterruptedException e2) {}

		Homepage.purgeCaches();

		try {
			response.sendRedirect(".main?cmd=recover");
			response.flushBuffer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	@Override
	public String getTemplateName() {
		return "recoverdb.vm" ;
	}


}
