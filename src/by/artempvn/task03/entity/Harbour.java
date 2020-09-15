package by.artempvn.task03.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import by.artempvn.task03.creator.HarbourDataCreator;

public class Harbour {
	private static final String DEFAULT_PASS = "input/data.txt";
	private static Harbour instance = new Harbour();
	private int currentCargo;
	private int capacity;
	private int currentExportCargo;
	private int currentImportCargo;
	private List<Berth> berths = new ArrayList<>();

	private Harbour() {
		HarbourDataCreator creator = new HarbourDataCreator();
		Optional<HarbourData> data = creator.create(DEFAULT_PASS);
		int berthNumber;
		if (data.isPresent()) {
			currentCargo = data.get().getCurrentCargo();
			capacity = data.get().getCapacity();
			berthNumber = data.get().getBerthNumber();
		} else {
			// log
			currentCargo = 5;
			capacity = 20;
			berthNumber = 3;
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
		builder.append("Harbour [currentCargo=");
		builder.append(currentCargo);
		builder.append(", capacity=");
		builder.append(capacity);
		builder.append(", berthNumber=");
		builder.append(berths.size());
		builder.append("]");
		return builder.toString();
	}
}
