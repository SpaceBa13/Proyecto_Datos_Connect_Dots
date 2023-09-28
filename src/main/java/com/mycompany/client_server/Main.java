package com.mycompany.client_server;
/*
* default (nada): solo en el paquete
* public: todos incluso fuera del paquete
* private: solo la clase
* protected: lo mismo que default pero se puede heredar los atributos y asi por herencia con extends
 */

import javax.swing.*;
import java.awt.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main
{

    public static void main(String[] args)
    {
        int m = 6;
        int n = 9;
        int lineCount = 2*m*n - m - n;
        int tiempoDeEspera = 2500 / lineCount;



        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Prueba de Connect the Dots");

        JPanelDrawLines jPanel = new JPanelDrawLines(m, n);
        jPanel.setBackground(Color.BLACK);

        int panelWidth = 960;
        int panelHeight = 640;
        jPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        window.add(jPanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        LinkedList<Line> Lineas = new LinkedList<>();   // NO hace falta
        jPanel.drawnLines = Lineas;                     // NO hace falta

        for (int i = 0; i < 5; i++) {
            System.out.println(Color.BLUE);
        }




//        jPanel.doLayout();
        jPanel.setLayout(null);

//        for (int i = 0; i < m; i++)
//        {
//            for (int j = 0; j < n; j++)
//            {
//                JButton b = new JButton("BOTON NO TIENE TEXTO.");
//                b.setBounds(panelWidth * (j+1) / (n+1) - 4, panelHeight * (i+1) / (m+1) - 4, 16, 16);
//                jPanel.add(b);
//
//            }
//        }
        jPanel.placeButtons();
        System.out.println("jPanel.clickedButtons.getSize() ES: " + jPanel.clickedButtons.getSize());




        Game g = new Game(m,n, jPanel);
//        g.play(new Point(1,1), new Point(2,1));
//        g.showMatrices(1);
//        System.out.println();

        TimeUtilities.waitMilliseconds(300);

//
//        for (int i = 0; i < m; i++)
//        {
//            for (int j = 0; j < n-1; j++)
//            {
////                System.out.println("x0,x1: " + j + ' ' + (j+1));
////                System.out.println("y0,y1: " + i + ' ' + i);
//                g.play(new Point(j,i), new Point(j+1,i));
////                g.showMatrices(j+3*i);
////                System.out.println();
//
//                TimeUtilities.waitMilliseconds(tiempoDeEspera);
//
//            }
//        }
//        for (int i = 0; i < m-1; i++)
//        {
//            for (int j = 0; j < n; j++)
//            {
////                System.out.println("x0,x1: " + j + ' ' + j);
////                System.out.println("y0,y1: " + i + ' ' + (i+1));
//                g.play(new Point(j,i), new Point(j,i+1));
////                g.showMatrices(j+4*i + m*(n-1));
////                System.out.println();
//
//                TimeUtilities.waitMilliseconds(tiempoDeEspera);
//
//            }
//        }



//
//
//
//        for (int i = 0; i < m-1; i++) // m-1
//        {
//            for (int j = 0; j < n; j++) // n
//            {
//                Line l = new Line(new Point(j,i), true, m,n);
//                Lineas.append(l);
//
//                TimeUtilities.waitMilliseconds(tiempoDeEspera);
//
//                jPanel.repaint();
//            }
//        }
//
////        jPanel.drawnLines = Lineas;
//
//        for (int i = 0; i < m; i++) // m
//        {
//            for (int j = 0; j < n-1; j++) // n-1
//            {
//                Line l = new Line(new Point(j,i), false, m,n);
//                Lineas.append(l);
//
//                TimeUtilities.waitMilliseconds(tiempoDeEspera);
//
//                jPanel.repaint();
//            }
//        }
    }
}