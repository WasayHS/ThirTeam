package application;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import loot.Inventory;
import map.MapSetup;

import java.util.List;

public class ButtonEvents {

    public static void lootItemButton(ActionEvent event, int inventoryKey, Label message, Rectangle cell, Stage pickup) {
        Button actionBtn = (Button)event.getSource();

        if (actionBtn.equals("No")) {
            Inventory.inventory.remove(inventoryKey);
        }

        continueBtn(message);
        cell.setFill(MapSetup.EMPTY_IMG);
        pickup.close();
    }

    public static void continueBtn(Label message) {
        Stage contWindow = new Stage();
        VBox root = new VBox();
        Button continueBtn = new Button("Continue");
        continueBtn.setOnAction(event -> contWindow.close());

        root.setAlignment(Pos.CENTER);
        root.getChildren().add(message);
        root.getChildren().add(continueBtn);
        Scene scene = new Scene(root, 330, 100);

        contWindow.setScene(scene);
        contWindow.showAndWait();
    }

    public static Button createButton(int translateX, int translateY, String buttonName) {
        Button button = new Button();
        button.setText(buttonName);
        button.setTranslateX(translateX);
        button.setTranslateY(translateY);

        return button;
    }

    public static void addButtonToBox(VBox root, List<Button> buttonList) {
        for (Button button : buttonList) {
            root.getChildren().add(button);
        }
    }
    public static Button exitButton(int translateX, int translateY) {
        Button end = createButton(translateX, translateY, "End");
        end.setOnAction(e -> System.exit(1));

        return end;
    }

    public static Button playButton(Stage stage, int translateX, int translateY) {
        Button play = createButton(translateX, translateY, "Play Again");

        play.setOnAction(e ->{
            Stage newStage = new Stage();
            SceneChange.level =1;
            stage.close();
            SceneChange.setMorality(newStage);
        });

        return play;
    }
}
