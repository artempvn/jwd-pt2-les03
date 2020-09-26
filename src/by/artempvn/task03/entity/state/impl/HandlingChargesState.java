package by.artempvn.task03.entity.state.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.artempvn.task03.entity.Harbour;
import by.artempvn.task03.entity.Ship;
import by.artempvn.task03.entity.state.ShipState;
import by.artempvn.task03.exception.CustomException;
import by.artempvn.task03.service.HarbourService;
import by.artempvn.task03.service.impl.HarbourServiceImpl;

public class HandlingChargesState implements ShipState {
	private static final Logger logger = LogManager
			.getLogger(HandlingChargesState.class);

	@Override
	public void headingToHarbour(Harbour harbour, Ship ship) {
		logger.log(Level.WARN,
				"Can't head to harbour in handling charges state");
	}

	@Override
	public void enterHarbour(Ship ship) {
		logger.log(Level.WARN, "Can't enter harbour in handling charges state");
	}

	@Override
	public void handlingCharges(Ship ship) throws CustomException {
		if (ship == null) {
			throw new CustomException("Null input");
		}
		HarbourService service = HarbourServiceImpl.getInstance();
		switch (ship.getType()) {
		case EXPORT:
			service.loadCargoToShip(ship);
			break;
		case IMPORT:
			service.unloadCargoFromShip(ship);
			break;
		}
	}

	@Override
	public void exitHarbour(Ship ship) {
		logger.log(Level.WARN, "Can't exit harbour in handling charges state");
	}

	@Override
	public ShipState getNextState() {
		return new ExitHarbourState();
	}

}
