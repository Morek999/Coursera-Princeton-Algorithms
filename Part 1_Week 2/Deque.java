import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/******************************************************************************
 *  Name: MingTing Lu
 *  Date: 20181218
 *  Description:
 *      A double-ended queue or deque (pronounced “deck”) is a generalization
 *      of a stack and a queue that supports adding and removing items from
 *      either the front or the back of the data structure.
 *  (Coursera Princeton Algorithm Part 1)
 *****************************************************************************/

public class Deque<Item> implements Iterable<Item> {
    private int n;       // size of the stack
    private Node first;  // top of stack
    private Node last;   // bottom of stack

    private class Node {
        private Item item;
        private Node next;
        private Node previous;
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public Deque()                           // construct an empty deque
    {
        first = null;
        last = null;
        n = 0;
    }

    public boolean isEmpty()                 // is the deque empty?
    {
        return n == 0;
    }

    public int size()                        // return the number of items on the deque
    {
        return n;
    }

    public void addFirst(Item item)          // add the item to the front
    {
        if (item == null) throw new IllegalArgumentException("null input");
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;

        if (oldfirst != null) oldfirst.previous = first;
        if (last == null) last = first;

        n++;
    }

    public void addLast(Item item)           // add the item to the end
    {
        if (item == null) throw new IllegalArgumentException("null input");
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.previous = oldlast;

        if (oldlast != null) oldlast.next = last;
        if (first == null) first = last;

        n++;
    }

    public Item removeFirst()                // remove and return the item from the front
    {
        if (isEmpty()) throw new NoSuchElementException("empty stack");
        Item item = first.item;
        if (first.next == null) {
            first = null;
            last = null;
        }
        else {
            first = first.next;
            first.previous = null;
        }
        n--;
        return item;
    }

    public Item removeLast()                 // remove and return the item from the end
    {
        if (isEmpty()) throw new NoSuchElementException("empty stack");
        Item item = last.item;
        if (last.previous == null) {
            first = null;
            last = null;
        }
        else {
            last = last.previous;
            last.next = null;
        }
        n--;
        return item;
    }

    public Iterator<Item> iterator()         // return an iterator over items in order from front to end
    {
        return new ListIterator();
    }

    public static void main(String[] args)   // unit testing (optional)
    {
        Deque<String> q = new Deque<String>();
        StdOut.println(
                "[Test] 1.addFirst; 2.addLast; 3.isEmpty 4.size 5.removeFirst 6.removeLast 0.close: ");
        int loop = StdIn.readInt();
        while (loop != 0) {
            switch (loop) {
                case 1:
                    StdOut.println("Type a string:");
                    q.addFirst(StdIn.readString());
                    break;
                case 2:
                    StdOut.println("Type a string:");
                    q.addLast(StdIn.readString());
                    break;
                case 3:
                    StdOut.println(q.isEmpty());
                    break;
                case 4:
                    StdOut.println(q.size());
                    break;
                case 5:
                    StdOut.println(q.removeFirst());
                    break;
                case 6:
                    StdOut.println(q.removeLast());
                    break;
            }
            StdOut.println("-----");
            StdOut.println(
                    "[Test] 1.addFirst; 2.addLast; 3.isEmpty 4.size 5.removeFirst 6.removeLast 0.close: ");
            loop = StdIn.readInt();
        }
    }
}
