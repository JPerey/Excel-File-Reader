package com.polyesterprogrammer.excelfilereader;

public class SplitFileName {
	String oldFileName;
	String fileParts[];
	String cellValue;
	String newFileName;
	String pulledRevisionNum;
	String dashedRevisionNum;
	String firstFilePart[];
	int firstFilePartCount = 0;
	int count = 0;

	public SplitFileName(String fileName, String cellValue) {
		this.oldFileName = fileName;
		this.cellValue = cellValue;
	}

	// this method splits the old file name so that it can be worked on and puts
	// them back together
	public String SplitFiles() {
		if (oldFileName != null && oldFileName.length() > 1) {
			fileParts = oldFileName.split(" |_");

			ExcelRegexChecker erc = new ExcelRegexChecker();
			// R9,Rev9, Revision: 10
			pulledRevisionNum = erc.patternFinder("[Rr]([a-zA-Z:\\s*]+)?[0-9]+", cellValue);
			dashedRevisionNum = erc.patternReplacer(pulledRevisionNum);

		}
		for (String filePart : fileParts) {

			if (count < 1) {
				firstFilePart = filePart.split("-");				
				if(firstFilePart[firstFilePartCount].length() < 4){
					newFileName = "0" + filePart + dashedRevisionNum;
				}
				else{
					newFileName = filePart + dashedRevisionNum;
				}
					
				} else {
					newFileName = newFileName + " " + filePart;
				}
				count++;
			}
		
		count = 0;
		return newFileName;
	}

}
