package com.omegapoint.opendatagateway.information_retrieval;

<<<<<<< HEAD
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oracle.javafx.jmx.json.JSONReader;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


=======
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

>>>>>>> 8c89fef9dbf0df7d106ca4e4c5e4e9a4eee30e96
public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        main.readData();
    }

    private void readData(){
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            File input = new File(classLoader.getResource("test_input.xls").getFile());
<<<<<<< HEAD
            InputStreamReader reader = new InputStreamReader(new FileInputStream(input), "UTF-8");
            ObjectMapper mapper = new ObjectMapper();
            String pretty = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(CsvToJsonConverter.toJson(reader));
            System.out.println(pretty);
=======
            String data = XlsToCsv.xlsx(input);
            List<Map<?, ?>> report =  CsvToJsonConverter.readObjectsFromCsv(data);
>>>>>>> 8c89fef9dbf0df7d106ca4e4c5e4e9a4eee30e96
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
