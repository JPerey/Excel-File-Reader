package com.polyesterprogrammer.excelfilereader;
/* Written by : Jam Jam James Perey, 5/5/2016, Kai: PERJ
 * This program is to be used with Chevron Corp. and the T-min group in Richmond,CA to automate the File-Renaming Process
 *	
 *	TODO:    1. ability to input the amount of excel files to do. DONE 5/5/2016-removed
 *			 2. CHANGE FILENAME STORAGE TO A 2D ARRAY. WILL HOLD ALL VALUES FOR EACH FILENAME! DONE
		     3. while loop to repeat steps for each excel file. DONE 
		     4. Allow program to switch form old and new file formats. DONE
		     5. Replace all " .11 " thk with " .1 " thk for any " 1/2" " thicknesses. DONE
		     6. if the files have already been edited, have a check to make sure it doesn't go through them again. DONE
		     7. exclude .jpg's, .pdf's. DONE
		     8. if a folder is located inside the directory, program will look into that folder for more excel files to revise. 
*/

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

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
		// input for folder path
		Scanner input = new Scanner(System.in);
		// iterable digit that will go through collection of files
		int i = 0;
		//dictates if program is done
		int finished = 0; 
		int subDirectoryIterator1 = 0;
		int subDirectoryIterator2 = 0;
		int subDirectoryIterator3 = 0;
		// collection of all files found in given directory
		ArrayList<String> fileNameList = new ArrayList<String>();
		ArrayList<String> subDirectory1 = new ArrayList<String>();
		ArrayList<String> subDirectory2 = new ArrayList<String>();
		ArrayList<String> subDirectory3 = new ArrayList<String>();
		// folder directory given by user
		String parentFilePath;
		String currentFilePath;
		// data pulled from excel cell
		String cellValue = null;
		// the new file name that will replace the existing file name
		String newFileName = null;

		System.out.println(
				"Please input Parent Folder Directory (ex: O:\\Technical\\DED\\11-Misc\\DED Tmins for Meridium)");
		// this should be the only input needed from the user
		parentFilePath = input.nextLine();
		FolderLooper fL = new FolderLooper(parentFilePath);

		FileDirectoryReader fdr = new FileDirectoryReader(parentFilePath);

		// initial creation of all arrays
		subDirectory1 = fL.subFolder1ArrayCreator();
		subDirectory2 = fL.subFolder2ArrayCreator();
		// initial filenames will come from deepest sub-folder first
		subDirectory3 = fL.subFolder3ArrayCreator();
		// this for loop outputs the filename's that will be worked on
		fileNameList = fdr.subFolder2FindFilePath(subDirectory3.get(subDirectoryIterator3));
		System.out.println("the size of the filenamelist: " + fileNameList.size());
		if (!(fileNameList.size() == 0)) {
			currentFilePath = subDirectory2.get(subDirectoryIterator2);

		} else {
			currentFilePath = subDirectory1.get(subDirectoryIterator1);
			fileNameList = fdr.SubFolder1FindFilePath(subDirectory1.get(subDirectoryIterator1));
			System.out.println("size of filename list in the 1st sub folder: " + fileNameList.size());
			System.out.println("wassup bitches");
		}

		System.out.println(" Iterating through: " + currentFilePath);
		for (String fileNameListing : fileNameList) {
			System.out.println("ISO to be revised: " + fileNameListing);
		}
	do {
			try {
				do {
					// Each time this runs, a new filepath and filename must be
					// created.

					ThicknessChecker tC = new ThicknessChecker();
					cellValue = tC.thicknessCheckerMethod(currentFilePath, fileNameList.get(i));

					SplitFileName fileSplitter = new SplitFileName(fileNameList.get(i), cellValue);
					newFileName = fileSplitter.SplitFiles();

					String oldFilePath = currentFilePath + "\\" + fileNameList.get(i);
					String newFilePath = currentFilePath + "\\" + newFileName;

					// turns Strings into an actual file path that can be used
					Path oldFileDir = Paths.get(oldFilePath);

					NewFilePath renamingMethod = new NewFilePath();

					renamingMethod.excelNewName(oldFileDir, newFilePath);
					System.out.println("old file name: " + fileNameList.get(i) + " || new file name: " + newFileName);

					System.out.println("---------------------------------");
					i++;
				} while (i < fileNameList.size());
			} catch (IndexOutOfBoundsException ioobE) {
				System.out.println("All files in directory have already been edited.");
				ioobE.printStackTrace();
			}
		for (String subDirectory1Index : subDirectory1) {
				for (String subDirectory2Index : subDirectory2) {
					if (subDirectory3.size() > 0 ) {
						for(String subDirectory3Index : subDirectory3){
							i++;
						}

					}
				}
			}

} while (finished < 1);

		input.close();
		System.out.println("Program finished.");

	}

}
