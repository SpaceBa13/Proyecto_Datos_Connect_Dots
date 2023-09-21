package com.mycompany.client_server;
public class LinkedList<T>
{
    Node<T> first;
    public LinkedList(){}

    public LinkedList(int len, T data)
    {
        for (int i = 0; i < len; i++)
        {
            this.append(data);
        }
    }
    public boolean isEmpty() {return first == null; }

    public void insertFirst(T DataNewFirst)
    {
        Node<T> AuxNode = new Node<T>();        //new Node<T>() Asi se usa el constructor con <T> parametro
        AuxNode.next = first.next;
        AuxNode.data = first.data;
        first.next = AuxNode;
        first.data = DataNewFirst;
    }
    public void append(T data)
    {
        Node<T> newNode = new Node<T>(data);

        if(isEmpty())
        {
            first = newNode;
        }
        else
        {
            Node<T> piv = first;
            while(piv.next != null)
            {
                piv = piv.next;
            }
            piv.next = newNode;
        }
    }
    public void insertAt(int index, T data)
    {
        if (index == 0){insertFirst(data); return;}
        if (index == getSize()){append(data); return;}

        Node<T> piv = first;
        for (int i = 2; i <= index; i ++) {piv = piv.next;}
        Node<T> AuxNode = piv.next;
        Node<T> NewNode = new Node<T>(data, AuxNode);
        //piv = new Node(piv.data, NewNode); ESTA ES DIFERENTE A LA LINEA DE ABAJO.
        piv.next = NewNode;
        /*
        Node<T> NewNode = new Node<T>(data, AuxNode);
        //piv = new Node(piv.data, NewNode); ESTA ES DIFERENTE A LA LINEA DE ABAJO.
        piv.next = NewNode;

        -->
        //piv = new Node(piv.data, NewNode); ESTA ES DIFERENTE A LA LINEA DE ABAJO.
        piv.next = new Node<T>(data, AuxNode);
        */
    }
    public void deleteFirst()
    {
        if (isEmpty()) {return;}
        first = first.next;
    }
    public void deleteLast()
    {
        if (isEmpty()) {return;}
        if (first.next == null) {first = null; return;}

        Node<T> piv = first;
        Node<T> pivPrevious = first;
        while (piv.next != null)
        {
            pivPrevious = piv;
            piv = piv.next;
        }
        pivPrevious.next = null;
    }
    public void deleteAt(int index)
    {
        if (index == 0){deleteFirst();return;}

        Node<T> piv = first;
        Node<T> pivPrevious = first;
        for (int i = 0; i < index; i++)
        {
            pivPrevious = piv;
            piv = piv.next;
        }
        pivPrevious.next = piv.next;
    }
    public int getSize()
    {
        if (first == null){return 0;}
        int Size = 0;
        Node<T> piv = first;
        while (piv != null)
        {
            Size ++;
            piv = piv.next;
        }
        return Size;
    }

    public void showList()
    {
        if(isEmpty())
        {
            System.out.println("()-->");
        }
        else
        {
            Node<T> piv = first;
            while(piv != null)
            {
                System.out.print("(" + piv.data + ")-->");
                piv = piv.next;
            }
            System.out.println();
        }
    }
    public void showList2()
    {
        if(isEmpty())
        {
            System.out.println("()");
        }
        else
        {
            Node<T> piv = first;
            while(piv != null)
            {
                System.out.print("(" + piv.data + ")\t");
                piv = piv.next;
            }
            System.out.println();
        }
    }

    public T getAt(int index)
    {
        if (index == 0){return first.getData();}
        else
        {
            if(index < getSize())
            {
                Node<T> piv = first;
                for(int i=0; i < index; i++){piv = piv.next;}
                return piv.getData();
            }
            else
            {
                System.out.println("LinkedList/getAt: Index out of bounds");
                return null;
            }
        }
    }
    public void setAt(int index, T data)
    {
        if(index == 0){first.setData(data);}
        else
        {
            if(index < getSize())
            {
                Node<T> piv = first;
                for(int i=0; i < index; i++){piv = piv.next;}
                piv.setData(data);
            }
            else
            {
                System.out.println("LinkedList/setAt: Index out of bounds");
            }
        }
    }
    public Node<T> getNodeAt(int index)
    {
        if (index == 0){return first;}
        Node<T> piv = first;
        for(int i=1; i < index; i++){piv = piv.next;}
        return piv;
    }


}
