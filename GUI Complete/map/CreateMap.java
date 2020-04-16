package map;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import unit.Boss;
import unit.Player;

import java.util.List;

public class CreateMap {

    public static void spawnNonFriendly(int i, int j, List<Position> entity, Rectangle cell, ImagePattern entityImg) {
        for (Position p: entity) {
            if (i == p.getX() && j == p.getY() && !cell.getFill().equals(MapSetup.ENEMY_IMG)) {
                cell.setFill(entityImg);
            }
        }
    }

    public static void spawnEntities (int i, int j, Rectangle cell, int size) {
        if (!cell.getFill().equals(MapSetup.ENEMY_IMG) && !cell.getFill().equals(MapSetup.TERRAIN_IMG)) {
            if (i == 0 && j == size /2) {
                cell.setFill(MapSetup.PORTAL_IMG);
            } else if (i == 0 || j == 0 || i == size-1 || j == size-1) {
                cell.setFill(MapSetup.WALL_IMG);
            } else if (i == size-2 && j == (int)size/2) {
                cell.setFill(MapSetup.PLAYER_IMG);
            } else {
                cell.setFill(MapSetup.EMPTY_IMG);
            }
        }
    }

    public static void bossLevelSpawn(int i, int j, int size, Rectangle cell, Player player, Boss boss) {
        if (i==0 || i==size-1) {
            cell.setFill(MapSetup.WALL_IMG);
        } else if ((j==0|| j == size-1)&&j!=size/2) {
            cell.setFill(MapSetup.TERRAIN_IMG);
        }else{
            cell.setFill(MapSetup.EMPTY_IMG);
        }
        if(i==0&&j==(size-1)/2){
            cell.setFill(MapSetup.PORTAL_IMG);
        }
        if(i==player.getPosition().getX() && j==player.getPosition().getY()){
            cell.setFill(MapSetup.PLAYER_IMG);
        }
        if(i==boss.getPosition().getX() && j==boss.getPosition().getY()){
            cell.setFill(MapSetup.BOSS_IMG);
        }
    }
}
