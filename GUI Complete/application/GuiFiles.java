package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.io.*;
import java.util.Scanner;

public class GuiFiles {

    public static void openInstructions() {
        try {
            File instruction1 = new File("src/Instructions.txt");
            Desktop d = Desktop.getDesktop();
            d.open(instruction1);
        } catch (IOException e) {
            System.err.println("Unknown Error");
        }
    }

    public static void loadStartImage(Pane layout) {
        try{
            Image gamestart  = new Image(new FileInputStream("src/application/StartGame.png"));
            layout.getChildren().add(new ImageView(gamestart));
        }catch (IllegalArgumentException | FileNotFoundException i) {
            System.err.println("Error: \"StartGame.png\" not found");
        }
    }

    public static String readText(String fileLocation) throws FileNotFoundException {
        Scanner reader = new Scanner(new FileReader(fileLocation));
        String text = "";
        while (reader.hasNextLine()) {
            text = text.concat(reader.nextLine() + "\r\n");
        }
        reader.close();
        return text;
    }
}
