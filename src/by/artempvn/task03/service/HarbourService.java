package by.artempvn.task03.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.artempvn.task03.entity.Berth;
import by.artempvn.task03.entity.Harbour;
import by.artempvn.task03.entity.Ship;
import by.artempvn.task03.exception.CustomException;

public class HarbourService {
	private static final HarbourService instance = new HarbourService();
	ReentrantLock lock = new ReentrantLock(true);
	private final Semaphore semaphore;
	private static final Logger logger = LogManager
			.getLogger(HarbourService.class);

	{
		semaphore = new Semaphore(
				Harbour.getInstance().getBerthsReadOnly().size(), true);
	}

	private HarbourService() {
	}

	public static HarbourService getInstance() {
		return instance;
	}

	public void enterHarbour(Harbour harbour, Ship ship)
			throws CustomException {
		try {
			semaphore.acquire();
			List<Berth> berths = harbour.getBerthsReadOnly();
			Optional<Berth> berth = Optional.empty();
			do {
				logger.log(Level.INFO, "Ship " + ship.getName()
						+ " is looking for free berth");
				lock.lock();
				berth = berths.stream()
						.filter(anyBerth -> anyBerth.getShip() == null)
						.findFirst();
				if (berth.isPresent()) {
					berth.get().setShip(ship);
				}
				lock.unlock();
				if (!berth.isPresent()) {
					TimeUnit.SECONDS.sleep(3);
				}
			} while (!berth.isPresent());
			logger.log(Level.INFO, "Ship " + ship.getName() + " entered berth №"
					+ berth.get().getNumber());
		} catch (InterruptedException ex) {
			throw new CustomException("Failed during thread manipulation", ex);
		}
	}

	public void handlingCharges(Harbour harbour, Ship ship) {
		switch (ship.getType()) {
		case EXPORT:
			loadCargoToShip(harbour, ship);
			break;
		case IMPORT:
			unloadCargoFromShip(harbour, ship);
			break;
		}
	}

	public void loadCargoToShip(Harbour harbour, Ship ship) {
		while (ship.getCurrentCargo() < ship.getCapacity()) {
			lock.lock();
			if (isOperationPossible(harbour, ship)) {
				harbour.incrementCurrentExportCargo();
				logger.log(Level.INFO, "Ship " + ship.getName()
						+ " got permission for loading");
			} else {
				logger.log(Level.INFO,
						"There is no more cargo for ship " + ship.getName());
				lock.unlock();
				return;
			}
			lock.unlock();
			if (harbour.loadCargoToShip()) {
				if (!ship.loadCargo()) {
					boolean cargoReturning = false;
					lock.lock();
					if (harbour.getCurrentCargo() + harbour
							.getCurrentImportCargo() < harbour.getCapacity()) {
						harbour.unloadCargoFromShip();
						cargoReturning = true;
					}
					lock.unlock();
					logger.log(Level.WARN, "Ship " + ship.getName()
							+ "couldn't load cargo. Cargo returning was successful: "
							+ cargoReturning);
				}
				logger.log(Level.INFO, "Ship " + ship.getName()
						+ " got cargo. Current cargo is "
						+ ship.getCurrentCargo() + "/" + ship.getCapacity());
			}
		}
		logger.log(Level.INFO, "Ship " + ship.getName() + " load complete");
	}

	public void unloadCargoFromShip(Harbour harbour, Ship ship) {
		while (ship.getCurrentCargo() > 0) {
			lock.lock();
			if (isOperationPossible(harbour, ship)) {
				harbour.incrementCurrentImportCargo();
				logger.log(Level.INFO, "Ship " + ship.getName()
						+ " got permission for unloading");
			} else {
				logger.log(Level.INFO,
						"There is no more space for cargo from ship "
								+ ship.getName());
				lock.unlock();
				return;
			}
			lock.unlock();
			if (ship.unloadCargo()) {
				harbour.unloadCargoFromShip();
				logger.log(Level.INFO, "Ship " + ship.getName()
						+ " unloaded cargo. Current cargo is "
						+ ship.getCurrentCargo() + "/" + ship.getCapacity());
			} else {
				logger.log(Level.INFO,
						"Ship " + ship.getName() + " failed to unload cargo");
			}
		}
		logger.log(Level.INFO, "Ship " + ship.getName() + " unload complete");
	}

	public boolean exitHarbour(Harbour harbour, Ship ship) {
		boolean isShipExit = false;
		List<Berth> berths = harbour.getBerthsReadOnly();
		Optional<Berth> berth = berths.stream()
				.filter(anyBerth -> anyBerth.getShip() == ship).findFirst();
		if (berth.isPresent()) {
			berth.get().setShip(null); // make argyment type = Optional
			logger.log(Level.INFO, "Ship " + ship.getName() + " leaves berth №"
					+ berth.get().getNumber());
			semaphore.release();
			isShipExit = true;
		}
		return isShipExit;
	}

	private boolean isOperationPossible(Harbour harbour, Ship ship) {
		boolean isPossible = false;
		switch (ship.getType()) {
		case EXPORT:
			int numberAvailableCargo = harbour.getCurrentCargo()
					- harbour.getCurrentExportCargo();
			if (numberAvailableCargo > 0) {
				isPossible = true;
			}
			break;
		case IMPORT:
			int amountFreeSpaceForCargo = harbour.getCapacity()
					- harbour.getCurrentCargo()
					+ harbour.getCurrentImportCargo();
			if (amountFreeSpaceForCargo > 0) {
				isPossible = true;
			}
			break;
		}
		return isPossible;
	}
}
