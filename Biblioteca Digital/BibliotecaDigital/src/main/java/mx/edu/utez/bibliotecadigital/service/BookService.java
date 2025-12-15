package mx.edu.utez.bibliotecadigital.service;

import mx.edu.utez.bibliotecadigital.model.Book;
import mx.edu.utez.bibliotecadigital.dataestructures.SinglyLinkedList;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private SinglyLinkedList<Book> books = new SinglyLinkedList<>();
    private long nextId = 1;

    public Book create(String title, String author, int copies) {
        Book book = new Book(nextId++, title, author, copies);
        books.add(book);
        return book;
    }

    public SinglyLinkedList<Book> findAll() {
        return books;
    }

    public Book findById(long id) {
        for (int i = 0; i < books.size(); i++) {
            Book b = books.getByIndex(i);
            if (b != null && b.getId() == id) {
                return b;
            }
        }
        return null;
    }

    public Book update(long id, String title, String author) {
        Book book = findById(id);
        if (book != null) {
            book.setTitle(title);
            book.setAuthor(author);
        }
        return book;
    }

    public boolean deactivate(long id) {
        Book book = findById(id);
        if (book != null) {
            book.setActive(false);
            return true;
        }
        return false;
    }
}
