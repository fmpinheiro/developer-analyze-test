package br.com.segware.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reader {

	/**
	 * @param fileName - The entire path of the file.
	 * @param separator - The separator to separate in columns.
	 * @return A Map<String, List<String[]> which key is content and value is the columns from csv file.
	 */
	public static Map<String, List<String[]>> readCsv(final String fileName, final String separator) {
		final Path path = Paths.get(fileName);
		final Map<String, List<String[]>> fullContent = new HashMap<>();
		
		try (BufferedReader reader = Files.newBufferedReader(path)) {
			final List<String[]> content = new ArrayList<>();
			
			String line;
			while ((line = reader.readLine()) != null) {
				final String[] columns = line.split(separator);
				content.add(columns);
			}
			
			fullContent.put(Constants.CONTENT, content);
		} catch (IOException e) {
			Message.printError("Cannot read this file." + e.getMessage());
		}

		return fullContent;
	}
	

}
