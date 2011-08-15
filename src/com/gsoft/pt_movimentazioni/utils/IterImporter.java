package com.gsoft.pt_movimentazioni.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import com.gsoft.doganapt.data.Consegna;
import com.gsoft.doganapt.data.Documento;
import com.gsoft.doganapt.data.Movimento;
import com.gsoft.doganapt.data.MovimentoIVA;
import com.gsoft.doganapt.data.Stallo;
import com.gsoft.doganapt.data.adapters.MovimentoAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoDoganaleAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoIvaAdapter;
import com.gsoft.doganapt.data.adapters.StalloAdapter;
import com.gsoft.pt_movimentazioni.data.MovimentoQuadrelli;
import com.gsoft.pt_movimentazioni.data.MovimentoQuadrelliAdapter;
import com.gtsoft.utils.common.FormattedDate;

public abstract class IterImporter {


	MovimentoIvaAdapter registroIVA = null ;
	MovimentoDoganaleAdapter registroDoganale = null ;
	MovimentoQuadrelliAdapter quadAdp = null ;

	public IterImporter( final MovimentoDoganaleAdapter m, final MovimentoIvaAdapter i, final MovimentoQuadrelliAdapter q ) {
		registroIVA = i ;
		registroDoganale = m ;
		quadAdp = q ;
	}

	public void apriConsegna( final Consegna c, final FormattedDate d , final Documento documento, final Documento documentoPV, final String note ) throws Exception  {

		final MovimentoIVA m = new MovimentoIVA();

		m.setIdConsegna(c.getId());
		m.setData(d);
		m.setUmido(c.getPesopolizza());
		m.setSecco(c.calcolaSecco(m.getUmido()));

		m.setDocumento(documento);
		m.setDocumentoPV(documentoPV);
		m.setIdMerce(c.getIdmerce());
		m.setIsLocked(Boolean.FALSE);
		m.setIsScarico(Boolean.FALSE);
		m.setIsRettifica(Boolean.FALSE);

		m.setNote(note);

		if ( c.getValoreUnitario() != null ) {
			if ( c.getIsValutaEuro() ) {
				m.setValoreEuro(c.getValoreUnitario().doubleValue() * m.getSecco().doubleValue() ) ;
			}
			else {
				m.setValoreDollari(c.getValoreUnitario().doubleValue() * m.getSecco().doubleValue() ) ;
				c.updateValore(m) ;
			}
		}

		registroIVA.create(m);

	}

	/* UNUSED
	public void apriConsegna_old( Consegna c, FormattedDate d ) throws Exception  {

		Integer id = c.getId() ;

		c.setDataChiusura(null);

		HashMap<Integer,Stallo> stalliCoinvolti = new HashMap<Integer,Stallo>(3);

		Collection stalli = StalloAdapter.getAllCached(true);

		Stallo s = null ;


		for ( Iterator i = stalli.iterator() ; i.hasNext() ; ){
			s = ( Stallo ) i.next() ;
			if (id.equals( s.getIdConsegnaPrenotata() ) ) {
				s.setIdConsegnaAttuale(id);
				s.setIdConsegnaPrenotata(null);
				s.setImmessoInLiberaPratica(Boolean.FALSE) ;

				s.setAttuale( new Double(0) );
				s.setCaricato( new Double(0) );

				stalliCoinvolti.put(s.getId(), s);
			}
		}

		if ( stalliCoinvolti.size() < 1 ) {
			System.out.println("Eccezione da implementare: Nessuno stallo associato alla consegna da aprire.");
			return ;
//			throw new Exception ( "STALLI") ;
		}

		importaPrimoCarico(c, d) ;

		StalloAdapter adp = Stallo.newAdapter() ;
		for ( Iterator<Stallo> i = stalliCoinvolti.values().iterator() ; i.hasNext() ; ){
			s = i.next() ;
			adp.update(s);
		}



		Consegna.newAdapter().update(c);
	}

	 */

