package edu.kit.informatik.C;

/**
 * @author Sebastian Schindler
 * @version 1.0
 */
public class Book {

    private String title;
    private String year;
    private String creator;

    /**
     * Constructs a new book with title, creator and year
     * @param title title
     * @param year year
     * @param creator creator
     */
    public Book(String title, String year, String creator) {
        this.title = title;
        this.year = year;
        this.creator = creator;
    }

    /**
     * Returns the title
     * @return title of the book
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the year
     * @return year of the book
     */
    public String getYear() {
        return year;
    }

    /**
     * Returns the creator
     * @return creator of the book
     */
    public String getCreator() {
        return creator;
    }


}
