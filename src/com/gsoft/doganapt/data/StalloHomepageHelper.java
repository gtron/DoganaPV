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

	THashMap cacheStalli ;
	THashMap cacheLastMovimento ;

	MovimentoQuadrelliAdapter qAdp;
	MovimentoDoganaleAdapter registroDoganale;
	MovimentoIvaAdapter registroIVA;

	public StalloHomepageHelper(Vector<Stallo> stalli, MovimentoQuadrelliAdapter qAdp, MovimentoDoganaleAdapter registroDoganale, MovimentoIvaAdapter registroIVA) {
		this.qAdp = qAdp;
		this.registroDoganale = registroDoganale;
		this.registroIVA = registroIVA;

		initCacheFromStalli(stalli);

	}

	private void initCacheFromStalli(Vector<Stallo> stalli) {

		ArrayList<Stallo> stalliAttivi = getStalliAttivi(stalli);


		FormattedDate fromData = new FormattedDate();
		fromData.setTime(fromData.getTime() - ( 45L * 24 * 3600 * 1000 ) );

		try {
			ArrayList<MovimentoQuadrelli> movimentiDaImportare = qAdp.getScarichiDaImportare(fromData, stalliAttivi);

			if ( movimentiDaImportare != null ) {
				cacheStalli = new THashMap(stalliAttivi.size());
				cacheLastMovimento = new THashMap(stalliAttivi.size());

				//			Login.debug("fromData:" + fromData + " movimentiDaImportare.size: " + movimentiDaImportare.size());

				Stallo s;
				Movimento m = null;


				for ( MovimentoQuadrelli q : movimentiDaImportare ) {
					s = StalloAdapter.getByCodice(q.getCodiceFornitore(), false);
					m = null;

					try {

						if ( s.getConsegna().getNumero().equals( Integer.valueOf( q.getConsegna()))) {

							Movimento lastIva = registroIVA.getLast(s.getConsegna(), s, true, true );
							Movimento lastDog = registroDoganale.getLast(s.getConsegna(), s, true, true );
							
//							Login.debug("Stallo: "+s.getNome() + " Pend:"+q.getData() + 
//									(lastIva != null ? " Iva:"+lastIva.getData() : "" ) + 
//									(lastDog != null ? " Dog:"+lastDog.getData() : "" ));
							
							if ( lastIva != null ) {
								
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

							if ( m != null && m.getData() != null && 
									q.getData().ymdString().compareTo( m.getData().ymdString() ) > 0) {
								cacheStalli.put(s, q);
								cacheLastMovimento.put(s, m);
							}
						}
					} catch (Exception ex) {
						StringBuilder trace = new StringBuilder(5);
						int count=0;
						for ( StackTraceElement t : ex.getStackTrace() ) {
							trace.append("\n"+t.toString());
							if( ++count > 15 ) {
								break;
							}
						}
						Login.debug("Error StalloHomepage:" + ex.getMessage() + trace);
					}
				}
			}

		} catch (Exception e) {
			StringBuilder trace = new StringBuilder(5);
			for ( StackTraceElement t : e.getStackTrace() ) {
				trace.append("\n"+t.toString());
			}
			Login.debug("Error StalloHomepage:" + e.getMessage() + trace);
		}

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

	public boolean hasScarichiPendenti(Stallo s ) {
		return cacheStalli!= null && cacheStalli.containsKey(s);
	}
	public Object getScaricoPendente(Stallo s ) {
		if ( cacheStalli == null )
			return null;
		return cacheStalli.get(s);
	}
	public Object getLastMovimento(Stallo s ) {
		if ( cacheLastMovimento == null )
			return null;
		return  cacheLastMovimento.get(s);
	}

}