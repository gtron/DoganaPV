
package com.gsoft.doganapt.cmd.consegne;

import java.util.ArrayList;

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
import com.gsoft.doganapt.data.adapters.StalloAdapter;
import com.gsoft.pt_movimentazioni.data.MovimentoQuadrelli;
import com.gtsoft.utils.common.BeanAdapter2;
import com.gtsoft.utils.common.FormattedDate;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;


public class TravasaStallo extends VelocityCommand {
	
	public TravasaStallo ( GtServlet callerServlet) {
		super(callerServlet);
	}
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {
		
		Integer idConsegna = getIntParam("idC", true);
		Consegna c = ConsegnaAdapter.get(idConsegna);
		ctx.put( ContextKeys.OBJECT , c ) ;
		
		Integer idStalloOrigine= getIntParam("from", true);
		Integer idStalloDestinazione= getIntParam("to", true);
		
		Stallo origine, destinazione;
		for( Stallo s : c.getStalli() ) {
			if ( s.getId() == idStalloOrigine ) origine = s ;
			if ( s.getId() == idStalloDestinazione ) destinazione = s ;
		}
		
		
		FormattedDate data = getDateParam("data", true);
		
		String doc = getParam("doc", false);
		String doc_num = getParam("doc_num", false);
		FormattedDate doc_data = getDateParam("doc_data", false);
		
		Documento doc = Documento.getDocumento( doc, doc_data , doc_num ); 

		ctx.put("isIva", getBooleanParam("iva", false).booleanValue()) ;



		
		if ( c != null && getBooleanParam(Strings.EXEC) ) {
			
			MovimentoAdapter adp = (MovimentoAdapter) getAdapter() ;

			// Movimento Uscita dallo stallo di origine
			Movimento m = adp.newMovimento() ;
			m.setIdConsegna(idConsegna);
			
			m.setIsLocked(false);
			m.setIsScarico(isScarico);
			m.setIsRettifica( false );
			m.setUmido(q.getNetto());
			m.setSecco( c.calcolaSecco(m.getUmido()) );
			m.setData(q.getData()) ;
			m.setIdMerce( c.getIdmerce() );
			m.setIdConsegna( c.getId()) ;
			
			Stallo s = null ;
			if ( isScarico ) {
				s = StalloAdapter.getByCodice((q.getCodiceFornitore()), false) ;
			}
			else {
				s = StalloAdapter.getByCodice((q.getCodiceCliente()), false) ;
			}
			m.setStallo( s );

			
			
			
		}
		return null ;
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