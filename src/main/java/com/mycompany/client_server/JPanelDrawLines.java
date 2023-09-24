package com.mycompany.client_server;
import javax.swing.*;
import java.awt.*;

public class JPanelDrawLines extends JPanel
{
//    public int x = 100;
//    public int y = 100;
//    public int width = 100;
//    public int height = 8;
    public LinkedList<Line> drawnLines = new LinkedList<>();

    @Override
    public void repaint() {
        super.repaint();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.white);
        int lineCount = drawnLines.getSize();
        for (int i = 0; i < lineCount; i++)
        {
            System.out.println("I ES IGUAL A:" + i);
            Line l = drawnLines.getAt(i);   // HACER LUEGO QUE TOME EL ELEMENTO 0 Y VACIE LA LISTA.
            if (l.isVertical) {g2.fillRect(l.x * 960 / 100000, l.y * 640 / 100000 +8, l.width, l.height * 640 / 100000 -8);
                System.out.println("INTENTO HACER EL RECTANGULO VERTICAL en: x =" + l.x  * 960 / 100000 + "y y en:" + l.y * 640 / 100000);}
            else {g2.fillRect(l.x * 960 / 100000 +8, l.y * 640 / 100000, l.width * 960 / 100000 -8, l.height);System.out.println("INTENTO HACER EL RECTANGULO HORIZONTAL en: x =" + l.x  * 960 / 100000 + "y y en:" + l.y * 640 / 100000);}
        }
    }

}
