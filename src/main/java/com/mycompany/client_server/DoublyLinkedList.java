package com.mycompany.client_server;

public class DoublyLinkedList {
    Node head;
    Node tail;
    int size;

    /**
     * Constructor
     */
    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Anade un elemento al principio de la lista
     * @param data
     */
    public void addFirst(Object data) {
        Node temp = new Node(data);
        if (head == null) {
            head = temp;
            tail = temp;
            size++;
        }
        else {
            temp.next = head;
            head.prev = temp;
            head = temp;
            size++;
        }
    }

    /**
     * anade un elemento al final de la lista
     * @param data
     */
    public void addLast(Object data) {
        Node temp = new Node(data);
        if (tail == null) {
            head = temp;
            tail = temp;
            size++;
        }
        else {
            tail.next = temp;
            temp.prev = tail;
            tail = temp;
            size++;
        }
    }

    /**
     * Elimina el primer elemnto de la lista
     * @return
     */
    public Object deleteFirst() {
        if (head == null) {
            return null;
        }
        if (head == tail) {
            head = null;
            tail = null;
            size--;
            return null;
        }
        Node temp = head;
        head = head.next;
        head.prev = null;
        temp.next = null;
        size--;
        return null;
    }

    /**
     * Elimina el ultimo elemento de la lista
     */
    public void deleteLast() {
        if (tail == null) {
            return;
        }

        if (head == tail) {
            head = null;
            tail = null;
            size--;
            return;
        }

        Node temp = tail;
        tail = tail.prev;
        tail.next = null;
        temp.prev = null;
        size--;
    }

    /**
     * Busca un elemento dado en la lista
     * @param searchValue
     * @return
     */
    public boolean find(Object searchValue) {
        Node current = this.head;
        while (current != null) {
            if (current.getData().equals(searchValue)) {
                return true;
            } else {
                current = current.getNext();
            }
        }
        return false;
    }

    /**
     * Imprimne la lista elemento po elemento
     */
    public void displayList() {
        Node current = this.head;
        while (current != null) {
            System.out.println(current.getData());
            current = current.getNext();
        }
    }

    /**
     * Retorna el elemento contenido en la posicion dada
     * @param i
     * @return
     */
    public Object get_index(int i) {
        int position = 1;
        Node current = this.head;
        if(i == 1){
            return current.getData();
        }
        while (current != null && position < i) {
            current = current.next;
            position++;
        }return current.getData();
    }
}
