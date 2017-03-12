package com.omegapoint.opendatagateway.information_retrieval;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by matper on 2017-03-10.
 */
public class XlsToCsv {

	static public String xlsx(File inputFile) {
		// For storing data into CSV files
		String result = null;

		try {
			FileInputStream fis = new FileInputStream(inputFile);

			result = xlsx(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

	static public String xlsx(InputStream stream) {
		// For storing data into CSV files
		StringBuilder data = new StringBuilder();

		try {
			Workbook workbook = null;

			//String ext = FilenameUtils.getExtension(inputFile.toString());

			//if (ext.equalsIgnoreCase("xlsx")) {
			//workbook = new XSSFWorkbook(fis);
			//} else if (ext.equalsIgnoreCase("xls")) {
			try {
				workbook = new HSSFWorkbook(stream);
			} catch (OfficeXmlFileException e) {
				System.out.println("Message:" + e.getMessage());
			}

			if (workbook == null) {
				System.out.println("Using XSSF instead of HSSF");
				workbook = new XSSFWorkbook(stream);
			}
			//}

			// Get first sheet from the workbook

			workbook.setMissingCellPolicy(Row.CREATE_NULL_AS_BLANK);
			int numberOfSheets = workbook.getNumberOfSheets();
			// Iterate through each rows from first sheet

			for (int i = 0; i < numberOfSheets; i++) {
				Sheet sheet = workbook.getSheetAt(0);

				for (int rn = sheet.getFirstRowNum(); rn <= sheet.getLastRowNum(); rn++) {
					Row row = sheet.getRow(rn);
					StringBuilder rowString = new StringBuilder();

					if (row == null) {
						// There is no data in this row, handle as needed
						System.err.println("======== NULL ROW");
					} else {
						// Row "rn" has data
						for (int cn = 0; cn < row.getLastCellNum(); cn++) {
							Cell cell = row.getCell(cn);
							if (cell == null) {

								System.err.println("-------- NULL CELL");
								rowString.append("-");
							} else {
								switch (cell.getCellType()) {
									case Cell.CELL_TYPE_BOOLEAN:
										rowString.append(cell.getBooleanCellValue());
										break;
									case Cell.CELL_TYPE_NUMERIC:
										rowString.append(cell.getNumericCellValue());

										break;
									case Cell.CELL_TYPE_STRING:
										String cellData = cell.getStringCellValue().replaceAll("\n", "-").trim();
										rowString.append(cellData.length()<1?"-":cellData);
										break;

									case Cell.CELL_TYPE_BLANK:
										rowString.append("+");
										break;
									default:
										rowString.append(cell);
								}
							}
							if (cn < row.getLastCellNum()-1) {
								rowString.append("|");
							}
						}
					}
					if (rowString.length() > 0) {
						System.err.println(rowString.toString());
						data.append(rowString.toString()).append('\n'); // appending new line after each row
					}
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return data.toString();
	}
}
