package com.mycompany.client_server;

import java.awt.*;
import java.util.Random;

public class Game
{
    public int gameState = 0;
    public boolean turn = true;
    public int score = 0;
    public int playerID;
    public int puerto_propio;
    public int m,n;
    public Matrix nodeMatrix, horizontalLineMatrix, verticalLineMatrix, squareMatrix;
    public JPanelDrawLines jPanel;

    Random puerto = new Random();
    int puerto_cliente = puerto.nextInt(5000, 9999);

    Cliente cliente = new Cliente( "a", puerto_cliente);




    // sentPlays es el mensaje que se enviara al servidor y
    //siendo recibido por todos los clientes (remitente inclusive) se concatena a receivedPlays.
    public LinkedList<Play> sentPlays = new LinkedList<>();
    public LinkedList<Play> receivedPlays = new LinkedList<>();

    public Game(int m, int n, JPanelDrawLines jPanel, int playerID)
    {
        this.m = m;
        this.n = n;
        nodeMatrix = new Matrix(m,n);
        horizontalLineMatrix = new Matrix(m,n-1);
        verticalLineMatrix = new Matrix(m-1,n);
        squareMatrix = new Matrix(m-1,n-1);
        this.jPanel = jPanel;
        this.playerID = playerID;
        Thread hilo_cliente = new Thread(cliente);
        hilo_cliente.start();


        while (true){gameStates();}

    }
    public Game(int m, int n, JPanelDrawLines jPanel, int playerID, LinkedList<Play> receivedPlays)
    {
        this.m = m;
        this.n = n;
        nodeMatrix = new Matrix(m,n);
        horizontalLineMatrix = new Matrix(m,n-1);
        verticalLineMatrix = new Matrix(m-1,n);
        squareMatrix = new Matrix(m-1,n-1);
        this.jPanel = jPanel;
        this.playerID = playerID;
        this.receivedPlays = receivedPlays;
        Thread hilo_cliente = new Thread(cliente);
        hilo_cliente.start();


        while (true){gameStates();}
    }

    public void replay(){
                while (receivedPlays.getSize() > 0)
                {
                    Play replay = receivedPlays.getAt(0);
                    System.out.println("Esta haciendo un replay.");
                    play(replay.dot1, replay.dot2, replay.playerID);
                    receivedPlays.deleteFirst();
                    System.out.println("Esta en el if del while de received Plays");
//                     TimeUtilities.waitMilliseconds(250);
                }
        }


