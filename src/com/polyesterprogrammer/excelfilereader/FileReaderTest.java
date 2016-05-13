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
		// dictates if program is done
		//int subDirectoryIterator1 = 0;
		int subDirectoryIterator2 = 0;
		int subDirectoryIterator3 = 0;
		int resetIterator = 1;
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
		subDirectory2 = fL.subFolder2ArrayCreator(subDirectory1.get(i));
		// initial filenames will come from deepest sub-folder first
		subDirectory3 = fL.subFolder3ArrayCreator(subDirectory2.get(0));
		// this for loop outputs the filename's that will be worked on
		CallandPrint callPrint = new CallandPrint();
		// fileNameList = fdr.subFolder2FindFilePath(subDirectory3.get(0));
		System.out.println("the size of the filenamelist: " + fileNameList.size());
		currentFilePath = subDirectory3.get(0);

		// Loops that will create new filename arrays for folders, and calls
		// CallPrint Method
		// System.out.println(" Iterating through: " + currentFilePath);
		// 1st sub folder section
		for (String subDirectory1Index : subDirectory1) {
			System.out.println("checking: 1st sub");

			// 2nd sub folder section
			for (String subDirectory2Index : subDirectory2) {
				System.out.println("checking: 2nd sub: " + subDirectory2Index);
				if (subDirectoryIterator3 > 0) {
					System.out.println("toot toot");
					//System.out.println("the subDirectoryIterator2 is NOW: " + subDirectoryIterator2);
					System.out.println("the directory is: " + subDirectory2Index);
					subDirectory3 = fL.subFolder3ArrayCreator(subDirectory2Index);
					System.out.println("list of subdirectory3 folders");
					for( String sub3 : subDirectory3){
						System.out.println("sub3 directory:" + sub3);
					}
				}
				if (!(subDirectory3.size() == 0)) {
					// 3rd sub folder section
					System.out.println("Size of the ");
					for (String subDirectory3Index : subDirectory3) {

						fileNameList.clear();
						fileNameList = fdr.subFolder2FileNameArrayMethod(subDirectory3Index);
						System.out
								.println("size of the filename list in the 3rd sub folder is: " + fileNameList.size());
						 System.out.println(" Iterating through BELLY: " +
						 subDirectory3Index);
						callPrint.callAndPrintMethod(fileNameList, subDirectory3Index);
						System.out.println("Finished iterating through: " + subDirectory3Index);

					}
				}
				// end of 3rd sub folder section
				
				currentFilePath = subDirectory2Index;
				System.out.println(subDirectory2Index);
				fileNameList.clear();
				fileNameList = fdr.SubFolder1FileNameArrayMethod(subDirectory2Index);
				// system.out.println(" Iterating throughPOW : " +
				// currentFilePath);
				callPrint.callAndPrintMethod(fileNameList, currentFilePath);
				System.out.println("Finished iterating through: " + subDirectory2Index);
				subDirectoryIterator3++;
				subDirectoryIterator2++;
			}
			// end of 2nd sub folder section
			
/*			currentFilePath = subDirectory1Index;
			fileNameList.clear();
			fileNameList = fdr.parentDirectoryFileNameArrayMethod(subDirectory1Index);*/
			
			System.out.println("the parent folder directory we are currently on is: " + subDirectory1Index);
			subDirectoryIterator2=0;
			System.out.println("the subDirectory 2 size is " + subDirectory2.size());
			
			//subDirectory1 = fL.subFolder1ArrayCreator();
			subDirectory2 = fL.subFolder2ArrayCreator(subDirectory1.get(resetIterator));
			// initial filenames will come from deepest sub-folder first
			subDirectory3 = fL.subFolder3ArrayCreator(subDirectory2.get(0));
			resetIterator++;
			
			/*if (subDirectory2.size() > subDirectoryIterator2) {
				for(String sub2 : subDirectory2){
					System.out.println("file in 2nd sub folder array: " + sub2);
				}
				subDirectory2 = fL.subFolder2ArrayCreator(subDirectoryIterator2);
			}*/

		}
		// end of 1st sub folder section

		input.close();
		System.out.println("Program finished.");

	}

}
