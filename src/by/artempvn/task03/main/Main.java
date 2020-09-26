package by.artempvn.task03.main;

import java.util.List;
import by.artempvn.task03.creator.ShipCreator;
import by.artempvn.task03.entity.Ship;
import by.artempvn.task03.exception.CustomException;

public class Main {
	private static final String INPUT_DATA_FILE = "input/data.txt";

	public static void main(String... args) throws CustomException {
		List<Ship> ships = new ShipCreator().create(INPUT_DATA_FILE);
		for (Ship ship : ships) {
			Thread thread = new Thread(ship);
			thread.start();
		}
	}
}