    // Define estados de juego que varian segun gameState y avanzan de uno a otro cuando se leen ciertas variables.
    public void gameStates()
    {
        System.out.println("Entro a gameStates");
        Point clickedDot1 = new Point();
        Point clickedDot2 = new Point();

        while(gameState == 0)   // Replay de las jugadas de los otros jugadores.
        {
            if (this.cliente.estado_del_mensaje){
                actualizar_valores(this.cliente.Punto_1, this.cliente.Punto_2, this.cliente.ID_jugador);
            }
                while (receivedPlays.getSize() > 0)
                {
                    Play replay = receivedPlays.getAt(0);
                    if (replay.playerID == this.playerID)
                    {
                        System.out.println("Esta haciendo un replay.");
                        play(replay.dot1, replay.dot2, replay.playerID);
                    }
                    receivedPlays.deleteFirst();
                    System.out.println("Esta en el if del while de received Plays");
//                     TimeUtilities.waitMilliseconds(250);
                }
                gameState ++;
            }

        System.out.println("Entra al while 1");
        while(gameState == 1)   // Espera el primer clic de punto de la matriz.
        {
            if (this.cliente.estado_del_mensaje){
                actualizar_valores(this.cliente.Punto_1, this.cliente.Punto_2, this.cliente.ID_jugador);
            }


            if (jPanel.clickedButtons.getSize() > 0)
            {
                clickedDot1 = jPanel.clickedButtons.getAt(0);
                jPanel.selectedDots.append(clickedDot1);
                jPanel.repaint();
                gameState ++;
            }
            else {System.out.print("");}
        }

        while(gameState == 2)   // Espera el segundo clic de punto de la matriz.
                                //Si se completa un cuadrado, da puntos y vuelve a etapa 1.
        {
            if (this.cliente.estado_del_mensaje){
                actualizar_valores(this.cliente.Punto_1, this.cliente.Punto_2, this.cliente.ID_jugador);}
//            System.out.println("ENTRO A while 2");

            if (jPanel.clickedButtons.getSize() == 2)
            {
                clickedDot2 = jPanel.clickedButtons.getAt(1);

                int clickedDot1X = clickedDot1.x;
                int clickedDot1Y = clickedDot1.y;
                int clickedDot2X = clickedDot2.x;
                int clickedDot2Y = clickedDot2.y;

                int distanceX = clickedDot2X - clickedDot1X;
                int distanceY = clickedDot2Y - clickedDot1Y;

                int distance = distanceX*distanceX + distanceY*distanceY;

                if (distance == 1)
                {

                    jPanel.selectedDots.append(clickedDot2);
                    jPanel.repaint();   // repaint ya se ejecuta dentro de play.
                    // Se copian los puntos porque play es destructivo, pero jPanel necesita esos puntos.
                    Point clickedDot1Copy = new Point(clickedDot1X, clickedDot1Y);
                    Point clickedDot2Copy = new Point(clickedDot2X, clickedDot2Y);

//                    sentPlays.append(new Play(playerID, new Point(clickedDot1X, clickedDot1Y), new Point(clickedDot2X, clickedDot2Y)));

                    int scoreToAdd = play(clickedDot1Copy, clickedDot2Copy, playerID).getSize();
                    System.out.println("Gano " + scoreToAdd + " p");
                    if (scoreToAdd > 0)
                    {
                        score += scoreToAdd;

                        Play playToSend = new Play(playerID, new Point(clickedDot1X, clickedDot1Y), new Point(clickedDot2X, clickedDot2Y));

                        gameState = 1;

                        jPanel.clickedButtons.deleteFirst();
                        jPanel.clickedButtons.deleteFirst();
                    }
                    else
                    {
                        gameState = 0;

                        while (jPanel.selectedDots.getSize() > 0)
                        {
                            jPanel.selectedDots.deleteFirst();  // se borra justo despues de crearse el selected dot y no es apreciable.
                        }

                        Play playToSend = new Play(playerID, new Point(clickedDot1X, clickedDot1Y), new Point(clickedDot2X, clickedDot2Y));
                        System.out.println("Jugada Enviada: " + playToSend.playerID + ", " + playToSend.dot1.x + ", " + playToSend.dot1.y );
                        System.out.println("Jugada Enviada: " + playToSend.playerID + ", " + playToSend.dot2.x + ", " + playToSend.dot2.y);

                        this.cliente.send(playToSend);
                        TimeUtilities.waitMilliseconds(2000);

//                        Point Punto1 = this.cliente.Punto_1;
//                        Point Punto2 = this.cliente.Punto_2;
//                        int ID = this.cliente.ID_jugador;
                        actualizar_valores(this.cliente.Punto_1, this.cliente.Punto_2, this.cliente.ID_jugador);
//                        Play jugada_recibida = new Play(ID, Punto1, Punto2);
//                        Play jugada_recibida = this.cliente.mensaje;
//                        receivedPlays.append(jugada_recibida);
//                        replay();

                        jPanel.clickedButtons.deleteFirst();
                        jPanel.clickedButtons.deleteFirst();





//                        while (true)
//                        {
//                            if(cliente == null){
//                                System.out.println("Error Desaparecio el Cliente");
//                            }
//                            if (cliente.estado_del_mensaje)
//                            {
//
//                                receivedPlays.append(cliente.mensaje);
//                                System.out.println("El cliente recibio respuesta");
//                                System.out.println(cliente.mensaje);
//                                break;
//                            }else{
////                                System.out.println("no hubo respuesta");
//                            }
//                        }
//                        System.out.println("Salio del while");

                    }

                }
                else
                {
                    jPanel.clickedButtons.deleteLast();
                    System.out.println("Error: los puntos no son adyacentes");

                }
            }
        }
    }












    // Inserta una linea en una de las matrices de lineas horizontalLineMatrix o verticalLineMatrix
    //y anadiendo la linea a la lista de lineas drawnLines de jPanel la dibuja en pantalla.

