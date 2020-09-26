package by.artempvn.task03.creator;

import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.artempvn.task03.entity.HarbourData;
import by.artempvn.task03.exception.CustomException;
import by.artempvn.task03.reader.DataReader;

public class HarbourDataCreator {
	private static final String SPACE = " ";
	private static final Logger logger = LogManager
			.getLogger(HarbourDataCreator.class);

	public Optional<HarbourData> create(String path) {
		DataReader reader = new DataReader();
		List<String> strings;
		try {
			strings = reader.readFile(path);
		} catch (CustomException ex) {
			logger.log(Level.ERROR, "Failed to read data file");
			return Optional.empty();
		}
		String[] values = strings.get(0).split(SPACE);
		int currentCargo = Integer.parseInt(values[0]);
		int capacity = Integer.parseInt(values[1]);
		int berthNumber = Integer.parseInt(values[2]);
		return Optional
				.of(new HarbourData(currentCargo, capacity, berthNumber));
	}

}
