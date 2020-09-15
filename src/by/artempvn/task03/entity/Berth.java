package by.artempvn.task03.entity;

public class Berth {
	
	private int number;
	private Ship ship;
	

	public Berth(int number) {
		this.number = number;
	}

	public int getNumber() {
		return number;
	}
	
	public Ship getShip() {
		return ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

}
