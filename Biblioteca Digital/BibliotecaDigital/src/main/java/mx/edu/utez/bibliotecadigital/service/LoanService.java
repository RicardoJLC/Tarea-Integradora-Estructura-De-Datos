package mx.edu.utez.bibliotecadigital.service;

import mx.edu.utez.bibliotecadigital.model.Book;
import mx.edu.utez.bibliotecadigital.model.HistoryAction;
import mx.edu.utez.bibliotecadigital.model.Loan;
import mx.edu.utez.bibliotecadigital.dataestructures.SinglyLinkedList;
import org.springframework.stereotype.Service;

@Service
public class LoanService {

    private SinglyLinkedList<Loan> loans = new SinglyLinkedList<>();
    private long nextId = 1;

    private final BookService bookService;
    private final HistoryService historyService;

    public LoanService(BookService bookService, HistoryService historyService) {
        this.bookService = bookService;
        this.historyService = historyService;
    }

    // Flujo B
    public Loan createLoan(long userId, long bookId) {
        Book book = bookService.findById(bookId);
        if (book == null || !book.isActive()) return null;

        if (book.getAvailableCopies() > 0) {
            Loan loan = new Loan(nextId++, userId, bookId);
            loans.add(loan);
            book.decreaseCopies();

            historyService.record(
                    new HistoryAction(
                            HistoryAction.ActionType.CREATE_LOAN,
                            userId,
                            bookId,
                            loan.getId()
                    )
            );
            return loan;
        } else {
            book.getWaitlist().enqueue(userId);
            historyService.record(
                    new HistoryAction(
                            HistoryAction.ActionType.ADD_TO_WAITLIST,
                            userId,
                            bookId,
                            null
                    )
            );
            return null;
        }
    }

    // Flujo C
    public boolean returnLoan(long loanId) {
        for (int i = 0; i < loans.size(); i++) {
            Loan loan = loans.getByIndex(i);
            if (loan != null && loan.getId() == loanId && loan.isActive()) {
                loan.close();
                Book book = bookService.findById(loan.getBookId());

                if (!book.getWaitlist().isEmpty()) {
                    Long nextUser = book.getWaitlist().dequeue();
                    Loan newLoan = new Loan(nextId++, nextUser, book.getId());
                    loans.add(newLoan);

                    historyService.record(
                            new HistoryAction(
                                    HistoryAction.ActionType.RETURN_LOAN,
                                    nextUser,
                                    book.getId(),
                                    newLoan.getId()
                            )
                    );
                } else {
                    book.increaseCopies();
                }
                return true;
            }
        }
        return false;
    }

    public SinglyLinkedList<Loan> getActiveLoans() {
        SinglyLinkedList<Loan> active = new SinglyLinkedList<>();
        for (int i = 0; i < loans.size(); i++) {
            Loan loan = loans.getByIndex(i);
            if (loan != null && loan.isActive()) {
                active.add(loan);
            }
        }
        return active;
    }

    public SinglyLinkedList<Loan> getLoansByUser(long userId) {
        SinglyLinkedList<Loan> result = new SinglyLinkedList<>();
        for (int i = 0; i < loans.size(); i++) {
            Loan loan = loans.getByIndex(i);
            if (loan != null && loan.getUserId() == userId) {
                result.add(loan);
            }
        }
        return result;
    }
}
