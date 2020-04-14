package main;

import map.Position;
import map.TextMap;
import printFormat.BorderedStrings;
import printFormat.LevelTitle;
import printFormat.OptionsText;

import java.io.IOException;
import java.util.List;

public class TextApp {
    private static TextApp text = new TextApp();
    private static BorderedStrings title = new LevelTitle();
    private static BorderedStrings choices = new OptionsText();
    public static int mapSize = 7;
    public static int level = 1;

    private static void startGame() {
        TextMap textMap = new TextMap(mapSize);
        title.printBox(" ", String.format("Level %s", level), " ");
        textMap.printMap();

        while (textMap.getPlayer().getStats().getHealth() > 0) {
            choices.printBox("1. Move/Attack", "2. Open Inventory", "3. Save Game", "4. Main Menu");
            int choice = UserInput.validUserChoice(4);

            if (choice == 1) {
                UserInput.locationInput(textMap);
            } else if (choice == 2) {
                System.out.println(GameFiles.readLines("Inventory"));
            } else if (choice == 3) {
                GameFiles.makeFileCopy("SaveInventory", "Inventory");
                choices.printBox("Your inventory has been saved.");
            } else {
                optionToExit();
            }
        }
        gameOver(textMap);
    }

    public static void nextLevel(List<Position> enemyCoords) {
        if (enemyCoords.size() <= 0) {
            choices.printBox("Proceed to the next level?", "1. Yes", "2. No");
            if (UserInput.validUserChoice(2) == 1) {
                level++;
                mapSize+=2;

                levelCount();
                startGame();
            }
            System.exit(1);
        }
    }

    private static void levelCount() {
        if (level == 3) {
            title.printBox(" ", "Congratulations! You finished the text version of 'A Beast's Weapon' !", "Thank you for playing!", " ");
            System.exit(1);
        }
    }

    private static void gameOver(TextMap textMap) {
        if (textMap.getPlayer().getStats().getHealth() < 0) {
            title.printBox("Game Over.", "You were slain by the enemy.");
            optionToExit();
        }
    }

    public static void optionToExit() {
        choices.printBox("1. Main menu", "2. Exit");

        if (UserInput.validUserChoice(2)==1) {
            try {
                TextApp.main(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            title.printBox("Goodbye!");
            System.exit(1);
        }
    }

    private void mainMenu(int choice) {
        if (choice == 1) {
            GameFiles.newGameInventory();
            startGame();
        } else if (choice == 2) {
            GameFiles.loadInventory();
            startGame();
        } else if (choice == 3) {
            System.out.println(GameFiles.readLines("Inventory"));
            optionToExit();
        } else {
            System.exit(1);
        }
    }

    public static void main (String[] args) throws IOException {
        title.printBox(" ", "    A Beast's", "     Weapon", " ", "1. New Game", "2. Load Game", "3. Instructions", "4. Exit Game"," ");
        text.mainMenu(UserInput.validUserChoice(4));
    }
}
