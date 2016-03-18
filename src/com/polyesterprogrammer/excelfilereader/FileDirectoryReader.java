package com.polyesterprogrammer.excelfilereader;

import java.io.File;
import java.util.ArrayList;

public class FileDirectoryReader {
	// file path from user input
	private String filePath = null;
	// will hold the list of file names in this class to be passed on
	private ArrayList<String> onlyFileNames = new ArrayList<String>();
	int i;

	//this method finds all files in a directory and adds them to the array
	public FileDirectoryReader(String filePath) {
		this.filePath = filePath;
	}

	public ArrayList<String> FindFilePath() {
		try {
			ExcelRegexChecker erc = new ExcelRegexChecker();
			File folder = new File(filePath);
			File[] listOfFiles = folder.listFiles();

			for (File file : listOfFiles) {
				if (file.isFile()) {
					//add check for 3 dashes here
					onlyFileNames.add(file.getName());
					System.out.println("TESTING TESTING file names: " + onlyFileNames.get(i));
					erc.regexRepeater(onlyFileNames, i);
					i++;
				}
			}
		} catch (NullPointerException npE) {
			System.out.println(
					"File Path: " + filePath + "is incorrect. Please check that you correctly copied and try again.");
		}

		return onlyFileNames;

	}

}
