package com.polyesterprogrammer.excelfilereader;

import java.io.File;
import java.util.ArrayList;

public class FileDirectoryReader {
	private String filePath = null;
	private ArrayList<String> onlyFileNames = new ArrayList<String>();
	
	public FileDirectoryReader(String filePath){
		this.filePath = filePath;
	}

	 public ArrayList<String> FindFilePath(){
	//public static void main(String[] args){
		File folder = new File(filePath);
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
		    if (file.isFile()) {
		        //System.out.println(onlyFileNames.add(file.getName()));
		    	onlyFileNames.add(file.getName());
		    	System.out.println(onlyFileNames.size());
		    }
		}
		System.out.println(listOfFiles[0]);
		System.out.println(listOfFiles[1]);
		
		System.out.println(onlyFileNames.get(0));
		System.out.println(onlyFileNames.get(1));
		System.out.println(onlyFileNames.get(2));
		return onlyFileNames;
		


	
	}
	
}
