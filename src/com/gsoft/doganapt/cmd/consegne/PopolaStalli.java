package com.gsoft.doganapt.cmd.consegne;

import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.data.Consegna;
import com.gsoft.doganapt.data.Movimento;
import com.gsoft.doganapt.data.Stallo;
import com.gsoft.doganapt.data.adapters.ConsegnaAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoDoganaleAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoIvaAdapter;
import com.gsoft.doganapt.data.adapters.StalloAdapter;
import com.gsoft.pt_movimentazioni.data.MovimentoQuadrelli;
import com.gsoft.pt_movimentazioni.data.MovimentoQuadrelliAdapter;
import com.gsoft.pt_movimentazioni.utils.PtMovimentazioniImporter;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;


public class PopolaStalli extends VelocityCommand {

	protected static String TEMPLATE = "consegne/popolastalli.vm" ;

	protected MovimentoQuadrelliAdapter quadAdp = null ;

	private Consegna consegna = null ;
	private MovimentoAdapter registro = null;

	private StalloAdapter stalloAdp = null ;

	public PopolaStalli ( final GtServlet callerServlet) {
		super(callerServlet);
	}
	@Override
	public Template exec(final HttpServletRequest req, final HttpServletResponse resp, final Context ctx) throws Exception  {

		try {

			final Integer id = getIntParam("id", true);

			consegna = ConsegnaAdapter.get(id);

			// this.setConsegna(c);
			ctx.put( ContextKeys.OBJECT , consegna ) ;

			quadAdp = new MovimentoQuadrelliAdapter(
					PtMovimentazioniImporter.getInstance().getAccessDB());


			if ( consegna != null ) {
				doPopola();
				//				if ( getBooleanParam(Strings.EXEC) ) {
				//				}
				//				else {
				//					// TODO: showConfirm("Procedere con l'esecuzione dell'operazione : Popola Stalli ? ");
				//				}
			}


			/*
			 * OLD

				if ( c.getIter().getHasrettifica()  ) {
					if ( getBooleanParam(Strings.EXEC) ) {
						doPopola(c);

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
					final ArrayList<String> codiciStalli = quadAdp.getCodiciStalli(c, null);
					final StalloAdapter sAdp = Stallo.newAdapter();
					Stallo s = null ;
					for (final String string : codiciStalli) {
						s = StalloAdapter.getByCodice(string, false);

						if ( s != null ) {
							s.setIdConsegnaAttuale(c.getId()) ;

							sAdp.update(s);
						}
					}
				}
			}
			 */

		} catch ( final Exception e) {
			ctx.put("err" ,  e );
		}

		return null;
	}

