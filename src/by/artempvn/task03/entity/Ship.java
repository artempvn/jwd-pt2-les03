package by.artempvn.task03.entity;

import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.artempvn.task03.exception.CustomException;
import by.artempvn.task03.service.HarbourService;

public class Ship implements Runnable {
	private static final Logger logger = LogManager.getLogger(Ship.class);

	public enum ShipType {
		IMPORT, EXPORT
	}

	private String name;
	private int capacity;
	private ShipState state = ShipState.IN_WATERS;
	private int currentCargo;
	private ShipType type;

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

	public ShipState getState() {
		return state;
	}

	public int getCurrentCargo() {
		return currentCargo;
	}

	public ShipType getType() {
		return type;
	}

	public boolean unloadCargo() {
		boolean isUnloadSuccess = false;
		if (currentCargo > 0) {
			try {
				TimeUnit.SECONDS.sleep(5);
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
				TimeUnit.SECONDS.sleep(5);
				currentCargo++;
				isLoadSuccess = true;
			} catch (InterruptedException e) {
				return false;
			}
		}
		return isLoadSuccess;
	}

	@Override
	public void run() {
		HarbourService service = HarbourService.getInstance();
		try {
			service.enterHarbour(Harbour.getInstance(), this); // harbour field?
			service.handlingCharges(Harbour.getInstance(), this);
			service.exitHarbour(Harbour.getInstance(), this);
		} catch (CustomException e) {
			logger.log(Level.ERROR, "Ship " + name + " was drown");
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Ship [name=");
		builder.append(name);
		builder.append(", capacity=");
		builder.append(capacity);
		builder.append(", state=");
		builder.append(state);
		builder.append(", currentCargo=");
		builder.append(currentCargo);
		builder.append(", type=");
		builder.append(type);
		builder.append("]");
		return builder.toString();
	}
}
