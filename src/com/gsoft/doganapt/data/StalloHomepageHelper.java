package com.gsoft.doganapt.data;

import java.util.ArrayList;
import java.util.Vector;

import com.gsoft.doganapt.cmd.Login;
import com.gsoft.doganapt.data.adapters.MovimentoDoganaleAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoIvaAdapter;
import com.gsoft.doganapt.data.adapters.StalloAdapter;
import com.gsoft.pt_movimentazioni.data.MovimentoQuadrelli;
import com.gsoft.pt_movimentazioni.data.MovimentoQuadrelliAdapter;
import com.gtsoft.utils.common.FormattedDate;

import gnu.trove.THashMap;

public class StalloHomepageHelper {

	FormattedDate minData ;
	FormattedDate maxValido;

	THashMap cacheStalliDaImportare ;
	THashMap cacheStalliDaRegistrare ;
	THashMap cacheLastMovimento ;

	MovimentoQuadrelliAdapter qAdp;
	MovimentoDoganaleAdapter registroDoganale;
	MovimentoIvaAdapter registroIVA;

	public StalloHomepageHelper(Vector<Stallo> stalli, MovimentoQuadrelliAdapter qAdp, MovimentoDoganaleAdapter registroDoganale, MovimentoIvaAdapter registroIVA) {
		this.qAdp = qAdp;
		this.registroDoganale = registroDoganale;
		this.registroIVA = registroIVA;

		ArrayList<Stallo> stalliAttivi = getStalliAttivi(stalli);
		
		cacheStalliDaImportare = new THashMap(stalliAttivi.size());
		cacheStalliDaRegistrare = new THashMap(stalliAttivi.size());
		cacheLastMovimento = new THashMap(stalliAttivi.size());
		
		ArrayList<Stallo> stalliAttiviRestanti = initCacheFromStalliDaRegistrare(stalliAttivi);
		
		initCacheFromStalliDaImportare(stalliAttiviRestanti);

	}
	
	private ArrayList<Stallo> initCacheFromStalliDaRegistrare(ArrayList<Stallo> stalli) {
		ArrayList<Stallo> stalliAttiviRestanti = new ArrayList<Stallo>(3);

		for ( Stallo s : stalli ) {
			try {
				
				Movimento m = null;

				Vector<Movimento> list = registroDoganale.getDaRegistrareByIdStallo(s.getId());
				if ( list == null || list.size() < 1 ) {
					list = registroIVA.getDaRegistrareByIdStallo(s.getId());
				}
				
				if ( list != null && list.size() > 0 ) {
					m = findLastMovimentoRegistrato(s);
					
					cacheStalliDaRegistrare.put(s, list.get(list.size() - 1));
					cacheLastMovimento.put(s, m);
				} else {
					stalliAttiviRestanti.add(s);
				}
			} catch (Exception e) {
				Login.debug(e, this.getClass().getName());
			}
		}
		
		return stalliAttiviRestanti;
	}

	private void initCacheFromStalliDaImportare(ArrayList<Stallo> stalli) {

		FormattedDate fromData = new FormattedDate();
		fromData.setTime(fromData.getTime() - ( 45L * 24 * 3600 * 1000 ) );

		try {
			ArrayList<MovimentoQuadrelli> movimentiDaImportare = qAdp.getScarichiDaImportare(fromData, stalli);

			if ( movimentiDaImportare != null ) {
//				Login.debug("fromData:" + fromData + " movimentiDaImportare.size: " + movimentiDaImportare.size());

				Stallo s = null;
				Movimento m = null;

				for ( MovimentoQuadrelli q : movimentiDaImportare ) {
					s = StalloAdapter.getByCodice(q.getCodiceFornitore(), false);
					m = null;

//					Login.debug("---\nConsegna da importare: " + q.getConsegna() + " Data:" + q.getData().ymdString() );
					try {
						if ( s.getConsegna().getNumero().equals( Integer.valueOf( q.getConsegna()))) {

							m = findLastMovimentoRegistrato(s);
							
//							if ( m != null ) 
//								Login.debug("Ultimo Movimento registrato: " + m.getId() + " Data:" + m.getData().ymdString());
//							else
//								Login.debug("NON C'E' Ultimo Movimento registrato");
							
							if ( m == null ||
									m.getData() != null && 
									q.getData().ymdString().compareTo( m.getData().ymdString() ) > 0) {
								cacheStalliDaImportare.put(s, q);
								cacheLastMovimento.put(s, m);
							}
						}
					} catch (Exception ex) {
						Login.debug(ex, this.getClass().getName());
					}
				}
			}
		} catch (Exception e) {
			Login.debug(e, this.getClass().getName());
		}
	}

	private Movimento findLastMovimentoRegistrato(Stallo s) throws Exception {
		
		Movimento m = null;
		
		Movimento lastIva = registroIVA.getLast(s.getConsegna(), s, true, true );
		Movimento lastDog = registroDoganale.getLast(s.getConsegna(), s, true, true );
		
//		Login.debug("Stallo: "+s.getNome() + //  " Pend:"+q.getData() + 
//				(lastIva != null ? " Iva:"+lastIva.getData() : "" ) + 
//				(lastDog != null ? " Dog:"+lastDog.getData() : "" ));
		
		if ( lastIva != null ) {
			m = lastIva;
			
			if ( lastDog != null ) {
				if ( lastIva.getData() != null && 
						lastIva.getData().ymdString().compareTo( lastDog.getData().ymdString() ) > 0
						) {
					m = lastIva ;
				} else {
					m = lastDog;
				}
			} 
		} else { // else if ( lastDog != null ) 
			m = lastDog;
		}
		return m;
	}

	private ArrayList<Stallo> getStalliAttivi(Vector<Stallo> stalli) {
		ArrayList<Stallo> stalliAttivi = new ArrayList<Stallo>();

		for( Stallo s : stalli ) {
			if ( s.getIdConsegnaAttuale() != null ) {
				stalliAttivi.add(s);
			}
		}
		return stalliAttivi;
	}

	public boolean hasScarichiPendentiImportazione(Stallo s ) {
		return cacheStalliDaImportare!= null && cacheStalliDaImportare.containsKey(s);
	}
	
	public boolean hasScarichiPendentiRegistrazione(Stallo s ) {
		return cacheStalliDaRegistrare!= null && cacheStalliDaRegistrare.containsKey(s);
	}
	
	public boolean hasScarichiPendenti(Stallo s ) {
		return hasScarichiPendentiImportazione(s) || hasScarichiPendentiRegistrazione(s) ;
	}
	public Movimento getScaricoPendenteRegistrazione(Stallo s ) {
		if ( cacheStalliDaRegistrare == null )
			return null;
		return (Movimento) cacheStalliDaRegistrare.get(s);
	}
	public Movimento getScaricoPendenteImportazione(Stallo s ) {
		if ( cacheStalliDaImportare == null )
			return null;
		return (Movimento) cacheStalliDaImportare.get(s);
	}
	public Object getLastMovimento(Stallo s ) {
		if ( cacheLastMovimento == null )
			return null;
		return  cacheLastMovimento.get(s);
	}

}