package com.polyesterprogrammer.excelfilereader;

import java.io.File;
import java.util.ArrayList;

public class FolderLooper {
	// used to just indicate starting point of all file paths
	private String parentFolderDirectory;
	String[] fileListings1;
	String[] fileListings2;
	String[] fileListings3;
	String[] folderListings1;
	String[] folderListings2;
	String[] folderListings3;
	String subFolderDirectory1;
	String subFolderDirectory2;
	String subFolderDirectory3;
	int array1MethodIterator = 0;
	int array2MethodIterator = 0;
	int array3MethodIterator = 0;
	ArrayList<String> subFolderPathArray1 = new ArrayList<String>();
	ArrayList<String> subFolderPathArray2 = new ArrayList<String>();
	ArrayList<String> subFolderPathArray3 = new ArrayList<String>();

	public FolderLooper(String folderDirectory) {
		this.parentFolderDirectory = folderDirectory;
	}

	public ArrayList<String> subFolder1ArrayCreator() {
		System.out.println("Running folderLooperMethod");
		subFolderDirectory1 = parentFolderDirectory;
		File subFile1 = new File(subFolderDirectory1);
		fileListings1 = subFile1.list();
		folderListings1 = new String[fileListings1.length];

		for (String folderName1 : fileListings1) {
			System.out.println("folder being checked: " + folderName1);
			if (new File(subFolderDirectory1 + "\\" + folderName1).isDirectory()) {
				folderListings1[array1MethodIterator] = subFolderDirectory1 + "\\" + folderName1;
				subFolderPathArray1.add(folderListings1[array1MethodIterator]);
				System.out.println("the " + array1MethodIterator + " index of the 1st sub folder array is: "
						+ subFolderPathArray1.get(array1MethodIterator));

			}
			array1MethodIterator++;
		}
		// folderArray2Creator();
		array1MethodIterator = 0;
		return subFolderPathArray1;
	}

	public ArrayList<String> subFolder2ArrayCreator(String array1Directory) {
		subFolderDirectory2 = array1Directory;
		File subFile2 = new File(subFolderDirectory2);
		fileListings2 = subFile2.list();
		folderListings2 = new String[fileListings2.length];
		if (!(subFolderPathArray2.isEmpty())) {
			subFolderPathArray2.clear();
		}

		for (String folderName2 : fileListings2) {
			System.out.println("folder being checked: " + folderName2);
			if (new File(subFolderDirectory2 + "\\" + folderName2).isDirectory()) {
				folderListings2[array2MethodIterator] = subFolderDirectory2 + "\\" + folderName2;
				subFolderPathArray2.add(folderListings2[array2MethodIterator]);
				System.out.println("The " + array2MethodIterator + " index of the 2nd sub folder array is: "
						+ subFolderPathArray2.get(array2MethodIterator));
				System.out.println("iterator is " + array2MethodIterator);
				array2MethodIterator++;
			}
		}
		// folderArray3Creator();
		array2MethodIterator = 0;
		return subFolderPathArray2;
	}

	public ArrayList<String> subFolder3ArrayCreator(String array2Directory) {
		//subFolderDirectory3 = subFolderPathArray2.get(array2Array3Iterator);
		subFolderDirectory3 = array2Directory;
		File subFile3 = new File(subFolderDirectory3);
		fileListings3 = subFile3.list();
		folderListings3 = new String[fileListings3.length];
		if (!(subFolderPathArray3.isEmpty())) {
			subFolderPathArray3.clear();
		}

		for (String folderName3 : fileListings3) {
			System.out.println("folder being checked: " + folderName3);
			if (new File(subFolderDirectory3 + "\\" + folderName3).isDirectory()) {
				folderListings3[array3MethodIterator] = subFolderDirectory3 + "\\" + folderName3;
				subFolderPathArray3.add(folderListings3[array3MethodIterator]);
				System.out.println("The " + array3MethodIterator + " index of the 3rd sub folder array is: "
						+ subFolderPathArray3.get(array3MethodIterator));
				array3MethodIterator++;
			}

		}
		array3MethodIterator = 0;
		return subFolderPathArray3;
	}

}