    // En las matrices de lineas horizontalLineMatrix o verticalLineMatrix la linea se guarda como un booleano
    //en la coordenada indicada por Point dot.
    // En la lista de lineas drawnLines de JPanel se guardan objetos de la clase Line.
    public void insertAndDrawLines(Point dot, boolean isVertical, int m, int n)
    {
        if (isVertical)
        {
            verticalLineMatrix.setAt(dot, true);
            jPanel.drawnLines.append(new Line(dot, true, m, n));
            jPanel.repaint();
        }
        else
        {
            horizontalLineMatrix.setAt(dot, true);
            jPanel.drawnLines.append(new Line(dot, false, m, n));
            jPanel.repaint();
        }
    }

    public void drawSquareLabel(Point dot, int playerID)
    {
        Label completedBy = new Label(String.valueOf(playerID));
        Dimension d;
        d = this.jPanel.getPreferredSize();
        completedBy.setBounds(d.width * (dot.x*2 + 3) / (n+1) / 2 - 8, d.height * (dot.y*2 + 3) / (m+1) / 2 - 8, 24, 24);
        Font font = new Font("Times New Roman", Font.BOLD, 32); // fuente?
        completedBy.setFont(font);
        this.jPanel.add(completedBy);
    }

    // Determina si los dots indicados forman una linea vertical u horizontal o son invalidos.
    public char connectionType(Point dot1, Point dot2)
    {
        int x = dot2.x - dot1.x;
        int y = dot2.y - dot1.y;
        if(x == 1 && y==0){return 'h';}  //Los dots dibujan una linea horizontal y dot1 es el izquierdo.
        if(x == -1 && y==0){return 'H';} //Los dots dibujan una linea horizontal, pero dot1 es el derecho.

        if(y == 1 && x==0){return 'v';}  //Los dots dibujan una linea horizontal y dot1 es el superior.
        if(y == -1 && x==0){return 'V';} //Los dots dibujan una linea horizontal, pero dot1 es el inferior.

        return 'E';  //Error, los dots no dibujan una linea de distancia 1.
    }

