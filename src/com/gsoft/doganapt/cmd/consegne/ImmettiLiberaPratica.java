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
import com.gsoft.doganapt.data.StalloConsegna;
import com.gsoft.doganapt.data.adapters.ConsegnaAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoDoganaleAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoIvaAdapter;
import com.gsoft.doganapt.data.adapters.StalloAdapter;
import com.gsoft.doganapt.data.adapters.StalloConsegnaAdapter;
import com.gsoft.pt_movimentazioni.utils.IterImporter;
import com.gtsoft.utils.common.FormattedDate;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.exception.ParameterException;
import com.gtsoft.utils.http.servlet.GtServlet;


public class ImmettiLiberaPratica extends VelocityCommand {

	public ImmettiLiberaPratica ( GtServlet callerServlet) {
		super(callerServlet);
	}

	FormattedDate dataILP = null;
	ArrayList<Integer> stalli = null;
	StalloAdapter stAdp = null;
	Consegna consegna = null;
	Integer idConsegna = null;
	MovimentoAdapter registroOut = null;
	MovimentoIvaAdapter registroIva = null;
	Documento documento = null;
	Documento documentoPV = null;

	Double valoreDollari = null;
	Double tassoCambio = null;
	Double sommaSeccoTotale = new Double(0);
	Double valoreTestp = null;
	StalloConsegna stalloConsegnaValoriUnitari = null ;

	@Override
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {

		idConsegna = getIntParam("c", true);
		consegna = ConsegnaAdapter.get(idConsegna);

		stalli = getIntParams("list", 1);
		dataILP = getDateParam("dataLP", true);

		valoreDollari = getDoubleParam("valoreDollari", true);
		valoreTestp = getDoubleParam("valoreTestp", true);
		tassoCambio = getDoubleParam("tasso", true);

		documento = getDocumento();
		documentoPV = getDocumentoPV();

		//		ctx.put( ContextKeys.OBJECT , c ) ;
		if ( consegna != null && getBooleanParam(Strings.EXEC) ) {
			doImmettiLiberaPratica();
		}

		response.sendRedirect(".consegne?id=" + idConsegna);
		response.flushBuffer();

		return null ;
	}

	protected void doImmettiLiberaPratica() throws Exception {

		Stallo s = null ;
		stAdp = new StalloAdapter();

		registroIva = new MovimentoIvaAdapter();

		ArrayList<Movimento> movimentiUscita = getMovimentiUscita();

		stalloConsegnaValoriUnitari = StalloConsegna.getNew(new FormattedDate());
		stalloConsegnaValoriUnitari.setValoreDollari(valoreDollari);
		stalloConsegnaValoriUnitari.setValoreEuro(
				stalloConsegnaValoriUnitari.getValoreArrotondatoEuro(valoreDollari/tassoCambio));

		stalloConsegnaValoriUnitari.setValoreTesTp(valoreTestp);
		stalloConsegnaValoriUnitari.setTassoEuroDollaro(tassoCambio);

		stalloConsegnaValoriUnitari.initValoriUnitari(sommaSeccoTotale);

		for ( Movimento m : movimentiUscita ) {

			StalloConsegnaAdapter stalloConsegnaAdapter = new StalloConsegnaAdapter();
			StalloConsegna stalloConsegna = stalloConsegnaAdapter.getByKeysIds(m.getIdStallo(), idConsegna);

			if ( stalloConsegna == null ) {
				// Non è ancora stato creato ... non dovrebbe succedere, cmq lo creiamo ora
				stalloConsegna = StalloConsegna.getNew(m.getData());
				stalloConsegna.setIdStallo(m.getIdStallo());
				stalloConsegna.setIdConsegna(idConsegna);
				stalloConsegna.setIsInLiberaPratica(Boolean.FALSE);
				Long id = (Long) stalloConsegnaAdapter.create(stalloConsegna);

				stalloConsegna.setId(id.intValue());
			}
			initStalloConsegna(stalloConsegna, m);

			MovimentoIVA miva = getMovimentoIva(m);

			stalloConsegna.assegnaValori(miva);

			s = m.getStallo();

			registroOut.create(m);
			registroIva.create(miva);
			stalloConsegnaAdapter.update(stalloConsegna);

			s.setImmessoInLiberaPratica(Boolean.TRUE);
			s.setIdConsegnaAttuale(idConsegna);

			stAdp.update(s);

		}

	}


	/**
	 * Aggiorna i dati dello StalloConsegna con i nuovi valori del movimento di ILP
	 * @param m
	 * @param stalloConsegna
	 */
	protected void initStalloConsegna(StalloConsegna stalloConsegna, Movimento m) {
		stalloConsegna.setDataImmissione(m.getData());
		stalloConsegna.setIsInLiberaPratica(true);
		stalloConsegna.setValoreUnitarioDollari( stalloConsegnaValoriUnitari.getValoreUnitarioDollari() );
		stalloConsegna.setValoreUnitarioEuro( stalloConsegnaValoriUnitari.getValoreUnitarioEuro() );
		stalloConsegna.setValoreUnitarioTesTp( stalloConsegnaValoriUnitari.getValoreUnitarioTesTp() );

		stalloConsegna.setTassoEuroDollaro( tassoCambio );

		stalloConsegna.initValori(m.getSecco());
	}

