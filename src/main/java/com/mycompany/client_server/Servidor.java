/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.client_server;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author SpaceBa
 */
public class Servidor implements Runnable {
    DoublyLinkedList lista_clientes = new DoublyLinkedList();
    Queue cola_clientes = new Queue();

    /**
     * Envia al cliente que esta de primero en la cola al ultimo puesto
     */
    public void enviar_cliente_atras() {
        cola_clientes.enqueue(cola_clientes.peek());
        cola_clientes.dequeue();
    }

    /**
     * Agrega los clientes a la cola para ser atendidos
     *
     * @param puerto
     */
    public void agregar_cliente_cola(Object puerto) {
        if (cola_clientes.Search(puerto) == false) {
            cola_clientes.enqueue(puerto);
        }
    }

    /**
     * Agrega los clientes a una lista donde esta todos los conectados al servidor
     *
     * @param puerto
     */
    public void agregar_cliente_lista(Object puerto) {
        if (lista_clientes.find(puerto) == false) {
            lista_clientes.addLast(puerto);
        }
    }

    /**
     * Renvia el paquete de datos a todos los clientes conectados (los que estan en la lista)
     * @param IP
     * @param entrada
     * @throws IOException
     */
    public void Reenvio(String IP, Paquete_Datos entrada) throws IOException {
        if (lista_clientes.size == 1) {
            /*Envia a el cliente conectado si solo hay uno*/
            try {
                Socket reenvio = new Socket(IP, (int) lista_clientes.get_index(1));
                /*Json*/
                ObjectMapper envio_json = new ObjectMapper();
                String Envio_json = envio_json.writeValueAsString(entrada);
                /*Envia el String en formato jason a traves del socket*/
                DataOutputStream paquete_enviar = new DataOutputStream(reenvio.getOutputStream());
                paquete_enviar.writeUTF(Envio_json);
                reenvio.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            /*Funcion que envia a todos los clientes*/
            for (int i = 1; i < lista_clientes.size + 1; i++) {
                try {
                    Socket reenvio = new Socket(IP, (int) lista_clientes.get_index(i));
                    /*Json*/
                    ObjectMapper envio_json = new ObjectMapper();
                    String Envio_json = envio_json.writeValueAsString(entrada);
                    /*Envia el String en formato jason a traves del socket*/
                    DataOutputStream paquete_enviar = new DataOutputStream(reenvio.getOutputStream());
                    paquete_enviar.writeUTF(Envio_json);
                    reenvio.close();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**\
     * Pone operativo el servidor y crea un hilo para que continue en escucha
     * @param args
     */
    public static void main(String[] args) {
        Servidor server = new Servidor(10000);
        Thread hilo_servidor = new Thread(server);
        hilo_servidor.start();
        System.out.println("En espera.....");
    }

    int puerto;

    /**
     * Constructor
     *
     * @param puerto
     */
    public Servidor(int puerto) {
        this.puerto = puerto;
    }

    /**
     * Crea un socket para recibir datos constantemente
     */
    @Override
    public void run() {
        try {
            ServerSocket servidor = new ServerSocket(11111);
            String IP = "127.0.0.1";
            String nick;
            Play mensaje;
            int puerto_destino;
            Paquete_Datos paquete_recibido;
            String lectura_json;


            while (true) {
                //Recibe los datos enviados por el cliente
                Socket socket = servidor.accept();
                DataInputStream paquete_entrada = new DataInputStream(socket.getInputStream());

                /*Json*/
                ObjectMapper recibido_json = new ObjectMapper();
                lectura_json = (String) paquete_entrada.readUTF();
                System.out.println("Antes del ERROR.");

                paquete_recibido = recibido_json.readValue(lectura_json, Paquete_Datos.class);
                System.out.println(paquete_recibido.getMensaje());
                /*Obtiene los datos del Objecto que entro por el socket*/
                nick = paquete_recibido.getUser();
                if(paquete_recibido.getMensaje() != null){
                mensaje = paquete_recibido.getMensaje();}

                puerto_destino = paquete_recibido.getPuerto();
                agregar_cliente_lista(puerto_destino);

                /*Zona de Purebas*/
                System.out.println("Cola aqui:");
                agregar_cliente_cola(puerto_destino);
                System.out.println("Nickname: " + nick);
                System.out.println("Puerto del Cliente: " + puerto_destino);
                System.out.println("Lista de clientes conectados: ");
                lista_clientes.displayList();
                System.out.println("Lista de clientes conectados en cola: ");
                cola_clientes.Display();
//                System.out.println("Su mensaje cuando un cliente se anade a la cola es: " + paquete_recibido.getMensaje());
                //Reenvio de Datos almacenados en el servidor hacia los clientes

//                if(lista_clientes.size >= 1){
//                    if(puerto_destino == (int) cola_clientes.peek()){
//                        Reenvio(IP, paquete_recibido);
//                        enviar_cliente_atras();
//                    }else{
//                        System.out.println("No es tu turno");
//                    }
//                }
                if(paquete_recibido.mensaje != null){
                    if (! paquete_recibido.mensaje.completedSquare){enviar_cliente_atras();}
                    paquete_recibido.setPuerto_para_turno((int) cola_clientes.peek());  // Cast a entero --> (int) cola_clientes.peek()

                    Reenvio(IP, paquete_recibido);
                }
                else
                {
                    System.out.println("EN EL ELSE DESDE EL SERVIDOR PAQUETE DICE QUE");
                    System.out.println("paquete_recibido.mensaje: " + paquete_recibido.mensaje);
                    System.out.println("paquete_recibido.getPuerto(): " + paquete_recibido.getPuerto());
                    System.out.println("paquete_recibido.getPuerto_para_turno(): " + paquete_recibido.getPuerto_para_turno());
                    System.out.println("paquete_recibido.getUser(): " + paquete_recibido.getUser());
                    System.out.println("paquete_recibido.getComentario(): " + paquete_recibido.getComentario());

                    // El nuevo en entrar a la cola es el del turno.
                    paquete_recibido.setMensaje(new Play(-1, new Point(-1, -1), new Point(-1, -1), false));
                    paquete_recibido.setPuerto_para_turno((int) cola_clientes.peek());
                    System.out.print("Entro un nuevo cliente de puerto " + paquete_recibido.getPuerto());
                    System.out.println(" y se va a enviar un paquete de -1 con puerto_para_turno (int) cola_clientes.peek(): " + (int) cola_clientes.peek());

                    Reenvio(IP, paquete_recibido);

                }

                socket.close();


            }

        } catch (IOException e) {
            throw new RuntimeException(e);


        }
    }
}
