package mx.edu.utez.bibliotecadigital.model;

import mx.edu.utez.bibliotecadigital.dataestructures.ArrayQueue;

public class Book {

    private long id;
    private String title;
    private String author;
    private String category;

    private int totalCopies;
    private int availableCopies;
    private boolean active;
    private ArrayQueue<Long> waitlist;

    public Book() {
        this.waitlist = new ArrayQueue<>(50);
        this.active = true;
    }

    public Book(long id, String title, String author, int totalCopies) {
        this();
        this.id = id;
        this.title = title;
        this.author = author;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
    }

    /*Getters */
    public long getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public int getTotalCopies() { return totalCopies; }
    public int getAvailableCopies() { return availableCopies; }
    public boolean isActive() { return active; }
    public ArrayQueue<Long> getWaitlist() { return waitlist; }

    /* Setters*/
    public void setId(long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setCategory(String category) { this.category = category; }
    public void setTotalCopies(int totalCopies) {
        int diff = totalCopies - this.totalCopies;
        this.totalCopies = totalCopies;
        this.availableCopies = Math.max(0, this.availableCopies + diff);
    }
    public void setAvailableCopies(int availableCopies) { this.availableCopies = availableCopies; }
    public void setActive(boolean active) { this.active = active; }
    public void setWaitlist(ArrayQueue<Long> waitlist) { this.waitlist = waitlist; }

    /*Helpers*/
    public void decreaseCopies() {
        if (this.availableCopies > 0) this.availableCopies--;
    }

    public void increaseCopies() {
        this.availableCopies++;
        if (this.availableCopies > this.totalCopies) this.availableCopies = this.totalCopies;
    }
}

