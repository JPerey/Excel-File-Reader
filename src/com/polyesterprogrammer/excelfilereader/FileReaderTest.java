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
		int fileCount; // this will hold the amount of files in the set
		int firstLoop = 0; // this will loop input for last 3 digits of files until it reaches fileCount #
		int secondLoop = 0; // this will loop and edit all files in the array until it reaches fileCount #
		String filePath; // this will take in the file path for the set of files to be worked on
		String moreSheets; // this will determine whether or not the ISO has a second sheet
		String[][] fileNameArray; // First part of file name. 2d array that will hold the last 3 digits, as well as whether or not that particular ISO has a second sheet
		String fileName = null; // the name of the file to be edited
		String cellValue = null; // data pulled from excel sheet to be added to file name
		String newFileName = null; // next file name to be edited. will replace file name value at end of loop

		System.out.println("How many files in this set(Not including second sheets)?");
		fileCount = Integer.parseInt(input.nextLine());
		System.out.println("What is the file path?(copy and paste file path of files)");
		filePath = input.nextLine() + "\\";
		// first column will hold the 3 digit number, second column will hold whether or not it has more than one sheet 0 for no 1 for yes
		//[row][column]
		fileNameArray = new String[fileCount][4]; 
		System.out.println("What is the the beginning 6 digits including dashes(ex: 101-008-)?");
		fileNameArray[firstLoop][0] = input.nextLine();
		System.out.println("Next section will repeat " + fileCount + " times");
		//this loop will take in the last 3 digits and see if any have a second sheet
		do{
		System.out.println("What is the #"+(firstLoop+1)+ " ISO's last 3 digits?(ex: 001,234,02A,03B)");
		fileNameArray[firstLoop][1] = input.nextLine();  
		System.out.println("Does this ISO have a second sheet? 'y' or 'n'");
		moreSheets = input.nextLine();
		switch (moreSheets.toLowerCase()){
		case "y":
			fileNameArray[firstLoop][2] = " sh1of2 ";
			break;
		case "n":
			fileNameArray[firstLoop][2] = " sh1of1 ";
			break;
		};
		System.out.println("What T-min Revision is this ISO(ex: 0, 1, 2, etc.)?");
		fileNameArray[firstLoop][3] = "R" + input.nextLine() + ".xlsm";
		fileNameArray[firstLoop][0] = fileNameArray[0][0];
		firstLoop++;
		}while(firstLoop <fileCount);;
		input.close();
		
		for (int row = 0; row < fileNameArray.length; row++) {
			for (int col = 0; col < fileNameArray[row].length; col++) {
				System.out.print(fileNameArray[row][col] + "\t");
			}
			System.out.println(); // adds spaces and breaks to the array output
		}
		
		
		try{
		do{
			//if statement to determine if next file is second sheet or new ISO.
			//if the loop comes back to do the second sheet of an ISO, it won't override the next file
		if(fileNameArray[secondLoop][2].equals("sh2of2")){
		
		}else{
			fileName = fileNameArray[secondLoop][0]+fileNameArray[secondLoop][1]+fileNameArray[secondLoop][2]+fileNameArray[secondLoop][3];
		}
		InputStream inp = new FileInputStream(filePath + fileName);
		XSSFWorkbook wb = new XSSFWorkbook(inp);
		// this chooses which workbook you start at
		XSSFSheet sheet = wb.getSheetAt(0);
		Row excelRow = sheet.getRow(6); // finds the specific row
		Cell excelCell = excelRow.getCell(3);// finds the specific column

		// copies cell string value to string variable so that it can be
		// appended
		if (excelCell.getCellType() == Cell.CELL_TYPE_STRING) {
			//System.out.println("string: " + excelCell.getStringCellValue()); USE this if you need to check current value of file name
			cellValue = excelCell.toString();
		} else if (excelCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			System.out.println("Wrong cell");
		}
		//creates the object that will do the renaming process
		SplitFileName fileSplitter = new SplitFileName(fileName, cellValue);
		newFileName = fileSplitter.SplitFiles();

		wb.close();
		inp.close();

		String oldFilePath = filePath + fileName;
		String newFilePath = filePath + newFileName;
		Path oldFileDir = Paths.get(oldFilePath); // turns the String oldFilePath into an actual Path that the computer can use

		NewFilePath renamingMethod = new NewFilePath();

		renamingMethod.excelNewName(oldFileDir, newFilePath);
		if(fileNameArray[secondLoop][2].equals(" sh1of2 ")){
			fileNameArray[secondLoop][2] = " sh2of2 ";
		}else{
			secondLoop++;
		}
		}while(secondLoop < fileCount);
		}catch(FileNotFoundException fnfE){
			System.out.println("file:" + fileName +"Does not exist. Please re-enter values for the name");
			fnfE.getStackTrace();
		} catch (IOException ioE) {
			
			ioE.printStackTrace();
		};

	}

}
