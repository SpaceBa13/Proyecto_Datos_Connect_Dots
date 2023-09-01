package com.mycompany.client_server;

public class Queue {
    public DoublyLinkedList lista = new DoublyLinkedList();

    public void enqueue(Object element) {
        this.lista.addLast(element);
    }
    public Object dequeue() {
        return this.lista.deleteFirst();
    }
    public void Display(){
        this.lista.displayList();
    }
    public boolean Search(Object valor) {
        if (this.lista.find(valor) == true) {
            return true;
        }else{
            return false;
        }
    }
}
