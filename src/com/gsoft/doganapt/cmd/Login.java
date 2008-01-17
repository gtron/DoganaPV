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

	public VelocityCommand clone() {
		return  new Login(this.callerServlet);
	}

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
		
//		BeanAdapter2 adp = BeanAdapter2.newAdapter();
//		ctx.put( ContextKeys.LIST , adp.getAll() ) ;
//		
//		Integer selectedId = getParameter.getInt(ID, req, false);
//		
//		if ( selectedId != null ) {
//			ctx.put( ContextKeys.OBJECT , adp.getByKey(selectedId) ) ;
//		}
		return null;
	}

	
	public String getTemplateName() {
		return T ;
	}
	
	

	
}
