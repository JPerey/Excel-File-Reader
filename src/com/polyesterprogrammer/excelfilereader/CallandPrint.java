package com.polyesterprogrammer.excelfilereader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class CallandPrint {
	String cellValue = null;
	// the new file name that will replace the existing file name
	String newFileName = null;
	String currentFilePath;
	ArrayList<String> fileNameList = new ArrayList<String>();
	int iterator = 0;
	int thicknessOnlyIterator = 0;

	public void callAndPrintMethod(ArrayList<String> fileNameList, ArrayList<String> thicknessOnlyFileNames,
			String currentFilePath) {
		System.out.println("--------------");
		for (String fileNameListing : fileNameList) {
			System.out.println("ISO to add revision # + verify 1-1/2\" thickness: " + fileNameListing);
		}
		System.out.println("--------------");
		for (String thicknessOnlySubListing : thicknessOnlyFileNames) {
			System.out.println("ISO to verify 1-1/2\" thickness ONLY: " + thicknessOnlySubListing);
		}
		System.out.println("--------------");

		while (thicknessOnlyFileNames.size() > thicknessOnlyIterator) {
			ThicknessCheckOnly tCO = new ThicknessCheckOnly();
			tCO.ThicknessCheckerMethodOnly(currentFilePath, thicknessOnlyFileNames.get(thicknessOnlyIterator));
			thicknessOnlyIterator++;
		}
		thicknessOnlyIterator = 0;
		try {
			while (fileNameList.size() > iterator) {
				ThicknessChecker tC = new ThicknessChecker();
				cellValue = tC.thicknessCheckerMethod(currentFilePath, fileNameList.get(iterator));

				SplitFileName fileSplitter = new SplitFileName(fileNameList.get(iterator), cellValue);
				newFileName = fileSplitter.SplitFiles();

				String oldFilePath = currentFilePath + "\\" + fileNameList.get(iterator);
				String newFilePath = currentFilePath + "\\" + newFileName;

				// turns Strings into an actual file path that
				// can be used
				Path oldFileDir = Paths.get(oldFilePath);

				NewFilePath renamingMethod = new NewFilePath();

				renamingMethod.excelNewName(oldFileDir, newFilePath);
				System.out
						.println("old file name: " + fileNameList.get(iterator) + " || new file name: " + newFileName);

				System.out.println("-------------------------------------------------------");
				iterator++;
			}
			System.out.println("Finished files to add revision #'s to.");

		} catch (IndexOutOfBoundsException ioobE) {
			System.out.println("no files to revise.");

			ioobE.printStackTrace();
		}
		iterator = 0;
	}
}