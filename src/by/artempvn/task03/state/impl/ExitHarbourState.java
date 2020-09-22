package by.artempvn.task03.state.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.artempvn.task03.entity.Harbour;
import by.artempvn.task03.entity.Ship;
import by.artempvn.task03.exception.CustomException;
import by.artempvn.task03.service.HarbourService;
import by.artempvn.task03.service.impl.HarbourServiceImpl;
import by.artempvn.task03.state.ShipState;

public class ExitHarbourState implements ShipState {
	private static final Logger logger = LogManager
			.getLogger(ExitHarbourState.class);

	@Override
	public void headingToHarbour(Harbour harbour, Ship ship) {
		logger.log(Level.WARN, "Can't head to harbour in exit harbour state");
	}

	@Override
	public void enterHarbour(Ship ship) {
		logger.log(Level.WARN, "Can't enter harbour in exit harbour state");
	}

	@Override
	public void handlingCharges(Ship ship) {
		logger.log(Level.WARN, "Can't handling charges in exit harbour state");
	}

	@Override
	public void exitHarbour(Ship ship) throws CustomException {
		if (ship == null) {
			throw new CustomException("Null input");
		}
		HarbourService service = HarbourServiceImpl.getInstance();
		service.exitHarbour(ship);
	}

	@Override
	public ShipState getNextState() {
		return new InWatersState();
	}

}
