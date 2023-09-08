package com.mycompany.client_server;

public class Matriz {
    public static void main(String[] args) {
        DoublyLinkedList listota = new DoublyLinkedList();
        DoublyLinkedList row1 = new DoublyLinkedList();
        row1.addLast(1);
        row1.addLast(2);
        row1.addLast(3);

        DoublyLinkedList row2 = new DoublyLinkedList();
        row2.addLast(4);
        row2.addLast(5);
        row2.addLast(6);

        DoublyLinkedList row3 = new DoublyLinkedList();
        row3.addLast(7);
        row3.addLast(8);
        row3.addLast(9);

        listota.addLast(row1.get_index(1));
        listota.addLast(row1.get_index(2));
        listota.addLast(row1.get_index(3)+"\n");

        listota.addLast(row2.get_index(1));
        listota.addLast(row2.get_index(2));
        listota.addLast(row2.get_index(3)+"\n");

        listota.addLast(row3.get_index(1));
        listota.addLast(row3.get_index(2));
        listota.addLast(row3.get_index(3));

        listota.displayList();

    }
}