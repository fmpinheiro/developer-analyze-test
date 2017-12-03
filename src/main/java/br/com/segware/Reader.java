package br.com.segware;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Reader {

   
        private final String csvFile = "developer-test-file-analyze-master/src/test/java/br/com/segware/relatorio.csv";
        private BufferedReader br = null;
        private String line = "";
        private String cvsSplitBy = ",";
        private List<String[]> output = new ArrayList<>();

        public List<String[]> inputReader(){
        try {
            br = new BufferedReader(new FileReader(csvFile));
            String temp[] = null;
            while ((line = br.readLine()) != null) {
            	temp = line.split(cvsSplitBy);
            	output.add(temp);               
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Arquivo nao encontrado");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro");
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
		return output;}
        
        
        public List<String[]> getList(){
        	return output;
        }
}

