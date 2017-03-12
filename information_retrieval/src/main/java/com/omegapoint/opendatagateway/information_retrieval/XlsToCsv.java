package com.omegapoint.opendatagateway.information_retrieval;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import org.apache.commons.io.FilenameUtils;
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

  static public String xlsx(File inputFile, String ext) {
    // For storing data into CSV files
    String result = null;

    try {
      FileInputStream fis = new FileInputStream(inputFile);

      result = xlsx(fis, ext);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return result;
  }

  static public String xlsx(InputStream stream, String ext) {
    // For storing data into CSV files
    StringBuilder data = new StringBuilder();

		try {
			Workbook workbook = null;

      //String ext = FilenameUtils.getExtension(inputFile.toString());

      if (ext.equalsIgnoreCase("xlsx")) {
        workbook = new XSSFWorkbook(stream);
      } else {
        workbook = new HSSFWorkbook(stream);
      }

			// Get first sheet from the workbook

			int numberOfSheets = workbook.getNumberOfSheets();
			// Iterate through each rows from first sheet

			for (int i = 0; i < numberOfSheets; i++) {
				Sheet sheet = workbook.getSheetAt(0);

				for (int rn = sheet.getFirstRowNum(); rn <= sheet.getLastRowNum(); rn++) {
					Row row = sheet.getRow(rn);
					StringBuilder rowString = new StringBuilder();

					if (row == null) {
						// There is no data in this row, handle as needed
					} else {
						// Row "rn" has data
						for (int cn = 0; cn < row.getLastCellNum(); cn++) {
							Cell cell = row.getCell(cn);
							if (cell == null) {
								rowString.append("-" + ",");
							} else {
								switch (cell.getCellType()) {
									case Cell.CELL_TYPE_BOOLEAN:
										rowString.append(cell.getBooleanCellValue() + ",");
										break;
									case Cell.CELL_TYPE_NUMERIC:
										rowString.append(cell.getNumericCellValue() + ",");

										break;
									case Cell.CELL_TYPE_STRING:
										String cellDataWithoutSpace = cell.getStringCellValue().replaceAll("\n", "-");
                                                                                String cellDataWithoutComma = cellDataWithoutSpace.replaceAll(",", ":");
                                                                          rowString.append(cellDataWithoutComma + ",");
										break;

									case Cell.CELL_TYPE_BLANK:
										rowString.append("" + ",");
										break;
									default:
										rowString.append(cell + ",");
								}
							}
						}
						System.err.println(rowString.toString());
					}
					if (rowString.length() > 0) {
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
