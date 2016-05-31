package com.polyesterprogrammer.excelfilereader;

import java.io.IOException;

import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;

//this class takes an excel file, and exports it to a pdf file
public class ExcelToPDF {
	String excelFileName;
	String excelFilePath;
	String fileNameWithoutXLSM;
	int i = 0;
	
	public void excelToPDF(String excelFileName, String excelFilePath){
	this.excelFileName = excelFileName;
	this.excelFilePath = excelFilePath;
	System.out.println("WE ARE IN THE EXCEL TO PDF FILE CLASS");
	try{
			fileNameWithoutXLSM = excelFileName.substring(0,excelFileName.lastIndexOf("."));
			Workbook workbook = new Workbook(excelFilePath + "\\" + excelFileName +"");
			workbook.save(excelFilePath + "\\" + fileNameWithoutXLSM + ".pdf", FileFormatType.PDF);
			i++;
	}catch(IOException ioE){
		ioE.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
		
		
	}
}
