package edu.kit.informatik.C;

import edu.kit.informatik.Levenshtein;
import edu.kit.informatik.Terminal;

import java.util.Locale;

/**
 * @author Sebastian Schindler
 * @version 1.0
 */
public class Search {

    private static float getLineDistance(String request, String line, String[] allbooks) {
        float distance1 = 1f;
        float distance2 = 1f;
        boolean or = false;
        String tmpRequest = request.substring(4, request.length() - 1);
        if (tmpRequest.startsWith("(")) {
            tmpRequest = tmpRequest.substring(1);
        }

        request = request.trim();


        if (!request.substring(0, 3).equals("OR(") && !request.substring(0, 4).equals("AND(")) {
            return getSingleDistance(request, line);
        }


        if (request.substring(0, 3).equals("OR(")) {
            or = true;
            tmpRequest = request.substring(4, request.length() - 1);
        }
        String[] requests = tmpRequest.split(", ");
        if (requests.length == 2) {
            distance1 = getSingleDistance(requests[0], line);
            distance2 = getSingleDistance(requests[1], line);
        }
        if (requests.length == 3) {
            if (requests[0].substring(0, 3).equals("OR(") || requests[0].substring(0, 4).equals("AND(")) {
                distance1 = getLineDistance(requests[0] + ", " + requests[1], line, allbooks);
                distance2 = getSingleDistance(requests[2], line);
            }
            if (requests[1].substring(0, 3).equals("OR(") || requests[1].substring(0, 4).equals("AND(")) {
                distance1 = getLineDistance(requests[1] + ", " + requests[2], line, allbooks);
                distance2 = getSingleDistance(requests[0], line);
            }
        }

        if (or) {
            if (distance1 < distance2) {
                return distance1;
            }
            return distance2;
        }
        if (distance1 < distance2) {
            return distance2;
        }
        return distance1;
    }

    private static float getSingleDistance(String request, String line) {
        String title = "";
        String year = "";
        String creator = "";

        request = request.trim();

        String[] attribs = line.split(",");
        for (String attrib : attribs) {
            if (attrib.substring(0, 5).toLowerCase(Locale.ENGLISH).equals("title")) {
                title = attrib.substring(6);
            }
            if (attrib.substring(0, 7).toLowerCase(Locale.ENGLISH).equals("creator")) {
                creator = attrib.substring(8);
            }
            if (attrib.substring(0, 4).toLowerCase(Locale.ENGLISH).equals("year")) {
                year = attrib.substring(5);
            }
        }

        if (request.substring(0, 5).toLowerCase(Locale.ENGLISH).equals("title")) {
            if (title.equals("unknown")) {
                return 1f;
            }
            Levenshtein lev = new Levenshtein(title, request.substring(6));
            return new Float(lev.getNormalizedLevenshteinDistance());
        }
        if (request.substring(0, 7).toLowerCase(Locale.ENGLISH).equals("creator")) {
            if (creator.equals("unknown")) {
                return 1f;
            }
            Levenshtein lev = new Levenshtein(creator, request.substring(8));
            return new Float(lev.getNormalizedLevenshteinDistance());
        }
        if (request.substring(0, 4).toLowerCase(Locale.ENGLISH).equals("year")) {
            if (year.equals("unknown")) {
                return 1f;
            }
            Levenshtein lev = new Levenshtein(year, request.substring(5));
            return new Float(lev.getNormalizedLevenshteinDistance());
        }

        Terminal.printLine("Error, unknown command!\n" +
                "Please input \"search\" followed by \"keyword=value\"");
        System.exit(1);
        return 1f;
    }


    private static boolean evalLine(String request, String line, float threshold, String[] allbooks) {
        return getLineDistance(request, line, allbooks) < threshold;
    }

    /**
     * Searches for a term in the librabry
     * @param request search term
     * @param threshold threshold for Levenshtein Distance
     * @param allbooks Array of all lines
     */
    public static void searchAndPrint(String request, float threshold, String[] allbooks) {
        for (int i = 0; i < allbooks.length; i++) {
            Terminal.printLine(allbooks[i] + "," + evalLine(request, allbooks[i], threshold, allbooks));
        }
    }

}