	/* UNUSED
	public void importaPrimoCarico( Consegna c , FormattedDate d ) throws Exception {
		Stallo s ;
		HashMap<Integer, MovimentoQuadrelli> listStalli = new HashMap<Integer, MovimentoQuadrelli>(5) ;
		Movimento m ;
		MovimentoQuadrelli mq ;
		Movimento rettifica = null ;
		double sommaPesoUmido = 0 ;
		double scartoUmido = 0 ;

		FormattedDate data = quadAdp.getMinDataCarico(c) ;
		FormattedDate lastData = data ;

		Vector list = null ;

		// fetch dei movimenti
		for ( Iterator is = c.getStalli().iterator() ; is.hasNext() ; ) {

			s = (Stallo) is.next() ;

			list = quadAdp.get(false, null , s);

			if ( list != null && list.size() > 0 ) {
				// ci dovrebbe essere un solo movimento per stallo
				mq = (MovimentoQuadrelli) list.firstElement();

				listStalli.put( s.getId(), mq );

				sommaPesoUmido += mq.getNetto().doubleValue() ;

				if ( lastData.before(mq.getData()) ) {
					lastData = mq.getData() ;
				}
			}
		}

		boolean primoCarico = true ;
		// per ogni stallo creo il movimento di carico
		for ( Iterator<MovimentoQuadrelli> is = listStalli.values().iterator() ; is.hasNext() ; ) {

			mq = is.next() ;

			m = getMovimento(false, mq , c, registroIVA ) ;
			m.setData(data);

			if ( primoCarico ) {
				primoCarico = false ;

				scartoUmido = c.getPesopolizza().doubleValue() - sommaPesoUmido ;

				m.setUmido( m.getUmido().doubleValue() + scartoUmido ) ;
				m.setSecco( c.calcolaSecco(m.getUmido()) );

				registroIVA.create(m);

				/*
	 * Non faccio + la rettifica, è una fase successiva

				// se non è un pesofinaleportodicarico devo fare il movimento di rettifica
				if ( ! c.isPesoFinalePortoCarico() ) {

					rettifica = getMovimento(false, mq, c, registroDoganale);
					rettifica.setIsRettifica(true);
					rettifica.setData(lastData) ;

					if ( scartoUmido > 0 ) {
						rettifica.setIsScarico( true ) ;
						rettifica.setUmido( new Double(scartoUmido) ) ;
					}
					else {
						rettifica.setIsScarico( false ) ;
						rettifica.setUmido( new Double( -1 * scartoUmido) ) ;
					}
					rettifica.setSecco( c.calcolaSecco(rettifica.getUmido()) );

					registroDoganale.create(rettifica);

				}
	 * /
			}
			else {
				registroIVA.create(m);
			}
		}
	}
	 */

	public void immettiLiberaPratica( final ArrayList<Integer> stalli, final FormattedDate data,
			final Documento docDogana, final Documento docIVA) throws Exception  {
		// TODO Metodo Vuoto xke l'azione non si applica ( rivedere gerarchia oggetti )
	}

	public void importTo( final Consegna c , final FormattedDate to , final ArrayList<Integer> idStalli) throws Exception {
		final ArrayList<FormattedDate> date = quadAdp.getDateDaImportare(c, getLastData( c, idStalli ) ) ;
		FormattedDate giornoCorrente = null ;

		Stallo s ;

		if( date != null ) {
			for (final Object element : date) {

				giornoCorrente = (FormattedDate) element ;

				if ( to != null && to.before(giornoCorrente ) ) {
					break ;
				}

				ArrayList<Object> stalli ;
				if ( idStalli == null || idStalli.size() < 1 ) {
					stalli = c.getStalli() ;
				}
				else {
					stalli = new ArrayList<Object>(idStalli.size()) ;

					for ( final Integer ids : idStalli ) {
						stalli.add(StalloAdapter.get(ids));
					}

				}

				// E tutti gli stalli di ciascuna consegna ...
				for (final Object element2 : stalli) {

					s = (Stallo) element2 ;

					//					if ( idStallo == null || idStallo.equals( s.getId() ) )
					doImport(c, s, giornoCorrente) ;
				}
			}
		}
	}

