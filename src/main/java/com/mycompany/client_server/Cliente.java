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
    String mensaje;
    String user;
    int puerto_propio;

    /**
     * Constructor
     * @param mensaje
     * @param user
     * @param puerto_propio
     */
    public Cliente(String mensaje, String user, int puerto_propio) {
        this.mensaje = mensaje;
        this.puerto_propio = puerto_propio;
        this.user = user;
    }

    @Override
    public void run() {
        String IP = "127.0.0.1";
        String nick, mensaje_recicibido;
        int puerto_destino;
        Paquete_Datos paquete_recibido;
        try {
            Socket socket = new Socket(IP, 11111);
            /*Crea una instancia del objeto Paquete de Datos para setear los datos a enviar*/
            Paquete_Datos envio = new Paquete_Datos();
            envio.setMensaje(mensaje);
            envio.setUser(user);
            envio.setPuerto(puerto_propio);

            /*Json*/
            ObjectMapper envio_json = new ObjectMapper();
            String Envio_json = envio_json.writeValueAsString(envio);

            /*Envia el String en formato jason a traves del socket*/
            DataOutputStream paquete_enviar = new DataOutputStream(socket.getOutputStream());
            paquete_enviar.writeUTF(Envio_json);
            socket.close();

            ServerSocket servidor_cliente = new ServerSocket(puerto_propio);
            Socket recibir_datos;
            Paquete_Datos paquete_entrante;
            String lectura_json;
            while(true){
                recibir_datos = servidor_cliente.accept();
                DataInputStream paquete_entrada = new DataInputStream(recibir_datos.getInputStream());

                /*Json*/
                ObjectMapper recibido_json = new ObjectMapper();
                lectura_json = (String) paquete_entrada.readUTF();
                paquete_entrante = recibido_json.readValue(lectura_json, Paquete_Datos.class);

                /*Obtiene los datos del Objecto que entro por el socket*/
                nick = paquete_entrante.getUser();
                mensaje_recicibido = paquete_entrante.getMensaje();
                puerto_destino = paquete_entrante.getPuerto();

                /*Pruebas*/
                System.out.println("Nickname: " + nick);
                System.out.println("Mensaje: " + mensaje_recicibido);
                System.out.println("Puerto del Cliente: " + puerto_destino);

                String Mensaje = nick + ": " + mensaje_recicibido + "\n";

                this.setChanged(); //Marca como modificado al objeto
                this.notifyObservers(Mensaje); //Notifica a los observadores que hubo un cambio en la variable mensaje
                this.clearChanged(); //Indica que ya no hay mas cambios

            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
