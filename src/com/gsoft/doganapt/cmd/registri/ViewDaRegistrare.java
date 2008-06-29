package com.gsoft.doganapt.cmd.registri;

import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.data.Consegna;
import com.gsoft.doganapt.data.adapters.ConsegnaAdapter;
import com.gsoft.doganapt.data.adapters.MerceAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoDoganaleAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoIvaAdapter;
import com.gtsoft.utils.common.BeanAdapter2;
import com.gtsoft.utils.common.FormattedDate;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.exception.ParameterException;
import com.gtsoft.utils.http.servlet.GtServlet;


public class ViewDaRegistrare extends VelocityCommand {
	protected static String TEMPLATE = "registri/daregistrare.vm";
	protected static String FILTER_CONSEGNA = "idconsegna";
	protected static String TIPO_REGISTRO_IVA = "iva";
	protected static String JSON = "json";
	
	//Parametri di jgrid
	protected static String PAGE = "page"; //La pagina richiesta
	protected static String ROWS = "rows"; //Il numero delle righe che il server deve rendere
	protected static String SIDX = "sidx"; //Colonna da ordinare
	protected static String SORD = "sord"; //Ordinamento asc o desc
	
	//Parametri di filtro
	protected static String NUMERO = "inputNum";
	protected static String DAL = "inputDal"; 
	protected static String AL = "inputAl"; 
	protected static String IDMERCE = "idMerce";
	protected static String RECORDS = "records";
	protected static String NUMCONSEGNA = "consegna";
	protected static String PARTITARIO = "partitario";
	
	protected static String HASHORDER="HASHORDER";
	
	
	public ViewDaRegistrare ( GtServlet callerServlet) {
		super(callerServlet);
	}
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {
		
		
		/*
		 * Attenzione :
		 * 
		 * A seconda dell'iter devo visualizzare un movimento di registro per ciascun
		 * movimento nel db 
		 * oppure 
		 * un movimento che raccoglie quelli del giorno ... 
		 * 
		 * percio' serve una nuova funzione per tirarli fuori e una nuova funzione x registrarli !!!
		 * 
		 */
		Boolean onlyNonRegistrati = Boolean.TRUE;
		
		Boolean isRegistroIva = getBooleanParam(TIPO_REGISTRO_IVA, false);
		if ( isRegistroIva == null )
			isRegistroIva = Boolean.FALSE;
		
		ctx.put( TIPO_REGISTRO_IVA ,  isRegistroIva) ;
		
		Boolean json = getBooleanParam(JSON, false);
		if ( json == null )
			json = Boolean.FALSE;
		ctx.put( JSON ,  json) ;

		
		Integer numConsegna=null;
		try  {
			numConsegna = getIntParam(NUMCONSEGNA, false);
		}catch(ParameterException pe){}
		
//		Integer numPartitario=null;
//		try  {
//			numPartitario = getIntParam(PARTITARIO, false);
//		}catch(ParameterException pe){}
		
		
		if(numConsegna!=null)
			ctx.put("actConsegne", true);
		
		Integer page = getIntParam(PAGE, false);
		Integer rows = getIntParam	(ROWS, false);
		String nomeColonna = getParam(SIDX, false);
		String ascDesc = getParam(SORD, false);
		HttpSession session=request.getSession(false);
		if ( session == null ) return null ; 
		
		Hashtable<String, String> hashOrder=(Hashtable<String, String>)session.getAttribute(HASHORDER);
		if(hashOrder==null){
			 hashOrder=new Hashtable<String, String>();
			 session.setAttribute(HASHORDER, hashOrder);
		}
		if(ascDesc!=null && nomeColonna!=null)
			hashOrder.put(nomeColonna, ascDesc);
		else if(nomeColonna!=null)
			hashOrder.remove(nomeColonna);
		Integer numero=null;
		try  {
			numero=getIntParam(NUMERO, false);
		}catch(ParameterException pe){}
		FormattedDate dal=null;
		String checkDal=getParam(DAL, false);
		if(checkDal!=null && !checkDal.equals("undefined"))
			try  {
				dal=getDateParam(DAL, false);
				
			}catch(ParameterException pe){}
		FormattedDate al=null;
		String checkAl=getParam(AL, false);
		if(checkAl!=null && !checkAl.equals("undefined"))
			try  {al=getDateParam(AL, false);ctx.put("actAl",true);}
			catch(ParameterException pe){}
		Integer idMerce=null;
		try  {
			idMerce=getIntParam(IDMERCE, false);
			}
		catch(ParameterException pe){}
		Integer records=null;
		try  {records=getIntParam(RECORDS, false);}catch(ParameterException pe){}

		Integer idConsegna = getIntParam(FILTER_CONSEGNA, false);
		Consegna c = null ;
		if ( idConsegna != null )
			c = ConsegnaAdapter.get(idConsegna, true) ;
		
		/*ctx.put( "list" , ( isRegistroIva ? new MovimentoIvaAdapter() : new MovimentoDoganaleAdapter() ) 
					.getRegistro( onlyRegistrati.booleanValue(), c )) ;*/
		
		MovimentoAdapter adp = ( isRegistroIva ? 
				new MovimentoIvaAdapter() : new MovimentoDoganaleAdapter() ) ;
		
		ctx.put( "list" , adp 
					.getDaRegistrare2(c, null)) ;
		
		//Metto nel contesto tutti i parametri della ricerca che ho effettuato 
		ctx.put("page" ,page);
		ctx.put("rows",rows);
		ctx.put("sidx",nomeColonna);
		ctx.put("sord",ascDesc);
		ctx.put("records",records);
		ctx.put("consegne",numConsegna);
		ctx.put("numero",numero);
		ctx.put("idMerce",idMerce);
		if(dal!=null) ctx.put("dal",dal.dmyString());
		if(al!=null) ctx.put("al",al.dmyString());

		if(numero!=null) ctx.put("actNum",true);
		if(dal!=null && al!=null) ctx.put("actDate",true);
		if(idMerce!=null) ctx.put("actMerce",true);
		if(numConsegna!=null) ctx.put("actConsegne",true);
		
		ctx.put( "merci" , MerceAdapter.getAllCachedRegistro(adp)) ;
		
		HttpSession s =  req.getSession(false);
		ctx.put("isAdmin", s.getAttribute("admin") ) ;
		
		return null ;
	}
	

	public VelocityCommand clone() {
		return  new ViewDaRegistrare(this.callerServlet);
	}
	
	public BeanAdapter2 getAdapter() {
		return new ConsegnaAdapter();
	}

	public String getTemplateName() {
		return TEMPLATE;
	}
}