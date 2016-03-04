package com.polyesterprogrammer.excelfilereader;
/*
 * Written by: Jam Jam James Perey
 * This program is to be used with Chevron Corp. and the T-min group in Richmond,CA to automate the File-Renaming Process
 *	
 *	TODO:    1.ability to input the amount of excel files to do
		     2. while loop to repeat steps for each excel file
*/

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

// this class takes in the old file via its File Path, and MOVES it to a new File Path, changing its name along the way
class NewName {
	public Path newName(Path oldName, String newNameString) {
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
		int fileCount; // this will hold the amount of files in the set
//		int fileStartingNumber; // sometimes a set starts in a number other than 1
		int toEndOfLoop = 0;
		String fileName;
		String cellValue = null;
		String newFileName = null;
		
		
		System.out.println("How many files in this set?");
		fileCount = Integer.parseInt(input.nextLine());
//		System.out.println("What is the starting number of the set?");
//		fileStartingNumber = Integer.parseInt(input.nextLine());
		System.out.println("What is the name of the first file?");
		fileName = input.nextLine();
		input.close();
		
		do{
		InputStream inp = new FileInputStream("C:\\Test\\" + fileName);
		XSSFWorkbook wb = new XSSFWorkbook(inp);
		XSSFSheet sheet = wb.getSheetAt(0); // this chooses which workbook you
											// start at
		Row row = sheet.getRow(6); // finds the specific row
		Cell cell = row.getCell(3);// finds the specific column

		// copies cell string value to string variable so that it can be
		// appended
		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			System.out.println("string: " + cell.getStringCellValue());
			cellValue = cell.toString();
		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			System.out.println("Wrong cell");
		}
		//creates the object that will do the renaming process
		SplitFileName fileSplitter = new SplitFileName(fileName, cellValue);
		newFileName = fileSplitter.SplitFiles();

		wb.close();
		inp.close();

		String oldFilePath = "C:\\Test\\" + fileName;
		String newFilePath = "C:\\Test\\" + newFileName;
		Path oldFileDir = Paths.get(oldFilePath);

		NewName renamingMethod = new NewName();

		renamingMethod.newName(oldFileDir, newFilePath);
		toEndOfLoop++;
		
		fileName = fileSplitter.GetNewFileLooper();
		System.out.println(fileName);
		}while(toEndOfLoop < fileCount);

	}

}
