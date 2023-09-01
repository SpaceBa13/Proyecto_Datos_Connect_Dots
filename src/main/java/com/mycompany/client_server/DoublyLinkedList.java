package com.mycompany.client_server;

public class DoublyLinkedList {
    Node head;
    Node tail;

    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
    }

    public void addFirst(Object data) {
        Node temp = new Node(data);
        if (head == null) {
            head = temp;
            tail = temp;
        }
        else {
            temp.next = head;
            head.prev = temp;
            head = temp;
        }
    }
    public void addLast(Object data) {
        Node temp = new Node(data);
        if (tail == null) {
            head = temp;
            tail = temp;
        }
        else {
            tail.next = temp;
            temp.prev = tail;
            tail = temp;
        }
    }

    public Object deleteFirst() {
        if (head == null) {
            return null;
        }
        if (head == tail) {
            head = null;
            tail = null;
            return null;
        }
        Node temp = head;
        head = head.next;
        head.prev = null;
        temp.next = null;
        return null;
    }

    public void deleteLast() {
        if (tail == null) {
            return;
        }

        if (head == tail) {
            head = null;
            tail = null;
            return;
        }

        Node temp = tail;
        tail = tail.prev;
        tail.next = null;
        temp.prev = null;
    }


}
