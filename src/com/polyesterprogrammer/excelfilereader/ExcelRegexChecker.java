package com.polyesterprogrammer.excelfilereader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelRegexChecker {
	String fileName;
	String pulledRevisionNumber;
	String newRevisionNumber;
	Boolean repeatedFile;
	int count = 0;

	// this method finds the rev number in the old file name and outputs it
	public String patternFinder(String str2Find, String isoNum) {
		String pulledRevisionNum = null;

		Pattern rFinder = Pattern.compile(str2Find);
		Matcher patternFind = rFinder.matcher(isoNum);
		
		try{
		if (patternFind.find()) {

			pulledRevisionNum = patternFind.group();

		} else {
			
			System.out.println("Sheet does not have a Revision number indicated in cell. Please locate ISO Rev and enter in ISO");
		}}catch(NullPointerException npE){
			System.out.println("Program closing until ISO revision is entered");
			System.exit(0);
		}

		return pulledRevisionNum;

	}

	// this method takes the revision number and removes "R" and replaces with
	// "-"
	public String patternReplacer(String str2Replace) {
		String newFileName;

		Pattern replace = Pattern.compile("[R]([a-zA-Z:\\s*]+)?");

		Matcher regexMatcher = replace.matcher(str2Replace);
		newFileName = regexMatcher.replaceAll("-");

		return newFileName;
	}

	public Boolean regexRepeater(String fileName) {
		// Pattern used to find repeated files
		Pattern fileRepeatChecker = Pattern.compile("[(0-9)]{0,}[(A-Z)]?[(0-9)]{0,}[\\-]"); 
		Matcher regexRepeatFileMatcher = fileRepeatChecker.matcher(fileName);

		while (regexRepeatFileMatcher.find()) {
			count++;
			if (count > 2) {
				repeatedFile = true;
			} else {
				repeatedFile = false;
			}

		}
		count = 0;

		return repeatedFile;

	}
}
