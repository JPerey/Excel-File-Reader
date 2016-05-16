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

	public void callAndPrintMethod(ArrayList<String> fileNameList, String currentFilePath) {
		System.out.println("--------------");
		for (String fileNameListing : fileNameList) {
			System.out.println("ISO to be revised: " + fileNameListing);
		}
		System.out.println("--------------");
		try{
		do{
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
		System.out.println("old file name: " + fileNameList.get(iterator) + " || new file name: " + newFileName);

		System.out.println("-------------------------------------------------------");
		iterator++;
		}while (iterator < fileNameList.size());
		}catch(IndexOutOfBoundsException ioobE){
			System.out.println("no files to revise.");
		}
		iterator = 0;
	}
}