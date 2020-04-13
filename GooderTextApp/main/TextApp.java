package main;

import map.TextMap;
import printFormat.BorderedStrings;
import printFormat.LevelTitle;
import printFormat.OptionsText;

import java.io.IOException;

public class TextApp {
    private static TextApp text = new TextApp();
    private static BorderedStrings title = new LevelTitle();
    private static BorderedStrings choices = new OptionsText();
    public static int mapSize = 7;

    private void startGame() throws IOException {
        TextMap.printMap();

        do {
            choices.printBox("1. Move/Attack", "2. Open Inventory");

            if (UserInput.validUserChoice(2) == 2) {
                GameFiles.printFileContents("Inventory");
            }
            UserInput.locationInput();
        } while (TextMap.player.getStats().getHealth() > 0);
        gameOver();
    }

    // NextLevel

    private static void gameOver() throws IOException {
        if (TextMap.player.getStats().getHealth() < 0) {
            title.printBox("            Game Over.", "  You were slain by the enemy.");
            optionToExit();
        }
    }

    private static void optionToExit() throws IOException {
        choices.printBox("1. Main menu", "2. Exit");

        if (UserInput.validUserChoice(2)==1) {
            TextApp.main(null);
        } else {
            title.printBox("Goodbye!");
            System.exit(1);
        }
    }

    private void mainMenu(int choice) throws IOException {
        if (choice == 1) {
            GameFiles.newGameInventory();
            startGame();

        } else if (choice == 2) {
            if (GameFiles.loadSavedInventory()) {
                startGame();
            }
            choices.printBox("No saved files were found.");
            optionToExit();

        } else if (choice == 3) {
            GameFiles.printFileContents("Instructions");
            optionToExit();
        }
    }

    public static void main (String[] args) throws IOException {
        title.printBox(" ", "    A Beast's", "     Weapon", " ", "1. New Game", "2. Load Game", "3. Instructions", " ");
        text.mainMenu(UserInput.validUserChoice(3));
    }
}
