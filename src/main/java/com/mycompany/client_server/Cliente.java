/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.client_server;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;

/**
 *
 * @author SpaceBa
 */
public class Cliente extends Observable implements Runnable{
    Play mensaje;
    String user, comentario;
    int puerto_propio;
    boolean estado_del_mensaje = false;

    Point Punto_1;
    Point Punto_2;
    int ID_jugador;

    /**
     * Constructor
     * @param user
     * @param puerto_propio
     */
    public Cliente(String user, int puerto_propio) {
        this.puerto_propio = puerto_propio;
        this.user = user;
    }
    public Cliente(int puerto_propio) {
        this.puerto_propio = puerto_propio;
    }




    public void send(Play mensaje) {
        String IP = "127.0.0.1";
        int puerto_destino;
        Paquete_Datos paquete_recibido = new Paquete_Datos();
        try {
            Socket socket = new Socket(IP, 11111);
            /*Crea una instancia del objeto Paquete de Datos para setear los datos a enviar*/
            Paquete_Datos envio = new Paquete_Datos();
            envio.setMensaje(mensaje);
            envio.setUser(user);
            envio.setPuerto(puerto_propio);
            envio.setComentario(comentario);

            /*Json*/
            ObjectMapper envio_json = new ObjectMapper();
            String Envio_json = envio_json.writeValueAsString(envio);

            /*Envia el String en formato jason a traves del socket*/
            DataOutputStream paquete_enviar = new DataOutputStream(socket.getOutputStream());
            paquete_enviar.writeUTF(Envio_json);
            socket.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void send(int puerto){
        String IP = "127.0.0.1";
        try {
            Socket socket = new Socket(IP, 11111);
            /*Crea una instancia del objeto Paquete de Datos para setear los datos a enviar*/
            Paquete_Datos envio = new Paquete_Datos();
            envio.setMensaje(mensaje);
            envio.setUser(user);
            envio.setPuerto(puerto);
            envio.setComentario(comentario);

            /*Json*/
            ObjectMapper envio_json = new ObjectMapper();
            String Envio_json = envio_json.writeValueAsString(envio);

            /*Envia el String en formato jason a traves del socket*/
            DataOutputStream paquete_enviar = new DataOutputStream(socket.getOutputStream());
            paquete_enviar.writeUTF(Envio_json);
            socket.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void run() {
        String IP = "127.0.0.1";
        String nick, comentario_recibido;
        Play mensaje_recibido;
        int puerto_destino;
        Paquete_Datos paquete_recibido = new Paquete_Datos();
        try {
            ServerSocket servidor_cliente = new ServerSocket(puerto_propio);
            Socket recibir_datos;
            Paquete_Datos paquete_entrante;
            String lectura_json;
            while(true){
                this.estado_del_mensaje = false;
                recibir_datos = servidor_cliente.accept();
                this.estado_del_mensaje = true;
                DataInputStream paquete_entrada = new DataInputStream(recibir_datos.getInputStream());

                /*Json*/
                ObjectMapper recibido_json = new ObjectMapper();
                lectura_json = (String) paquete_entrada.readUTF();
                paquete_entrante = recibido_json.readValue(lectura_json, Paquete_Datos.class);

                /*Obtiene los datos del Objecto que entro por el socket*/
                nick = (String) paquete_entrante.getUser();
                mensaje_recibido = paquete_entrante.getMensaje();
                puerto_destino = (int) paquete_entrante.getPuerto();
                comentario_recibido = (String) paquete_entrante.getComentario();


                /*Pruebas*/
                System.out.println("Nickname: " + nick);
                System.out.println("Puerto del Cliente: " + puerto_destino);


                this.mensaje = mensaje_recibido;
                this.Punto_1 = mensaje_recibido.dot1;
                this.Punto_2 = mensaje_recibido.dot2;
                this.ID_jugador = mensaje_recibido.playerID;


                System.out.println("Se recibieron los datos del servidor");
                System.out.println("Jugada Recibida: " + mensaje_recibido.playerID + ", " + mensaje_recibido.dot1.x + ", " + mensaje_recibido.dot1.y );
                System.out.println("Jugada Recibida: " + mensaje_recibido.playerID + ", " + mensaje_recibido.dot2.x + ", " + mensaje_recibido.dot2.y);
                System.out.println("ID: " + mensaje_recibido.playerID);

                System.out.println("Datos propios de la clase");
                System.out.println("Jugada Recibida: " + this.ID_jugador + ", " +  this.Punto_1.x + ", "+  this.Punto_1.y);
                System.out.println("Jugada Recibida: " + this.ID_jugador + ", " + this.Punto_2.x + ", "+  this.Punto_2.y);

                this.estado_del_mensaje = false;
                recibir_datos.close();
                paquete_entrada.close();

            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
