package com.mycompany.client_server;

public class Node {
    Object data;
    Node prev;
    Node next;

    public Node(Object data)
    {
        this.data = data;
        this.prev = null;
        this.next = null;
    }
}

