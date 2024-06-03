package gokul;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.StringTokenizer;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRCsvDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;  
/******************************************************/
/*Class Name: Letter_Content	  					  */
/*Created By: Gokul R 								  */
/*Created Date:25/06/2023							  */
/*Version:0.0										  */
/******************************************************/
public class Letter_Content {
	/******************************************************/
	/*Method Name: TokenizeHeader	  					  */
	/*Purpose:Take Header From String oF Array 			  */
	/******************************************************/
	public static String[] TokenizeHeader(String HeaderStr) {
		int i = 0;
		StringTokenizer st = new StringTokenizer(HeaderStr, ",");
		String[] ColNameArrayJp = new String[st.countTokens()];

		while (st.hasMoreTokens()) {
			ColNameArrayJp[i++] = st.nextToken();
		}
		return ColNameArrayJp;
	}
	public void fillReport() throws IOException {
		/******************************************************/
		/*Method Name: Letter_Content	  					  */
		/*Purpose:Fill Report								  */
		/* i)Append Data to String Buffer 					  */
	    /* ii)Create Lst File 								  */
		/* iii)Pick Lst File From Lst Location				  */
		/* iv)Generate PDF File Using Jasper				  */
		/******************************************************/
		
		//Declaration and Define The FileLocation
		String dLstLoc = "E:Gokul_Jasper/CBI/WorkingFIle_13012023/New_24062023/LstFile";
		String dOutFileLoc = "E:Gokul_Jasper/CBI/WorkingFIle_13012023/New_24062023/OutFile";
		String dReportLoc = "E:Gokul_Jasper/CBI/WorkingFIle_13012023/New_24062023/ReportFile";
		String sFileName = "Letter_Content_";
		String sLayoutName = "Letter_Format.jasper";
		String sGetlstFullPath = "";
		String sOutFileExtension = ".pdf";
		
		//Header Declaration String oF Array Variable
		String[] colarray;
		
		//Jasper Headers Name
		String sHeaders = "Date,Name,Department,Address,City,State,Subject,Content,City,Name";
		colarray = TokenizeHeader(sHeaders);
		
		//Assign Content to final keyword
		final String Name = "Gokul R,";
		final String Name1 = "Gokul R";
		final String Department = "IT,";
		final String Address = "40c,Dharmaraja Koil Street Kalambur,Polur Taluk,";
		final String City = "Tiruvannamalai,";
		final String City1 = "Tiruvannamalai";
		final String State = "Tamil Nadu 606 903.";
		final String Subject = "Req. PG Rent";
		String Content = "I hope this email finds you well. I am writing to discuss our ongoing search for a suitable PG accommodation. Despite our efforts over the past month, we have been unable to find a PG below the budget of 12,000 rupees. Most of the available options range from 13,000 to 15,000 rupees per month.\n";
		Content = Content+"Considering the circumstances, I kindly request your assistance in securing a PG at a rent of 13,000 rupees per month. As we previously discussed with Murali, he is also eager to join me in this accommodation. It has become increasingly challenging for me to commute daily, and I believe that moving to a PG will greatly alleviate this difficulty.\n";
		Content = Content+"Ideally, I would like to make the transition to the new PG by next week. I understand the urgency of the situation and would greatly appreciate it if you could take immediate action to help us find a suitable option.\n";
		Content = Content+"Thank you for your attention to this matter, and I eagerly await your prompt response.";
		
		//Get Absolute Date from JAVA_LIBRARY
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDateTime date = LocalDateTime.now();
		String Date = dtf.format(date);
		
		//Appending All String Content to StringBuffer
		StringBuffer lstcontent = new StringBuffer();
		lstcontent.append(Date);
		lstcontent.append("|");
		lstcontent.append(Name);
		lstcontent.append("|");
		lstcontent.append(Department);
		lstcontent.append("|");
		lstcontent.append(Address);
		lstcontent.append("|");
		lstcontent.append(City);
		lstcontent.append("|");
		lstcontent.append(State);
		lstcontent.append("|");
		lstcontent.append(Subject);
		lstcontent.append("|");
		lstcontent.append(Content);
		lstcontent.append("|");
		lstcontent.append(City1);
		lstcontent.append("|");
		lstcontent.append(Name1);
		
		//Create File Path and File Name
		File file = new File(dLstLoc);
		File getfile = File.createTempFile(sFileName, ".lst",file);//Generate Random File Name to Absolute path
		String getfilename = getfile.getName();//Save File Name to String
		sGetlstFullPath = dLstLoc+"/"+getfilename;//Get FullPath and File Name
		
		//Generate Output File to Respective Path
		FileOutputStream lstoutfile = new FileOutputStream(sGetlstFullPath,false);
		byte[] lstByteArry = new byte[102400];
		lstByteArry = lstcontent.toString().getBytes();// Convert the String that is to be written into byte array
		lstoutfile.write(lstByteArry);// Write the byte array to the file using file output stream
		lstoutfile.close();//Close OutPutStream
		
		//Jasper Engine Starts Here
		try {
			char Fld_Delim = '|';//Delimited by '|' Separator
			InputStream sLayout = new FileInputStream(dReportLoc+"/"+sLayoutName);//Get Jasper File From the Path
			InputStream sLst = new FileInputStream(sGetlstFullPath);//Get LST FULL LOCATION PATH
			JRCsvDataSource jrCsvDataSource = new JRCsvDataSource(sLst);//Create Data into CSV Format
			
			File outfile = new File(dOutFileLoc);
			File getout = File.createTempFile("Letter_",".pdf", outfile);
			String getoutfile = getout.getName();
			String sGetpdfOutPath = dOutFileLoc+"/"+getoutfile;
			
			
			jrCsvDataSource.setFieldDelimiter(Fld_Delim);//set as delimiter
			jrCsvDataSource.setRecordDelimiter("\r\n");//set as new row and new line
			jrCsvDataSource.setUseFirstRowAsHeader(false);//set headerasFirstHeader
			jrCsvDataSource.setColumnNames(colarray);//set Column Name
			
			JasperPrint jasperprint = JasperFillManager.fillReport(sLayout, new HashMap(), jrCsvDataSource);//Parameter JasperLocation,Object,Data Source
			
			if(sOutFileExtension.equals(".pdf")) {//Define Extension
				JRPdfExporter pdfexport = new JRPdfExporter();//Create Object PDF Exporter
				pdfexport.setParameter(JRExporterParameter.JASPER_PRINT, jasperprint);//setParameter to PDF Exporter
				pdfexport.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, sGetpdfOutPath);//setParameter to PDF Exporter
				pdfexport.setParameter(JRPdfExporterParameter.IS_COMPRESSED, Boolean.TRUE);//setParameter to PDF Exporter
				pdfexport.setParameter(JRPdfExporterParameter.IS_ENCRYPTED, Boolean.TRUE);//setParameter to PDF Exporter
				pdfexport.setParameter(JRPdfExporterParameter.IS_128_BIT_KEY, Boolean.TRUE);//setParameter to PDF Exporter
				pdfexport.setParameter(JRPdfExporterParameter.METADATA_TITLE, "Sample Letter");//setParameter to PDF Exporter
				pdfexport.setParameter(JRPdfExporterParameter.METADATA_SUBJECT, "Letter Format");//setParameter to PDF Exporter
				pdfexport.setParameter(JRPdfExporterParameter.METADATA_AUTHOR, "Gokul R");//setParameter to PDF Exporter
				pdfexport.exportReport();//Export PDF To Desired Path.
			}
		}
		catch(Exception e){
			System.out.println("Error In Jasper Engine"+e);
		}
		//End Of Jsper Engine
	}
}
