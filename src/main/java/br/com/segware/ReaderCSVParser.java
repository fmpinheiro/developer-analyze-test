package br.com.segware;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.opencsv.CSVReader;

public class ReaderCSVParser {
	private final String csvFile = "developer-test-file-analyze-master/src/test/java/br/com/segware/relatorio.csv";
	private List<String[]> output;
	public List<String[]> inputReader() {
		
		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(csvFile));
			String[] line;
			while ((line = reader.readNext()) != null) {
            	output.add(line);   
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return output;

	}
	
	 public List<String[]> getList(){
     	return output;
     }
}
