package Taskeren.GameOfLife;

public class Point {
	
	public static final Point CLEAR = new Point();
	
	private boolean isFull = false;
	
	public Point() {}
	
	public void setFull() {
		this.isFull = true;
	}
	
	public void setClear() {
		this.isFull = false;
	}
	
	public boolean isFull() {
		return isFull;
	}
	
	@Override
	public String toString() {
		return this.isFull ? "O" : " ";
	}
	
}
