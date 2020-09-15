package by.artempvn.task03.reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.artempvn.task03.exception.CustomException;

public class DataReader {

	private static final String DEFAULT_PASS = "input/data.txt";
	private final static Logger logger = LogManager.getLogger(DataReader.class);

	public List<String> readFile(String path) throws CustomException {
		if (path == null || !Files.isReadable(Paths.get(path))) {
			logger.log(Level.WARN,
					"Can't read file " + path + ". Try to use default path");
			path = DEFAULT_PASS;
		}
		List<String> strings = new ArrayList<>();
		try (Stream<String> stream = Files.lines(Paths.get(path))) {
			strings = stream.collect(Collectors.toList());
		} catch (IOException ex) {
			throw new CustomException("Error: could't open data file.", ex);
		}
		if (strings.isEmpty()) {
			throw new CustomException("Data is empty");
		}
		logger.log(Level.INFO, "Number of read strings = " + strings.size());
		return strings;
	}

}
