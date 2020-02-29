public class Location {
	private final int X_MAX = 1280;
	private final int Y_MAX = 720;
	private int xCoord;
	private int yCoord;
	
	public Location(int x, int y){
		if (x>X_MAX && y>Y_MAX && y<0 && x<0){
			System.err.println("Error: Invalid Coordinates given");
		}else{
			this.xCoord = x;
			this.yCoord = y;
		}
	}
		
	public Location(Location l){
		this.xCoord = l.getX();
		this.yCoord = l.getY();
	}
	

	public void setX(int x){
		if (xCoord+x <= X_MAX && xCoord+x >= 0){
			xCoord = xCoord + x;
		}
	}
	
	public void setY(int y){
		if (yCoord+y <= Y_MAX && yCoord+y >= 0){
			yCoord = yCoord + y;
		}
	}
	
	public int getX(){
		return xCoord;
	}
	public int getY(){
		return yCoord;
	}
}