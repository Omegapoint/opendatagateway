package com.omegapoint.opendatagateway.information_retrieval;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        main.readData();
    }

    private void readData(){
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            File input = new File(classLoader.getResource("test_input.xls").getFile());
            InputStreamReader reader = new InputStreamReader(new FileInputStream(input), "UTF-8");
            ObjectMapper mapper = new ObjectMapper();
            String pretty = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(CsvToJsonConverter.toJson(reader));
            System.out.println(pretty);
            String data = XlsToCsv.xlsx(input);
            List<Map<?, ?>> report =  CsvToJsonConverter.readObjectsFromCsv(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
