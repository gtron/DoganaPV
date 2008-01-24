package com.gsoft.doganapt.cmd.consegne;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.data.Consegna;
import com.gsoft.doganapt.data.Documento;
import com.gsoft.doganapt.data.Movimento;
import com.gsoft.doganapt.data.MovimentoIVA;
import com.gsoft.doganapt.data.Stallo;
import com.gsoft.doganapt.data.adapters.ConsegnaAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoDoganaleAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoIvaAdapter;
import com.gsoft.doganapt.data.adapters.StalloAdapter;
import com.gsoft.pt_movimentazioni.data.MovimentoQuadrelli;
import com.gsoft.pt_movimentazioni.data.MovimentoQuadrelliAdapter;
import com.gsoft.pt_movimentazioni.utils.PtMovimentazioniImporter;
import com.gtsoft.utils.common.FormattedDate;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;


public class PopolaStalli extends VelocityCommand {

	protected static String TEMPLATE = "consegne/popolastalli.vm" ;

	public PopolaStalli ( GtServlet callerServlet) {
		super(callerServlet);
	}
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {

		Integer id = getIntParam("id", true);
		
		Consegna c = ConsegnaAdapter.get(id);
		ctx.put( ContextKeys.OBJECT , c ) ;
		
		MovimentoQuadrelliAdapter quadAdp = new MovimentoQuadrelliAdapter(
				PtMovimentazioniImporter.getInstance().getAccessDB());
		
		if ( c != null ) {
			if ( c.getIter().getHasrettifica()  ) {
				if ( getBooleanParam(Strings.EXEC) ) {
					doRettifica(c);
					String umiditaNuova = getParam("umidita", false) ;
					if ( umiditaNuova != null ) {
						c.setTassoUmidita(new Double( umiditaNuova )) ;
						Consegna.newAdapter().update(c);
					}
				}
//				else if ( c.isPesoFinalePortoCarico() ) {
//					doPFPC(c,quadAdp);
//					response.sendRedirect(".consegne?id=" + c.getId());
//				}
				else {
					showRettifica(c, quadAdp,  ctx);
				}
			}
			else {
				ArrayList<String> codiciStalli = quadAdp.getCodiciStalli(c, null);
				StalloAdapter sAdp = Stallo.newAdapter();
				Stallo s = null ;
				for ( Iterator<String> i = codiciStalli.iterator() ; i.hasNext() ; ) {
					s = (Stallo) StalloAdapter.getByCodice(i.next(), false);
					
					if ( s != null ) {
						s.setIdConsegnaAttuale(c.getId()) ;
						
						sAdp.update(s);
					}
				}
			}
		}
		
		return null;
	}


	public VelocityCommand clone() {
		return  new PopolaStalli(this.callerServlet);
	}

	public String getTemplateName() {
		return TEMPLATE;
	}
	
