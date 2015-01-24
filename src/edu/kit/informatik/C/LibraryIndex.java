package edu.kit.informatik.C;


import edu.kit.informatik.Levenshtein;
import edu.kit.informatik.Terminal;

import java.util.Locale;
import java.util.TreeMap;

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
    }

    private static boolean evalLine(String title, String year, String creator, String line) {
        boolean out = false;

        String[] attribs = line.split(",");

        for (String attrib: attribs) {
            if (title != null && attrib.substring(0, 5).toLowerCase(Locale.ENGLISH).equals("title")) {
                    Levenshtein titleLev = new Levenshtein(attrib.substring(6), title);
                    if (titleLev.getNormalizedLevenshteinDistance() < threshold) {
                        out = true;
                    }
            }
            if (creator != null && attrib.substring(0, 7).toLowerCase(Locale.ENGLISH).equals("creator")) {
                    Levenshtein creatorLev = new Levenshtein(attrib.substring(8), creator);
                    if (creatorLev.getNormalizedLevenshteinDistance() < threshold) {
                        out = true;
                    }
            }
            if (year != null && attrib.substring(0, 4).toLowerCase(Locale.ENGLISH).equals("year")) {
                Levenshtein yearLev = new Levenshtein(attrib.substring(5), year);
                if (yearLev.getNormalizedLevenshteinDistance() < threshold) {
                    out = true;
                }
            }
        }


        return out;
    }

    /**
     * Main Method
     * @param args arguments from command line
     */
    public static void main(String[] args) {
        init(args);

    }


}
