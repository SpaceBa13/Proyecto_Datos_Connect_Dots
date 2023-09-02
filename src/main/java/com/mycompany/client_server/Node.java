package com.mycompany.client_server;

public class Node {
    Object data;
    Node prev;
    Node next;

    public Node(Object data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Node getNext() {
        return this.next;
    }
    public void setNext(Node node) {
        this.next = node;
    }

}

