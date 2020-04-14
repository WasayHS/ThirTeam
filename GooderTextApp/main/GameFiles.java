package main;

import java.io.*;
import java.util.Scanner;

public class GameFiles {

    public static Scanner openFiles (String fileName) throws FileNotFoundException {
        String path = new File(String.format("src/%s", fileName)).getAbsolutePath();
        File file = new File(path);
        Scanner line = new Scanner(file);
        return line;
    }

    public static PrintWriter writeScanner (String path) {
        FileWriter fw = null;

        try {
            fw = new FileWriter(path, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter writer = new PrintWriter(bw);
        return writer;
    }
    public static void printFileContents(String fileName) throws FileNotFoundException {
        Scanner line = openFiles(fileName);
        String readLine = "";

        while(line.hasNextLine()) {
            readLine = readLine.concat(line.nextLine() + "\n");
        }
        System.out.println(readLine);
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

    public static void saveInventory(String fileName) throws IOException {
        String savePath = new File(String.format("src/%s", fileName)).getAbsolutePath();
        File saveFile = new File(savePath);
        PrintWriter writer = writeScanner(savePath);
        Scanner inventory = openFiles("Inventory");
        String readLine = "";

        if (!saveFile.exists()) {
            saveFile.createNewFile();
        }

        while(inventory.hasNextLine()) {
            readLine = readLine.concat(inventory.nextLine() + "\n");
        }
        writer.println(readLine);
        writer.close();
    }

    public static boolean loadSavedInventory () {
        File file = new File(new File(String.format("src/SavedInventory.txt")).getAbsolutePath());
        return (file.exists());
    }
}
