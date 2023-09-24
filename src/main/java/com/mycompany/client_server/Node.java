package com.mycompany.client_server;

public class Node<T>
{
    T data;
    public Node<T> next;


    public Node() {}
    public Node(T data) {this.data = data;}
    public Node(T data, Node<T> next)
    {
        this.data = data;
        this.next = next;
    }

    public T getData() {return data;}
    public void setData(T data) {this.data = data;}
    public Node<T> getNext() {return next;}
}
