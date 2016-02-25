package com.polyesterprogrammer.excelfilereader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.commons.*;
import org.apache.commons.io.FileUtils;

import static java.nio.file.StandardCopyOption.*;

class NewName {
	public Path newName(Path oldName, String newNameString){
		try {
			return Files.move(oldName, oldName.resolveSibling(newNameString));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return null;
    }
}


public class FileReaderTest {

	public static void main(String[] args) throws Exception {
		Scanner input = new Scanner(System.in);
		String fileName;
		String cellValue = null;
		String removeXLSM;
		String newFileName = null;
		System.out.println("What is the name of the file?");
		fileName = input.nextLine();
		input.close();
		 InputStream inp = new FileInputStream("C:\\Test\\" + fileName);
		    XSSFWorkbook wb = new XSSFWorkbook(inp);
		    XSSFSheet sheet = wb.getSheetAt(0);
		    Row row = sheet.getRow(6);
		    Cell cell = row.getCell(3);
		    
		    if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
		        System.out.println("string: " + cell.getStringCellValue());
		        cellValue = cell.toString();
		    }else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
		        System.out.println("Wrong cell");
		    }
		    //System.out.println(cellValue.charAt(13));
		    
		    
		    removeXLSM = fileName;
		    if(removeXLSM!= null && removeXLSM.length() > 1){
		    	newFileName = removeXLSM.substring(0, removeXLSM.length()-5);
		    	newFileName = newFileName + " ISO Rev" + cellValue.charAt(13) + ".xlsm";
		    }
		    wb.close();
		    inp.close();
		    //String newFileName = fileName + "ISO Rev: " + cellValue.charAt(13);
		    //System.out.println(newFileName);
		    String oldFilePath = "C:\\Test\\" + fileName;
		    String newFilePath = "C:\\Test\\" + newFileName;
		    Path oldFileDir = Paths.get(oldFilePath);
		    //File oldFile = new File("C:\\Test\\" + fileName);
		    
		    //File newFile = new File("C:\\Test\\Created\\" + newFileName);
		   
		    //try{
		    //	FileUtils.copyDirectory(oldFile, newFile);
		    //}catch(Exception e){
		    //	System.out.println("It didnt work");
		    //};
		    //oldFile.renameTo(new File("C:\\Test\\" + newFileName));
		     
		    NewName renamingMethod = new NewName();
		    
		    renamingMethod.newName(oldFileDir, newFilePath);
		    
		    
		    

		    	

	}

}
