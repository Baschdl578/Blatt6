package edu.kit.informatik.A;

import edu.kit.informatik.Terminal;

import java.util.*;

/**
 * @author Sebastian Schindler
 * @version 1.0
 */
public class Indexer {
    private static TreeMap<String, LinkedList<Integer>> tree;
    private static String[] book;



    private static void parseArgs(String args[]) {
        if (args.length != 1) {
            Terminal.printLine("Error, please supply only one argument");
            System.exit(0);
        }

        book = Reader.read(args[0]);
    }

    private static void getInput() {
        String[] commands = Terminal.readLine().split(" ");

        if (commands[0].equals("search")) {
            String out = commands[1] + ":";
            String values = "";
            if (commands.length == 2 && tree.containsKey(commands[1])) {
                ListIterator<Integer> iter = tree.get(commands[1]).listIterator(0);
                while (iter.hasNext()) {
                    if (!values.equals("")) values += ",";
                    values += iter.next().toString();
                }
            } else {
                values = "null";
            }
            out += values;
            if (commands.length != 2) {
                out = "Error, please give a valid command";
            }

            Terminal.printLine(out);
        } else {

            if (commands[0].equals("info")) {
                String out = "";
                Iterator<String> keys = tree.keySet().iterator();

                while (keys.hasNext()) {
                    if (!out.equals("")) out += ",";
                    String key = keys.next();
                    out += key + ":";

                    String values = "";
                    ListIterator<Integer> iter = tree.get(key).listIterator(0);
                    while (iter.hasNext()) {
                        if (!values.equals("")) values += ",";
                        values += iter.next().toString();
                    }
                    out += values;
                }
                Terminal.printLine(out);

            } else {

                if (commands[0].equals("quit")) {
                    return;
                } else {
                    Terminal.printLine("Error, unknown command");
                }
            }
        }

        getInput();
    }


    private static void fillTree() {
        int pageNmbr = 0;
        for (String line: book) {
            if (line.substring(0, 5).equals("Seite")) {
                pageNmbr = Integer.parseInt(line.substring(5));
                continue;
            }

            String[] words = line.split(" ");
            for (String word: words) {
                LinkedList<Integer> values;
                if (tree.containsKey(word)) {
                    values = tree.get(word);
                    if (!values.contains(pageNmbr)) {
                        values.add(pageNmbr);
                    }
                } else {
                    values = new LinkedList<Integer>();
                    values.add(pageNmbr);
                }
                tree.put(word, values);
            }
        }
    }

    /**
     * Main method, Indexes the book
     * @param args arguments from command line
     */
    public static void main(String[] args) {
        tree = new TreeMap<String, LinkedList<Integer>>();
        parseArgs(args);
        fillTree();
        getInput();
    }
}
