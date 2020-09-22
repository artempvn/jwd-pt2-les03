package by.artempvn.task03.entity;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.artempvn.task03.exception.CustomException;
import by.artempvn.task03.state.ShipState;
import by.artempvn.task03.state.impl.InWatersState;

public class Ship implements Runnable {
	private static final int HANDLING_TIME = 5;
	private static final Logger logger = LogManager.getLogger(Ship.class);

	public enum ShipType {
		IMPORT, EXPORT
	}

	private String name;
	private int capacity;
	private ShipState state = new InWatersState();
	private int currentCargo;
	private ShipType type;
	private Optional<Harbour> targetHarbour;

	public Ship(String name, int currentCargo, int capacity, ShipType type) {
		this.name = name;
		this.currentCargo = currentCargo;
		this.capacity = capacity;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public int getCapacity() {
		return capacity;
	}

	public int getCurrentCargo() {
		return currentCargo;
	}

	public ShipType getType() {
		return type;
	}

	public Optional<Harbour> getTargetHarbour() {
		return targetHarbour;
	}

	public void setTargetHarbour(Harbour harbour) {
		this.targetHarbour = Optional.of(harbour);
	}

	public boolean unloadCargo() {
		boolean isUnloadSuccess = false;
		if (currentCargo > 0) {
			try {
				TimeUnit.SECONDS.sleep(HANDLING_TIME);
				currentCargo--;
				isUnloadSuccess = true;
			} catch (InterruptedException e) {
				return false;
			}
		}
		return isUnloadSuccess;
	}

	public boolean loadCargo() {
		boolean isLoadSuccess = false;
		if (currentCargo < capacity) {
			try {
				TimeUnit.SECONDS.sleep(HANDLING_TIME);
				currentCargo++;
				isLoadSuccess = true;
			} catch (InterruptedException e) {
				return false;
			}
		}
		return isLoadSuccess;
	}

	public void headingToHarbour() throws CustomException {
		state.headingToHarbour(Harbour.getInstance(), this);
		state = state.getNextState();
	}

	public void enterHarbour() throws CustomException {
		state.enterHarbour(this);
		state = state.getNextState();
	}

	public void handlingCharges() throws CustomException {
		state.handlingCharges(this);
		state = state.getNextState();
	}

	public void exitHarbour() throws CustomException {
		state.exitHarbour(this);
		state = state.getNextState();
	}

	@Override
	public void run() {
		try {
			headingToHarbour();
			enterHarbour();
			handlingCharges();
			exitHarbour();
		} catch (CustomException e) {
			logger.log(Level.ERROR, "Ship " + name + " was drown");
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Ship [name=").append(name).append(", capacity=")
				.append(capacity).append(", currentCargo=").append(currentCargo)
				.append(", type=").append(type).append("]");
		return builder.toString();
	}

}
