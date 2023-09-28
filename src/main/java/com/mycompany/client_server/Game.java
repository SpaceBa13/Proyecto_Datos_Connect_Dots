package com.mycompany.client_server;

public class Game
{
    public int gameState = 0;
    public int score = 0;
    public int m,n;
    public Matrix nodeMatrix, horizontalLineMatrix, verticalLineMatrix, squareMatrix;
    public JPanelDrawLines jPanel;

    public Game(int m,int n, JPanelDrawLines jPanel)
    {
        this.m = m;
        this.n = n;
        nodeMatrix = new Matrix(m,n);
        horizontalLineMatrix = new Matrix(m,n-1);
        verticalLineMatrix = new Matrix(m-1,n);
        squareMatrix = new Matrix(m-1,n-1);
        this.jPanel = jPanel;


        while (true){gameStates();}
    }




    // Define estados de juego que varian segun gameState y avanzan de uno a otro cuando se leen ciertas variables.
    public void gameStates()
    {
        System.out.println("ENTRO A gameStates");
        Point clickedDot1 = new Point();
        Point clickedDot2 = new Point(0,0);
        while(gameState == 0)
        {
            System.out.println("ENTRO A while 0");

            if (jPanel.clickedButtons.getSize() != 0)
            {
                clickedDot1 = jPanel.clickedButtons.getAt(0);
                jPanel.selectedDots.append(clickedDot1);
                jPanel.repaint();
                gameState ++;
            }
        }
        while(gameState == 1)
        {
//            System.out.println("ENTRO A while 1");

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
                    jPanel.repaint();   // repaint se ejecuta dentro de play.
                    // Se copian los puntos porque play es destructivo, pero jPanel necesita esos puntos.
                    Point clickedDot1Copy = new Point(clickedDot1X, clickedDot1Y);
                    Point clickedDot2Copy = new Point(clickedDot2X, clickedDot2Y);

                    if (play(clickedDot1Copy, clickedDot2Copy).getSize() > 0)
                    {
                        gameState = 0;

                        jPanel.clickedButtons.deleteFirst();
                        jPanel.clickedButtons.deleteFirst();
                    }
                    else
                    {
//                        gameState ++;
                        gameState = 0;


                        while (jPanel.selectedDots.getSize() > 0)
                        {
                            jPanel.selectedDots.deleteFirst();  // se borra justo despues de crearse el selected dot y no es apreciable.
                        }

                        jPanel.clickedButtons.deleteFirst();
                        jPanel.clickedButtons.deleteFirst();

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
    LinkedList<Point> checkSquares(Point dot1, Point dot2, char connection)
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
            }
            // comprueba si se completa un cuadrado con la nueva linea como lado inferior.
            dotAux.y -= 2;  // dotAux es arriba; dot2, arriba y a la derecha.
            dot2.y -= 1;
            if (dotAux.y >= 0 && horizontalLineMatrix.getAt(dotAux) && verticalLineMatrix.getAt(dotAux) && verticalLineMatrix.getAt(dot2))
            {
                squareMatrix.setAt(dotAux, true); // La linea horizontal es el lado inferior de un nuevo cuadrado segun el if.
                newSquares.append(dotAux);
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
            }
            // comprueba si se completa un cuadrado con la nueva linea como lado derecho.
            dotAux.x -= 2;  // dot1 es la posicion actual; dotAux, izquierda; dot2, abajo y a la izquierda.
            dot2.x -= 1;
            if (dotAux.x >= 0 && verticalLineMatrix.getAt(dotAux) && horizontalLineMatrix.getAt(dotAux) && horizontalLineMatrix.getAt(dot2))
            {
                squareMatrix.setAt(dotAux, true); // La linea horizontal es el lado inferior de un nuevo cuadrado segun el if.
                newSquares.append(dotAux);
            }
        }
        return newSquares;
    }
    // Devuelve una LinkedList con las posiciones de los cuadrados creados en la squareMatrix.
    public LinkedList<Point> play(Point dot1, Point dot2)
    {
        LinkedList<Point> newSquares = new LinkedList<>();

        //Determina tipo de linea.
        char connection = connectionType(dot1, dot2);
        switch(connection)
        {
            case 'H':
                if (dot2.x < n-1 && dot2.y < m)
                {
                    newSquares = checkSquares(dot2, dot1, connection);  // Da los puntos al reves para que dot1 sea el menor
                }
                break;
            case 'V':
                if (dot2.x < n && dot2.y < m-1)
                {
                    newSquares = checkSquares(dot2, dot1, connection);  // Da los puntos al reves para que dot1 sea el menor
                }
                break;
            case 'h':
                if (dot1.x < n-1 && dot1.y < m)
                {
                    newSquares = checkSquares(dot1, dot2, connection);  // Da los puntos al reves para que dot1 sea el menor
                }
                break;
            case 'v':
                if (dot1.x < n && dot1.y < m-1)
                {
                    newSquares = checkSquares(dot1, dot2, connection);  // Da los puntos al reves para que dot1 sea el menor
                }
                break;
            case 'E':
                System.out.println("Error en Game: los puntos dot1 " + dot1.x + ',' + dot1.y + " y dot2 " + dot2.x + ',' + dot2.y + " tienen distancia mayor a 1");
                return null;    //Error si los puntos dados son invalidos.
        }
        return newSquares;
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
