package com.polyesterprogrammer.excelfilereader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ThicknessChecker {

	public String thicknessCheckerMethod(String filePath, String fileName) {
		Row excelRow;
		Row thicknessRow;
		Row sheetCheckerRow;
		Cell excelCell = null;
		Cell thicknessCell = null;
		Cell sheetCheckerCell;
		double thicknessValue = 0;
		String newSheetFormula = "IF(D24<16000,0.1,IF($J$19>400,0.1,0.07))";
		String cellValue = null;
		

		try {
			FileInputStream inp = new FileInputStream(filePath + "\\" + fileName);
			XSSFWorkbook wb = new XSSFWorkbook(inp);
			// this chooses which workbook you start at
			XSSFSheet sheetChecker = wb.getSheetAt(0);
			XSSFSheet sheetThickness = wb.getSheetAt(0);
			XSSFSheet sheetRevision = wb.getSheetAt(0);
			// starting cell position to check new or old sheet
			sheetCheckerRow = sheetChecker.getRow(0);// row
			sheetCheckerCell = sheetCheckerRow.getCell(9);// cell
			System.out.println("ISO being formatted: " + fileName);

			// NEW SHEET SECTION
			if (sheetCheckerCell.toString().equals("filename: ")) {
				System.out.println("New Sheet");
				excelRow = sheetRevision.getRow(6);
				excelCell = excelRow.getCell(3);
				sheetCheckerRow = sheetChecker.getRow(18);
				sheetCheckerCell = sheetCheckerRow.getCell(1);
				if (sheetCheckerCell.toString().equals("Design Press' (P) ")) {
					thicknessRow = sheetThickness.getRow(16);
					thicknessCell = thicknessRow.getCell(18);
					thicknessCell.setCellType(Cell.CELL_TYPE_FORMULA);
					thicknessCell.setCellFormula(newSheetFormula);

				}

			}

			// OLD SHEET SECTION
			sheetCheckerRow = sheetChecker.getRow(2); // specific row
			sheetCheckerCell = sheetCheckerRow.getCell(2);// specific column
			if (sheetCheckerCell.toString().equals("filename: ")) {
				System.out.println("Old Sheet");
				sheetCheckerRow = sheetChecker.getRow(0);
				sheetCheckerCell = sheetCheckerRow.getCell(0);
				// Old 5D Bend Sheet
				if (sheetCheckerCell.toString()
						.equals("Tmin-Req for Intrados of 5D Bends in Piping Circuits (Yr 2000 & later)")) {
					System.out.println("!5D Bend ISO!");
					// setting excel revision cells
					excelRow = sheetRevision.getRow(5);
					excelCell = excelRow.getCell(2);
					// setting excel revision cells
					thicknessRow = sheetThickness.getRow(24);
					thicknessCell = thicknessRow.getCell(6);
					thicknessCell.setCellType(Cell.CELL_TYPE_NUMERIC);
					thicknessValue = thicknessCell.getNumericCellValue();
					// ---------Pipe Thickness checker
					if (thicknessValue == .11) {
						// deleting value inside cell, and replacing with .1
						thicknessCell.setCellValue(0.100);
					}

				}
				// Old Tmin Basis Sheet
				if (sheetCheckerCell.toString().endsWith("Circuits")) {
					// setting excel revision cells
					excelRow = sheetRevision.getRow(5);
					excelCell = excelRow.getCell(2);
					// setting excel revision cells
					thicknessRow = sheetThickness.getRow(25);
					thicknessCell = thicknessRow.getCell(5);
					thicknessCell.setCellType(Cell.CELL_TYPE_NUMERIC);
					thicknessValue = thicknessCell.getNumericCellValue();
					// ---------Pipe Thickness checker
					if (thicknessValue == .11) {
						// setting value to .100
						thicknessCell.setCellValue(0.100);
					}

				}

			}

			if (excelCell.getCellType() == Cell.CELL_TYPE_STRING) {
				cellValue = excelCell.toString();
			} else if (excelCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				System.out.println("The cell equals: " + excelCell.toString());
				System.out.println("The row index is: " + excelCell.getRowIndex());
				System.out.println("The column index is: " + excelCell.getColumnIndex());
				System.out.println("Wrong cell");
			}
			//outputs the piping thickness
			System.out.println("1-1/2\" Piping THK: " + thicknessCell);
			// closes scanner object
			inp.close();
			// file path for new excel file to save to
			FileOutputStream outputFile = new FileOutputStream(new File(filePath + "\\" + fileName));
			// writes thickness changes to new excel file
			wb.write(outputFile);
			// closes excel file
			outputFile.close();
			// closes excel reader stream
			wb.close();
		} catch (FileNotFoundException fnfE) {
			System.out.println("File does not exist.");
			fnfE.getStackTrace();
		} catch (IOException ioE) {
			System.out.println("File Path: " + filePath + " cannot be found. Please check file path and try again. ");
			ioE.printStackTrace();
		} catch (IndexOutOfBoundsException ioobE) {
			System.out.println("All files in directory have already been edited.");
			// ioobE.printStackTrace();
		}
		return cellValue;
	}

}