	private void doPopola() throws Exception {

		registro = consegna.getIter().getImporter(
				new MovimentoDoganaleAdapter(),
				new MovimentoIvaAdapter(), null ).getRegistroPrimoCarico() ;

		// carica i carichi attualmente presenti, quello senza stallo sarà quello che ancora devo distribuire
		// popolerà un array di stalli che hanno già i carichi, confronterò il totale con quello che mi dice quadrelli
		// se è inferiore lo attualizzo scontandolo dal movimento originario
		// se il movimento ha il num di registro non si tocca
		final Map<Integer, Movimento> carichi = registro.getCarichiInizialiPerStallo(consegna); //idStallo, movimento


		Movimento primoCarico = carichi.get(MovimentoAdapter.ID_STALLO_CARICO_APERTURA);
		boolean hasCaricoApertura = ( primoCarico != null );
		if ( ! hasCaricoApertura ) {
			for ( final Integer i : carichi.keySet() ) {
				primoCarico = carichi.get(i);
				break;
			}
		}

		if ( stalloAdp == null ) {
			stalloAdp = new StalloAdapter();
		}

		Movimento carico = null;
		double sommaUmido = 0 ;
		for ( final Stallo s : getStalliFromQuadrelli() ) {

			// verifico se c'è già il movimento di carico
			carico = carichi.get(s.getId());

			if ( carico != null ) {

				// se c'è già il movimento per quello stallo e non ha num. di Registro :
				// gli aggiorno l'umido scontando la differenza dal fittizio

				// e se è già registrato o già in LP, non lo tocco!
				if( carico.getNumRegistro() != null || s.getImmessoInLiberaPratica() ) {
					continue ;
				}

				carico.setUmido(s.getAttuale());
				carico.setSecco( consegna.calcolaSecco(carico.getUmido()) );

				registro.update(carico);

			}
			else {
				// se non c'è lo creo a partire dal movimento di carico fittizio,
				// gli assegno il peso umido che mi dice quadrelli e lo sconto dal fittizio

				if ( hasCaricoApertura ) {
					carico = primoCarico;
					hasCaricoApertura = false ;
				}
				else {
					carico = primoCarico.clone();

					carico.setDocumento(primoCarico.getDocumento());
					carico.setDocumentoPV(primoCarico.getDocumentoPV());
				}
				carico.setIdStallo(s.getId());

				carico.setUmido(s.getAttuale());
				carico.setSecco( consegna.calcolaSecco(carico.getUmido()) );

				if ( carico.getId() != null ) {
					registro.update(carico);
				} else {
					registro.create(carico);
				}
			}

			sommaUmido += s.getAttuale();

			stalloAdp.update(s);

		}

		double umidoRestante = consegna.getPesopolizza().doubleValue() - sommaUmido ;
		if ( umidoRestante != 0 ) {
			double caricato = carico.getUmido();
			double nuovoCarico = caricato + umidoRestante ;

			carico.setUmido(nuovoCarico);
			carico.setSecco( consegna.calcolaSecco(carico.getUmido()) );

			registro.update(carico);
		}


		response.sendRedirect(".consegne?id=" + consegna.getId() );
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Stallo> getStalliFromQuadrelli() throws Exception {

		final ArrayList<String> codiciStalli = quadAdp.getCodiciStalli(consegna, null);

		if ( codiciStalli == null )
			throw new Exception("Non sono stati trovati stalli per la consegna!");

		final ArrayList<Stallo> list = new ArrayList<Stallo>(codiciStalli.size());

		Stallo s = null ;
		Vector<MovimentoQuadrelli> listMov = null;
		double sommaUmido = 0.;

		for (final String string : codiciStalli) {

			s = StalloAdapter.getByCodice(string, false);

			if ( s != null ) {
				list.add(s);

				if ( ! consegna.getId().equals( s.getIdConsegnaAttuale() ) ) {
					s.setIdConsegnaAttuale(consegna.getId());
					s.setImmessoInLiberaPratica(Boolean.FALSE);
				}

				listMov = quadAdp.get(false, null , s);

				sommaUmido = 0 ;

				for (final Object element : listMov) {
					sommaUmido += ((MovimentoQuadrelli) element).getNetto().doubleValue() ;
				}

				s.setCaricato(sommaUmido);
				s.setAttuale(sommaUmido);
			}
		}
		return list ;
	}

	protected Consegna getConsegna() {
		return consegna  ;
	}

	@Override
	public VelocityCommand clone() {
		return  new PopolaStalli(callerServlet);
	}

	@Override
	public String getTemplateName() {
		return TEMPLATE;
	}

	/*
	private void doPFPC_unused(final Consegna c,  final MovimentoQuadrelliAdapter quadAdp ) throws Exception {
		final ArrayList<String> codiciStalli = quadAdp.getCodiciStalli(c, null);
		Vector listMov = null ;
		//		ArrayList<ArrayList> listStalli = new ArrayList<ArrayList>(codiciStalli.size());
		Stallo s = null ;
		double sommaUmido = 0 ;
		double sommaTotale = 0 ;

		double diff = 0;

		FormattedDate dataCarico = null ;
		Long numRegistroCarico = null ;
		Movimento mov = null;

		final MovimentoAdapter registro = c.getIter().getImporter(
				new MovimentoDoganaleAdapter(),
				new MovimentoIvaAdapter(), null ).getRegistroPrimoCarico() ;

		for ( final Iterator<String> i = codiciStalli.iterator() ; i.hasNext() ; ) {

			s = StalloAdapter.getByCodice(i.next(), false);

			if ( s != null ) {

				s.setIdConsegnaAttuale(c.getId());

				listMov = quadAdp.get(false, null , s);

				sommaUmido = 0 ;

				for ( final Iterator iM = listMov.iterator() ; iM.hasNext() ; ) {
					sommaUmido += ((MovimentoQuadrelli) iM.next()).getNetto().doubleValue() ;
				}

				sommaTotale += sommaUmido;

				if ( mov == null ) {


					final Vector list = registro.getByConsegna(false, c.getId(), null , null, null ) ;
					if ( list != null && list.size() > 0 ) {
						mov = (Movimento) list.firstElement() ;
					} else
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

					if ( i.hasNext() ) {
						mov.setUmido( sommaUmido ) ;
					} else {
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

	private void doPopola_old(final Consegna c ) throws Exception {
		//		String documentoRettifica = getParam("docR", false);
		//		String documentoImmissione = getParam("docImm", false);

		final FormattedDate dataRettifica = getDateParam("data", false);
		final boolean doRettifica = ! ( c.getPesoFinalePortoCarico().booleanValue() || ( dataRettifica == null ) ) ;

		FormattedDate dataCarico = null ;
		Long numRegistroCarico = null ;

		final String doc_ = getParam("doc", false);
		final String doc_num = getParam("doc_num", false);
		final FormattedDate doc_data = getDateParam("doc_data", false);

		final Documento doc = Documento.getDocumento( doc_, doc_data , doc_num );

		final String docPV_ = getParam("docPV", false);
		final String docPV_num = getParam("docPV_num", false);
		final FormattedDate docPV_data = getDateParam("docPV_data", false);

		final Documento docPV = Documento.getDocumento( docPV_, docPV_data , docPV_num );

		final ArrayList<Integer> idStalli = getIntParams("stalli", 1);

		Double rettificaUmido = null ;
		Double rettificaSecco = null ;
		Boolean isRettificaScarico = null ;

		Double pesoUmido = null ;

		Movimento mov = null;
		final MovimentoAdapter registro = c.getIter().getImporter(
				new MovimentoDoganaleAdapter(),
				new MovimentoIvaAdapter(), null ).getRegistroPrimoCarico() ;

		Stallo s = null ;
		final StalloAdapter sAdp = Stallo.newAdapter() ;

		Documento doc_IM = null;
		Documento docPV_IM = null;

		for (final Integer integer : idStalli) {
			s = StalloAdapter.get(integer);

			pesoUmido = new Double( getParam("umido_" + s.getId() , true).replaceAll("\\.", "") );

			if ( doRettifica ) {
				rettificaUmido = new Double(
						new Double( getParam("u0_" + s.getId() , true) ).doubleValue()
						-
						new Double( getParam("u1_" + s.getId() , true) ).doubleValue() ) ;

				rettificaSecco = new Double(
						new Double( getParam("s0_" + s.getId() , true) ).doubleValue()
						-
						new Double( getParam("s1_" + s.getId() , true) ).doubleValue() ) ;

			}
			else {
				rettificaUmido = 0. ;
				rettificaSecco = 0. ;
			}

			if ( mov == null ) {
				final Vector list = registro.getByConsegna(false, c.getId(), null , null, null ) ;
				if ( list != null && list.size() > 0 ) {
					mov = (Movimento) list.firstElement() ;
				} else
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

			if ( doRettifica ) {
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

		if ( doRettifica ) {
			final String umiditaNuova = getParam("umidita", false) ;
			if ( umiditaNuova != null ) {
				c.setTassoUmidita(new Double( umiditaNuova )) ;
				Consegna.newAdapter().update(c);
			}
		}

		response.sendRedirect(".consegne?id=" + c.getId() );
	}

	private void showRettifica_old(final Consegna c, final MovimentoQuadrelliAdapter quadAdp, final Context ctx) throws Exception {
		double sommaUmido = 0 ;
		double totaleUmido = 0 ;

		Vector listMov = null ;

		final ArrayList<String> codiciStalli = quadAdp.getCodiciStalli(c, null);

		ctx.put("c", codiciStalli );

		final ArrayList<ArrayList> list = new ArrayList<ArrayList>(codiciStalli.size());
		ArrayList<Object> row = null;

		Stallo s = null ;
		for (final String string : codiciStalli) {

			row = new ArrayList<Object>(3) ;


			s = StalloAdapter.getByCodice(string, false);

			if ( s != null ) {
				row.add(s);

				s.setIdConsegnaAttuale(c.getId());

				listMov = quadAdp.get(false, null , s);

				sommaUmido = 0 ;

				for ( final Iterator mov = listMov.iterator() ; mov.hasNext() ; ) {
					sommaUmido += ((MovimentoQuadrelli) mov.next()).getNetto().doubleValue() ;
				}

				row.add(sommaUmido);

				totaleUmido += sommaUmido ;
				list.add(row);
			}
		}

		ctx.put("list", list );
		ctx.put("totaleUmido", totaleUmido );

		final double rettificaUmido = totaleUmido - c.getPesopolizza().doubleValue();
		ctx.put("rUmido", rettificaUmido );
	}
	 */
}