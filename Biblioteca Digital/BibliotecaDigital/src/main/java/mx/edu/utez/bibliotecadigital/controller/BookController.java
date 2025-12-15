package mx.edu.utez.bibliotecadigital.controller;

import mx.edu.utez.bibliotecadigital.model.Book;
import mx.edu.utez.bibliotecadigital.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {
        Book created = bookService.create(
                book.getTitle(),
                book.getAuthor(),
                book.getTotalCopies()
        );
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable long id) {
        Book book = bookService.findById(id);
        if (book == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(book);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> deactivate(@PathVariable long id) {
        boolean ok = bookService.deactivate(id);
        if (!ok) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().build();
    }
}
