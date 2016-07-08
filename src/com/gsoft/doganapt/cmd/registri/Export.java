package com.gsoft.doganapt.cmd.registri;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.cmd.Login;
import com.gsoft.doganapt.data.adapters.ConsegnaAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoDoganaleAdapter;
import com.gsoft.doganapt.data.adapters.MovimentoIvaAdapter;
import com.gtsoft.utils.common.BeanAdapter2;
import com.gtsoft.utils.common.FormattedDate;
import com.gtsoft.utils.http.VelocityCommand;
import com.gtsoft.utils.http.servlet.GtServlet;


public class Export extends ViewRegistro {
	private static final Integer MAX_ROWS = 50000;
	
	protected static String TEMPLATE = "registri/csv/view.vm";
	
	public Export ( GtServlet callerServlet) {
		super(callerServlet);
	}
	public Template exec(HttpServletRequest req, HttpServletResponse resp, Context ctx) throws Exception  {
		
		Boolean isRegistroIva = getBooleanParam(TIPO_REGISTRO_IVA, false);
		String registro = "iva";
		if ( isRegistroIva == null ) {
			isRegistroIva = Boolean.FALSE;
			registro = "doganale";
		}
		
		Boolean isDebug = getBooleanParam("d", false);
		
		FormattedDate now = new FormattedDate();
		String timestamp = now.fullString().replaceAll("[:-]","").replaceAll(" ", "_");
		
		if ( ! Boolean.TRUE.equals(isDebug) ) {
		
			resp.setContentType("application/force-download");
	//		resp.setContentLength((int)f.length());
	//		resp.setHeader("Content-Transfer-Encoding", "binary");
			String filename = "export_registro_" + registro + "_" + timestamp + ".csv";
			
			
			resp.setHeader("Content-Disposition","attachment; filename=\"" + filename + "\"");//fileName);
		}
		
		ctx.put( TIPO_REGISTRO_IVA ,  isRegistroIva) ;
		
		Boolean json = getBooleanParam(JSON, false);
		if ( json == null )
			json = Boolean.FALSE;
		ctx.put( JSON ,  json) ;
		
		FormattedDate dal = getDateParam(DAL, false);
		FormattedDate al = getDateParam(AL, false);
		
		if ( al != null ) {
			al = new FormattedDate( al.ymdString() + " 23:59:59" );
		}
		

		MovimentoAdapter adp = ( isRegistroIva ? 
				new MovimentoIvaAdapter() : new MovimentoDoganaleAdapter() ) ;
		
		Vector vector = adp.getRegistro( true, null , null, MAX_ROWS, null, null, dal, al, null, null );
		
		ctx.put( "list" , vector);
//		writeExcel(resp, vector);
		
		
		Login.logAction("Exporting registry " + registro + " - from: " + dal + " to: " + al, request);
		
		
		return null ;
	}
	

	public VelocityCommand clone() {
		return  new Export(this.callerServlet);
	}
	
	public BeanAdapter2 getAdapter() {
		return new ConsegnaAdapter();
	}

	public String getTemplateName() {
		return TEMPLATE;
	}
	
	private void writeExcel( HttpServletResponse resp, Vector<?> vector ) {
//		Workbook wb = new HSSF	Workbook();
//		Sheet personSheet = wb.createSheet("PersonList");
//		Row headerRow = personSheet.createRow(0);
//		Cell nameHeaderCell = headerRow.createCell(0);
//		Cell addressHeaderCell = headerRow.createCell(1);

		int row = 1;
		for(Object _m : vector ) {
		    
//		    Row dataRow = personSheet.createRow(row);

//		    Cell dataNameCell = dataRow.createCell(0);
//		    dataNameCell.setCellValue(name);

//		    Cell dataAddressCell = dataRow.createCell(1);
//		    dataAddressCell.setCellValue(address);

//			$!m.NumRegistro	$!tools.Date.format($dateFormat	, $m.Data)	$!m.Consegna.Numero	$!m.Consegna.NumeroPartitario	$!m.Documento	$!m.Consegna.Merce	#if($m.getIsScarico() ||  $m.Umido.intValue() < 0 )
//				0	$!tools.Number.format($m.UmidoAssoluto).replaceAll("[.]", "")#else$!tools.Number.format($m.Umido).replaceAll("[.]", "")	0#end	#if($m.getIsScarico() ||  $m.Secco.intValue() < 0 )
//				0	$!tools.Number.format($m.SeccoAssoluto).replaceAll("[.]", "")#else
//				$!tools.Number.format($m.Secco).replaceAll("[.]", "")	0#end
				
		    row = row + 1;
		}

//		String outputDirPath = "D:/PersonList.xls";
//		FileOutputStream fileOut = new FileOutputStream(outputDirPath);
//		wb.write(resp.getWriter());
	}
	
}