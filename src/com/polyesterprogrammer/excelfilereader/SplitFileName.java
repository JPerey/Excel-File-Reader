package com.polyesterprogrammer.excelfilereader;

public class SplitFileName {
	String oldFileName;
	String fileParts[];
	String cellValue;
	String newFileName;
	String pulledRevisionNum;
	String dashedRevisionNum;

	public SplitFileName(String fileName, String cellValue) {
		this.oldFileName = fileName;
		this.cellValue = cellValue;
	}

	//this method splits the old file name so that it can be worked on and puts them back together
	public String SplitFiles() {
		if (oldFileName != null && oldFileName.length() > 1) {
			fileParts = oldFileName.split(" ");
			
			ExcelRegexChecker erc = new ExcelRegexChecker();
			System.out.println("The Excel sheet revision data is " + cellValue);
			//R9,Rev9, Revision: 10
			pulledRevisionNum = erc.patternFinder("[R]([a-zA-Z:\\s*]+)?[0-9]+", cellValue);
			System.out.println("TESTING TESTING The pulled revision number is " + pulledRevisionNum);
			dashedRevisionNum = erc.patternReplacer(pulledRevisionNum);


		}
		newFileName = fileParts[0]+ dashedRevisionNum + " " +fileParts[1] + " " +fileParts[2];
		return newFileName;
	}

}
