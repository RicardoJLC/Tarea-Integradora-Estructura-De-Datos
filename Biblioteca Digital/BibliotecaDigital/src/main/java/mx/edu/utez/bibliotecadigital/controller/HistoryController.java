package mx.edu.utez.bibliotecadigital.controller;

import mx.edu.utez.bibliotecadigital.model.Book;
import mx.edu.utez.bibliotecadigital.model.HistoryAction;
import mx.edu.utez.bibliotecadigital.service.BookService;
import mx.edu.utez.bibliotecadigital.service.HistoryService;
import mx.edu.utez.bibliotecadigital.service.LoanService;
import mx.edu.utez.bibliotecadigital.dataestructures.ArrayQueue;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/history")
public class HistoryController {

    private final HistoryService historyService;
    private final LoanService loanService;
    private final BookService bookService;

    public HistoryController(
            HistoryService historyService,
            LoanService loanService,
            BookService bookService
    ) {
        this.historyService = historyService;
        this.loanService = loanService;
        this.bookService = bookService;
    }

    @PostMapping("/undo")
    public ResponseEntity<?> undo() {

        HistoryAction action = historyService.undo();

        if (action == null) {
            return ResponseEntity.badRequest().body(
                    Map.of("message", "No hay acciones para deshacer")
            );
        }

        if (action.getActionType() == HistoryAction.ActionType.CREATE_LOAN) {

            loanService.returnLoan(action.getLoanId());

            Book book = bookService.findById(action.getBookId());
            if (book != null && action.getPreviousAvailableCopies() != null) {
                book.setAvailableCopies(action.getPreviousAvailableCopies());
            }

            return ResponseEntity.ok(
                    Map.of("message", "Se deshizo la creación del préstamo")
            );
        }

        if (action.getActionType() == HistoryAction.ActionType.ADD_TO_WAITLIST) {

            Book book = bookService.findById(action.getBookId());
            if (book == null) {
                return ResponseEntity.notFound().build();
            }

            ArrayQueue<Long> original = book.getWaitlist();
            ArrayQueue<Long> temp = new ArrayQueue<>(original.size());

            while (!original.isEmpty()) {
                Long current = original.dequeue();
                if (current != null && current != action.getUserId()) {
                    temp.enqueue(current);
                }
            }

            book.setWaitlist(temp);

            return ResponseEntity.ok(
                    Map.of("message", "Se deshizo la reserva del usuario")
            );
        }

        return ResponseEntity.ok(
                Map.of("message", "Acción no soportada para undo")
        );
    }
}
