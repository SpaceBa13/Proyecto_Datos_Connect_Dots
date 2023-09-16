package com.mycompany.client_server;

public class Queue {
    public DoublyLinkedList lista = new DoublyLinkedList();

    /**
     * Agrega un elemento al final de la lista
     * @param element
     */
    public void enqueue(Object element) {
        this.lista.addLast(element);
    }

    /**
     * Elimina el primer elemento de la lista
     * @return
     */
    public Object dequeue() {
        return this.lista.deleteFirst();
    }

    /**
     * Imprime la cola
     */
    public void Display(){
        this.lista.displayList();
    }

    /**
     * Retorna el primer elemento de la lista
     * @return
     */
    public Object peek(){
        return this.lista.get_index(1);
    }

    /**
     * Busca un elemento dado en la lista
     * @param valor
     * @return
     */
    public boolean Search(Object valor) {
        if (this.lista.find(valor) == true) {
            return true;
        }else{
            return false;
        }
    }
}
