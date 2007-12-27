package com.gsoft.doganapt.cmd.movimenti;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.data.Movimento;
import com.gsoft.doganapt.data.MovimentoIVA;
import com.gsoft.doganapt.data.adapters.IterAdapter;
import com.gsoft.doganapt.data.adapters.MerceAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoDoganaleAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoIvaAdapter;
import com.gsoft.doganapt.data.adapters.StalloAdapter;
import com.gtsoft.utils.common.BeanAdapter2;
import com.gtsoft.utils.common.BeanEditor;
import com.gtsoft.utils.common.FormattedDate;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;


public class EditGruppoMovimenti extends BeanEditor {
	
	protected static String TEMPLATE = "movimenti/edit_group.vm";
	
	String template ;
	boolean isIva = false ;
	
	public EditGruppoMovimenti ( GtServlet callerServlet) {
		super(callerServlet);
		template = TEMPLATE;
	}
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {
		
		Template t = null ;
		isIva =  "1".equals( getParam("iva", false) ) ;
		
		ctx.put("isIva", isIva) ;
		
		Collection stalli = StalloAdapter.getAllCached();
		ctx.put("merci" ,  MerceAdapter.getAllCached());
		ctx.put("iters" ,  IterAdapter.getAllCached());
		ctx.put("stalli" ,  stalli);
		
		FormattedDate data = getDateParam("data", true);
		Integer idConsegna = getIntParam("idC", true);
		
		MovimentoAdapter adp = (MovimentoAdapter) getAdapter() ;
		
		int tipo = getIntParam("t", true).intValue();
		
		ArrayList<Integer> idMovimenti = getIntParams("id", 0) ;
		
		Vector movimenti = null ;
		
		if ( idMovimenti == null || idMovimenti.size() < 1 ){
			movimenti = adp.getGroup(idConsegna,data , 
					( tipo == 1 || tipo - 10 == 1 ), tipo  > 9 );
		}
		else {
			StringBuilder sb = new StringBuilder(idMovimenti.size() * 2);
			sb.append("id in (");
			
			Iterator<Integer> i = idMovimenti.iterator();
			while( i.hasNext() ) { 
				sb.append(i.next()); 
				if( i.hasNext() ) sb.append(",");
			}
			sb.append(")");
			
			movimenti = adp.getWithWhere( sb.toString() );
		}
		ctx.put("list" ,  movimenti);
		
		
		if ( getBooleanParam(Strings.EXEC) ) {
			
			
			adp.fillFromRequest(req, false);
			Movimento m = null ;
			
			for ( Iterator i = movimenti.iterator() ; i.hasNext() ; ) {
				m = (Movimento) i.next();
				
				adp.updateCommonFields(m);
				
				double u = getDoubleParam("u0_" + m.getId() , true ).doubleValue() ;
				u -= getDoubleParam("u1_" + m.getId() , true ).doubleValue();
				
				double s = getDoubleParam("s0_" + m.getId() , true ).doubleValue() ;
				s -= getDoubleParam("s1_" + m.getId() , true ).doubleValue();
				
				if ( isIva ) {
					((MovimentoIVA) m).setValoreEuro( 
							getDoubleParam("v0_" + m.getId() , true ) )  ;
					
					((MovimentoIVA) m).setValoreDollari( 
							getDoubleParam("v1_" + m.getId() , true ) )  ;
				}
				
				m.setUmido(new Double(u));
				m.setSecco(new Double(s));
			
				
				adp.update(m);
			}
			
			resp.sendRedirect(".consegne?id=" + m.getIdConsegna() );
		}
		
		return t;
	}


	public VelocityCommand clone() {
		return  new EditGruppoMovimenti(this.callerServlet);
	}
	MovimentoAdapter adapter = null ;
	public BeanAdapter2 getAdapter(  )  {
		if ( adapter == null ) {
			if ( isIva )
				adapter = new MovimentoIvaAdapter();
			else 
				adapter = new MovimentoDoganaleAdapter();
		}
		
		return adapter;
	}
	public String getTemplateName() {
		return template;
	}
}