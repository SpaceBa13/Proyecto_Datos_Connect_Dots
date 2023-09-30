
/*
* default (nada): solo en el paquete
* public: todos incluso fuera del paquete
* private: solo la clase
* protected: lo mismo que default pero se puede heredar los atributos y asi por herencia con extends
 */
package com.mycompany.client_server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main
{


    public static void createGame(int m, int n, String playerName)
    {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Prueba de Connect the Dots");

        JPanelDrawLines jPanel = new JPanelDrawLines(m, n);
        jPanel.setBackground(Color.BLACK);

        int panelWidth = 960;
        int panelHeight = 640;
        jPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));


        System.out.println("La ventana de A esta anadiendo un jPanel");
        window.add(jPanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        LinkedList<Line> Lineas = new LinkedList<>();   // NO hace falta
        jPanel.drawnLines = Lineas;                     // NO hace falta

//        jPanel.doLayout();
        jPanel.setLayout(null);

        jPanel.placeButtons();
        System.out.println("jPanel.clickedButtons.getSize() ES: " + jPanel.clickedButtons.getSize());


        Game g = new Game(m,n, jPanel, 1);


//        Play jugada1 = new Play(1, new Point(1,2), new Point(1,3));
//        Play jugada2 = new Play(1, new Point(2,2), new Point(2,3));
//        Play jugada3 = new Play(1, new Point(1,2), new Point(2,2));
//        Play jugada4 = new Play(300, new Point(1,3), new Point(2,3));
//
//        LinkedList<Play> listaJugadas = new LinkedList<>();
//        listaJugadas.append(jugada1);
//        listaJugadas.append(jugada2);
//        listaJugadas.append(jugada3);
//        listaJugadas.append(jugada4);
//
//        Game g = new Game(m,n, jPanel, 0, listaJugadas);

//        g.play(new Point(1,1), new Point(2,1));
//        g.showMatrices(1);
//        System.out.println();

//        TimeUtilities.waitMilliseconds(300);
    }

    public static void main(String[] args) throws JsonProcessingException {
//        int m = 5;
//        int n = 5;
//        int lineCount = 2*m*n - m - n;
//        int tiempoDeEspera = 2500 / lineCount;

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Prueba de Connect the Dots");

        JPanelDrawLines jPanel = new JPanelDrawLines();
        jPanel.setBackground(Color.white);

        int panelWidth = 960;
        int panelHeight = 640;
        jPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        window.add(jPanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

//        String filasM = JOptionPane.showInputDialog(window, "Ingrese el numero de filas");



        JButton createGameButton = new JButton("createGameButton");
        int auxWidth = (panelWidth / 2) - 48;
        int auxHeight = (panelHeight * 3) / 4;
        createGameButton.setBounds(auxWidth, auxHeight, 96, 32);
//        createGameButton.setBounds(0,0, 48, 16);
        createGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("Llego a createGameButton");

//                createGame(4, 3, "A");

            }
        });
        createGameButton.setVisible(true);
        jPanel.add(createGameButton);


//        JTextField rowM = new JTextField("");
//        rowM.setBounds(panelWidth / 4 - 48, panelHeight / 4, 96, 32);

//        rowM.setVisible(true);

        System.out.println("Adds");
//        jPanel.add(rowM);


//        Play jugada1 = new Play(1, new Point(1,2), new Point(1,3));
//        Play jugada2 = new Play(1, new Point(2,2), new Point(2,3));
//        LinkedList listaJugadas = new LinkedList();
//        listaJugadas.append(jugada1);
//        listaJugadas.append(jugada2);


//        Random puerto = new Random();
//        int puerto_propio = puerto.nextInt(5000, 9999);
//
//        Cliente cliente = new Cliente(listaJugadas, "a", puerto_propio, "PRUEBA");
//        Thread hilo_cliente = new Thread(cliente);
//        hilo_cliente.start();
//        ObjectMapper mapper = new ObjectMapper();
//        String Envio_json = mapper.writeValueAsString(jugada1);
//        System.out.println(Envio_json);
//        paquete_recibido = recibido_json.readValue(lectura_json, Paquete_Datos.class);


        createGame(5, 5, "A");

    }
}