package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GameFiles {

    public static void readInstructions() throws FileNotFoundException {
        String path = new File("src/Instructions").getAbsolutePath();
        File instructionFile = new File(path);
        Scanner line = new Scanner(instructionFile);

        String instructions = "";

        while(line.hasNextLine()) {
            instructions = instructions.concat(line.nextLine() + "\n");
        }
        System.out.println(instructions);
    }
}
