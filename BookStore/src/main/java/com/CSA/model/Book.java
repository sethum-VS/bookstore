package com.CSA.model;

/**
 * Represents a book in the bookstore system.
 */
public class Book {
    private int id;
    private String title;
    private int authorId;
    private String isbn;
    private int publicationYear;
    private double price;
    private int stockQuantity;

    /**
     * Default constructor for Book.
     */
    public Book() {
    }

    /**
     * Parameterized constructor for Book.
     * 
     * @param id The unique identifier of the book
     * @param title The title of the book
     * @param authorId The ID of the author who wrote the book
     * @param isbn The ISBN of the book
     * @param publicationYear The year the book was published
     * @param price The price of the book
     * @param stockQuantity The quantity of the book in stock
     */
    public Book(int id, String title, int authorId, String isbn, int publicationYear, double price, int stockQuantity) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    /**
     * Gets the ID of the book.
     * 
     * @return The book ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the book.
     * 
     * @param id The book ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the title of the book.
     * 
     * @return The book title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the book.
     * 
     * @param title The book title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the author ID of the book.
     * 
     * @return The author ID
     */
    public int getAuthorId() {
        return authorId;
    }

    /**
     * Sets the author ID of the book.
     * 
     * @param authorId The author ID to set
     */
    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    /**
     * Gets the ISBN of the book.
     * 
     * @return The ISBN
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Sets the ISBN of the book.
     * 
     * @param isbn The ISBN to set
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Gets the publication year of the book.
     * 
     * @return The publication year
     */
    public int getPublicationYear() {
        return publicationYear;
    }

    /**
     * Sets the publication year of the book.
     * 
     * @param publicationYear The publication year to set
     */
    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    /**
     * Gets the price of the book.
     * 
     * @return The price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the book.
     * 
     * @param price The price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the stock quantity of the book.
     * 
     * @return The stock quantity
     */
    public int getStockQuantity() {
        return stockQuantity;
    }

    /**
     * Sets the stock quantity of the book.
     * 
     * @param stockQuantity The stock quantity to set
     */
    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    /**
     * Returns a string representation of the Book object.
     * 
     * @return String representation of the Book
     */
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authorId=" + authorId +
                ", isbn='" + isbn + '\'' +
                ", publicationYear=" + publicationYear +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                '}';
    }
}



