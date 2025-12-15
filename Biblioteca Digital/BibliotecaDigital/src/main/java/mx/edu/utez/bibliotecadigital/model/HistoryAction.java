package mx.edu.utez.bibliotecadigital.model;

public class HistoryAction {

    public enum ActionType {
        CREATE_LOAN,
        RETURN_LOAN,
        ADD_TO_WAITLIST
    }

    private ActionType actionType;
    private Long userId;
    private Long bookId;
    private Long loanId;
    private Integer previousAvailableCopies;
    private String timestamp;

    public HistoryAction() { }

    public HistoryAction(ActionType actionType, Long userId, Long bookId, Long loanId) {
        this.actionType = actionType;
        this.userId = userId;
        this.bookId = bookId;
        this.loanId = loanId;
    }

    /* Getters */
    public ActionType getActionType() { return actionType; }
    public Long getUserId() { return userId; }
    public Long getBookId() { return bookId; }
    public Long getLoanId() { return loanId; }
    public Integer getPreviousAvailableCopies() { return previousAvailableCopies; }
    public String getTimestamp() { return timestamp; }

    /* Setters */
    public void setActionType(ActionType actionType) { this.actionType = actionType; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }
    public void setLoanId(Long loanId) { this.loanId = loanId; }
    public void setPreviousAvailableCopies(Integer previousAvailableCopies) { this.previousAvailableCopies = previousAvailableCopies; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
