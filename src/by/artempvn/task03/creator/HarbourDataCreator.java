package by.artempvn.task03.creator;

import java.util.List;
import java.util.Optional;
import by.artempvn.task03.entity.HarbourData;
import by.artempvn.task03.exception.CustomException;
import by.artempvn.task03.reader.DataReader;

public class HarbourDataCreator {

	public Optional<HarbourData> create(String path) {
		DataReader reader = new DataReader();
		List<String> strings;
		try {
			strings = reader.readFile(path);
		} catch (CustomException e) {
			// log
			return Optional.empty();
		}
		String[] values = strings.get(0).split(" ");
		int currentCargo = Integer.parseInt(values[0]);
		int capacity = Integer.parseInt(values[1]);
		int berthNumber = Integer.parseInt(values[2]);
		return Optional
				.of(new HarbourData(currentCargo, capacity, berthNumber));
	}

}
