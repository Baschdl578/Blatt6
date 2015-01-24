package edu.kit.informatik.C;

import edu.kit.informatik.Terminal;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Sebastian Schindler
 * @version 1.0
 */
public class Reader {
    /**
     * Counts the lines in a File
     * @param filename filename
     * @return number of Lines
     * @throws java.io.IOException on unexpected Error
     */
    public static int countLines(String filename) throws IOException {
        int lines = 0;
        FileReader in = null;
        try {
            in = new FileReader(filename);
        } catch (FileNotFoundException e) {
            Terminal.printLine("The file was not found.");
            System.exit(1);
        }
        BufferedReader reader = new BufferedReader(in);
        try {
            String line = reader.readLine();
            while (line != null) {
                lines++;
                line = reader.readLine();
            }
        } catch (IOException e) {
            Terminal.printLine("Something went wrong. Is the file valid?");
            System.exit(1);
        }
        return lines;
    }
    /**
     * Reads a file
     * @param args file
     * @return String array with all the lines;
     */
    public static String[] read(String args) {
        String[] lines = null;
        FileReader in = null;
        try {
            in = new FileReader(args);
        } catch (FileNotFoundException e) {
            Terminal.printLine("The file was not found.");
            System.exit(1);
        }
        BufferedReader reader = new BufferedReader(in);
        try {
            lines = new String[countLines(args)];
            String line = reader.readLine();
            int i = 0;
            while (line != null) {
                lines[i] = line;
                i++;
                line = reader.readLine();
            }
        } catch (IOException e) {
            Terminal.printLine("Something went wrong. Is the file valid?");
            System.exit(1);
        }
        return lines;
    }
}