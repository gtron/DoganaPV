package com.gsoft.doganapt.cmd.registri;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.data.Consegna;
import com.gsoft.doganapt.data.adapters.ConsegnaAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoDoganaleAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoIvaAdapter;
import com.gtsoft.utils.common.BeanAdapter2;
import com.gtsoft.utils.common.FormattedDate;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.exception.ParameterException;
import com.gtsoft.utils.http.servlet.GtServlet;


public class ViewRegistro extends VelocityCommand {
	protected static String TEMPLATE = "registri/view.vm";
	protected static String FILTER_CONSEGNA = "idconsegna";
	protected static String FILTER_NONSOLOREGISTRATI = "onlyreg";
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
	
	
	public ViewRegistro ( GtServlet callerServlet) {
		super(callerServlet);
	}
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {
		
		Boolean onlyRegistrati = getBooleanParam(FILTER_NONSOLOREGISTRATI, false);
		if ( onlyRegistrati == null )
			onlyRegistrati = Boolean.TRUE;
		
		ctx.put( FILTER_NONSOLOREGISTRATI , onlyRegistrati) ;
		
		Boolean isRegistroIva = getBooleanParam(TIPO_REGISTRO_IVA, false);
		if ( isRegistroIva == null )
			isRegistroIva = Boolean.FALSE;
		
		ctx.put( TIPO_REGISTRO_IVA ,  isRegistroIva) ;
		
		Boolean json = getBooleanParam(JSON, false);
		if ( json == null )
			json = Boolean.FALSE;
		ctx.put( JSON ,  json) ;

		getParam(NUMCONSEGNA, false);
		Integer numConsegna = getIntParam(NUMCONSEGNA, false);
		Integer page = getIntParam(PAGE, false);
		Integer rows = getIntParam	(ROWS, false);
		String nomeColonna = getParam(SIDX, false);
		String ascDesc = getParam(SORD, false);
		Integer numero=null;
		try  {numero=getIntParam(NUMERO, false);}catch(ParameterException pe){}
		FormattedDate dal=null;
		String checkDal=getParam(DAL, false);
		if(checkDal!=null && !checkDal.equals("undefined"))
			try  {dal=getDateParam(DAL, false);}catch(ParameterException pe){}
		FormattedDate al=null;
		String checkAl=getParam(AL, false);
		if(checkAl!=null && !checkAl.equals("undefined"))
			try  {al=getDateParam(AL, false);}catch(ParameterException pe){}
		Integer idMerce=null;
		try  {idMerce=getIntParam(IDMERCE, false);}catch(ParameterException pe){}
		Integer records=null;
		try  {records=getIntParam(RECORDS, false);}catch(ParameterException pe){}

		Integer idConsegna = getIntParam(FILTER_CONSEGNA, false);
		Consegna c = null ;
		if ( idConsegna != null )
			c = ConsegnaAdapter.get(idConsegna, true) ;
		
		/*ctx.put( "list" , ( isRegistroIva ? new MovimentoIvaAdapter() : new MovimentoDoganaleAdapter() ) 
					.getRegistro( onlyRegistrati.booleanValue(), c )) ;*/
		MovimentoAdapter adp = ( isRegistroIva ? new MovimentoIvaAdapter() : new MovimentoDoganaleAdapter() );
		if(records==null){
			Vector list= adp.getRegistro( onlyRegistrati.booleanValue(),c);
			records=list.size();
			ctx.put( "list" , list) ;
		}
		else
			if(rows!=null){
				if(rows >=records)
					page=1;
			}
			
			ctx.put( "list" ,  adp.getRegistro( onlyRegistrati.booleanValue(), c ,
						page,rows,nomeColonna,ascDesc,numero,dal,al,idMerce,numConsegna) ) ;
		
		//Metto nel contesto tutti i parametri della ricerca che ho effettuato 
		ctx.put("page" ,page);
		ctx.put("rows",rows);
		ctx.put("sidx",nomeColonna);
		ctx.put("sord",ascDesc);
		ctx.put("records",records);
		ctx.put("consegne",numConsegna);

		ctx.put("numero",numero);
		if(dal!=null)
			ctx.put("dal",dal.dmyString());
		if(al!=null)
			ctx.put("al",al.dmyString());
		ctx.put("idMerce",idMerce); 
		
		ctx.put( "merci" ,adp.getMerci() ) ;
		
		return null ;
	}
	

	public VelocityCommand clone() {
		return  new ViewRegistro(this.callerServlet);
	}
	
	public BeanAdapter2 getAdapter() {
		return new ConsegnaAdapter();
	}

	public String getTemplateName() {
		return TEMPLATE;
	}
}