	/**
	 * @return Lista dei movimenti di uscita, uno per stallo di cui si sta facendo la ILP
	 * @throws Exception
	 */
	protected ArrayList<Movimento> getMovimentiUscita() throws Exception {
		Stallo s;
		Movimento m;
		Integer idStallo;
		ArrayList<Movimento> movimentiUscita = new ArrayList<Movimento>(stalli.size());

		for (Object _id : stalli) {
			idStallo = (Integer) _id ;
			s = StalloAdapter.get(idStallo) ;

			if (! s.getHasLiberaPratica() ) {
				m = getMovimentoUscita(s);
				movimentiUscita.add( m );

				sommaSeccoTotale += m.getSecco();
			}
		}
		return movimentiUscita;
	}

	private Movimento getMovimentoUscita(Stallo s) throws Exception {

		IterImporter iterImporter = consegna.getIter()
				.getImporter(
						new MovimentoDoganaleAdapter(),
						registroIva,
						null);

		registroOut = iterImporter.getRegistroOutPerILP() ;

		@SuppressWarnings("unchecked")
		Vector<Movimento> list = registroOut.getByConsegna(
				false, idConsegna, s.getId(), null , null );

		double sommaUmido = 0 ;
		double sommaSecco = 0 ;
		Movimento m = null;

		if ( list != null && list.size() > 0 ) {
			for ( Iterator<Movimento> i = list.iterator() ; i.hasNext() ; ) {
				m = i.next();

				if ( m.getIsScarico() ) {
					sommaUmido -= m.getUmido().doubleValue() ;
					sommaSecco -= m.getSecco().doubleValue() ;
				}
				else {
					sommaUmido += m.getUmido().doubleValue() ;
					sommaSecco += m.getSecco().doubleValue() ;
				}
			}
			initMovimentoUscita(m);

			m.setSecco(sommaSecco);
			m.setUmido(sommaUmido);
		}
		return m ;
	}

	private void initMovimentoUscita( Movimento m ) {
		m.setDocumento(documento);
		m.setDocumentoPV(documento);
		m.setIsScarico(true);
		m.setNumRegistro(null);
		m.setIsRettifica(false);
		m.setData( dataILP ) ;
		m.setId(null);
		m.setNote(MovimentoDoganaleAdapter.NOTE_SCARICO);
		m.setCodProvenienza(MovimentoAdapter.COD_PROVENIENZA_PORTOVESME);

		if ( consegna.getIter().getRegdoganale()) {
			m.setCodPosizioneDoganale(MovimentoAdapter.COD_POSIZIONEDOGANALE_ENAZ);
		} else {
			m.setCodPosizioneDoganale(MovimentoAdapter.COD_POSIZIONEDOGANALE_NAZ);
		}
	}

	private MovimentoIVA getMovimentoIva( Movimento m) {
		MovimentoIVA miva = (MovimentoIVA) registroIva.newMovimento();

		miva.setDocumento(m.getDocumento());
		miva.setDocumentoPV(m.getDocumentoPV());
		miva.setIsScarico(false);
		miva.setSecco(m.getSecco());
		miva.setUmido(m.getUmido());
		miva.setIdMerce(m.getIdMerce());
		miva.setNumRegistro(null);
		miva.setIdStallo(m.getIdStallo());
		miva.setIsRettifica(false);
		miva.setIdConsegna(m.getIdConsegna());
		miva.setData( m.getData() ) ;
		miva.setId(null);
		miva.setIsLocked(Boolean.FALSE);

		miva.setNote(MovimentoAdapter.NOTE_CARICO);
		miva.setCodProvenienza(MovimentoAdapter.COD_PROVENIENZA_MAGDOG);

		if ( consegna.getIter().getRegdoganale()) {
			miva.setCodPosizioneDoganale(MovimentoAdapter.COD_POSIZIONEDOGANALE_ENAZ);
		} else {
			miva.setCodPosizioneDoganale(MovimentoAdapter.COD_POSIZIONEDOGANALE_NAZ);
		}

		return miva;
	}

	private Documento getDocumento() throws ParameterException {
		String doc = getParam("doc", false);
		String doc_num = getParam("doc_num", false);
		FormattedDate doc_data = getDateParam("doc_data", false);

		return Documento.getDocumento( doc, doc_data , doc_num );
	}
	private Documento getDocumentoPV() throws ParameterException {
		String docPV = getParam("docIVA", false);
		String docPV_num = getParam("docIVA_num", false);
		FormattedDate docPV_data = getDateParam("docIVA_data", false);

		return Documento.getDocumento( docPV, docPV_data , docPV_num );
	}

	@Override
	public VelocityCommand clone() {
		return  new ImmettiLiberaPratica(callerServlet);
	}

	@Override
	public String getTemplateName() {
		return ViewConsegna.TEMPLATE ;
	}
}