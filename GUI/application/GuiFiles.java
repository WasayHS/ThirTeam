package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.io.*;
import java.util.Scanner;

/**
 * Class to open external resources
 * @author Bonnie's Computer
 *
 */
public class GuiFiles {

	/**
	 * Method to open the instructions of the game
	 */
    public static void openInstructions() {
        try {
            File instruction1 = new File("GUIInstructions.txt");
            if(instruction1.exists()){ 
            	Desktop d = Desktop.getDesktop();
            	d.open(instruction1);// opens the instructions for you
            }else{
            	instruction1.createNewFile();
            	throw new FileNotFoundException();
            }
        } catch(FileNotFoundException i){
        	makeInstructions();
        	openInstructions();
        } catch (IOException e) {
        	System.err.println("Unknown IO Error When opening the file");
		} 
    }
    
    /**
	 * Method to make the instructions of the game
	 */
    public static void makeInstructions(){
    	BufferedWriter w;
    	
		try {
			w = new BufferedWriter(new FileWriter(new File("GUIInstructions.txt")));
			w.write("Bring your mouse cursor one block above or beside the player to move.\n");
	    	w.write("The player cannot move diagonally.\n");
	    	w.write("The player can attack any enemy (or boss) that is one block away from the player.\n");
	    	w.write("The player can attack diagionally using ranged attacks.\n");
	    	w.write("The door on the top will only be opened only if all the enemies are eliminated.\n");
	    	w.write("Sometimes the enemy may drop a potion that can be used to the player's advantage.\n");
	    	w.write("The potions can be used during battle using the Use Potion button.\n");
	    	w.write("After the player goes to the next level, their stats will go back to it's original state.\n");
	    	w.write("Although stats will be reset, they can keep the potions that were aquired in the previous level.\n");
	    	w.write("Good luck and enjoy the game.");
	    	w.close();
		} catch (IOException e) {
			System.out.println("IO Error when creating the file");
		}
    	
    }
    
    
    
    /**
     * Method to load the start image of the game
     * @param layout Pane of the title screen
     */
    public static void loadStartImage(Pane layout) {
        try{
            Image gamestart  = new Image(new FileInputStream("GUI/application/StartGame.png"));
            layout.getChildren().add(new ImageView(gamestart));
        }catch (IllegalArgumentException | FileNotFoundException i) {
            System.err.println("Error: \"StartGame.png\" not found");
        }
    }

    /**
     * Method to read text from a file (Used for Story)
     * @param fileLocation String of the file location
     * @return A String of the text from the file
     * @throws FileNotFoundException
     */
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
