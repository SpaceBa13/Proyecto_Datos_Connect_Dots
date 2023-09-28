package com.mycompany.client_server;

// Calcula como dibujar un linea en pantalla a partir de la coordenada de esta en
//las matrices de lineas horizontalLineMatrix o verticalLineMatrix, si es horizontal o vertical y las dimensiones (mxn) de
//matriz de puntos del juego.
// Calcula el largo de un rectangulo delgado y la posicion en que se dibuja. JPanelDrawLines dibuja los rectangulos que se le den
public class Line
{
    public int x, y, width, height, m, n;
    boolean isVertical;

    public Line(Point coordinates, boolean isVertical, int m, int n)
    {
        this.x = coordinates.x;
        this.y = coordinates.y;
        this.m = m;
        this.n = n;

        this.isVertical = isVertical;

        if (isVertical)
        {
            this.width = 8;
            this.height = 100000 / (m+1);
            this.x = 100000 / (n+1) * (x+1);
            this.y = this.height * (y+1);
        }
        else
        {
            this.width = 100000 / (n+1);
            this.height = 8;
            this.x = this.width * (x+1);
            this.y = 100000 / (m+1) * (y+1);
        }
    }
}
