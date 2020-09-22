package by.artempvn.task03.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.artempvn.task03.creator.HarbourDataCreator;

public class Harbour {
	private static final int DEFAULT_BERTH_VALUE = 3;
	private static final int DEFAULT_CAPACITY_VALUE = 20;
	private static final int DEFAULT_CARGO_VALUE = 5;
	private static final String DEFAULT_PASS = "input/data.txt";
	private static Harbour instance = new Harbour();
	private int currentCargo;
	private int capacity;
	private int currentExportCargo;
	private int currentImportCargo;
	private List<Berth> berths = new ArrayList<>();
	private static final Logger logger = LogManager.getLogger(Harbour.class);

	private Harbour() {
		HarbourDataCreator creator = new HarbourDataCreator();
		Optional<HarbourData> data = creator.create(DEFAULT_PASS);
		int berthNumber;
		if (data.isPresent()) {
			currentCargo = data.get().getCurrentCargo();
			capacity = data.get().getCapacity();
			berthNumber = data.get().getBerthNumber();
		} else {
			logger.log(Level.ERROR, "Use default values for harbour");
			currentCargo = DEFAULT_CARGO_VALUE;
			capacity = DEFAULT_CAPACITY_VALUE;
			berthNumber = DEFAULT_BERTH_VALUE;
		}
		for (int i = 1; i <= berthNumber; i++) {
			berths.add(new Berth(i));
		}
	}

	public static Harbour getInstance() {
		return instance;
	}

	public int getCurrentCargo() {
		return currentCargo;
	}

	public int getCapacity() {
		return capacity;
	}

	public List<Berth> getBerthsReadOnly() {
		return Collections.unmodifiableList(berths);
	}

	public int getCurrentExportCargo() {
		return currentExportCargo;
	}

	public void incrementCurrentExportCargo() {
		currentExportCargo++;
	}

	public int getCurrentImportCargo() {
		return currentImportCargo;
	}

	public void incrementCurrentImportCargo() {
		currentImportCargo++;
	}

	public boolean unloadCargoFromShip() {
		boolean isUnloadingSuccessful = false;
		if (currentCargo < capacity) {
			currentCargo++;
			currentImportCargo--;
			isUnloadingSuccessful = true;
		}
		return isUnloadingSuccessful;
	}

	public boolean loadCargoToShip() {
		boolean isLoadingSuccessful = false;
		if (currentCargo > 0) {
			currentCargo--;
			currentExportCargo--;
			isLoadingSuccessful = true;
		}
		return isLoadingSuccessful;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Harbour [currentCargo=").append(currentCargo)
				.append(", capacity=").append(capacity)
				.append(", number of berths=").append(berths.size())
				.append("]");
		return builder.toString();
	}

}
