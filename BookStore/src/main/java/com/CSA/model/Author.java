package com.CSA.model;

/**
 * Represents an author in the bookstore system.
 */
public class Author {
    private int id;
    private String name;
    private String biography;

    /**
     * Default constructor for Author.
     */
    public Author() {
    }

    /**
     * Parameterized constructor for Author.
     * 
     * @param id The unique identifier of the author
     * @param name The name of the author
     * @param biography The biography of the author
     */
    public Author(int id, String name, String biography) {
        this.id = id;
        this.name = name;
        this.biography = biography;
    }

    /**
     * Gets the ID of the author.
     * 
     * @return The author ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the author.
     * 
     * @param id The author ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the author.
     * 
     * @return The author name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the author.
     * 
     * @param name The author name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the biography of the author.
     * 
     * @return The author biography
     */
    public String getBiography() {
        return biography;
    }

    /**
     * Sets the biography of the author.
     * 
     * @param biography The author biography to set
     */
    public void setBiography(String biography) {
        this.biography = biography;
    }

    /**
     * Returns a string representation of the Author object.
     * 
     * @return String representation of the Author
     */
    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", biography='" + biography + '\'' +
                '}';
    }
}
