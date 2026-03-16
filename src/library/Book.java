package library;

import java.io.Serializable;

public class Book implements Serializable {

    public int id;
    public String title;
    public String author;
    public boolean isBorrowed;

    public Book(int id, String title, String author) {

        this.id = id;
        this.title = title;
        this.author = author;
        this.isBorrowed = false;

    }
}