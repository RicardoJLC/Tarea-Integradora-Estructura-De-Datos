package mx.edu.utez.bibliotecadigital.controller;
import mx.edu.utez.bibliotecadigital.model.Loan;
import mx.edu.utez.bibliotecadigital.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    public ResponseEntity<?> createLoan(@RequestBody Map<String, Long> body) {
        long userId = body.get("userId");
        long bookId = body.get("bookId");

        Loan loan = loanService.createLoan(userId, bookId);

        if (loan == null) {
            return ResponseEntity.ok(
                    Map.of("message", "No hay copias disponibles. Usuario agregado a lista de espera.")
            );
        }

        return ResponseEntity.status(201).body(loan);
    }

    @PostMapping("/{loanId}/return")
    public ResponseEntity<?> returnLoan(@PathVariable long loanId) {
        boolean ok = loanService.returnLoan(loanId);
        if (!ok) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(
                Map.of("message", "Libro devuelto correctamente")
        );
    }

    @GetMapping("/active")
    public ResponseEntity<?> activeLoans() {
        return ResponseEntity.ok(loanService.getActiveLoans());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> loansByUser(@PathVariable long userId) {
        return ResponseEntity.ok(loanService.getLoansByUser(userId));
    }
}

