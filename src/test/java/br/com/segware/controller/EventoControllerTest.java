package br.com.segware.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.segware.model.Evento;
import br.com.segware.util.Message;

public class EventoControllerTest {

	private static Path path;
	private static final String RESOURCE_DIR = "src/test/resources/";
	private static final String FILENAME = RESOURCE_DIR + "test.csv";
	
	private Controller<Evento> controller;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		final Map<String, List<String[]>> fileContent = new HashMap<>();
		fileContent.put("content", createContent());
		
		final File directory = new File(RESOURCE_DIR);
		if (!directory.exists()) {
			directory.mkdirs();
		}
		path = Paths.get(FILENAME);
		createCsvFile(path.toAbsolutePath().toString(), fileContent);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		Files.delete(path);
	}

	@Before
	public void setUp() throws Exception {
		controller = new EventoController(FILENAME);
	}
	
	@Test
	public void shouldLoadCsvFile() {
		controller.loadCsv();
		
		final List<Evento> events = controller.findAll();
		Assert.assertEquals(2, events.size());
	}

	private static List<String[]> createContent() {
		final String[] row1 = new String[] {"1", "0001", "E130", "ALARME", "2014-06-25 12:00:00", "2014-06-25 12:05:32", "AT01"};
		final String[] row2 = new String[] {"2", "0002", "E131", "ARME", "2014-06-25 12:01:03", "2014-06-25 12:05:36", "AT02"};

		return Arrays.asList(row1, row2);
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
