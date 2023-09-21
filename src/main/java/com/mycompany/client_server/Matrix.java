package com.mycompany.client_server;
public class Matrix
{
    LinkedList<LinkedList<Boolean>> M = new LinkedList<>();
    int m,n; //MxN = Rows x Columns

    public Matrix(int m, int n)
    {
        this.m = m;
        this.n = n;
        for (int i = 0; i < m; i++)
        {
            LinkedList<Boolean> l = new LinkedList<>(n, false);
            M.append(l);
        }
    }

    public void showMatrix()
    {
        for(int i=0; i < m; i++)
        {
            M.getAt(i).showList2();
        }
    }
    public void showMatrixln()
    {
        for(int i=0; i < m; i++)
        {
            M.getAt(i).showList2();
        }
        System.out.println();
    }

    public boolean getAt(Point coords)
    {
        int x = coords.x;
        int y = coords.y;
        return M.getAt(y).getAt(x);
    }
    public void setAt(Point coords, boolean data)
    {
        int x = coords.x;
        int y = coords.y;
        if((y <= m && y >= 0) && (x <= n && x >= 0))
        {
            M.getAt(y).setAt(x, data);
        }
        else
        {
            System.out.println("Matrix/setAt: Index out of bounds");
        }
    }

    public Node<LinkedList<Boolean>> getFirst()
    {
        return M.first;
    }

}
