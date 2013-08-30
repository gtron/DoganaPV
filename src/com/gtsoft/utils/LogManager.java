/*
 * FileManager.java
 *
 * Created on 10. duben 2002, 12:41
 */

package com.gtsoft.utils;

import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

import com.gtsoft.utils.common.ConfigManager;
import com.gtsoft.utils.common.FormattedDate;

/**
 *
 * @author  Gianluca Mereu
 * @version
 */
public class LogManager {

	public static String logFile = null ;

	public static void initLog(ServletConfig config) {
		return;
	}

	void bad(ServletConfig config) {
		try {
			logFile =  ConfigManager.getProperty("logging.filename") ;

			System.out.println("Log4JInitServlet is initializing log4j");
			String log4jLocation = config.getInitParameter("Log4jConfig");

			if ( log4jLocation == null ) {
				log4jLocation = "WEB-INF/conf/log4j.properties";
			}
			ServletContext sc = config.getServletContext();

			if (log4jLocation == null) {
				System.err.println("*** No log4j-properties-location init param, so initializing log4j with BasicConfigurator");
				BasicConfigurator.configure();
			} else {
				String webAppPath = sc.getRealPath("/");
				String log4jProp = webAppPath + log4jLocation;
				File f = new File(log4jProp);
				if (f.exists()) {
					System.out.println("Initializing log4j with: " + log4jProp);
					PropertyConfigurator.configure(log4jProp);
				} else {
					System.err.println("*** " + log4jProp + " file not found, so initializing log4j with BasicConfigurator");
					BasicConfigurator.configure();
				}
			}

		} catch (Exception e) {
			logFile = null;
		}


		if ( logFile == null || logFile.length() < 1 ) {
			logFile = ConfigManager.getWebappRoot() + "WEB-INF" + File.separator + "log" + File.separator + "app.log";
		}

		try {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			if ( stackTraceElements != null && stackTraceElements.length > 2 ) {
				System.out.println( new FormattedDate().fullString() + stackTraceElements[2].getClassName() );
			}

		} catch (Exception ex) {
			System.out.println( new FormattedDate().fullString() + " --- Eccezione al settare il file di log : " + ex.getMessage());
			return;
		}
	}
}
