package com.gsoft.doganapt.cmd;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

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

		HttpSession s =  req.getSession();

		String user = getParam("user", false);
		String pwd  = getParam("pwd", false);
		if ( "user".equals(user)) {

			if ( pwd != null && pwd.equals("d0gan4") ) {
				s.setAttribute("logged", Boolean.TRUE) ;
				s.setAttribute("admin", Boolean.FALSE) ;
				resp.sendRedirect(".main?cmd=home");
			}
			else {
				ctx.put("msg", "Password errata!") ;
			}
		}
		else if ( "admin".equals(user)) {

			if ( pwd != null && pwd.equals("s1lente") ) {
				s.setAttribute("logged", Boolean.TRUE) ;
				s.setAttribute("admin", Boolean.TRUE) ;
				resp.sendRedirect(".main?cmd=home");
			}
			else {
				ctx.put("msg", "Password errata!") ;
			}
		}
		else {
			s.setAttribute("logged", Boolean.FALSE) ;
		}

		ctx.put("user", user) ;

		Homepage.purgeCaches();

		return null;
	}

	@Override
	public String getTemplateName() {
		return T ;
	}

}