    // Escribe los cuadrados creados en squareMatrix y crea y devuelve una LinkedList con las coordenadas Point de los cuadrados.
    LinkedList<Point> checkSquares(Point dot1, Point dot2, char connection, int playerID)
    {
        LinkedList<Point> newSquares = new LinkedList<>();
        // h o H es horizontal.
        if (connection == 'h' || connection == 'H')
        {
            insertAndDrawLines(dot1, false, m, n);
            // comprueba si se completa un cuadrado con la nueva linea como lado superior.
            Point dotAux = new Point(dot1.x, dot1.y);
            dotAux.y += 1;  // dotAux es abajo; dot2, a la derecha.
            if (dotAux.y < m && horizontalLineMatrix.getAt(dotAux) && verticalLineMatrix.getAt(dot1) && verticalLineMatrix.getAt(dot2))
            {
                squareMatrix.setAt(dot1, true); // La linea horizontal es el lado superior de un nuevo cuadrado segun el if.
                newSquares.append(dot1);
                drawSquareLabel(dot1, playerID);
            }
            // comprueba si se completa un cuadrado con la nueva linea como lado inferior.
            dotAux.y -= 2;  // dotAux es arriba; dot2, arriba y a la derecha.
            dot2.y -= 1;
            if (dotAux.y >= 0 && horizontalLineMatrix.getAt(dotAux) && verticalLineMatrix.getAt(dotAux) && verticalLineMatrix.getAt(dot2))
            {
                squareMatrix.setAt(dotAux, true); // La linea horizontal es el lado inferior de un nuevo cuadrado segun el if.
                newSquares.append(dotAux);
                drawSquareLabel(dotAux, playerID);
            }
        }
        // v o V es vertical.
        if (connection == 'v' || connection == 'V')
        {
            insertAndDrawLines(dot1, true, m, n);
            // comprueba si se completa un cuadrado con la nueva linea como lado izquierdo.
            Point dotAux = new Point(dot1.x, dot1.y);
            dotAux.x += 1;  // dot1 es la posicion actual; dotAux, a la derecha; dot2, abajo.
            if (dotAux.x < n &&  dot1.y < m-1 && verticalLineMatrix.getAt(dotAux) && horizontalLineMatrix.getAt(dot1) && horizontalLineMatrix.getAt(dot2))
            {
                squareMatrix.setAt(dot1, true); // La linea vertical es el lado izquierdo de un nuevo cuadrado segun el if.
                newSquares.append(dot1);
                drawSquareLabel(dot1, playerID);
            }
            // comprueba si se completa un cuadrado con la nueva linea como lado derecho.
            dotAux.x -= 2;  // dot1 es la posicion actual; dotAux, izquierda; dot2, abajo y a la izquierda.
            dot2.x -= 1;
            if (dotAux.x >= 0 && verticalLineMatrix.getAt(dotAux) && horizontalLineMatrix.getAt(dotAux) && horizontalLineMatrix.getAt(dot2))
            {
                squareMatrix.setAt(dotAux, true); // La linea horizontal es el lado inferior de un nuevo cuadrado segun el if.
                newSquares.append(dotAux);
                drawSquareLabel(dotAux, playerID);
            }
        }
        return newSquares;
    }
    // Devuelve una LinkedList con las posiciones de los cuadrados creados en la squareMatrix. Los puntos dot1 y 2 son
    //los que une la nueva linea y replay indica si es una jugada por parte del usuario o si se esta usando
    //automaticamente la funcion con los datos de receivedPlays.
    public LinkedList<Point> play(Point dot1, Point dot2, int playerID)
    {
        LinkedList<Point> newSquares = new LinkedList<>();

        //Determina tipo de linea.
        char connection = connectionType(dot1, dot2);
        switch(connection)
        {
            case 'H':
                if (dot2.x < n-1 && dot2.y < m)
                {
                    newSquares = checkSquares(dot2, dot1, connection, playerID);  // Da los puntos al reves para que dot1 sea el menor
                }
                break;
            case 'V':
                if (dot2.x < n && dot2.y < m-1)
                {
                    newSquares = checkSquares(dot2, dot1, connection, playerID);  // Da los puntos al reves para que dot1 sea el menor
                }
                break;
            case 'h':
                if (dot1.x < n-1 && dot1.y < m)
                {
                    newSquares = checkSquares(dot1, dot2, connection, playerID);  // Da los puntos al reves para que dot1 sea el menor
                }
                break;
            case 'v':
                if (dot1.x < n && dot1.y < m-1)
                {
                    newSquares = checkSquares(dot1, dot2, connection, playerID);  // Da los puntos al reves para que dot1 sea el menor
                }
                break;
            case 'E':
                System.out.println("Error en Game: los puntos dot1 " + dot1.x + ',' + dot1.y + " y dot2 " + dot2.x + ',' + dot2.y + " tienen distancia mayor a 1");
                return null;    //Error si los puntos dados son invalidos.
        }
        return newSquares;
    }
    public void actualizar_valores(Point Punto1,Point Punto2, int ID){
        Punto1 = this.cliente.Punto_1;
        Punto2 = this.cliente.Punto_2;
        ID = this.cliente.ID_jugador;
        Play jugada_recibida = new Play(ID, Punto1, Punto2);
        receivedPlays.append(jugada_recibida);

        System.out.println("jugada_recibida: " + jugada_recibida.playerID + ", " + jugada_recibida.dot1.x + ", " + jugada_recibida.dot1.y );
        System.out.println("jugada_recibida: " + jugada_recibida.playerID + ", " + jugada_recibida.dot2.x + ", " + jugada_recibida.dot2.y );
    }


    //Debug methods
    public void showMatricesln(int Step)
    {
        System.out.println(Step + "- " + "nodeMatrix:");
        nodeMatrix.showMatrixln();

        System.out.println(Step + "- " + "horizontalLineMatrix:");
        horizontalLineMatrix.showMatrixln();

        System.out.println(Step + "- " + "verticalLineMatrix:");
        verticalLineMatrix.showMatrixln();

        System.out.println(Step + "- " + "squareMatrix:");
        squareMatrix.showMatrixln();
    }
    public void showMatrices(int Step)
    {
        System.out.println(Step + "- " + "nodeMatrix:");
        nodeMatrix.showMatrix();

        System.out.println(Step + "- " + "horizontalLineMatrix:");
        horizontalLineMatrix.showMatrix();

        System.out.println(Step + "- " + "verticalLineMatrix:");
        verticalLineMatrix.showMatrix();

        System.out.println(Step + "- " + "squareMatrix:");
        squareMatrix.showMatrix();
    }
}
