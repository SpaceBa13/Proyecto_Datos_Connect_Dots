package com.mycompany.client_server;

public class Nodo {
    Object data;
    Nodo prev;
    Nodo next;

    /**
     * Constructor
     * @param data
     */
    public Nodo(Object data) {
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
    public Nodo getNext() {
        return this.next;
    }

}

