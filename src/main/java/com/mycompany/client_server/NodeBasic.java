package com.mycompany.client_server;
public class NodeBasic
{
    int id;

    public NodeBasic()
    {
        //System.out.println("Nodo creado");
    }
    public NodeBasic(int id)
    {
        this.id = id;
        //System.out.println("Nodo creado con constructor especifico");
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public void showId()
    {
        System.out.println("ID: " + this.id);
    }
}
