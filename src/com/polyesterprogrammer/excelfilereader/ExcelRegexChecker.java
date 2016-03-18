package com.polyesterprogrammer.excelfilereader;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelRegexChecker {
	String fileName;
	String pulledRevisionNumber;
	String newRevisionNumber;

	// this method finds the revision number in the old file name and ooutputs
	// it
	public String patternFinder(String str2Find, String isoNum) {
		String pulledRevisionNum = null;

		Pattern rFinder = Pattern.compile(str2Find);
		Matcher patternFind = rFinder.matcher(isoNum);

		if (patternFind.find()) {

			pulledRevisionNum = patternFind.group();

		} else {
			System.out.println("you a dumbass");
		}

		return pulledRevisionNum;

	}

	// this method takes the revision number and removes "R" and replaces with
	// "-"
	public String patternReplacer(String str2Replace) {
		String newFileName;

		Pattern replace = Pattern.compile("\\s[Ra-zA-Z\\:] ");

		Matcher regexMatcher = replace.matcher(str2Replace);
		newFileName = regexMatcher.replaceAll("-");

		return newFileName;
	}

	public Boolean regexRepeater(ArrayList<String> fileNames, int count) {
		Pattern fileRepeatChecker = Pattern.compile("(\\d{1,}/-]{4,})");
		Matcher regexRepeatFileMatcher = fileRepeatChecker.matcher(fileNames.get(count));

		while (regexRepeatFileMatcher.find()) {
				//System.out.println(regexRepeatFileMatcher.group());
		//	System.out.println("Files have already been compiled. Shutting program down");
			//System.exit(0);
			
		}

		return null;

	}
}
