package com.gsoft.doganapt.cmd.consegne;

import java.util.ArrayList;
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
import com.gsoft.doganapt.data.StalloConsegna;
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
import com.gtsoft.utils.http.exception.ParameterException;
import com.gtsoft.utils.http.servlet.GtServlet;


public class Rettifica extends VelocityCommand {

	protected static String TEMPLATE = "consegne/rettifica.vm" ;
	private Consegna consegna = null;
	private boolean isIva = false ;

	private MovimentoAdapter registro = null ;

	public Rettifica ( final GtServlet callerServlet) {
		super(callerServlet);
	}

	@Override
	public Template exec(final HttpServletRequest req, final HttpServletResponse resp, final Context ctx) throws Exception  {

		MovimentoQuadrelliAdapter quadAdp = null ;
		try {
			final Integer id = getIntParam("id", true);
			consegna = ConsegnaAdapter.get(id);
			ctx.put( ContextKeys.OBJECT , consegna ) ;

			quadAdp = new MovimentoQuadrelliAdapter(
					PtMovimentazioniImporter.getInstance().getAccessDB());

			if ( consegna != null ) {
				if ( consegna.getIter().getHasrettifica()  ) {

					initRegistroPrimoCarico();

					if ( getBooleanParam(Strings.EXEC) ) {
						doRettifica();
					}
					else {
						showRettifica(quadAdp,  ctx);
					}
				}
			}

		} catch ( final Exception e) {
			ctx.put("err" ,  e );
			if ( consegna != null && quadAdp != null) {
				showRettifica(quadAdp,  ctx);
			}
		}

		return null;
	}

	private void doRettifica() throws Exception {

		final FormattedDate dataRettifica = getDateParam("data", true);

		final Documento doc = getDocumentoFromPost("doc");
		final Documento docPV = getDocumentoFromPost("docPV");

		final ArrayList<Integer> idStalli = getIntParams("stalli", 1);

		final boolean debug = true ; // getBooleanParam("debug",false) ;

		Double rettificaUmido = null ;
		Double rettificaSecco = null ;

		Double rettificaOneri = null;

		Stallo s = null ;
		Integer idStallo = null;
		Movimento mov = null;
		Movimento movCarico = null ;

		final ArrayList<Movimento> toCreate = new ArrayList<Movimento>(idStalli.size());

		for (final Integer integer : idStalli) {

			s = StalloAdapter.get(integer);
			idStallo = s.getId();

			rettificaUmido = new Double(
					new Double( getParam("u0_" + idStallo , true) ).doubleValue()
					-
					new Double( getParam("u1_" + idStallo , true) ).doubleValue() ) ;

			rettificaSecco = new Double(
					new Double( getParam("s0_" + idStallo , true) ).doubleValue()
					-
					new Double( getParam("s1_" + idStallo , true) ).doubleValue() ) ;


			if ( consegna.getId().equals(s.getIdConsegnaAttuale())
					&&
					( Math.abs(rettificaSecco) + Math.abs(rettificaUmido)) > 0 ) {


				// Ora non c'è + bisogno di fare l'update
				// del movimento di carico perchè viene lasciato inalterato dal popola stalli
				if ( false && Math.abs(rettificaUmido) > 0 ) {

					final Vector<?> list = registro.getByConsegna(false, consegna.getId(), idStallo , "isscarico asc, id asc" , null ) ;
					if ( list != null && list.size() > 0 ) {
						movCarico = (Movimento) list.firstElement() ;
					}

					movCarico.setUmido( movCarico.getUmido().doubleValue() - rettificaUmido ) ;
					movCarico.setSecco( consegna.calcolaSecco(movCarico.getUmido()) );

					registro.update(movCarico);
				}

				if ( movCarico == null ) {
					final Vector<?> list = registro.getByConsegna(false, consegna.getId(), null , null , null ) ;
					if ( list != null && list.size() > 0 ) {
						movCarico = (Movimento) list.firstElement() ;
					}
				}

				mov = registro.newMovimento();

				mov.setId(null);
				mov.setNumRegistro(null);
				mov.setData(dataRettifica);
				mov.setUmido( rettificaUmido ) ;
				mov.setSecco( rettificaSecco );
				mov.setIdStallo(idStallo);
				mov.setIdConsegna(consegna.getId());
				mov.setIdMerce(movCarico.getIdMerce());
				mov.setIsScarico(Boolean.FALSE);
				mov.setIsRettifica(Boolean.TRUE);
				mov.setIsLocked(Boolean.FALSE);
				mov.setDocumento(doc);
				mov.setDocumentoPV(docPV);

				mov.setNote(MovimentoAdapter.NOTE_RETTIFICA_PESO);

				if (debug) {
					System.out.printf("Stallo: %d , Umido: %f , Secco %f \n",  idStallo , rettificaUmido, rettificaSecco );
				}

				if (isIva) {

					MovimentoIVA movIva = (MovimentoIVA) mov;

					//					try {
					//						// consegna.updateValore( (MovimentoIVA) mov);
					//					}
					//					catch ( final Exception e ){
					//						((MovimentoIVA) mov).setValoreDollari(0.0);
					//						((MovimentoIVA) mov).setValoreEuro(0.0);
					//					}
					rettificaOneri = new Double( getParam("rettificaOneri", true) ).doubleValue();
					movIva.setValoreTestp( rettificaOneri );
					movIva.setValoreEuro( rettificaOneri  );

					movIva.setValoreIva( StalloConsegna.calcolaValoreIva(rettificaOneri) );

					movIva.setValoreDollari( Double.valueOf(0d) );
					movIva.setValoreNetto( movIva.getValoreDollari() );

					if ( consegna.getIter().getRegdoganale()) {
						movIva.setCodPosizioneDoganale(MovimentoIvaAdapter.COD_POSIZIONEDOGANALE_ENAZ);
					} else {
						movIva.setCodPosizioneDoganale(MovimentoIvaAdapter.COD_POSIZIONEDOGANALE_NAZ);
					}

				}
				toCreate.add(mov);


			} else
				throw new Exception("Errore: Lo stallo " + s.getParco() + " " + s.getNumero() +
						" non è assegnato alla Consegna elaborata!<br> Effettuare il popola stalli prima della rettifica.");




		}

		for( final Movimento m : toCreate) {
			if ( m != null ) {
				registro.create(m);

				if (debug) {
					System.out.println("Created Stallo:" + idStallo);
				}
			}
		}

		final String umiditaNuova = getParam("umidita", false) ;
		if ( umiditaNuova != null ) {
			consegna.setTassoUmidita(new Double( umiditaNuova )) ;
			Consegna.newAdapter().update(consegna);
		}

		response.sendRedirect(".consegne?id=" + consegna.getId() );
	}


