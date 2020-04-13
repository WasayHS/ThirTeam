package main;

import java.io.*;
import java.util.Scanner;

public class GameFiles {

    public static void printFileContents(String fileName) throws FileNotFoundException {
        String path = new File(String.format("src/%s", fileName)).getAbsolutePath();
        File file = new File(path);
        Scanner line = new Scanner(file);

        String readLine = "";

        while(line.hasNextLine()) {
            readLine = readLine.concat(line.nextLine() + "\n");
        }
        System.out.println(readLine);
    }

    public static void addToInventory(String lootName, String lootStats) {
        String path = new File("src/Inventory").getAbsolutePath();

        try {
            FileWriter fw = new FileWriter(path, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter writer = new PrintWriter(bw);

            writer.append(String.format("     %s :: %s", lootName, lootStats));
            writer.append("\n- - - - - - - - - - - - - - - - - - - - - - - - - -");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void newGameInventory() throws IOException {
        String path = new File("src/Inventory").getAbsolutePath();
        FileWriter fw = new FileWriter(path, false);
        PrintWriter pw = new PrintWriter(fw, false);
        pw.flush();

        pw.write("++================== INVENTORY ==================++\n");
        pw.close();
        fw.close();
    }

    public static boolean loadSavedInventory () {
        File file = new File(new File(String.format("src/SavedInventory")).getAbsolutePath());
        return (file.exists());
    }
}
