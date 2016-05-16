package com.polyesterprogrammer.excelfilereader;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelRegexChecker {
	String fileName;
	String pulledRevisionNum = null;
	int pulledRevisionNumInt = 0;
	// checks if a file has already been edited
	Boolean repeatedFile;
	int count = 0;
	// checks if the inputed revision number from user is actually a number
	boolean incorrect = true;
	Scanner input = new Scanner(System.in);

	// this method finds the revision number(ex:R2) in the old file name and
	// outputs it
	public String patternFinder(String str2Find, String isoNum) {

		Pattern rFinder = Pattern.compile(str2Find);
		Matcher patternFind = rFinder.matcher(isoNum);

		try {
			if (patternFind.find()) {

				pulledRevisionNum = patternFind.group();

			} else {
				// user can input revision number manually if it cannot be found
				do {
					try {
						System.out.println(
								"Sheet does not have a Revision number indicated in cell. Please locate ISO Rev and enter in ISO");
						System.out.println("What is ISO: " + isoNum + "'s revision number(ex: 1,2,3,4,11, etc.)?");
						pulledRevisionNumInt = Integer.parseInt(input.nextLine());

						if (pulledRevisionNumInt >= 1) {
							incorrect = false;
						}

					} catch (Exception e) {
						System.out.println("Incorrect value entered. Please enter a number");
					}
				} while (incorrect);
			}
			incorrect = true;
			pulledRevisionNum = "R" + pulledRevisionNumInt;
		} catch (NullPointerException npE) {
			System.out.println("Program closing until ISO revision is entered");
			System.exit(0);
		}

		return pulledRevisionNum;

	}

	// this method takes the revision number and removes "R" and replaces with
	// "-"
	public String patternReplacer(String str2Replace) {
		String newFileName;

		Pattern replace = Pattern.compile("[Rr]([a-zA-Z:\\s*]+)?");

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
