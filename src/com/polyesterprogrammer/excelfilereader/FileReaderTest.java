package com.polyesterprogrammer.excelfilereader;
/*
 * Written by: Jam Jam James Perey
 * This program is to be used with Chevron Corp. and the T-min group in Richmond,CA to automate the File-Renaming Process
 *	
 *	TODO:    1.ability to input the amount of excel files to do. DONE
 *			 2. CHANGE FILENAME STORAGE TO A 2D ARRAY. WILL HOLD ALL VALUES FOR EACH FILENAME!
		     3. while loop to repeat steps for each excel file. DONE
*/

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

// this class takes in the old file via its File Path, and MOVES it to a new File Path, changing its name along the way
class NewFilePath {
	public Path excelNewName(Path oldName, String newNameString) {
		try {
			return Files.move(oldName, oldName.resolveSibling(newNameString));
		} catch (IOException e) {

			e.printStackTrace();
		}
		return null;
	}
}

public class FileReaderTest {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		int i =0;
		ArrayList<String> fileNameList = new ArrayList<String>();
		//int fileCount; // this will hold the amount of files in the set
		//int firstLoop = 0; // this will loop input for last 3 digits of files until it reaches fileCount #
		//int secondLoop = 0; // this will loop and edit all files in the array until it reaches fileCount #
		String filePath; // this will take in the file path for the set of files to be worked on
		//String moreSheets; // this will determine whether or not the ISO has a second sheet
		//String[][] fileNameArray; // First part of file name. 2d array that will hold the last 3 digits, as well as whether or not that particular ISO has a second sheet
		//String fileName = null; // the name of the file to be edited
		String cellValue = null; // data pulled from excel sheet to be added to file name
		String newFileName = null; // next file name to be edited. will replace file name value at end of loop

		System.out.println("Where is the folder located (ex: O:\\Technical\\DED\\11-Misc\\DED Tmins for Meridium)?");
		filePath = input.nextLine();
		FileDirectoryReader fdr = new FileDirectoryReader(filePath);
		
		fileNameList = fdr.FindFilePath();
		System.out.println("TESTING TESTING Whats in fileNameList: " + fileNameList);
		
		//while(iterator.hasNext()){
		//	System.out.println(filePath +"\\" +iterator.next());
		//}


		try{
		do{
     	InputStream inp = new FileInputStream(filePath +"\\" + fileNameList.get(i));
		XSSFWorkbook wb = new XSSFWorkbook(inp);
		// this chooses which workbook you start at
		XSSFSheet sheet = wb.getSheetAt(0);
		Row excelRow = sheet.getRow(6); // finds the specific row
		Cell excelCell = excelRow.getCell(3);// finds the specific column

		// copies cell string value to string variable so that it can be
		// appended
		if (excelCell.getCellType() == Cell.CELL_TYPE_STRING) {
			System.out.println("TESTING CELL TYPE string: " + excelCell.getStringCellValue()); //USE this if you need to check current value of file name
			cellValue = excelCell.toString();
		} else if (excelCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			System.out.println("Wrong cell");
		}
		//creates the object that will do the renaming process
		System.out.println("TESTING TESTING What is going to be the old file name: " + fileNameList.get(i));
		SplitFileName fileSplitter = new SplitFileName(fileNameList.get(i), cellValue);
		newFileName = fileSplitter.SplitFiles();

		wb.close();
		inp.close();

		String oldFilePath = filePath +"\\" + fileNameList.get(i);
		String newFilePath = filePath +"\\" + newFileName;
		System.out.println("TESTING TESTING oldfilepath is: " + oldFilePath);
		System.out.println("TESTING TESTING newfilepath is: " + newFilePath);
		
		Path oldFileDir = Paths.get(oldFilePath); // turns the String oldFilePath into an actual Path that the computer can use

		NewFilePath renamingMethod = new NewFilePath();

		renamingMethod.excelNewName(oldFileDir, newFilePath);
		i++;
		}while(i< fileNameList.size());
		}catch(FileNotFoundException fnfE){
			System.out.println("file:"  +"Does not exist. Please re-enter values for the name");
			fnfE.getStackTrace();
		} catch (IOException ioE) {
			
			ioE.printStackTrace();
		};

	}

}
