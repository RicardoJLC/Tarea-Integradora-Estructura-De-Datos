package mx.edu.utez.bibliotecadigital.service;

import mx.edu.utez.bibliotecadigital.model.HistoryAction;
import mx.edu.utez.bibliotecadigital.dataestructures.ArrayStack;
import org.springframework.stereotype.Service;

@Service
public class HistoryService {

    private ArrayStack<HistoryAction> stack = new ArrayStack<>(50);

    public void record(HistoryAction action) {
        stack.push(action);
    }

    public HistoryAction undo() {
        return stack.pop();
    }

    public ArrayStack<HistoryAction> getHistory() {
        return stack;
    }
}
