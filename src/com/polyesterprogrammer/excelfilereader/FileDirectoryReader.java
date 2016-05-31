package com.polyesterprogrammer.excelfilereader;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.io.FilenameUtils;

public class FileDirectoryReader {
	// file path from user input
	private String filePath = null;
	// will hold the list of file names in this class to be passed on
	private ArrayList<String> onlySubFileNames1 = new ArrayList<String>();
	private ArrayList<String> onlySubFileNames2 = new ArrayList<String>();
	private ArrayList<String> onlySubFileNames3 = new ArrayList<String>();
	private ArrayList<String> thicknessOnlyFileNames1 = new ArrayList<String>();
	private ArrayList<String> thicknessOnlyFileNames2 = new ArrayList<String>();
	private ArrayList<String> thicknessOnlyFileNames3 = new ArrayList<String>();
	int i;
	Boolean repeatedFile;
	String fileExtension;

	// this method finds all files in a directory and adds them to the array
	public FileDirectoryReader(String filePath) {
		this.filePath = filePath;
	}

	public ArrayList<String> parentDirectoryFileNameArrayMethod(String parentFilePath) {
		try {
			ExcelRegexChecker erc = new ExcelRegexChecker();
			File folder = new File(parentFilePath);
			File[] listOfFiles = folder.listFiles();

			for (File file : listOfFiles) {
				repeatedFile = erc.regexRepeater(file.getName());
				if (file.isFile()) {
					fileExtension = FilenameUtils.getExtension(file.getName());
					//System.out.println("the file extension is: " + fileExtension);
					//System.out.println("i is: " + i);
					if (repeatedFile) {
						System.out.println("File: " + file.getName()
								+ " has already been compiled. Moving to Thickness Checking only.");

						if (!(fileExtension.equals("xlsm")) || file.getName().startsWith("$")
								|| file.getName().startsWith("~") || file.getName().startsWith("~$")) {
							System.out.println("File: " + file.getName() + " is an invalid file. Removed from array.");
						} else {
							thicknessOnlyFileNames1.add(file.getName());
						}

					} else {

						if (!(fileExtension.equals("xlsm")) || file.getName().startsWith("$")
								|| file.getName().startsWith("~") || file.getName().startsWith("~$")) {
							System.out.println("File: " + file.getName() + " is an invalid file. Removed from array.");
						} else {
							onlySubFileNames1.add(file.getName());
						}
					}
				}

			}
			i++;

		} catch (NullPointerException npE) {
			npE.getStackTrace();
			System.out.println(
					"File Path: " + filePath + "is incorrect. Please check that you correctly copied and try again.");

		}
		i = 0;
		return onlySubFileNames1;

	}

	public ArrayList<String> SubFolder1FileNameArrayMethod(String subFilePath1) {
		try {
			ExcelRegexChecker erc = new ExcelRegexChecker();
			File folder = new File(subFilePath1);
			File[] listOfFiles = folder.listFiles();

			for (File file : listOfFiles) {
				repeatedFile = erc.regexRepeater(file.getName());
				if (file.isFile()) {
					fileExtension = FilenameUtils.getExtension(file.getName());
					//System.out.println("the file extension is: " + fileExtension);
					//System.out.println("i is: " + i);
					if (repeatedFile) {
						System.out.println("File: " + file.getName()
								+ " has already been compiled. Moving to Thickness Checking only.");

						if (!(fileExtension.equals("xlsm")) || file.getName().startsWith("$")
								|| file.getName().startsWith("~") || file.getName().startsWith("~$")) {
							System.out.println("File: " + file.getName() + " is an invalid file. Removed from array.");
						} else {
							thicknessOnlyFileNames2.add(file.getName());
						}

					} else {

						if (!(fileExtension.equals("xlsm")) || file.getName().startsWith("$")
								|| file.getName().startsWith("~") || file.getName().startsWith("~$")) {
							System.out.println("File: " + file.getName() + " is an invalid file. Removed from array.");
						} else {
							onlySubFileNames2.add(file.getName());

						}
					}
				}
				i++;
			}

		} catch (NullPointerException npE) {
			npE.getStackTrace();
			System.out.println("File Path: " + subFilePath1
					+ "is incorrect. Please check that you correctly copied and try again.");

		}
		i = 0;
		return onlySubFileNames2;

	}

	public ArrayList<String> subFolder2FileNameArrayMethod(String subFilePath2) {
		try {
			ExcelRegexChecker erc = new ExcelRegexChecker();
			File folder = new File(subFilePath2);
			File[] listOfFiles = folder.listFiles();

			for (File file : listOfFiles) {
				repeatedFile = erc.regexRepeater(file.getName());
				if (file.isFile()) {
					fileExtension = FilenameUtils.getExtension(file.getName());
					//System.out.println("i is: " + i);
					//System.out.println("the file extension is: " + fileExtension);
					if (repeatedFile) {
						System.out.println("File: " + file.getName()
								+ " has already been compiled. Moving to Thickness Checking only.");

						if (!(fileExtension.equals("xlsm")) || file.getName().startsWith("$")
								|| file.getName().startsWith("~") || file.getName().startsWith("~$")) {
							System.out.println("File: " + file.getName() + " is an invalid file. Removed from array.");
						} else {
							thicknessOnlyFileNames3.add(file.getName());
						}

					} else {

						if (!(fileExtension.equals("xlsm")) || file.getName().startsWith("$")
								|| file.getName().startsWith("~") || file.getName().startsWith("~$")) {
							System.out.println("File: " + file.getName() + " is an invalid file. Removed from array.");
						} else {
							onlySubFileNames3.add(file.getName());
						}
					}
				}
				i++;
			}

		} catch (NullPointerException npE) {
			npE.getStackTrace();
			System.out.println(
					"File Path: " + filePath + " is incorrect. Please check that you correctly copied and try again.");

		}
		i = 0;
		return onlySubFileNames3;
	}

	public ArrayList<String> returnThicknessOnlySubFile1() {
		return thicknessOnlyFileNames1;
	}

	public ArrayList<String> returnThicknessOnlySubFile2() {
		return thicknessOnlyFileNames2;
	}

	public ArrayList<String> returnThicknessOnlySubFile3() {
		return thicknessOnlyFileNames3;
	}

}
