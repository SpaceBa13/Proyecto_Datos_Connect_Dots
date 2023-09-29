package com.mycompany.client_server;

import java.io.Serializable;

/**
 *
 * @author SpaceBa
 */
public class Paquete_Juego implements Serializable {
    private int puerto;
    private String user;

    private LinkedList jugada;

    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public int getPuerto() {
        return puerto;
    }
    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public LinkedList getJugada() {
        return jugada;
    }

    public void setJugada(LinkedList jugada) {
        this.jugada = jugada;
    }
}
