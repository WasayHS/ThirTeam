import java.util.ArrayList;

public class Location {
	private final int X_MAX = 1280;
	private final int Y_MAX = 720;
	private int xCoord;
	private int yCoord;
	
	public Location(){
		this.xCoord = xCoord;
		this.yCoord = yCoord;
	}
	

	public void setX(int x){
		if (xCoord+x <= X_MAX){
			xCoord = xCoord + x;
		}
	}
	
	public void setY(int y){
		if (yCoord+y <= Y_MAX){
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
