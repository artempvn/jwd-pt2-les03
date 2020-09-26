package by.artempvn.task03.creator;

import java.util.ArrayList;
import java.util.List;
import by.artempvn.task03.entity.Ship;
import by.artempvn.task03.exception.CustomException;
import by.artempvn.task03.reader.DataReader;

public class ShipCreator {

	private static final String SPACE = " ";

	public List<Ship> create(String path) throws CustomException {
		DataReader reader = new DataReader();
		List<String> strings;
		try {
			strings = reader.readFile(path);
		} catch (CustomException e) {
			throw new CustomException("Failed to read data file");
		}
		List<Ship> ships = new ArrayList<>();
		for (int i = 1; i < strings.size(); i++) {
			String[] values = strings.get(i).split(SPACE);
			String name = values[0];
			int currentCargo = Integer.parseInt(values[1]);
			int capacity = Integer.parseInt(values[2]);
			Ship.ShipType type = Ship.ShipType.valueOf(values[3].toUpperCase());
			ships.add(new Ship(name, currentCargo, capacity, type));
		}
		return ships;
	}
}
