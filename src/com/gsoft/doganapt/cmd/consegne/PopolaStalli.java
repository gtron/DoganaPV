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
import com.gsoft.doganapt.data.StalloConsegna;
import com.gsoft.doganapt.data.adapters.ConsegnaAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoDoganaleAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoIvaAdapter;
import com.gsoft.doganapt.data.adapters.StalloAdapter;
import com.gsoft.doganapt.data.adapters.StalloConsegnaAdapter;
import com.gsoft.pt_movimentazioni.data.MovimentoQuadrelli;
import com.gsoft.pt_movimentazioni.data.MovimentoQuadrelliAdapter;
import com.gsoft.pt_movimentazioni.utils.PtMovimentazioniImporter;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;


public class PopolaStalli extends VelocityCommand {

	protected static String TEMPLATE = "consegne/popolastalli.vm" ;

	protected MovimentoQuadrelliAdapter quadAdp = null ;

	private Integer idConsegna = null ;
	private Consegna consegna = null ;
	private MovimentoAdapter registro = null;

	private StalloAdapter stalloAdp = null ;
	private StalloConsegnaAdapter stalloConsegnaAdp = null;
	private StalloConsegna scValoriUnitari = null;

	double sommaUmido = 0 ;
	double sommaSecco = 0 ;

	public PopolaStalli ( final GtServlet callerServlet) {
		super(callerServlet);
	}
	@Override
	public Template exec(final HttpServletRequest req, final HttpServletResponse resp, final Context ctx) throws Exception  {

		try {

			idConsegna = getIntParam("id", true);

			consegna = ConsegnaAdapter.get(idConsegna);

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

		double secco = 0 ;

		ArrayList<Stallo> stalli = getStalliFromQuadrelli();

		for ( final Stallo s : stalli ) {

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
				secco = consegna.calcolaSecco(carico.getUmido());
				carico.setSecco( secco );

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

					carico.setNumRegistro(primoCarico.getNumRegistro());
					carico.setDocumento(primoCarico.getDocumento());
					carico.setDocumentoPV(primoCarico.getDocumentoPV());
				}
				carico.setIdStallo(s.getId());

				carico.setUmido(s.getAttuale());
				secco = consegna.calcolaSecco(carico.getUmido());
				carico.setSecco( secco );

				if ( carico.getId() != null ) {
					registro.update(carico);
				} else {
					Long id = Long.valueOf( registro.create(carico).toString() );
					carico.setId( new Integer( id.intValue()  )  );
				}
				
				carichi.put(s.getId(), carico);
			}

			s.setIdConsegnaAttuale(consegna.getId());

			sommaUmido += s.getAttuale();
			sommaSecco += secco;

			stalloAdp.update(s);

		}

		double umidoRestante = consegna.getPesopolizza().doubleValue() - sommaUmido ;

		if ( umidoRestante != 0 && carico != null ) {
			double caricato = carico.getUmido();
			double nuovoCarico = caricato + umidoRestante ;

			carico.setUmido(nuovoCarico);
			carico.setSecco( consegna.calcolaSecco(carico.getUmido()) );

			registro.update(carico);
		}

		// Sistemiamo gli StalliConsegna
		stalloConsegnaAdp = StalloConsegna.newAdapter();

		scValoriUnitari = getStalloConsegnaValoriUnitari(consegna.calcolaSecco(consegna.getPesopolizza()));

		if ( scValoriUnitari != null ) {
			StalloConsegna stalloConsegna = null;

			for ( final Stallo s : stalli ) {

				stalloConsegna = getStalloConsegna(s);

				stalloConsegna.setIdStallo(s.getId());

				carico = carichi.get(s.getId());

				if ( carico != null ) {
					stalloConsegna.initValori(carico.getSecco());
				}
				// se non ho il carico non inizializzo i valori ... non mi serve a nulla no?
				//				else {
				//					stalloConsegna.initValori(seccoRestante);
				//				}

				stalloConsegnaAdp.update(stalloConsegna);
			}
		}

		response.sendRedirect(".consegne?id=" + consegna.getId() );
	}

	private StalloConsegna getStalloConsegnaValoriUnitari( double sommaSecco ) throws Exception {
		StalloConsegna sc = stalloConsegnaAdp.getByKeysIds( StalloConsegnaAdapter.ID_STALLO_APERTURA, idConsegna);

		if ( sc == null ) {
			// Se non ho quello ancora non assegnato posso usare uno qualunque per i valori iniziali
			sc = stalloConsegnaAdp.getFirstByIdConsegna(idConsegna);
		}

		if ( sc != null ) {
			sc.setValoreUnitarioDollari(sc.getValoreDollari().doubleValue() / sommaSecco);
			sc.setValoreUnitarioTesTp(sc.getValoreTesTp().doubleValue() / sommaSecco);
			sc.setValoreUnitarioEuro(sc.getValoreUnitarioDollari().doubleValue() / sc.getTassoEuroDollaro().doubleValue());
		}

		return sc;
	}

	private StalloConsegna getStalloConsegna(Stallo s) throws Exception {

 		StalloConsegna stalloConsegna = stalloConsegnaAdp.getByKeysIds( s.getId(), idConsegna);

		if ( stalloConsegna == null ) {
			stalloConsegna = stalloConsegnaAdp.getByKeysIds(StalloConsegnaAdapter.ID_STALLO_APERTURA, idConsegna);
			if ( stalloConsegna != null )
				stalloConsegna.setIdStallo(s.getId());
		}

		if ( stalloConsegna == null ) {
			if ( scValoriUnitari == null )
				//		mancano tutti i dati per poterlo creare
				throw new Exception("Attenzione: Situazione non prevista: StalloConsegna non presente.");

			stalloConsegna = scValoriUnitari.clone();
			stalloConsegna.setIdStallo(s.getId());

			Object _id =   stalloConsegnaAdp.create(stalloConsegna);
			stalloConsegna.setId(Integer.valueOf("" +_id));
		}

		stalloConsegna.setValoreUnitarioDollari(scValoriUnitari.getValoreUnitarioDollari());
		stalloConsegna.setValoreUnitarioEuro(scValoriUnitari.getValoreUnitarioEuro());
		stalloConsegna.setValoreUnitarioTesTp(scValoriUnitari.getValoreUnitarioTesTp());

		return stalloConsegna;
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
					ConsegnaAdapter.assegnaStalloAConsegna(consegna, s);
				}

				listMov = (Vector<MovimentoQuadrelli>) quadAdp.get(false, null , s);

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


}