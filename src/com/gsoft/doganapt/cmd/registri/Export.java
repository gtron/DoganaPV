package com.gsoft.doganapt.cmd.registri;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.gsoft.doganapt.cmd.Login;
import com.gsoft.doganapt.data.Consegna;
import com.gsoft.doganapt.data.Movimento;
import com.gsoft.doganapt.data.MovimentoIVA;
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
		
		Vector<Movimento> vector = adp.getRegistro( true, null , null, MAX_ROWS, null, null, dal, al, null, null );
		
//		ctx.put( "list" , vector);
		Login.logAction("Exporting registry " + registro + " - from: " + dal + " to: " + al, request);
		
		
		if ( ! Boolean.TRUE.equals(isDebug) ) {
			
//			resp.setContentType("application/force-download");
	//		resp.setContentLength((int)f.length());
	//		resp.setHeader("Content-Transfer-Encoding", "binary");
			
			String filename = "export_registro_" + registro + "_" + timestamp + ".xls";
			resp.setHeader("Content-Disposition","attachment; filename=\"" + filename + "\"");//fileName);
			
			resp.setContentType("application/vnd.ms-excel");
			
			writeAsExcel(resp, vector, isRegistroIva);
			
		}
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
	
	private void writeAsExcel( HttpServletResponse resp, Vector<?> vector, boolean isIva ) throws IOException {
		Workbook wb = new HSSFWorkbook();
		
		Sheet sheet = wb.createSheet("Movimenti");
		Row headerRow = sheet.createRow(0);
		
		int col = 0 ;
		
		headerRow.createCell(col++).setCellValue("Num. Registro");
		headerRow.createCell(col++).setCellValue("Data");
		headerRow.createCell(col++).setCellValue("Num. Consegna");
		headerRow.createCell(col++).setCellValue("NumeroPartitario");
		headerRow.createCell(col++).setCellValue("Documento");
	    
	    headerRow.createCell(col++).setCellValue("Merce");
	    headerRow.createCell(col++).setCellValue("Umido IN");
	    headerRow.createCell(col++).setCellValue("Umido OUT");
	    headerRow.createCell(col++).setCellValue("Secco IN");
	    headerRow.createCell(col++).setCellValue("Secco OUT");
	   
	    if(isIva) {
	    	headerRow.createCell(col++).setCellValue("Valore");
	    }

		Row dataRow;
		Movimento m;
		Consegna c;
		int row = 1;
		
		for(Object _m : vector ) {
		    
			m = (Movimento) _m;
			c = m.getConsegna();
			
		    dataRow = sheet.createRow(row);
		    
		    col = 0;

		    dataRow.createCell(col++).setCellValue(m.getNumRegistro());
		    dataRow.createCell(col++).setCellValue(m.getData().dmyString());
		    dataRow.createCell(col++).setCellValue(c.getNumero());
		    dataRow.createCell(col++).setCellValue(c.getNumeroPartitario());
		    
		    if ( m.getDocumento() != null ) {
		    	dataRow.createCell(col++).setCellValue(m.getDocumento().toString());
		    } else {
		    	dataRow.createCell(col++).setCellValue("");
		    }
		    
		    dataRow.createCell(col++).setCellValue(c.getMerce().getNome());
		    
		    if(m.getIsScarico() || m.getUmido().intValue() < 0 ) {
		    	dataRow.createCell(col++).setCellValue("");
		    	dataRow.createCell(col++).setCellValue(m.getUmidoAssoluto());
		    } else {
		    	dataRow.createCell(col++).setCellValue(m.getUmidoAssoluto());
		    	dataRow.createCell(col++).setCellValue("");
		    }
		    if(m.getIsScarico() ||  m.getSecco().intValue() < 0 ) {
		    	dataRow.createCell(col++).setCellValue("");
		    	dataRow.createCell(col++).setCellValue(m.getSeccoAssoluto());
		    } else {
		    	dataRow.createCell(col++).setCellValue("");
		    	dataRow.createCell(col++).setCellValue(m.getSeccoAssoluto());
		    }
		   
		    if(isIva) {
		    	dataRow.createCell(col++).setCellValue(((MovimentoIVA) m).getValoreEuro());
		    }
				
		    row++;
		}
		
		for ( int x = 0 ; x < col ; x++ ) {
			sheet.autoSizeColumn(x);
		}

//		String outputDirPath = "D:/PersonList.xls";
//		FileOutputStream fileOut = new FileOutputStream(outputDirPath);
//		resp.setContentType("application/vnd.ms-excel");
//		resp.setHeader("Content-Disposition", "attachment; filename=MyExcel.xls");
        
		ServletOutputStream out = response.getOutputStream();
        wb.write(out);
        wb.close();
        out.flush();
        out.close();
	}
	
}