
package com.gsoft.doganapt.cmd.consegne;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.data.Consegna;
import com.gsoft.doganapt.data.Documento;
import com.gsoft.doganapt.data.Movimento;
import com.gsoft.doganapt.data.Stallo;
import com.gsoft.doganapt.data.adapters.ConsegnaAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoDoganaleAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoIvaAdapter;
import com.gtsoft.utils.common.BeanAdapter2;
import com.gtsoft.utils.common.FormattedDate;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;


public class TravasaStallo extends VelocityCommand {
	
	public TravasaStallo ( GtServlet callerServlet) {
		super(callerServlet);
	}
	
	Consegna consegna ;
	FormattedDate data ;
	Documento documento ;
	Boolean isIva ;
	
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {
		
		Integer idConsegna = getIntParam("idC", true);
		consegna = ConsegnaAdapter.get(idConsegna);
		ctx.put( ContextKeys.OBJECT , consegna ) ;
		
		Integer idStalloOrigine= getIntParam("from", true);
		Integer idStalloDestinazione= getIntParam("to", true);
		
		Stallo s = null, origine = null, destinazione = null;
		for( Object tmp : consegna.getStalli() ) {
			s = (Stallo) tmp;
			if ( s.getId().equals( idStalloOrigine )) origine = s ;
			if ( s.getId().equals(idStalloDestinazione )) destinazione = s ;
		}
		
		if ( origine == null || destinazione == null )
				throw new Exception("Destinazione/Origine");
		
		
		data = getDateParam("data", false);
		if ( data == null ) 
			data = new FormattedDate();
		
		String doc = getParam("doc", false);
		String doc_num = getParam("doc_num", false);
		FormattedDate doc_data = getDateParam("doc_data", false);
		
		documento = Documento.getDocumento( doc, doc_data , doc_num ); 

		isIva = getBooleanParam("iva", false) ;
		if ( isIva == null ) isIva = Boolean.FALSE;
		ctx.put("isIva", isIva.booleanValue()) ;

		
		if ( consegna != null && getBooleanParam(Strings.EXEC) ) {
			
			Movimento scarico = getMovimento( origine , null );
			
			Movimento carico = getMovimento(destinazione, scarico);
			
			MovimentoAdapter adp = (MovimentoAdapter) getAdapter() ;
			
			adp.create(scarico);
			adp.create(carico);
			
		}
		return null ;
	}

	private Movimento getMovimento( Stallo s , Movimento scarico) throws Exception {
		
		MovimentoAdapter adp = (MovimentoAdapter) getAdapter() ;

		Movimento m = adp.newMovimento() ;
		m.setIdConsegna( consegna.getId() );
		m.setIdMerce( consegna.getIdmerce() );
		
		m.setIsLocked(false);
		m.setIsRettifica( false );
		m.setData(data) ;
		m.setDocumento(documento);
		m.setStallo( s );
		
		if ( scarico == null ) {
			m.setUmido( s.getGiacenzaDoganale(false) );
			m.setSecco( s.getGiacenzaDoganale(true) );
			m.setIsScarico(true);
		}
		else {
			m.setUmido( scarico.getUmido() );
			m.setSecco( scarico.getSecco() );
			m.setIsScarico(false);
		}
		
		return m ;
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

	public VelocityCommand clone() {
		return  new TravasaStallo(this.callerServlet);
	}
	
	public String getTemplateName() {
		return ViewConsegna.TEMPLATE ;
	}
}