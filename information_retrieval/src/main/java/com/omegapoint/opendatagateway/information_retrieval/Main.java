package com.omegapoint.opendatagateway.information_retrieval;

import java.io.File;
import java.io.IOException;
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
            String data = XlsToCsv.xlsx(input);
            List<Map<?, ?>> report =  CsvToJsonConverter.readObjectsFromCsv(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