	private MovimentoAdapter initRegistroPrimoCarico() {
		registro = consegna.getIter().getImporter(
				new MovimentoDoganaleAdapter(),
				new MovimentoIvaAdapter(), null ).getRegistroPrimoCarico();

		isIva = registro.isIva();

		return registro;
	}

	private void showRettifica(final MovimentoQuadrelliAdapter quadAdp, final Context ctx) throws Exception {
		double sommaUmido = 0 ;
		double totaleUmido = 0 ;

		Vector<?> listMov = null ;

		final ArrayList<String> codiciStalli = quadAdp.getCodiciStalli(consegna, null);

		ctx.put("c", codiciStalli );
		ctx.put("isIva", isIva );

		final ArrayList<ArrayList<Object>> list = new ArrayList<ArrayList<Object>>(codiciStalli.size());
		ArrayList<Object> row = null;

		Stallo s = null ;
		for (final String string : codiciStalli) {

			row = new ArrayList<Object>(3) ;

			s = StalloAdapter.getByCodice(string, false);

			if ( s != null ) {
				row.add(s);
				s.setIdConsegnaAttuale(consegna.getId());
				listMov = quadAdp.get(false, null , s);

				sommaUmido = 0 ;

				for (final Object element : listMov) {
					sommaUmido += ((MovimentoQuadrelli) element).getNetto().doubleValue() ;
				}

				row.add(sommaUmido);

				totaleUmido += sommaUmido ;
				list.add(row);
			}
		}

		ctx.put("list", list );
		ctx.put("totaleUmido", totaleUmido );

		final double rettificaUmido = totaleUmido - consegna.getPesopolizza().doubleValue();
		ctx.put("rUmido", rettificaUmido );
		ctx.put("rUmidoTonnellate", Math.ceil( Math.abs( rettificaUmido / 1000 ) ) );
	}

	protected Documento getDocumentoFromPost( final String fieldPrefix ) throws ParameterException {
		final String doc = getParam(fieldPrefix, false);
		final String doc_num = getParam(fieldPrefix + "_num", false);
		final FormattedDate doc_data = getDateParam(fieldPrefix + "_data", false);

		return Documento.getDocumento( doc, doc_data , doc_num );
	}

	@Override
	public VelocityCommand clone() {
		return  new Rettifica(callerServlet);
	}

	@Override
	public String getTemplateName() {
		return TEMPLATE;
	}

}