	private void doPFPC(Consegna c,  MovimentoQuadrelliAdapter quadAdp ) throws Exception {
		ArrayList<String> codiciStalli = quadAdp.getCodiciStalli(c, null);
		Vector listMov = null ;
//		ArrayList<ArrayList> listStalli = new ArrayList<ArrayList>(codiciStalli.size());
		Stallo s = null ;
		double sommaUmido = 0 ;
		double sommaTotale = 0 ;
		
		double diff = 0;
		
		FormattedDate dataCarico = null ;
		Long numRegistroCarico = null ;
		Movimento mov = null;
		
		MovimentoAdapter registro = c.getIter().getImporter(
				new MovimentoDoganaleAdapter(),
				new MovimentoIvaAdapter(), null ).getRegistroPrimoCarico() ;
		
		for ( Iterator<String> i = codiciStalli.iterator() ; i.hasNext() ; ) {

			s = (Stallo) StalloAdapter.getByCodice(i.next(), false);

			if ( s != null ) {
				
				s.setIdConsegnaAttuale(c.getId());

				listMov = quadAdp.get(false, null , s);

				sommaUmido = 0 ;

				for ( Iterator iM = listMov.iterator() ; iM.hasNext() ; ) {
					sommaUmido += ((MovimentoQuadrelli) iM.next()).getNetto().doubleValue() ;
				}
				
				sommaTotale += sommaUmido;
				
				if ( mov == null ) {
					
					
					Vector list = registro.getByConsegna(false, c.getId(), null , null, null ) ;
					if ( list != null && list.size() > 0 )
						mov = (Movimento) list.firstElement() ;
				
					else
						throw new Exception("Carico iniziale non trovato!");
					
					dataCarico = mov.getData();
					numRegistroCarico = mov.getNumRegistro();
					
					mov.setIdStallo(s.getId());
					mov.setData(dataCarico);
					mov.setIsScarico(Boolean.FALSE);
					mov.setNumRegistro(numRegistroCarico);
					mov.setUmido( sommaUmido ) ;
					mov.setSecco( c.calcolaSecco(mov.getUmido()) );
					mov.setIsRettifica(Boolean.FALSE);
//					mov.setDocumento(documentoImmissione);
					
					registro.update(mov);
					
				}
				else {
					
					mov.setId(null);
					mov.setIdStallo(s.getId());
					mov.setData(dataCarico);
					mov.setNumRegistro(numRegistroCarico);
					
					if ( i.hasNext() )
						mov.setUmido( sommaUmido ) ;
					else {
						diff = c.getPesopolizza().doubleValue() - sommaTotale ;
						
						mov.setUmido( sommaUmido + diff ) ;
					}
					
					mov.setSecco( c.calcolaSecco(mov.getUmido()) );
					mov.setIsRettifica(Boolean.FALSE);
					mov.setIsScarico(Boolean.FALSE);
//					mov.setDocumento(documentoImmissione);
					
					
						
					registro.create(mov);
				}
			}
			
		}
		
		
		
		if (  diff == 0 && mov != null ) {
			
			diff = c.getPesopolizza().doubleValue() - sommaTotale ;
			
			mov.setUmido( new Double( mov.getUmido().doubleValue() + diff ) );
			mov.setSecco( c.calcolaSecco(mov.getUmido()) );
			
			registro.update(mov);
		}
		
	}
	
