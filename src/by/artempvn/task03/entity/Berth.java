package by.artempvn.task03.entity;

import java.util.Optional;

public class Berth {

	private int number;
	private Optional<Ship> ship = Optional.empty();

	public Berth(int number) {
		this.number = number;
	}

	public int getNumber() {
		return number;
	}

	public Optional<Ship> getShip() {
		return ship;
	}

	public void setShip(Optional<Ship> ship) {
		this.ship = ship;
	}

}
