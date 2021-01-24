package main;

import map.TextMap;
import printFormat.BorderedStrings;
import printFormat.LevelTitle;
import printFormat.OptionsText;

import java.io.FileNotFoundException;

public class TextApp {
    private static TextApp text = new TextApp();
    private static BorderedStrings title = new LevelTitle();
    private static BorderedStrings choices = new OptionsText();

    public void newGame() {
        TextMap.printMap();
        while (TextMap.player.getStats().getHealth() > 0) {
            UserInput.locationInput();
        }
    }

    public static void optionToExit() throws FileNotFoundException {
        choices.printBox("1. Main menu", "2. Exit");

        if (UserInput.validUserChoice(2)==1) {
            TextApp.main(null);
        } else {
            title.printBox("Goodbye!");
            System.exit(1);
        }
    }

    public void mainMenu(int choice) throws FileNotFoundException {
        if (choice == 1) {
            newGame();
        } else if (choice == 2) {
            System.out.println("Load Saved File");
        } else if (choice == 3) {
            GameFiles.readInstructions();
            optionToExit();
        }
    }

    public static void main (String[] args) throws FileNotFoundException {
        title.printBox(" ", "    A Beast's", "     Weapon", " ", "1. New Game", "2. Load Game", "3. Instructions", " ");
        text.mainMenu(UserInput.validUserChoice(3));
    }
}
