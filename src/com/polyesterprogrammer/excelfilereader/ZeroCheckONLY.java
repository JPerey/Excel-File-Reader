package com.polyesterprogrammer.excelfilereader;

public class ZeroCheckONLY {
		String oldFileName;
		String fileParts[];
		String cellValue;
		String newFileName;
		String firstFilePart[];
		int firstFilePartCount = 0;
		int count = 0;

		public ZeroCheckONLY(String fileName) {
			this.oldFileName = fileName;
		}

		// this method splits the old file name so that it can be worked on and puts
		// them back together
		public String CheckForZeroes() {
			if (oldFileName != null && oldFileName.length() > 1) {
				fileParts = oldFileName.split(" |_");
			}
			for (String filePart : fileParts) {

				if (count < 1) {
					firstFilePart = filePart.split("-");				
					if(firstFilePart[firstFilePartCount].length() < 4){
						newFileName = "0" + filePart;
					}
					else{
						newFileName = filePart;
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