	private void doRettifica(Consegna c ) throws Exception {
//		String documentoRettifica = getParam("docR", false);
//		String documentoImmissione = getParam("docImm", false);
		
		boolean isPesoFinalePC = c.getPesoFinalePortoCarico().booleanValue() ;
		
		FormattedDate dataRettifica = getDateParam("data", ! isPesoFinalePC);
		FormattedDate dataCarico = null ;
		Long numRegistroCarico = null ;
		
		String doc_ = getParam("doc", false);
		String doc_num = getParam("doc_num", false);
		FormattedDate doc_data = getDateParam("doc_data", false);
		
		Documento doc = Documento.getDocumento( doc_, doc_data , doc_num ); 
		
		String docPV_ = getParam("docPV", false);
		String docPV_num = getParam("docPV_num", false);
		FormattedDate docPV_data = getDateParam("docPV_data", false);
		
		Documento docPV = Documento.getDocumento( docPV_, docPV_data , docPV_num ); 
		
		ArrayList<Integer> idStalli = getIntParams("stalli", 1);

		Double rettificaUmido = null ;
		Double rettificaSecco = null ;
		Boolean isRettificaScarico = null ;
		
		Double pesoUmido = null ;
	
		Movimento mov = null;
		MovimentoAdapter registro = c.getIter().getImporter(
				new MovimentoDoganaleAdapter(),
				new MovimentoIvaAdapter(), null ).getRegistroPrimoCarico() ;
		
		Stallo s = null ;
		StalloAdapter sAdp = Stallo.newAdapter() ;
		
		Documento doc_IM = null;
		Documento docPV_IM = null;
		
		for ( Iterator<Integer> i = idStalli.iterator() ; i.hasNext() ; ) {
			s = StalloAdapter.get(i.next());

			pesoUmido = new Double( getParam("umido_" + s.getId() , true).replaceAll("\\.", "") );
			
			rettificaUmido = new Double( 
					new Double( getParam("u0_" + s.getId() , true) ).doubleValue() 
					-
					new Double( getParam("u1_" + s.getId() , true) ).doubleValue() ) ;
			
			rettificaSecco = new Double( 
					new Double( getParam("s0_" + s.getId() , true) ).doubleValue() 
					-
					new Double( getParam("s1_" + s.getId() , true) ).doubleValue() ) ;
		
			if ( mov == null ) {
				Vector list = registro.getByConsegna(false, c.getId(), null , null, null ) ;
				if ( list != null && list.size() > 0 )
					mov = (Movimento) list.firstElement() ;
			
				else
					throw new Exception("Carico iniziale non trovato!");
				
				dataCarico = mov.getData();
				numRegistroCarico = mov.getNumRegistro();
				
				mov.setIdStallo(s.getId());
				mov.setData(dataCarico);
				mov.setIsScarico(Boolean.FALSE);
				mov.setNumRegistro(numRegistroCarico);
				mov.setUmido( pesoUmido.doubleValue() - rettificaUmido.doubleValue() ) ;
				mov.setSecco( c.calcolaSecco(mov.getUmido()) );
				mov.setIsRettifica(Boolean.FALSE);
//				mov.setDocumento(documentoImmissione);
				
				if (mov instanceof MovimentoIVA) {
					c.updateValore( (MovimentoIVA) mov);
				}
				registro.update(mov);
				
				if ( rettificaUmido.doubleValue() < 0 ) {
					isRettificaScarico = Boolean.TRUE ;
					rettificaUmido = new Double( -1 * rettificaUmido.doubleValue());
				}
				else {
					isRettificaScarico = Boolean.FALSE ; 
				}
				
				doc_IM = mov.getDocumento() ;
				docPV_IM = mov.getDocumentoPV() ;
			}
			else {
				mov.setIdStallo(s.getId());
				mov.setData(dataCarico);
				mov.setNumRegistro(numRegistroCarico);
				mov.setUmido( pesoUmido.doubleValue() - rettificaUmido.doubleValue() ) ;
				mov.setSecco( c.calcolaSecco(mov.getUmido()) );
				mov.setIsRettifica(Boolean.FALSE);
				mov.setIsScarico(Boolean.FALSE);
				mov.setDocumento(doc_IM);
				mov.setDocumentoPV(docPV_IM);
				
				if (mov instanceof MovimentoIVA) {
					c.updateValore( (MovimentoIVA) mov);
				}
				mov.setId(null);
				registro.create(mov);
			}
			
			if ( ! isPesoFinalePC ) {
				mov.setId(null);
				mov.setNumRegistro(null);
				mov.setData(dataRettifica);
				mov.setIsScarico(isRettificaScarico);
				mov.setUmido( rettificaUmido ) ;
				mov.setSecco( rettificaSecco );
				mov.setIsRettifica(Boolean.TRUE);
				mov.setDocumento(doc);
				mov.setDocumentoPV(docPV);
				
				if (mov instanceof MovimentoIVA) {
					c.updateValore( (MovimentoIVA) mov);
				}
				registro.create(mov);
			}
			
			s.setIdConsegnaAttuale(c.getId());
			sAdp.update(s);
		}
		
		response.sendRedirect(".consegne?id=" + c.getId() );
	}
	
	private void showRettifica(Consegna c, MovimentoQuadrelliAdapter quadAdp, Context ctx) throws Exception {
		double sommaUmido = 0 ;
		double totaleUmido = 0 ;
		
		Vector listMov = null ;

		ArrayList<String> codiciStalli = quadAdp.getCodiciStalli(c, null);

		ArrayList<ArrayList> list = new ArrayList<ArrayList>(codiciStalli.size());
		ArrayList<Object> row = null;

		Stallo s = null ;
		for ( Iterator<String> i = codiciStalli.iterator() ; i.hasNext() ; ) {

			row = new ArrayList<Object>(3) ;
			

			s = (Stallo) StalloAdapter.getByCodice(i.next(), false);

			if ( s != null ) {
				row.add(s);

				s.setIdConsegnaAttuale(c.getId());

				listMov = quadAdp.get(false, null , s);

				sommaUmido = 0 ;

				for ( Iterator mov = listMov.iterator() ; mov.hasNext() ; ) {
					sommaUmido += ((MovimentoQuadrelli) mov.next()).getNetto().doubleValue() ;
				}

				row.add(sommaUmido);
				
				totaleUmido += sommaUmido ; 
				list.add(row);
			}
		}

		ctx.put("list", list );
		ctx.put("totaleUmido", totaleUmido );
		
		double rettificaUmido = totaleUmido - c.getPesopolizza().doubleValue();
		ctx.put("rUmido", rettificaUmido );
	}
}