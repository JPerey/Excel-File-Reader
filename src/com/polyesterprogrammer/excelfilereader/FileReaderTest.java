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
		     8. if a folder is located inside the directory, program will look into that folder for more excel files to revise.  DONE
*/

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

// this class takes in the old file via its File Path, and MOVES it to a new File Path, changing its name along the way
class NewFilePath {
	public Path excelNewName(Path oldName, String newNameString) {
		try {
			return Files.move(oldName, oldName.resolveSibling(newNameString));
		} catch (IOException e) {
			System.out.println("File with same name located already in directory. Please check file \"" + oldName
					+ "\" and see if it is named correctly");
		}
		return null;
	}
}

public class FileReaderTest {

	public static void main(String[] args) {
		// input for folder path
		Scanner input = new Scanner(System.in);
		// iterator for SUB sub-folder to move with sub-folder
		int subDirectoryIterator3 = 0;
		/*
		 * resetIterator moves the parent directory folder index up by one each
		 * // time the program is done with all the files inside that particular
		 * parent directory folder
		 */
		int resetIterator = 1;
		int parentFolderArrayCounter = 0;
		// collection of all files found in given directory
		ArrayList<String> fileNameList = new ArrayList<String>();
		ArrayList<String> subDirectory1 = new ArrayList<String>();
		ArrayList<String> subDirectory2 = new ArrayList<String>();
		ArrayList<String> subDirectory3 = new ArrayList<String>();
		ArrayList<String> thicknessOnlySubFiles2 = new ArrayList<String>();
		ArrayList<String> thicknessOnlySubFiles3 = new ArrayList<String>();
		// folder directory given by user
		String parentFilePath;
		String currentFilePath;
		boolean insideFER = false;

		System.out.println(
				"Please input Parent Folder Directory (ex: O:\\Technical\\DED\\11-Misc\\DED Tmins for Meridium)");
		// this should be the only input needed from the user
		parentFilePath = input.nextLine();
		FolderLooper fL = new FolderLooper(parentFilePath);

		FileDirectoryReader fdr = new FileDirectoryReader(parentFilePath);
		// HotCircuitArrayInitialization hCAI = new
		// HotCircuitArrayInitialization(hotCircuitFilePath);

		try {
			// initial creation of all arrays
			subDirectory1 = fL.subFolder1ArrayCreator();
			subDirectory2 = fL.subFolder2ArrayCreator(subDirectory1.get(0));
			// initial filenames will come from deepest sub-folder first
			subDirectory3 = fL.subFolder3ArrayCreator(subDirectory2.get(0));
			// initializes file name array of hot circuits that need pdf's made
			// hotCircuitFiles = hCAI.hotCirccuitArray();

		} catch (NullPointerException npE) {
			System.out
					.println("File Directory: \"" + parentFilePath + "\" incorrect. Please check and re-run program.");
			System.exit(0);
		}

		CallandPrint callPrint = new CallandPrint();
		// sets which current file array to look at first
		if (!(subDirectory3.size() == 0)) {
			currentFilePath = subDirectory3.get(0);
		} else {
			currentFilePath = subDirectory2.get(0);
		}

		// Loops that will cycle through each folder and edit files
		// 1st sub folder section
		for (String subDirectory1Index : subDirectory1) {
			System.out.println("Currently in Parent Folder: " + subDirectory1Index);

			// 2nd sub folder section
			for (String subDirectory2Index : subDirectory2) {
				System.out.println("Currently in sub-folder: " + subDirectory2Index);

				if (subDirectoryIterator3 > 0) {
					subDirectory3 = fL.subFolder3ArrayCreator(subDirectory2Index);
				}
				if (!(subDirectory3.size() == 0)) {
					// 3rd sub folder section
					for (String subDirectory3Index : subDirectory3) {
						if (subDirectory3Index.contains("Sent to FER")) {
							System.out.println("Currently in SUB sub-folder:" + subDirectory3Index);
							System.out.println("Currently in \"Sent to FER\" folder");
							insideFER = true;
						} else {
							System.out.println("Currently in SUB sub-folder:" + subDirectory3Index);
						}
						fileNameList.clear();
						thicknessOnlySubFiles3.clear();
						fileNameList = fdr.subFolder2FileNameArrayMethod(subDirectory3Index);
						thicknessOnlySubFiles3 = fdr.returnThicknessOnlySubFile3();
						callPrint.callAndPrintMethod(fileNameList, thicknessOnlySubFiles3, subDirectory3Index,
								insideFER);
						System.out.println("Finished iterating through SUB sub-folder: " + subDirectory3Index);
						insideFER = false;

					}
				}
				// end of 3rd sub folder section

				currentFilePath = subDirectory2Index;
				fileNameList.clear();
				thicknessOnlySubFiles2.clear();
				fileNameList = fdr.SubFolder1FileNameArrayMethod(subDirectory2Index);
				thicknessOnlySubFiles2 = fdr.returnThicknessOnlySubFile2();
				callPrint.callAndPrintMethod(fileNameList, thicknessOnlySubFiles2, currentFilePath, insideFER);
				System.out.println("Finished iterating through sub-folder: " + subDirectory2Index);
				subDirectoryIterator3++;
			}
			// end of 2nd sub folder section

			// creates new folder arrays for the 2nd and 3rd sub-folders after
			// each switch
			parentFolderArrayCounter++;
			if (subDirectory1.size() > parentFolderArrayCounter) {
				subDirectory2 = fL.subFolder2ArrayCreator(subDirectory1.get(resetIterator));
			}

			try {
				subDirectory3 = fL.subFolder3ArrayCreator(subDirectory2.get(0));
				// try-catch to break out of loop and end program
			} catch (IndexOutOfBoundsException ioobE2) {
				System.out.println("All files in sub folder Directory finished.");
				// break;
			}
			resetIterator++;
			subDirectoryIterator3 = 0;

			System.out.println("Finished iterating through Parent folder: " + subDirectory1Index);
		}
		// end of 1st sub folder section

		input.close();
		System.out.println("Program finished.");

	}

}
