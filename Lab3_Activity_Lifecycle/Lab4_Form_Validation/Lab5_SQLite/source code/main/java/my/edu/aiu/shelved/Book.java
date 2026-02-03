package my.edu.aiu.shelved;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.util.Locale;

public class Book {
    private int id;
    private String title;
    private double price;
    private boolean isEBook;
    private LocalDate created;

    public Book() { this(0, "No title", 0.00, true, LocalDate.now()); }

    public Book(int id, String title, double price, boolean isEBook, LocalDate created) {
        setId(id);
        setTitle(title);
        setPrice(price);
        setEBook(isEBook);
        setCreated(created);
    }

    //Todo: Add toString()

    // getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public double getPrice() { return price; }
    public boolean isEBook() { return isEBook; }
    public LocalDate getCreated() { return created; }

    // setters
    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setPrice(double price) { this.price = price; }
    public void setEBook(boolean isEBook) { this.isEBook = isEBook; }
    public void setCreated(LocalDate created) { this.created = created; }
}
