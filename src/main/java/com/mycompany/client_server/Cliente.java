/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.client_server;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
            envio_json.writeValueAsString(envio);

            /*Envia el String en formato jason a traves del socket*/
            ObjectOutputStream paquete_enviar = new ObjectOutputStream(socket.getOutputStream());
            paquete_enviar.writeObject(envio);
            socket.close();

            ServerSocket servidor_cliente = new ServerSocket(puerto_propio);
            Socket recibir_datos;
            Paquete_Datos paquete_entrante;
            while(true){
                recibir_datos = servidor_cliente.accept();
                ObjectInputStream paquete_entrada = new ObjectInputStream(recibir_datos.getInputStream());
                paquete_entrante = (Paquete_Datos) paquete_entrada.readObject();
                nick = paquete_entrante.getUser();
                mensaje_recicibido = paquete_entrante.getMensaje();
                puerto_destino = paquete_entrante.getPuerto();
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
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
