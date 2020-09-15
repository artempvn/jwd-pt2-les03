package by.artempvn.task03.entity;

public class HarbourData {
	private int currentCargo;
	private int capacity;
	private int berthNumber;
	
	public HarbourData(int currentCargo, int capacity,int berthNumber) {
		this.currentCargo = currentCargo;
		this.capacity = capacity;
		this.berthNumber = berthNumber;
	}
	public int getCurrentCargo() {
		return currentCargo;
	}
	public int getCapacity() {
		return capacity;
	}
	public int getBerthNumber() {
		return berthNumber;
	} 
}
