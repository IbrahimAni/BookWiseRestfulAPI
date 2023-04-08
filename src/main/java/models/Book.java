package models;

import jakarta.xml.bind.annotation.XmlRootElement;

/**
*
* The Book class represents a book with an id, title, author, date, genres, characters and synopsis.
* @author Ibrahim Anifowoshe (22450374).
* @version 1.0
*/
@XmlRootElement
public class Book {
    int id;
    String title;
    String author;
    String date;
    String genres;
    String characters;
    String synopsis;

    /**
     *The Book class represents a book with various attributes such as id, title, author, date, genres, characters and synopsis.
	 *This is a default constructor for the Book class.
	 *It creates a new instance of the Book class with default values for all attributes.
     */
    public Book() {}

    /**
     *The Book class represents a book with various attributes such as id, title, author, date, genres, characters and synopsis.
     *@param id The unique identifier for the book.
     *@param title The title of the book.
     *@param author The author of the book.
     *@param date The date of publication for the book.
     *@param genres The genres of the book.
     *@param characters The characters in the book.
     *@param synopsis A brief summary of the book.
     */
    public Book(int id, String title, String author, String date, String genres, String characters, String synopsis) {

        this.id = id;
        this.title = title;
        this.author = author;
        this.date = date;
        this.genres = genres;
        this.characters = characters;
        this.synopsis = synopsis;

    }

    /**
     * Returns the unique identifier for the book.
     * @return the id of the book.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the book.
     * @param id the new id for the book.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the title of the book.
     * @return the title of the book.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the title of the book
     * @param title the new title for the book
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the author of the book
     * @return the author of the book
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Set the author of the book
     * @param author the new author of the book
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Returns the date the book was released
     * @return the date the book was released
     */
    public String getDate() {
        return date;
    }

    /**
     * Set the date the book was released
     * @param date the new date of the book been released
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Return the genres of the book.
     * @return the genres of the book.
     */
    public String getGenres() {
        return genres;
    }

    /**
     * Set the genres of the book
     * @param genres the new genres of the book
     */
    public void setGenres(String genres) {
        this.genres = genres;
    }

    /**
     * Return the characters in the book.
     * @return the characters in the book.
     */
    public String getCharacters() {
        return characters;
    }

    /**
     * Set the characters of the book
     * @param characters the new characters of the book
     */
    public void setCharacters(String characters) {
        this.characters = characters;
    }

    /**
     * Returns the synopsis of the book.
     * @return the synopsis of the book.
     */
    public String getSynopsis() {
        return synopsis;
    }

    /**
     * Sets the synopsis of the book.
     * @param synopsis the new synopsis of the book.
     */
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    @Override
    public String toString() {
        return " | " + this.id + " | " + this.title + " | " + this.author + " | " + this.date + " | " + this.genres + " | " + this.characters + " | " + this.synopsis + " | ";
    }
}

