package com.gtsoft.utils;

import java.util.HashMap;

import com.gtsoft.utils.common.ConfigManager;
import com.gtsoft.utils.common.FormattedDate;

public class ManagerAliquoteIva {

	private static final String CONFIG_ALIQUOTE_FALLBACK = "0.21:2013-10-01,0.22";

	private static final int PRECISIONE_ALIQUOTA_IVA = 100 ; // 0,21 (2 decimali)
	private static final String CONFIG_KEY_ALIQUOTA_IVA = "aliquoteIva";

	private HashMap<String, Double> configurazioneAliquote = null;

	private String lastLimit = "9999";
	private String aliquotaCorrente = "";

	protected double calcolaAliquota(String giorno) {
		String result = null;
		if ( giorno.compareTo( lastLimit ) >= 0 ) {
			result = aliquotaCorrente;
		} else {
			String limiteMax = "9999";
			for ( String limite : configurazioneAliquote.keySet() ) {
				if ( giorno.compareTo(limite) < 0 ) {
					if ( limiteMax == null || limite.compareTo(limiteMax) < 0 ) {
						result = configurazioneAliquote.get(limite).toString();
						limiteMax = limite ;
					}
				}
			}
		}
		return Double.valueOf(result).doubleValue();
	}

	protected void initAliquote(String config) {

		String[] elementi = config.split(",");
		if ( elementi != null && elementi.length > 0 ) {
			configurazioneAliquote  = new HashMap<String, Double>(elementi.length);
			lastLimit = "0";

			for ( String e : elementi ) {
				String[] val = e.split(":");
				if ( val != null && val.length > 0 ) {
					if ( val.length > 1 ) {
						if ( lastLimit.compareTo(val[1]) < 0 ) {
							lastLimit = val[1];
						}
						configurazioneAliquote.put(val[1], Double.valueOf(val[0]));
					} else {
						aliquotaCorrente = val[0];
						configurazioneAliquote.put("0", Double.valueOf(aliquotaCorrente));
					}
				}
			}
		} else {
			configurazioneAliquote  = new HashMap<String, Double>(1);
		}
	}

	public String getLastLimit() {
		return lastLimit;
	}
	public String getAliquotaCorrente() {
		return aliquotaCorrente;
	}

	private static ManagerAliquoteIva manager = null;
	private static HashMap<String, Double> cache = null;

	protected static ManagerAliquoteIva getInstance() {

		if ( manager == null ) {
			manager = new ManagerAliquoteIva();

			String config = ConfigManager.getProperty(CONFIG_KEY_ALIQUOTA_IVA);
			if ( config == null ) {
				config = CONFIG_ALIQUOTE_FALLBACK;
			}
			manager.initAliquote(config);

			cache = new HashMap<String, Double>(manager.configurazioneAliquote.size());
		}
		return manager;

	}

	public static Double getAliquotaIvaPerGiorno(FormattedDate giorno) {

		ManagerAliquoteIva m = getInstance();

		String key = giorno.ymdString();

		if ( ! cache.containsKey(key) ){
			if ( cache.size() > 30 ) {
				cache.clear();
			}

			Double aliquota = m.calcolaAliquota(giorno.ymdString());

			long tmp = Math.round(
					aliquota.doubleValue() * PRECISIONE_ALIQUOTA_IVA );

			cache.put(key, Double.valueOf( 1d * tmp / PRECISIONE_ALIQUOTA_IVA ));
		}

		return cache.get(key);

	}

	public static void clearCache() {
		if ( cache != null) {
			cache.clear();
		}
	}
}
