package by.artempvn.task03.service;

import by.artempvn.task03.entity.Ship;
import by.artempvn.task03.exception.CustomException;

public interface HarbourService {

	void enterHarbour(Ship ship) throws CustomException;

	void loadCargoToShip(Ship ship) throws CustomException;

	void unloadCargoFromShip(Ship ship) throws CustomException;

	void exitHarbour(Ship ship) throws CustomException;

}
