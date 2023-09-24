package com.mycompany.client_server;

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
