package com.mycompany.client_server;
/*
* default (nada): solo en el paquete
* public: todos incluso fuera del paquete
* private: solo la clase
* protected: lo mismo que default pero se puede heredar los atributos y asi por herencia con extends
 */

import com.mycompany.client_server.*;
import com.mycompany.client_server.Point;
import com.mycompany.client_server.TimeUtilities;

import javax.swing.*;
import java.awt.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main
{

    public static void main(String[] args)
    {
        int m = 10;
        int n = 10;
        int tiempoDeEspera = 75;

        Game g = new Game(m,n);
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                System.out.println("x0,x1: " + j + ' ' + (j+1));
                System.out.println("y0,y1: " + i + ' ' + i);
                g.play(new Point(j,i), new Point(j+1,i));
                g.showMatrices(j+3*i);
                System.out.println();

            }
        }
        for (int i = 0; i < 2; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                System.out.println("x0,x1: " + j + ' ' + j);
                System.out.println("y0,y1: " + i + ' ' + (i+1));
                g.play(new Point(j,i), new Point(j,i+1));
                g.showMatrices(j+4*i + 9);
                System.out.println();
            }
        }
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Prueba de Connect the Dots");

        JPanelDrawLines jPanel = new JPanelDrawLines();
        jPanel.setBackground(Color.BLACK);

        int panelWidth = 960;
        int panelHeight = 640;
        jPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        window.add(jPanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        LinkedList<Line> Lineas = new LinkedList<>();
        jPanel.drawnLines = Lineas;
        for (int i = 0; i < m-1; i++) // m-1
        {
            for (int j = 0; j < n; j++) // n
            {
                Line l = new Line(new Point(j,i), true, m,n);
                Lineas.append(l);

                TimeUtilities.waitMilliseconds(tiempoDeEspera);

                jPanel.repaint();
            }
        }

//        jPanel.drawnLines = Lineas;

        for (int i = 0; i < m; i++) // m
        {
            for (int j = 0; j < n-1; j++) // n-1
            {
                Line l = new Line(new Point(j,i), false, m,n);
                Lineas.append(l);

                TimeUtilities.waitMilliseconds(tiempoDeEspera);

                jPanel.repaint();
            }
        }
//
//        LinkedList<Line> Lineas = new LinkedList<>();
//        for (int i = 0; i < 4; i++)
//        {
//            Line l = new Line(new Point(i,i), false, 5,6);
//            Lineas.append(l);
//        }
//        jPanel.drawnLines = Lineas;
//
//        for (int i = 0; i < 4; i++)
//        {
//            Line l = new Line(new Point(i,i), true, 5,6);
//            Lineas.append(l);
//        }


    }
}