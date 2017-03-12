package com.omegapoint.opendatagateway.information_retrieval;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class XlsToCsv {

	static public String xlsx(File inputFile, String type) {
		// For storing data into CSV files
		String result = null;

		try {
			FileInputStream fis = new FileInputStream(inputFile);

			result = xlsx(fis, type);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String xlsx(InputStream stream, String type) {
		// For storing data into CSV files
		StringBuilder data = new StringBuilder();

		try {
			Workbook workbook = WorkbookFactory.create(stream);

			workbook.setMissingCellPolicy(Row.CREATE_NULL_AS_BLANK);

			// Iterate through each rows from first sheet
			Sheet sheet = workbook.getSheetAt(0);
			for (int rn = sheet.getFirstRowNum(); rn <= sheet.getLastRowNum(); rn++) {
				Row row = sheet.getRow(rn);
				String rowString = parseRow(row);
				if (rowString.length() > 0) {
					data.append(rowString).append('\n'); // appending new line after each row
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}catch (InvalidFormatException e) {
			e.printStackTrace();
		}

		return data.toString();
	}

	private static String parseRow(Row row) {
		StringBuilder rowString = new StringBuilder();
		if (row != null) {
			for (int cn = 0; cn < row.getLastCellNum(); cn++) {
				Cell cell = row.getCell(cn);
				rowString.append(parseCell(cell));
				if (cn < row.getLastCellNum() - 1) {
					rowString.append("|");
				}
			}
		}
		return rowString.toString();
	}

	private static String parseCell(Cell cell) {
		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_BOOLEAN:
				return Boolean.toString(cell.getBooleanCellValue());
			case Cell.CELL_TYPE_NUMERIC:
				return Double.toString(cell.getNumericCellValue());
			case Cell.CELL_TYPE_STRING:
				return cell.getStringCellValue().replaceAll("\n", "-").trim();
			case Cell.CELL_TYPE_BLANK:
				return "";
			default:
				return cell.toString();
		}
	}
}
