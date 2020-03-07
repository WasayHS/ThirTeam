package map;

public class UnitLocation { //Stores player location or enemy location
	private int x;	// Can also be set to parent if you guys want
	private int y;
	
	public UnitLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
}
