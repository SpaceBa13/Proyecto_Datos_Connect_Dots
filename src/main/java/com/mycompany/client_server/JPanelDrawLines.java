package com.mycompany.client_server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JPanelDrawLines extends JPanel implements ActionListener
{
    @Override
    public void actionPerformed(ActionEvent e)
    {

    }
//    public int x = 100;
//    public int y = 100;
//    public int width = 100;
//    public int height = 8;
    boolean drawDots = true;
    int m;
    int n;
    double t = 0.0;
    public LinkedList<Line> drawnLines = new LinkedList<>();
    // Los dots resaltados. Se modifica en Game g.
    public LinkedList<Point> selectedDots = new LinkedList<>();
    // Los dots dados clic. Se modifica en JPanel jPanel a traves de los botones.
    public LinkedList<Point> clickedButtons = new LinkedList<>();

    public JPanelDrawLines(){}

    public JPanelDrawLines(int m, int n)
    {
        this.m = m;
        this.n = n;
//        this.doLayout();
//        this.setLayout(null);
    }


    public void placeButtons()
    {
        for (int i = 0; i < m; i++)
        {
            for (int j = 0; j < n; j++)
            {
                int x = j;
                int y = i;


                Dimension d;
                d = this.getPreferredSize();
                JButton b = new JButton("");
                b.setBounds(d.width * (j+1) / (n+1) - 4, d.height * (i+1) / (m+1) - 4, 16, 16);
                b.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        clickedButtons.append(new Point(x, y));
                        System.out.println("jPanel.clickedButtons.getSize() ES: " + clickedButtons.getSize() + " con (" + x + ", " + y + ')');

                    }
                });
//                b.setVisible(false);
                b.setOpaque(false);
                b.setContentAreaFilled(false);
                this.add(b);
            }
        }

    }



    @Override
    public void repaint() {
        super.repaint();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.black);
        int lineCount = drawnLines.getSize();
        for (int i = 0; i < lineCount; i++)
        {
//            System.out.println("I ES IGUAL A:" + i);
            Line l = drawnLines.getAt(i);   // HACER LUEGO QUE TOME EL ELEMENTO 0 Y VACIE LA LISTA.
            if (l.isVertical)
            {
                g2.fillRect(l.x * 960 / 100000, l.y * 640 / 100000 +8, l.width, l.height * 640 / 100000 -8);
//                System.out.println("INTENTO HACER EL RECTANGULO VERTICAL en: x =" + l.x  * 960 / 100000 + "y y en:" + l.y * 640 / 100000);
            }
            else
            {
                g2.fillRect(l.x * 960 / 100000 +8, l.y * 640 / 100000, l.width * 960 / 100000 -8, l.height);
//                System.out.println("INTENTO HACER EL RECTANGULO HORIZONTAL en: x =" + l.x  * 960 / 100000 + "y y en:" + l.y * 640 / 100000);
            }
//            TimeUtilities.waitMilliseconds(150);
        }

        if (drawDots)   // No hay condicion de finalizacion.
        {

            for (int i = 0; i < m; i++)
            {
                for (int j = 0; j < n; j++)
                {
                    Dimension d;
                    d = this.getPreferredSize();
//                    System.out.println("d.width" + d.width);
//                    System.out.println("d.height" + d.height);
                    g2.fillRect(d.width * (j+1) / (n+1) - 4, d.height * (i+1) / (m+1) - 4, 16, 16);
                }
            }
        }

        int lenSelectedDots = selectedDots.getSize();
        if (lenSelectedDots > 0)
        {
            int R = (int)Math.round(Math.sin(t + 4) * 64.0 + 191.0);
            int G = (int)Math.round(Math.sin(t + 2) * 64.0 + 191.0);
            int B = (int)Math.round(Math.sin(t) * 64.0 + 191.0);

//            System.out.println("R ES: " + R);
            g2.setColor(new Color(R, G, B));

            t += 0.1;
            for (int i = 0; i < lenSelectedDots; i++)
            {
                Dimension d;
                d = this.getPreferredSize();
                Point selectedDot = selectedDots.getAt(i);
                g2.fillRect(d.width * (selectedDot.x + 1) / (n+1) - 8, d.height * (selectedDot.y + 1) / (m+1) - 8, 24, 24);
            }
        }

    }

}
