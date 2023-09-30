package com.mycompany.client_server;
public class Play
{
    public int playerID;
    public Point dot1;
    public Point dot2;

    public Play(){}
    public Play(int playerID, Point dot1, Point dot2)
    {
        this.playerID = playerID;
        this.dot1 = dot1;
        this.dot2 = dot2;
    }
}
