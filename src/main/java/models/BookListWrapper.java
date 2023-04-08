package models;

import java.util.ArrayList;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "books")
@XmlAccessorType(XmlAccessType.FIELD)
public class BookListWrapper {
	@XmlElement(name = "book")
    private ArrayList<Book> books;

    public BookListWrapper() {
        books = new ArrayList<>();
    }

    public BookListWrapper(ArrayList<Book> books) {
        this.books = books;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }
}
