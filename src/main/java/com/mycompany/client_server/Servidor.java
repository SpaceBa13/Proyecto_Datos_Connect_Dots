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

/**
 * @author SpaceBa
 */
public class Servidor implements Runnable{
    DoublyLinkedList lista_clientes = new DoublyLinkedList();
    Queue cola_clientes = new Queue();

    public void enviar_cliente_atras(){
        cola_clientes.enqueue(cola_clientes.peek());
        cola_clientes.dequeue();
    }

    public void agregar_cliente_cola(Object puerto){
       if(cola_clientes.Search(puerto) == false){
           cola_clientes.enqueue(puerto);
       }
    }
    public void agregar_cliente_lista(Object puerto){
        if(lista_clientes.find(puerto) == false){
            lista_clientes.addLast(puerto);
        }
    }
    public void Reenvio(String IP, Paquete_Datos entrada) throws IOException {
        if(lista_clientes.size == 1){
            try {
                Socket reenvio = new Socket(IP, (int) lista_clientes.get_index(1));
                ObjectOutputStream paquete_reenviar = new ObjectOutputStream(reenvio.getOutputStream());
                paquete_reenviar.writeObject(entrada);
                reenvio.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            for (int i = 1; i < lista_clientes.size + 1; i++) {
                try {
                    Socket reenvio = new Socket(IP, (int) lista_clientes.get_index(i));
                    ObjectOutputStream paquete_reenviar = new ObjectOutputStream(reenvio.getOutputStream());
                    paquete_reenviar.writeObject(entrada);
                    reenvio.close();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
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

    /**
     * Constructor
     * @param puerto
     */
    public Servidor(int puerto) {
        this.puerto = puerto;
    }


    @Override
    public void run() {
        try {
            ServerSocket servidor = new ServerSocket(11111);
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
                agregar_cliente_lista(puerto_destino);
                System.out.println("Cola aqui:");
                agregar_cliente_cola(puerto_destino);
                System.out.println("Nickname: " + nick);
                System.out.println("Mensaje: " + mensaje);
                System.out.println("Puerto del Cliente: " + puerto_destino);
                System.out.println("Lista de clientes conectados: ");
                lista_clientes.displayList();
                System.out.println("Lista de clientes conectados en cola: ");
                cola_clientes.Display();
                enviar_cliente_atras();
                System.out.println("Lista de clientes conectados en cola: ");
                cola_clientes.Display();
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
