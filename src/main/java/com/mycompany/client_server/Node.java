package com.mycompany.client_server;

public class Node {
    Object data;
    Node prev;
    Node next;

    /**
     * Constructor
     * @param data
     */
    public Node(Object data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }

    /**
     * Retorna el valor contenido
     * @return
     */
    public Object getData() {
        return this.data;
    }

    /**
     * Obtiene el siguiente elemento mediante el puntero next
     * @return
     */
    public Node getNext() {
        return this.next;
    }

}

