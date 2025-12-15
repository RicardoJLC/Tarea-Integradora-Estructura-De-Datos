package mx.edu.utez.bibliotecadigital.model;

public class Loan {

    private long id;
    private long userId;
    private long bookId;
    private boolean active;

    public Loan(long id, long userId, long bookId) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.active = true;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public long getBookId() {
        return bookId;
    }

    public boolean isActive() {
        return active;
    }

    public void close() {
        this.active = false;
    }

    public void reopen() {
        this.active = true;
    }
}
