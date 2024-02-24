package com.gsoft.pt_movimentazioni.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.gsoft.doganapt.data.Consegna;
import com.gsoft.doganapt.data.Documento;
import com.gsoft.doganapt.data.Movimento;
import com.gsoft.doganapt.data.MovimentoDoganale;
import com.gsoft.doganapt.data.Stallo;
import com.gsoft.doganapt.data.adapters.MovimentoAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoDoganaleAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoIvaAdapter;
import com.gsoft.pt_movimentazioni.data.MovimentoQuadrelli;
import com.gsoft.pt_movimentazioni.data.MovimentoQuadrelliAdapter;
import com.gtsoft.utils.common.FormattedDate;

public class IterImporter_EXT extends IterImporter {

	public IterImporter_EXT( MovimentoDoganaleAdapter m, MovimentoIvaAdapter i, MovimentoQuadrelliAdapter q ) {
		super(m,i,q);
	}

	@Override
	public Movimento apriConsegna( Consegna c, FormattedDate d , Documento documento, Documento documentoPV, String note  ) throws Exception  {

		c.setDataChiusura(null);

		MovimentoDoganale m = new MovimentoDoganale();

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

		m.setCodPosizioneDoganale(MovimentoAdapter.COD_POSIZIONEDOGANALE_EXT);
		m.setCodProvenienza(MovimentoAdapter.COD_PROVENIENZA_PORTO);

		if ( note == null || note.trim().length() < 1 ) {
			note = MovimentoDoganaleAdapter.NOTE_CARICO;
		}


		m.setNote(note);

		registroDoganale.create(m);

		return m;

	}

	@Override
	public MovimentoAdapter getRegistroInPerILP() {
		return registroIVA;
	}

	@Override
	public MovimentoAdapter getRegistroOutPerILP() {
		return registroDoganale;
	}

	@Override
	public MovimentoAdapter getRegistroPrimoCarico() {
		return registroDoganale ;
	}

	public void importaPrimoCarico( Consegna c , FormattedDate d ) throws Exception {
		Stallo s ;
		HashMap<Integer, MovimentoQuadrelli> listStalli = new HashMap<Integer, MovimentoQuadrelli>(5) ;
		Movimento m ;
		MovimentoQuadrelli mq ;
		//		Movimento rettifica = null ;
		double sommaPesoUmido = 0 ;
		double scartoUmido = 0 ;

		//		FormattedDate data = quadAdp.getMinDataCarico(c) ;
		//		FormattedDate lastData = data ;

		Vector<?> list = null ;

		// fetch dei movimenti
		for (Object element : c.getStalli()) {

			s = (Stallo) element ;

			list = quadAdp.get(false, null , s);

			if ( list != null && list.size() > 0 ) {
				// ci dovrebbe essere un solo movimento per stallo
				mq = (MovimentoQuadrelli) list.firstElement();

				listStalli.put( s.getId(), mq );

				sommaPesoUmido += mq.getNetto().doubleValue() ;

				//				if ( lastData.before(mq.getData()) ) {
				//				lastData = mq.getData() ;
				//				}
			}
		}

		boolean primoCarico = true ;
		// per ogni stallo creo il movimento di carico
		for ( Iterator<MovimentoQuadrelli> is = listStalli.values().iterator() ; is.hasNext() ; ) {

			mq = is.next();

			m = getMovimento(false, mq , c, registroDoganale ) ;
			m.setData(d);

			if ( primoCarico ) {
				primoCarico = false ;

				scartoUmido = c.getPesopolizza().doubleValue() - sommaPesoUmido ;

				m.setUmido( m.getUmido().doubleValue() + scartoUmido ) ;
				m.setSecco( c.calcolaSecco(m.getUmido()) );



				/*
				 * Non faccio + la rettifica, è una fase successiva

				// se non è un pesofinaleportodicarico devo fare il movimento di rettifica
				if ( ! c.isPesoFinalePortoCarico() ) {

					rettifica = getMovimento(false, mq, c, registroDoganale);
					rettifica.setIsRettifica(true);
					rettifica.setData(lastData) ;

					if ( scartoUmido > 0 ) {
						rettifica.setIsScarico( true ) ;
						rettifica.setUmido( Double.valueOf(scartoUmido) ) ;
					}
					else {
						rettifica.setIsScarico( false ) ;
						rettifica.setUmido( Double.valueOf( -1 * scartoUmido) ) ;
					}
					rettifica.setSecco( c.calcolaSecco(rettifica.getUmido()) );

					registroDoganale.create(rettifica);

				}

			}
			else {
				registroDoganale.create(m);

				 */
			}

			registroDoganale.create(m);
		}
	}

	/**
	 * Restituisce la data dell'ultima importazione, se non ho ancora importato
	 * utilizzo la data di creazione al quale tolgo un giorno per fare in modo
	 * che venga importata anche quel giorno
	 */
	@Override
	protected FormattedDate getLastData( final Consegna c ,  final ArrayList<Integer> idStalli, final boolean onlyScarichi ) throws Exception {
		FormattedDate lastData = registroIVA.getLastDate(c, idStalli, onlyScarichi);

		// per le Extracomunitarie non bisogna guardare il registro doganale
		// altrimenti uno scarico IM4 potrebbe impedire di importare gli scarichi
		// fatti lo stesso giorno

		/*
		final FormattedDate lastData2 = registroDoganale.getLastDate(c, idStalli, onlyScarichi);

		if ( lastData == null ||
				( lastData2 != null && lastData2.after( lastData ) ) ) {
			lastData = lastData2 ;
		}
		 */

		return lastData;
	}


	//	private Movimento getMovimentoRettificaCarico(Consegna c , Stallo s , double pesoCaricoEffettivo , FormattedDate data ) throws Exception {
	//		Movimento m = registroDoganale.newMovimento() ;
	//
	//		double pesoConsegna = c.getPesopolizza() ;
	//
	//		double pesoRettifica = pesoConsegna - pesoCaricoEffettivo ;
	//
	//		if ( pesoRettifica > 0 ) {
	//			m.setIsScarico(false);
	//		}
	//		else {
	//			m.setIsScarico(true);
	//			pesoRettifica *= -1 ;
	//		}
	//
	//		m.setUmido( pesoRettifica );
	//
	//		m.setIsLocked(false);
	//		m.setIsRettifica( true );
	//
	//		m.setData(data) ;
	//		m.setIdMerce( Integer.valueOf(c.getIdmerce()));
	//		m.setIdConsegna(c.getId());
	//		m.setStallo( s );
	//
	//		return m ;
	//	}

}
