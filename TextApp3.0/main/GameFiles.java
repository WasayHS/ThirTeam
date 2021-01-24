package main;

import printFormat.BorderedStrings;
import printFormat.LevelTitle;

import java.io.*;
import java.util.Scanner;

public class GameFiles {
    private static BorderedStrings title = new LevelTitle();

    public static Scanner openFiles(String fileName) throws FileNotFoundException {
        String path = new File(String.format("src/%s", fileName)).getAbsolutePath();
        File file = new File(path);
        Scanner line = new Scanner(file);

        return line;
    }

    public static PrintWriter writeScanner(String path) {
        FileWriter fw = null;
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

    public static String readLines(String fileName) {
        Scanner line = null;
        String readLine = "";

        try {
            line = openFiles(fileName);

            while (line.hasNextLine()) {
                readLine = readLine.concat(line.nextLine() + "\n");
            }
        } catch (FileNotFoundException e) {
            title.printBox("No files were found.");
        }

        return readLine;
    }

    public static void addToInventory(String lootName, String lootStats) {
        String path = new File("src/Inventory").getAbsolutePath();
        PrintWriter writer = writeScanner(path);

        writer.append(String.format("\n     %s :: %s", lootName, lootStats));
        writer.append("\n- - - - - - - - - - - - - - - - - - - - - - - - - -");
        writer.close();
    }

    public static void newGameInventory() throws IOException {
        String path = new File("src/Inventory").getAbsolutePath();
        FileWriter fw = new FileWriter(path, false);
        PrintWriter pw = new PrintWriter(fw, false);
        pw.flush();

        pw.write("++================== INVENTORY ==================++");
        pw.close();
        fw.close();
    }

    public static void makeFileCopy(String copyFile, String fileName) throws IOException {
        String copyPath = new File(String.format("src/%s", copyFile)).getAbsolutePath();
        File newCopyFile = new File(copyPath);
        PrintWriter writer = writeScanner(copyPath);

        String fileToCopy = readLines(fileName);

        if (!newCopyFile.exists()) {
            newCopyFile.createNewFile();
        }

        writer.println(fileToCopy);
        writer.close();
    }

    public static void loadInventory() throws IOException {
        File file = new File(new File(String.format("src/SavedInventory")).getAbsolutePath());
        TextApp textApp = new TextApp();

        if (file.length()>0) {
            try {
                makeFileCopy("Inventory", "SavedInventory");
                title.printBox("Successfully loaded your saved inventory!");
            } catch (IOException e) {
                title.printBox("Cannot load saved files.");
                textApp.optionToExit();
            }
        }
    }
}
