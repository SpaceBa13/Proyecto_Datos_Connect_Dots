
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
        window.setTitle("Prueba de Connect the Dots: " + playerName);

        JPanelDrawLines jPanel = new JPanelDrawLines(m, n);
        jPanel.setBackground(Color.white);

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

        Random id = new Random();
        int ID = id.nextInt(1, 100);
        Game g = new Game(m,n, jPanel, ID);

    }

    public static void main(String[] args) throws JsonProcessingException {
        JFrame window = new JFrame();
        int fila = Integer.parseInt(JOptionPane.showInputDialog(window, "Ingrese numero de filas"));
        int columna = Integer.parseInt(JOptionPane.showInputDialog(window, "Ingrese numero de columnas"));
        String user = JOptionPane.showInputDialog(window, "Ingrese el nombre de usuario");
        createGame(fila, columna, user);

    }
}