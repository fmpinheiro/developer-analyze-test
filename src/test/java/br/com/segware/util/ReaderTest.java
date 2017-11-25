package br.com.segware.util;

import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderTest {
	
	private static final String SEPARATOR = ",";
	
	private static Path pathName;
	
	private static final String RESOURCE_DIR = "src/test/java/";
	
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		pathName = Paths.get(RESOURCE_DIR + "file.csv");
		
		final Map<String, List<String[]>> fileContent = new HashMap<>();
		fileContent.put("content", createContent());
		createCsvFile(pathName.toAbsolutePath().toString(), fileContent);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		Files.delete(pathName);
	}
	
	@Before
	public void setUp() throws Exception {
		System.setErr(new PrintStream(errContent));
	}

	@Test
	public void shouldReadACsvFileWithContent() {
		final Map<String, List<String[]>> content = Reader.readCsv(pathName.toAbsolutePath().toString(), SEPARATOR);
		assertNotNull(content.get("content"));
	}
	
	@Test
	public void shouldPrintAnErrorCannotReadThisFile() {
		final String anotherFile = RESOURCE_DIR + "relatoriox.csv";
		Reader.readCsv(anotherFile, SEPARATOR);
		Assert.assertTrue(errContent.toString().contains("Cannot read this file."));
	}

	private static List<String[]> createContent() {
		final String[] row1 = new String[] {"1", "0001", "E130", "ALARME", "2014-06-25 12:00:00", "2014-06-25 12:05:32", "AT01"};
		final String[] row2 = new String[] {"2", "0002", "E131", "ARME", "2014-06-25 12:01:03", "2014-06-25 12:05:36", "AT02"};
		
		final List<String[]> content = new ArrayList<>();
		content.add(row1);
		content.add(row2);
		return content;
	}
	
	private static void createCsvFile(final String fileName, final Map<String, List<String[]>> fileContent) {
		try (FileWriter writer = new FileWriter(fileName)) {
			final List<String[]> content = fileContent.get("content");
			writeColumns(writer, content);
		} catch (IOException e) {
			Message.printError(e.getMessage());
		}
	}

	private static void writeColumns(final FileWriter writer, final List<String[]> record) throws IOException {
		if (record == null) {
			Message.printError("There's no content to write.");
			return;
		}
		
		for (String[] columns : record) {
			writer.append(String.join(",", columns));
			writer.append("\n");
		}
	}
}
