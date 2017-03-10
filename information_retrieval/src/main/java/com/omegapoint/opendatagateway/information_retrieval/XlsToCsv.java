package com.omegapoint.opendatagateway.information_retrieval;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Iterator;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
      //  workbook = new XSSFWorkbook(fis);
      //} else if (ext.equalsIgnoreCase("xls")) {
      workbook = new HSSFWorkbook(stream);
      //}

      // Get first sheet from the workbook

      int numberOfSheets = workbook.getNumberOfSheets();
      Row row;
      Cell cell;
      // Iterate through each rows from first sheet

      for (int i = 0; i < numberOfSheets; i++) {
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();

        while (rowIterator.hasNext()) {
          row = rowIterator.next();
          // For each row, iterate through each columns
          Iterator<Cell> cellIterator = row.cellIterator();
          while (cellIterator.hasNext()) {

            cell = cellIterator.next();

            switch (cell.getCellType()) {
              case Cell.CELL_TYPE_BOOLEAN:
                data.append(cell.getBooleanCellValue() + ",");

                break;
              case Cell.CELL_TYPE_NUMERIC:
                data.append(cell.getNumericCellValue() + ",");

                break;
              case Cell.CELL_TYPE_STRING:
                data.append(cell.getStringCellValue() + ",");
                break;

              case Cell.CELL_TYPE_BLANK:
                data.append("" + ",");
                break;
              default:
                data.append(cell + ",");

            }
          }
          data.append('\n'); // appending new line after each row
        }

      }

    } catch (Exception ioe) {
      ioe.printStackTrace();
    }

    return data.toString();
  }
}
