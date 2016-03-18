package com.polyesterprogrammer.excelfilereader;

import java.io.File;
import java.util.ArrayList;

public class FileDirectoryReader {
	// file path from user input
	private String filePath = null;
	// will hold the list of file names in this class to be passed on
	private ArrayList<String> onlyFileNames = new ArrayList<String>();
	int i;
	Boolean repeatedFile;

	// this method finds all files in a directory and adds them to the array
	public FileDirectoryReader(String filePath) {
		this.filePath = filePath;
	}

	public ArrayList<String> FindFilePath() {
		try {
			ExcelRegexChecker erc = new ExcelRegexChecker();
			File folder = new File(filePath);
			File[] listOfFiles = folder.listFiles();

			for (File file : listOfFiles) {
				repeatedFile = erc.regexRepeater(file.getName());
				if (file.isFile()) {

					if (repeatedFile) {
						System.out.println("File: " + file.getName() + " has already been compiled. Skipping.");
					} else {
						onlyFileNames.add(file.getName());
						i++;
					}

				}
			}

		} catch (NullPointerException npE) {
			npE.getStackTrace();
			System.out.println(
					"File Path: " + filePath + "is incorrect. Please check that you correctly copied and try again.");

		}

		return onlyFileNames;

	}

}