	/**
	 * Diverso solo per le NOGLEN
	 * 
	 * @param c
	 * @param s
	 * @param giorno
	 * @throws Exception
	 */
	protected void doImport( final Consegna c, final Stallo s, final FormattedDate giorno ) throws Exception {

		innerImport( true, c, s, giorno , registroIVA ) ;

	}

	public MovimentoAdapter getRegistroPrimoCarico() {
		return registroIVA ;
	}

	//	protected abstract double importScarichi( Consegna c, Stallo s, FormattedDate giorno ) throws Exception;

	protected FormattedDate getLastData( final Consegna c , final ArrayList<Integer> idStalli )  throws Exception  {
		return getLastData(c, idStalli, true);
	}
	/**
	 * Restituisce la data dell'ultima importazione, se non ho ancora importato
	 * utilizzo la data di creazione al quale tolgo un giorno per fare in modo
	 * che venga importata anche quel giorno
	 */
	protected FormattedDate getLastData( final Consegna c ,  final ArrayList<Integer> idStalli, final boolean onlyScarichi ) throws Exception {
		FormattedDate lastData = registroIVA.getLastDate(c, idStalli, onlyScarichi);
		final FormattedDate lastData2 = registroDoganale.getLastDate(c, idStalli, onlyScarichi);

		if ( lastData == null ||
				( lastData2 != null && lastData2.after( lastData ) ) ) {
			lastData = lastData2 ;
		}

		return lastData;
	}

	/**
	 * Importa i movimenti di carico o di scarico nel giusto registro
	 * 
	 * @param isScarichi
	 * @param c
	 * @param s
	 * @param giorno
	 * @param adp
	 * @throws Exception
	 */
	protected void innerImport( final boolean isScarichi , final Consegna c, final Stallo s , final FormattedDate giorno , final MovimentoAdapter registro ) throws Exception {
		final Vector listCarichi = quadAdp.get(isScarichi, giorno, s ) ;

		Movimento m = null ;
		//		double sommaPesoUmido = 0 ;

		if ( listCarichi != null ) {
			for ( final Iterator i = listCarichi.iterator() ; i.hasNext();) {

				m = getMovimento(isScarichi, (MovimentoQuadrelli) i.next(), c , registro ) ;
				m.setSecco(c.calcolaSecco(m.getUmido()));
				m.setIdConsegna(c.getId());

				//				if ( forcedData != null )
				//					m.setData(forcedData);

				registro.create(m);

				m.getStallo().notifyMovimento(m, ! i.hasNext() );

				//				sommaPesoUmido += m.getUmido().doubleValue() ;
			}
		}
		//		return sommaPesoUmido ;
	}


	protected Movimento getMovimento(final boolean isScarico , final MovimentoQuadrelli q, final Consegna c, final MovimentoAdapter registro) {
		final Movimento m = registro.newMovimento() ;

		boolean isRettifica = false ;
		try {
			isRettifica = MovimentoQuadrelli.DESTINAZIONE_RETTIFICA_STRING.equals( q.getDestinazione().toString() );
		}
		catch ( final Exception  e ){
			isRettifica = false ;

			String nota = "" ;

			if ( q == null ) {
				nota = "Movimento nullo!" ;
			}
			else {
				if ( q.getDestinazione() == null ) {
					nota = "Destinazione nulla ! (" + isScarico  + ")"  ;
				}
			}
			m.setNote("Errore al stabilire se il movimento è una rettifica: \n" + nota );
		}

		m.setIsLocked(false);
		m.setIsScarico(isRettifica ? ( ! isScarico ) : isScarico );
		m.setIsRettifica(isRettifica);
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

		return m ;
	}
}

