package com.polyesterprogrammer.excelfilereader;

public class SplitFileName {
	String oldFileName;
	String fileParts[];
	String newFileSegment;
	String nextFileParts[];
	int converted;
	String cellValue;
	String newFileName;
	String nextFileName;
	
	public SplitFileName(String fileName, String cellValue){
		this.oldFileName = fileName; 
		this.cellValue = cellValue;
	}
	
	public String SplitFiles(){
		if (oldFileName != null && oldFileName.length() > 1) {
			fileParts = oldFileName.split(" ");
			cellValue = cellValue.substring(cellValue.length()-1);
			newFileSegment = fileParts[0] + "-" + cellValue;
			}
		newFileName = newFileSegment + " " + fileParts[1] + " " + fileParts[2];
		System.out.println("New file name:" + newFileName);
	return newFileName;

}
	
	public String GetNewFileLooper(){
		//starting: 101-008-005
		nextFileParts = fileParts[0].split("-");
		//after NFP[0] = 101; NFP[1] = 008; NFP[2] = 005;
		converted = Integer.parseInt(nextFileParts[2]);
		//MIGHT NEED TO ADD IF STATEMENT FOR 00's and 0's
		System.out.println("before:" +converted);
		if(converted<9){
			converted++;
			nextFileParts[2] = "00" + Integer.toString(converted);
		}else{
			converted++;
			nextFileParts[2] = "0" + Integer.toString(converted);
		}
		
		System.out.println("after:" + converted);
		nextFileName = nextFileParts[0] + "-" + nextFileParts[1] + "-" + nextFileParts[2]+ " " + fileParts[1] + " " + fileParts[2];
		
		
		return nextFileName;
	}
}
