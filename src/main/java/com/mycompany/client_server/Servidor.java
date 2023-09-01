/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.client_server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author SpaceBa
 */
public class Servidor implements Runnable{

    LinkedList lista_clientes = new LinkedList();
    Queue cola_cllientes = new Queue();



    public void agregar_cliente(int puerto){
        if (lista_clientes.size() == 0){
            lista_clientes.add(puerto);
        }else{
            for (int i = 0; i < lista_clientes.size(); i++) {
                if(i == lista_clientes.size() - 1) {
                    if (lista_clientes.get(i).equals(puerto)) {
                        break;
                    }else{
                        lista_clientes.add(puerto);
                    }
                }else{
                    if (lista_clientes.get(i).equals(puerto)) {
                        break;
                    }
                }
            }
        }
    }

    public void Reenvio(String IP, Paquete_Datos entrada) throws IOException {
        for(int i = 0; i < lista_clientes.size(); i++) {
            try {
                Socket reenvio = new Socket(IP, (int) lista_clientes.get(i));
                ObjectOutputStream paquete_reenviar = new ObjectOutputStream(reenvio.getOutputStream());
                paquete_reenviar.writeObject(entrada);
                reenvio.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        Servidor server = new Servidor(10000);
        Thread hilo_servidor = new Thread(server);
        hilo_servidor.start();
        System.out.println("En espera....");

    }
     int puerto;

    public Servidor(int puerto) {
        this.puerto = puerto;
    }


    @Override
    public void run() {
        try {
            ServerSocket servidor = new ServerSocket(10000);
            String IP = "127.0.0.1";
            String nick, mensaje;
            int puerto_destino;
            Paquete_Datos paquete_recibido;

            while(true){
                //Recibe los datos enviados por el cliente
                Socket socket =  servidor.accept();
                ObjectInputStream paquete_entrada = new ObjectInputStream(socket.getInputStream());
                paquete_recibido = (Paquete_Datos) paquete_entrada.readObject();
                nick = paquete_recibido.getUser();
                mensaje = paquete_recibido.getMensaje();
                puerto_destino = paquete_recibido.getPuerto();
                agregar_cliente(puerto_destino);
                System.out.println("Nickname: " + nick);
                System.out.println("Mensaje: " + mensaje);
                System.out.println("Puerto del Cliente: " + puerto_destino);
                System.out.println("Lista de clientes conectados" + lista_clientes);
                //Reenvio de Datos almacenados en el servidor hacia los clientes

                Reenvio(IP, paquete_recibido);

                socket.close();


            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
}
