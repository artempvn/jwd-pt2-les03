package by.artempvn.task03.main;

import java.util.List;
import by.artempvn.task03.creator.ShipCreator;
import by.artempvn.task03.entity.Ship;
import by.artempvn.task03.exception.CustomException;

public class Main {

	public static void main(String... args) throws CustomException {
		List<Ship> ships = new ShipCreator().create(null);
		for (Ship ship : ships) {
			Thread thread = new Thread(ship);
			thread.start();
		}
	}
}
