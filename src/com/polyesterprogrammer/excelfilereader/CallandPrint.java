package com.polyesterprogrammer.excelfilereader;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class CallandPrint {
	String cellValue = null;
	// the new file name that will replace the existing file name
	String newFileName = null;
	String currentFilePath;
	String newThicknessOnlyFileName;
	ArrayList<String> fileNameList = new ArrayList<String>();
	int iterator = 0;
	int thicknessOnlyIterator = 0;
	String beforeThicknessValue;
	String afterThicknessValue;

	public void callAndPrintMethod(ArrayList<String> fileNameList, ArrayList<String> thicknessOnlyFileNames,
			String currentFilePath, boolean insideFER) {
		// this section shows the list of what will happen to which files
		System.out.println("--------------");
		for (String fileNameListing : fileNameList) {
			System.out.println("ISO to add revision # + verify 1-1/2\" thickness + \"0\" check: " + fileNameListing);
		}
		System.out.println("--------------");
		for (String thicknessOnlySubListing : thicknessOnlyFileNames) {
			System.out.println("ISO to verify 1-1/2\" thickness + \"0\" check ONLY: " + thicknessOnlySubListing);
		}
		System.out.println("--------------");
		// this section shows the list of what will happen to which files
		
		while (thicknessOnlyFileNames.size() > thicknessOnlyIterator) {
			ThicknessCheckOnly tCO = new ThicknessCheckOnly();
			ZeroCheckONLY zCO = new ZeroCheckONLY(thicknessOnlyFileNames.get(thicknessOnlyIterator));
			tCO.ThicknessCheckerMethodOnly(currentFilePath, thicknessOnlyFileNames.get(thicknessOnlyIterator));
			// creates the .pdf object
			ExcelToPDF etPDF = new ExcelToPDF();
			newThicknessOnlyFileName = zCO.CheckForZeroes();
			// System.out.println("new thickness only file name is: " +
			// newThicknessOnlyFileName);
			beforeThicknessValue = tCO.returnBeforeThicknessValue();
			afterThicknessValue = tCO.returnAfterThicknessValue();
			System.out.println(" beforethickness value: " + beforeThicknessValue + "||| afterThicknessValue: "
					+ afterThicknessValue);
			if (insideFER == true) {
				if (beforeThicknessValue.contains(".11") && afterThicknessValue.length() < 4
						&& afterThicknessValue.contains("0.1")) {
					System.out.println(newThicknessOnlyFileName + " PDF being created");
					etPDF.excelToPDF(newThicknessOnlyFileName, currentFilePath);
				}
			}

			String oldFilePath = currentFilePath + "\\" + thicknessOnlyFileNames.get(thicknessOnlyIterator);
			String newFilePath = currentFilePath + "\\" + newThicknessOnlyFileName;

			// turns Strings into an actual file path that
			// can be used
			Path oldFileDir = Paths.get(oldFilePath);

			NewFilePath renamingMethod = new NewFilePath();

			renamingMethod.excelNewName(oldFileDir, newFilePath);

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
				//creates renaming object
				NewFilePath renamingMethod = new NewFilePath();
				// creates the .pdf object
				ExcelToPDF etPDF = new ExcelToPDF();
				renamingMethod.excelNewName(oldFileDir, newFilePath);
				System.out
						.println("old file name: " + fileNameList.get(iterator) + " || new file name: " + newFileName);

				System.out.println("-------------------------------------------------------");
				beforeThicknessValue = tC.returnBeforeThicknessValue();
				afterThicknessValue = tC.returnAfterThicknessValue();
				
				System.out.println(" beforethickness value: " + beforeThicknessValue + "||| afterThicknessValue: "
						+ afterThicknessValue);

				if (insideFER == true) {
					if (beforeThicknessValue.contains(".11") && afterThicknessValue.length() < 4
							&& afterThicknessValue.contains("0.1")) {
						System.out.println(newFileName + " PDF being created");
						etPDF.excelToPDF(newFileName, currentFilePath);
					}
				}

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