package com.polyesterprogrammer.excelfilereader;

/*
 * UPDATE 5/25/2016: This class also adds "0" to beginning of ISO Cell
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ThicknessCheckOnly {
	Row thicknessRow;
	Row sheetCheckerRow;
	Row isoRowZero;
	Cell sheetCheckerCell;
	Cell thicknessCell = null;
	Cell isoCellZero = null;
	String isoCellName = null;
	String isoCellArray[];
	String thicknessValue;
	String newSheetFormula = "IF(D24<16000,0.1,IF($J$19>400,0.1,0.07))";

	public void ThicknessCheckerMethodOnly(String filePath, String fileName) {

		try {
			System.out.println("ISO being formatted: " + fileName);
			FileInputStream inp = new FileInputStream(filePath + "\\" + fileName);
			XSSFWorkbook wb = new XSSFWorkbook(inp);
			XSSFSheet sheetChecker = wb.getSheetAt(0);
			XSSFSheet sheetThickness = wb.getSheetAt(0);
			// starting cell position to check new or old sheet
			sheetCheckerRow = sheetChecker.getRow(0);// row
			sheetCheckerCell = sheetCheckerRow.getCell(9);// cell

			// NEW SHEET SECTION
			if (sheetCheckerCell.toString().equals("filename: ")) {
				System.out.println("New Sheet");
				sheetCheckerRow = sheetChecker.getRow(18);
				sheetCheckerCell = sheetCheckerRow.getCell(1);

				// checking file name for 0
				isoRowZero = sheetChecker.getRow(6);
				isoCellZero = isoRowZero.getCell(3);
				isoCellName = isoCellZero.toString();
				isoCellArray = isoCellName.split("-");
				if (isoCellArray[0].length() < 4) {
					isoCellZero.setCellValue("0" + isoCellName);
				}
				// checking file name for 0

				if (sheetCheckerCell.toString().equals("Design Press' (P) ")) {
					thicknessRow = sheetThickness.getRow(16);
					thicknessCell = thicknessRow.getCell(18);
					thicknessValue = thicknessCell.toString();
					// ---------Pipe Thickness checker
					if (thicknessValue.contains(".11") || thicknessValue.contains(".1")) {
						// deleting value inside cell, and replacing with .1
						thicknessCell.setCellValue(0.100);
					} else if (thicknessValue.contains(".07")) {
						thicknessCell.setCellValue(0.07);
					}

				}

			}

			// OLD SHEET SECTION
			sheetCheckerRow = sheetChecker.getRow(2); // specific row
			sheetCheckerCell = sheetCheckerRow.getCell(2);// specific column
			if (sheetCheckerCell.toString().equals("filename:") || sheetCheckerCell.toString().equals("filename: ")) {
				System.out.println("Old Sheet");
				sheetCheckerRow = sheetChecker.getRow(0);
				sheetCheckerCell = sheetCheckerRow.getCell(0);

				// Old 5D Bend Sheet
				if (sheetCheckerCell.toString()
						.equals("Tmin-Req for Intrados of 5D Bends in Piping Circuits (Yr 2000 & later)")
						|| sheetCheckerCell.toString().equals(
								"\"B31.3\" Tmin-Req for Intrados of 5D Bends in Piping Circuits (Yr 2000 & later)")) {
					System.out.println("!5D Bend ISO!");

					// checking file name for 0
					isoRowZero = sheetChecker.getRow(5);
					isoCellZero = isoRowZero.getCell(2);
					isoCellName = isoCellZero.toString();
					isoCellArray = isoCellName.split("-");
					if (isoCellArray[0].length() < 4) {
						isoCellZero.setCellValue("0" + isoCellName);
					}
					// checking file name for 0

					thicknessRow = sheetThickness.getRow(24);
					thicknessCell = thicknessRow.getCell(6);

					thicknessValue = thicknessCell.toString();
					// ---------Pipe Thickness checker
					if (thicknessValue.contains(".11") || thicknessValue.contains(".1")) {
						// deleting value inside cell, and replacing with .1
						thicknessCell.setCellValue(0.100);
					} else if (thicknessValue.contains(".07")) {
						thicknessCell.setCellValue(.07);
					}

					/*
					 * thicknessCell.setCellType(Cell.CELL_TYPE_NUMERIC);
					 * thicknessValue = thicknessCell.getNumericCellValue(); //
					 * ---------Pipe Thickness checker if (thicknessValue ==
					 * .11) { // deleting value inside cell, and replacing with
					 * .1 thicknessCell.setCellValue(0.100); }
					 */

				}
				// Old Tmin Basis Sheet
				if (sheetCheckerCell.toString().endsWith("Circuits")) {

					// checking file name for 0
					isoRowZero = sheetChecker.getRow(5);
					isoCellZero = isoRowZero.getCell(2);
					isoCellName = isoCellZero.toString();
					isoCellArray = isoCellName.split("-");
					if (isoCellArray[0].length() < 4) {
						isoCellZero.setCellValue("0" + isoCellName);
					}
					// checking file name for 0

					thicknessRow = sheetThickness.getRow(25);
					thicknessCell = thicknessRow.getCell(5);

					thicknessValue = thicknessCell.toString();
					// ---------Pipe Thickness checker
					if (thicknessValue.contains(".11") || thicknessValue.contains(".1")) {
						// deleting value inside cell, and replacing with .1
						thicknessCell.setCellValue(0.100);
					} else if (thicknessValue.contains(".07")) {
						thicknessCell.setCellValue(.07);
					}

					/*
					 * thicknessCell.setCellType(Cell.CELL_TYPE_NUMERIC);
					 * thicknessValue = thicknessCell.getNumericCellValue(); //
					 * ---------Pipe Thickness checker if (thicknessValue ==
					 * .11) { // setting value to .100
					 * thicknessCell.setCellValue(0.100); }
					 * 
					 * }
					 */
				}

			}

			// outputs the piping thickness
			System.out.println("1-1/2\" Piping THK: " + thicknessCell);
			System.out.println("old ISO Cell: " + isoCellName + " || new ISO Cell: " + isoCellZero.toString());
			System.out.println("-----------------------------------------");
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
			// TODO Auto-generated catch block
			fnfE.printStackTrace();
		} catch (IOException ioE) {
			// TODO Auto-generated catch block
			ioE.printStackTrace();
		} catch (NullPointerException npE) {
			System.out.println(
					"File contents not detected. Please check that the excel \"tab\" is set correctly. Set \"MFG-510 Sheet\" to first tab and save.");
		}

	}

	public String returnBeforeThicknessValue(){
		return thicknessValue;
	}
	public String returnAfterThicknessValue() {
		return thicknessCell.toString();

	}
}
