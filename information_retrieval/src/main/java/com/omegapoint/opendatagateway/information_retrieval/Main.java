package com.omegapoint.opendatagateway.information_retrieval;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        main.readData();
    }

    private void readData(){
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            File input = new File(classLoader.getResource("test_input.xls").getFile());
            String data = xlsx(input);
            List<Map<?, ?>> report =  CsvToJsonConverter.readObjectsFromCsv(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


  static String xlsx(File inputFile) {
    // For storing data into CSV files
    StringBuilder data = new StringBuilder();

    try {
      // Get the workbook object for XLSX file
      FileInputStream fis = new FileInputStream(inputFile);
      Workbook workbook = null;

      String ext = FilenameUtils.getExtension(inputFile.toString());

      if (ext.equalsIgnoreCase("xlsx")) {
        workbook = new XSSFWorkbook(fis);
      } else if (ext.equalsIgnoreCase("xls")) {
        workbook = new HSSFWorkbook(fis);
      }

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
