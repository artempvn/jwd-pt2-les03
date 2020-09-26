package by.artempvn.task03.entity.state;

import by.artempvn.task03.entity.Harbour;
import by.artempvn.task03.entity.Ship;
import by.artempvn.task03.exception.CustomException;

public interface ShipState {

	void headingToHarbour(Harbour harbour, Ship ship) throws CustomException;

	void enterHarbour(Ship ship) throws CustomException;

	void handlingCharges(Ship ship) throws CustomException;

	void exitHarbour(Ship ship) throws CustomException;

	ShipState getNextState();

}
