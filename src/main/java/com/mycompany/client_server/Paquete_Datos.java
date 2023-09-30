/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.client_server;

import java.io.Serializable;

/**
 *
 * @author SpaceBa
 */
public class Paquete_Datos implements Serializable {
    private int puerto;
    private String user, comentario;
    public Play mensaje;
    public Play getMensaje() {
        return mensaje;
    }
    public void setMensaje(Play mensaje) {
        this.mensaje = mensaje;
    }
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

    public String getComentario() {return comentario;}
    public void setComentario(String comentario) {this.comentario = comentario;}
}
