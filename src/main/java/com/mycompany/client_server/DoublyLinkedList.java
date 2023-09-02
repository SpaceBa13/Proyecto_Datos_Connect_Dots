package com.mycompany.client_server;

public class DoublyLinkedList {
    Node head;
    Node tail;

    int size;

    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

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

    public void displayList() {
        Node current = this.head;
        while (current != null) {
            System.out.println(current.getData());
            current = current.getNext();
        }
    }
    public Object get_index(int i) {
        Node current = this.head;
        for (int a = 0; a == i-1; i++){
            current = current.getNext();
        }return current.getData();
    }
}
