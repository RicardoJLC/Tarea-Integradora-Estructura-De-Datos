package mx.edu.utez.bibliotecadigital.dataestructures;

public class ArrayQueue<T> {

    private Object[] data;
    private int front;
    private int rear;
    private int size;
    private int capacity;

    public ArrayQueue(int capacity) {
        this.capacity = capacity;
        this.data = new Object[capacity];
        this.front = 0;
        this.rear = 0;
        this.size = 0;
    }

    public boolean enqueue(T value) {
        if (size == capacity) {
            return false;
        }
        data[rear] = value;
        rear = (rear + 1) % capacity;
        size++;
        return true;
    }

    public T dequeue() {
        if (size == 0) {
            return null;
        }
        @SuppressWarnings("unchecked")
        T value = (T) data[front];
        data[front] = null;
        front = (front + 1) % capacity;
        size--;
        return value;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
}
