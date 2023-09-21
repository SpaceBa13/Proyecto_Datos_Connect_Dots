package com.mycompany.client_server;
import javax.swing.*;
import java.awt.*;

public class JPanelDrawLines extends JPanel
{
    @Override
    public void repaint() {
        super.repaint();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.white);
        g2.fillRect(100,100,100,8);
    }


}
