package by.artempvn.task03.entity.state.impl;

import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.artempvn.task03.entity.Harbour;
import by.artempvn.task03.entity.Ship;
import by.artempvn.task03.entity.state.ShipState;
import by.artempvn.task03.exception.CustomException;

public class InWatersState implements ShipState {
	private static final int TIME_TO_REACH_HARBOUR = 5;
	private static final Logger logger = LogManager
			.getLogger(InWatersState.class);

	@Override
	public void headingToHarbour(Harbour harbour, Ship ship)
			throws CustomException {
		if (ship == null || harbour == null) {
			throw new CustomException("Null input");
		}
		try {
			ship.setTargetHarbour(harbour);
			logger.log(Level.INFO,
					"Ship " + ship.getName() + " is heading to harbour");
			TimeUnit.SECONDS.sleep(TIME_TO_REACH_HARBOUR);
		} catch (InterruptedException ex) {
			throw new CustomException("Failed during thread manipulation", ex);
		}
	}

	@Override
	public void enterHarbour(Ship ship) {
		logger.log(Level.WARN, "Can't enter harbour in in waters state");
	}

	@Override
	public void handlingCharges(Ship ship) {
		logger.log(Level.WARN, "Can't handling charges in in waters state");
	}

	@Override
	public void exitHarbour(Ship ship) {
		logger.log(Level.WARN, "Can't exit harbour in in waters state");
	}

	@Override
	public ShipState getNextState() {
		return new EnterHarbourState();
	}

}
