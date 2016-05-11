package com.polyesterprogrammer.excelfilereader;

import java.io.File;
import java.util.ArrayList;

public class FileDirectoryReader {
	// file path from user input
	private String filePath = null;
	// will hold the list of file names in this class to be passed on
	private ArrayList<String> onlySubFileNames1 = new ArrayList<String>();
	private ArrayList<String> onlySubFileNames2 = new ArrayList<String>();
	private ArrayList<String> onlySubFileNames3 = new ArrayList<String>();
	int i;
	Boolean repeatedFile;

	// this method finds all files in a directory and adds them to the array
	public FileDirectoryReader(String filePath) {
		this.filePath = filePath;
	}

	public ArrayList<String> parentDirectoryFindFilePath(String parentFilePath) {
		try {
			ExcelRegexChecker erc = new ExcelRegexChecker();
			File folder = new File(parentFilePath);
			File[] listOfFiles = folder.listFiles();

			for (File file : listOfFiles) {
				repeatedFile = erc.regexRepeater(file.getName());
				if (file.isFile()) {

					if (repeatedFile) {
						System.out.println("File: " + file.getName() + " has already been compiled. Skipping.");
					} else {
						onlySubFileNames1.add(file.getName());
						if (!(file.getName().endsWith("xlsm"))) {
							onlySubFileNames1.remove(i);
						}
						i++;
					}

				}

			}

		} catch (NullPointerException npE) {
			npE.getStackTrace();
			System.out.println(
					"File Path: " + filePath + "is incorrect. Please check that you correctly copied and try again.");

		}

		return onlySubFileNames1;

	}
	
	public ArrayList<String> SubFolder1FindFilePath(String subFilePath1){
		try {
			ExcelRegexChecker erc = new ExcelRegexChecker();
			File folder = new File(subFilePath1);
			File[] listOfFiles = folder.listFiles();

			for (File file : listOfFiles) {
				repeatedFile = erc.regexRepeater(file.getName());
				if (file.isFile()) {

					if (repeatedFile) {
						System.out.println("File: " + file.getName() + " has already been compiled. Skipping.");
					} else {
						onlySubFileNames2.add(file.getName());
						if (!(file.getName().endsWith("xlsm"))) {
							onlySubFileNames2.remove(i);
						}
						i++;
					}

				}

			}

		} catch (NullPointerException npE) {
			npE.getStackTrace();
			System.out.println(
					"File Path: " + subFilePath1 + "is incorrect. Please check that you correctly copied and try again.");

		}
		
		return onlySubFileNames2;
		
	}

	public ArrayList<String> subFolder2FindFilePath(String subFilePath2){
		try {
			ExcelRegexChecker erc = new ExcelRegexChecker();
			File folder = new File(subFilePath2);
			File[] listOfFiles = folder.listFiles();

			for (File file : listOfFiles) {
				repeatedFile = erc.regexRepeater(file.getName());
				if (file.isFile()) {

					if (repeatedFile) {
						System.out.println("File: " + file.getName() + " has already been compiled. Skipping.");
					} else {
						onlySubFileNames3.add(file.getName());
						if (!(file.getName().endsWith("xlsm"))) {
							onlySubFileNames3.remove(i);
						}
						i++;
					}

				}

			}

		} catch (NullPointerException npE) {
			npE.getStackTrace();
			System.out.println(
					"File Path: " + filePath + " is incorrect. Please check that you correctly copied and try again.");

		}
		
		return onlySubFileNames3;
	}
	
}
