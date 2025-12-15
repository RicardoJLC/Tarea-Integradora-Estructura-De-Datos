package mx.edu.utez.bibliotecadigital.dataestructures;

public class ArrayStack<T> {

    private Object[] data;
    private int top;
    private int capacity;

    public ArrayStack(int capacity) {
        this.capacity = capacity;
        this.data = new Object[capacity];
        this.top = -1;
    }

    public boolean push(T value) {
        if (top + 1 == capacity) {
            return false;
        }
        data[++top] = value;
        return true;
    }

    public T pop() {
        if (top == -1) {
            return null;
        }
        @SuppressWarnings("unchecked")
        T value = (T) data[top];
        data[top--] = null;
        return value;
    }

    public boolean isEmpty() {
        return top == -1;
    }
}
