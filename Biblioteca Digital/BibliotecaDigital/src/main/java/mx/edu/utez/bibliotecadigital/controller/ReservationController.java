package mx.edu.utez.bibliotecadigital.controller;

import mx.edu.utez.bibliotecadigital.model.Book;
import mx.edu.utez.bibliotecadigital.service.BookService;
import mx.edu.utez.bibliotecadigital.dataestructures.ArrayQueue;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final BookService bookService;

    public ReservationController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<?> getWaitlist(@PathVariable long bookId) {
        Book book = bookService.findById(bookId);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book.getWaitlist());
    }

    @DeleteMapping
    public ResponseEntity<?> cancelReservation(
            @RequestParam long userId,
            @RequestParam long bookId
    ) {
        Book book = bookService.findById(bookId);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }

        ArrayQueue<Long> original = book.getWaitlist();
        ArrayQueue<Long> temp = new ArrayQueue<>(original.size());

        while (!original.isEmpty()) {
            Long current = original.dequeue();
            if (current != null && current != userId) {
                temp.enqueue(current);
            }
        }

        book.setWaitlist(temp);

        return ResponseEntity.ok(
                Map.of("message", "Reserva cancelada correctamente")
        );
    }
}
