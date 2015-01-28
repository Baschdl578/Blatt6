package edu.kit.informatik.C;


import edu.kit.informatik.Terminal;

import java.util.Locale;

/**
 * @author Sebastian Schindler
 * @version 1.0
 */
public class LibraryIndex {
    private static float threshold;
    private static String[] allBooks;


    private static void init(String[] args) {
        try {
            threshold = Float.parseFloat(args[0]);
        } catch (NumberFormatException e) {
            Terminal.printLine("Error, please enter a valid threshold (floating point number)");
            System.exit(0);
        }
        if (threshold < 0 || threshold > 1) {
            Terminal.printLine("Error, please enter a valid threshold between 0 and 1");
            System.exit(0);
        }

        allBooks = Reader.read(args[1]);
        parseBooks();
    }

    private static void parseBooks() {
        String[] tmp = new String[allBooks.length];
        for (int i = 0; i < allBooks.length; i++) {
            String[] attribs = allBooks[i].split(",");
            boolean missTitle = true;
            boolean missCreator = true;
            boolean missYear = true;
            String title = "title=unknown";
            String creator = "creator=unknown";
            String year = "year=unknown";
            for (String attrib: attribs) {
                if (attrib.substring(0, 5).toLowerCase(Locale.ENGLISH).equals("title")) {
                    title = attrib.toLowerCase(Locale.ENGLISH);
                }
                if (attrib.substring(0, 7).toLowerCase(Locale.ENGLISH).equals("creator")) {
                    creator = attrib.toLowerCase(Locale.ENGLISH);
                }
                if (attrib.substring(0, 4).toLowerCase(Locale.ENGLISH).equals("year")) {
                    year = attrib.toLowerCase(Locale.ENGLISH);
                }
            }
            String tmpBook = creator + "," + title + "," + year;
            tmp[i] = tmpBook;
        }
        allBooks = tmp;
    }

    private static void getInput() {
        String[] commands = Terminal.readLine().split(" ");

        if (commands[0].equals("quit")) {
            System.exit(0);
        }
        if (!commands[0].equals("search")) {
            Terminal.printLine("Error, unknown command!\n"
                    + "Please input \"search\" followed by \"keyword=value\".");
        } else {
            Search.searchAndPrint(commands[1], threshold, allBooks);
        }
        getInput();
    }

    /**
     * Main Method
     * @param args arguments from command line
     */
    public static void main(String[] args) {
        init(args);
        getInput();
    }


}
