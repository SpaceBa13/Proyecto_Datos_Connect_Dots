package com.mycompany.client_server;

public class TimeUtilities
{
    public static void waitMilliseconds(int milliseconds)
    {
        try
        {
            Thread.sleep(milliseconds); // Wait for 1 second (1000 milliseconds)
        }
        catch (InterruptedException e)
        {
            // Handle the InterruptedException
        }
    }
}
