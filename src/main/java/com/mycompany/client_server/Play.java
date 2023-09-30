package com.mycompany.client_server;
public class Play
{
    public int playerID;
    public Point dot1;
    public Point dot2;
    public boolean completedSquare;

    public Play(){}
    public Play(int playerID, Point dot1, Point dot2, boolean completedSquare)
    {
        this.playerID = playerID;
        this.dot1 = dot1;
        this.dot2 = dot2;
        this.completedSquare = completedSquare;
    }
}
