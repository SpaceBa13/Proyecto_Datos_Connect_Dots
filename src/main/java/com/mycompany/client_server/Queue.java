package com.mycompany.client_server;

public class Queue {
    private DoublyLinkedList lista;

    public void enqueue(Object element) {
        this.lista.addLast(element);
    }
    public Object dequeue() {
        return this.lista.deleteFirst();
    }

}
