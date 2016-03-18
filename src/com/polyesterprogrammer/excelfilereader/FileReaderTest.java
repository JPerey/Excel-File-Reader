package com.polyesterprogrammer.excelfilereader;
/*
 * Written by: Jam Jam James Perey
 * This program is to be used with Chevron Corp. and the T-min group in Richmond,CA to automate the File-Renaming Process
 *	
 *	TODO:    1.ability to input the amount of excel files to do. DONE
 *			 2. CHANGE FILENAME STORAGE TO A 2D ARRAY. WILL HOLD ALL VALUES FOR EACH FILENAME! done
		     3. while loop to repeat steps for each excel file. DONE 
		     4. Allow program to switch form old and new file formats. done
		     5. Replace all " .11 " thk with " .1 " thk for any " 1/2" " thicknesses
		     6. if the files have already been edited, have a check to make sure it doesn't go through them again
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

		// iterable digit that will go through collection of files
		int i = 0;
		// collection of all files found in given directory
		ArrayList<String> fileNameList = new ArrayList<String>();
		// folder directory given by user
		String filePath;
		// data pulled from excel cell
		String cellValue = null;
		// the new file name that will replace the existing file name
		String newFileName = null;

		System.out.println("Where is the folder located (ex: O:\\Technical\\DED\\11-Misc\\DED Tmins for Meridium)?");
		// this should be the only input needed from the user
		filePath = input.nextLine();
		FileDirectoryReader fdr = new FileDirectoryReader(filePath);

		fileNameList = fdr.FindFilePath();
		

		try {
			do {
				InputStream inp = new FileInputStream(filePath + "\\" + fileNameList.get(i));
				XSSFWorkbook wb = new XSSFWorkbook(inp);
				// this chooses which workbook you start at
				XSSFSheet sheet = wb.getSheetAt(0);
				// section will check for new or old sheet
				Row excelRow = sheet.getRow(18); // specific row
				Cell excelCell = excelRow.getCell(1);// specific column

				if (excelCell.getCellType() == Cell.CELL_TYPE_STRING) {
					// this is for old sheets
					if (excelCell.toString().equals("OD nom")) {
						excelRow = sheet.getRow(5);
						excelCell = excelRow.getCell(2);

						// this is for new sheets
					} else if (excelCell.toString().equals("Design Press' (P) ")) {
						excelRow = sheet.getRow(6);
						excelCell = excelRow.getCell(3);

					}
				}

				// copies cell string value to string variable
				if (excelCell.getCellType() == Cell.CELL_TYPE_STRING) {
					cellValue = excelCell.toString();
					//use this to check iff you need to see what is cell name
					//System.out.println("TESTING CELL TYPE string: " + cellValue);
				} else if (excelCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					System.out.println("Wrong cell");
				}
				// creates the object that will do the renaming process
				// System.out.println("TESTING TESTING What is going to be the
				// old file name: " + fileNameList.get(i));
				SplitFileName fileSplitter = new SplitFileName(fileNameList.get(i), cellValue);
				newFileName = fileSplitter.SplitFiles();

				wb.close();
				inp.close();

				String oldFilePath = filePath + "\\" + fileNameList.get(i);
				String newFilePath = filePath + "\\" + newFileName;
			
				//turns Strings into an actual file path that can be used
				Path oldFileDir = Paths.get(oldFilePath); 

				NewFilePath renamingMethod = new NewFilePath();

				renamingMethod.excelNewName(oldFileDir, newFilePath);
				System.out.println("old file name: " + fileNameList.get(i) + " || new file name: " + newFileName);
				i++;
			} while (i < fileNameList.size());
		} catch (FileNotFoundException fnfE) {
			System.out.println("File does not exist.");
			fnfE.getStackTrace();
		} catch (IOException ioE) {
			System.out.println("File Path: " +filePath+ " cannot be found. Please check file path and try again. " );
			ioE.printStackTrace();
		}catch(IndexOutOfBoundsException ioobE){
			System.out.println(" Pleaserestart program with correct file path");
		}
		;
		input.close();
		System.out.println("Program finished.");

	}

}
