package main;

import loot.TextInventory;
import printFormat.BorderedStrings;
import printFormat.LevelTitle;

import java.io.*;
import java.util.Scanner;

public class GameFiles {
    private static BorderedStrings title = new LevelTitle();

    /* openFiles(String)
     * Opens files
     *
     * @param filename: Type String - the name of the file being opened
     * @return line: returns type Scanner
     */
    public static Scanner openFiles(String fileName) {
        Scanner line = null;

        try {
            String path = new File(String.format("src/%s", fileName)).getAbsolutePath();
            File file = new File(path);
            line = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return line;
    }

    /* writeScanner(String)
     * Sets up the file's PrintWriter for writing/appending to files
     *
     * @param path: Type String - the path of the file to be written
     * @return writer: returns type PrintWriter
     */
    public static PrintWriter writeScanner(String path) {
        FileWriter fw;
        PrintWriter writer = null;

        try {
            fw = new FileWriter(path, true);
            BufferedWriter bw = new BufferedWriter(fw);
            writer = new PrintWriter(bw);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer;
    }

    /* readLines(String)
     * Reads the file provided line by line
     *
     * @param path: Type String - the path of the file to be read
     * @return readLine: returns type String - the contents of the file
     */
    public static String readLines(String fileName) {
        Scanner line;
        line = openFiles(fileName);
        String readLine = "";

        while (line.hasNextLine()) {
            readLine = readLine.concat(line.nextLine() + "\n");
        }
        return readLine;
    }

    /* addToInventory(String, String)
     * Method for adding items to inventory file
     *
     * @param lootName: Type String - name of the loot
     * @param lootStats: Type String - stats of the loot as a string
     */
    public static void addToInventory(String lootName, String lootStats) {
        String path = new File("src/Inventory").getAbsolutePath();
        PrintWriter writer = writeScanner(path);

        int numOfItems = TextInventory.inventory.size();

        writer.append(String.format("\n%s.     %s :: %s", numOfItems, lootName, lootStats));
        writer.append("\n- - - - - - - - - - - - - - - - - - - - - - - - - -");
        writer.close();
    }

    /* newGameInventory()
     * Empties the inventory for a new game
     */
    public static void newGameInventory() {
        try {
            String path = new File("src/Inventory").getAbsolutePath();
            FileWriter fw = new FileWriter(path, false);
            PrintWriter pw = new PrintWriter(fw, false);
            pw.flush();

            pw.write("++================== INVENTORY ==================++");
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* makeFileCopy(String, String)
     * Makes copies of files from another file
     *
     * @param copyFile: Type String - the file name of the copy
     * @param fileName: Type String - the file name to be copied from
     */
    public static void makeFileCopy(String copyFile, String fileName) {
        try {
            String path = new File(String.format("src/%s", copyFile)).getAbsolutePath();
            FileWriter fw = null;
            fw = new FileWriter(path, false);
            PrintWriter pw = new PrintWriter(fw, false);
            pw.flush();
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String copyPath = new File(String.format("src/%s", copyFile)).getAbsolutePath();
        PrintWriter writer = writeScanner(copyPath);

        String fileToCopy = readLines(fileName);

        writer.println(fileToCopy);
        writer.close();
    }

    /* loadSavedInventory()
     * Method that loads a saved inventory
     */
    public static void loadInventory() {
        File file = new File(new File(String.format("src/SavedInventory")).getAbsolutePath());

        if (file.length()>0) {
            makeFileCopy("Inventory", "SavedInventory");
            title.printBox("Successfully loaded your saved inventory!");
        } else {
            title.printBox("No saved files can be loaded.");
        }
    }
}
