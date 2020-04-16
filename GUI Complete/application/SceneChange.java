package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import map.CreateMap;
import map.MapSetup;
import map.Position;
import unit.Boss;
import unit.Player;

import java.io.*;
import java.util.List;

public class SceneChange {
	
	static boolean morality;
	static int level = 0;
	public static int mapSize = 7;
	
	private static void setMorality(boolean morality) {
		SceneChange.morality = morality;
	}

	private static void action(Stage window, boolean amorality) {
		setMorality(amorality);
			newLevel(window);
	}

	public static void newLevel(Stage window) {
		Scene nextScene;

		if(level == 0 || level == 2) {
			try {
				window.close();
				Stage stage = new Stage();
				stage.setScene(getCutScene(stage));
				level++;
				stage.show();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else if(level == 3 || level == 6) {
			nextScene = bossLevel(window, mapSize);
			level++;
			window.setScene(nextScene);
			window.show();
		} else if (level ==9) {
			window.close();
			Stage completed = new Stage();
			GameState.victory(completed);
		} else {
			nextScene = startGame(window, mapSize);
			level++;
			window.setScene(nextScene);
			window.show();
		}
	}
	
	public static Scene getTitleScene(Stage window) {
		Pane layout = new Pane();
		window.setTitle("A Beast's Weapon");
		Button start = ButtonEvents.createButton(180,0, "Begin");
		Button instructions = ButtonEvents.createButton(240, 0, "Instructions");
		GuiFiles.loadStartImage(layout);

		start.setOnAction(e -> setMorality(window));
		instructions.setOnAction(event -> { GuiFiles.openInstructions(); });

		layout.getChildren().add(start);
		layout.getChildren().add(instructions);
		Scene begin = new Scene(layout, 500,500);
		return begin;
	}

	public static void setMorality(Stage window) {
		Pane layout = new Pane ();
		Button evil = ButtonEvents.createButton(150,225,"Low (evil)");
		Button good = ButtonEvents.createButton(300,225,"High (good)");

		evil.setOnAction(e-> { action(window,false); });
		good.setOnAction(g-> { action(window,true); });
		
		layout.getChildren().add(evil);
		layout.getChildren().add(good);
		Scene begin = new Scene(layout, 500,500);

		window.setScene(begin);
		window.show();
	}

	public static Scene startGame(Stage window, int size) {
		GridPane grid = new GridPane();
		Player player = new Player(size-2, size/2);
		int screen = 500;
		int spawnCount = (int)(size*0.85);

		List<Position> enemies = MapSetup.createEnemyPositions(spawnCount, size);
		List<Position> terrain = MapSetup.createTerrainPositions(spawnCount, size);

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Rectangle cell = new Rectangle (screen/size, screen/size);

				CreateMap.spawnNonFriendly(i, j, enemies, cell, MapSetup.ENEMY_IMG);
				CreateMap.spawnNonFriendly(i, j, terrain, cell, MapSetup.TERRAIN_IMG);
				CreateMap.spawnEntities(i, j, cell, size);

				gameEventHandling(cell, grid, window, player, i, j, null);
			}	
		}
		Scene scene = new Scene(grid, 500, 500);
		return scene;
	}

	private static void gameEventHandling(Rectangle cell, GridPane grid, Stage window, Player player, int i, int j, Boss b){
		cell.setOnMouseEntered(enter -> {
			Position p = new Position(GridPane.getRowIndex(cell), GridPane.getColumnIndex(cell));

			cell.setFill(MapSetup.enterHover(cell));
			cell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
				MapSetup.updateGrid(grid, p, player, window, b);
			});
			cell.setOnMouseExited(exit -> cell.setFill(MapSetup.exitHover(cell)));
		});
		
		GridPane.setRowIndex(cell, i);
		GridPane.setColumnIndex(cell, j);
		grid.getChildren().add(cell);
	}
	
	public static Scene bossLevel(Stage window, int size){
		Player player = new Player(size/2,2);
		Boss b = new Boss(45,5, 5, 3,2,(int)(size/2));

		List<Position> BossList = MapSetup.setBossPosition(b);
		GridPane grid = new GridPane();

		///--------Creates walls, spikes and empty spots-------\\\
		for (int j = 0; j<size; j++){
			for (int i = 0; i<size; i++) {
				Rectangle cell = new Rectangle(i,j,500/size, 500/size);
				CreateMap.bossLevelSpawn(i, j, size, cell, player, b);
				gameEventHandling(cell, grid, window, player, i, j, b);
			}
		}
		Scene initBossLevel = new Scene(grid, 500,500);
		return initBossLevel;
	}

	public static Scene getCutScene(Stage window) throws FileNotFoundException {
		Scene cutScene = null;
		Button btn = ButtonEvents.createButton(0,0,"Continue");
		btn.setOnAction(e-> { newLevel(window); });

		switch(level) {
			case 0:
				Text expo = new Text();
				VBox neutral = new VBox();
				neutral.setAlignment(Pos.CENTER);
				expo.setText(GuiFiles.readText("src/cutsceneRes/Exposition.txt"));
				neutral.getChildren().addAll(expo,btn);
				cutScene = new Scene(neutral, 500,500);
				window.setScene(cutScene);
				break;
			case 2:
				if (morality == true) {
					Text goodOne = new Text();
					VBox good1 = new VBox();
					good1.setAlignment(Pos.CENTER);
					goodOne.setText(GuiFiles.readText("src/cutsceneRes/goodOne.txt"));
					good1.getChildren().addAll(goodOne,btn);
					cutScene = new Scene(good1, 500,500);
				} else {
					Text badOne = new Text();
					VBox bad1 = new VBox();
					bad1.setAlignment(Pos.CENTER);
					badOne.setText(GuiFiles.readText("src/cutsceneRes/badOne.txt"));
					bad1.getChildren().addAll(badOne,btn);
					cutScene = new Scene(bad1, 500,500);
				}
				window.setScene(cutScene);
				break;
		}
		return cutScene;
	}

}
