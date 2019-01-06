import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/******************************************************************************
 *  Name: MingTing Lu
 *  Date: 20181223
 *  Description:
 *      A randomized queue is similar to a stack or queue, except that the item
 *      removed is chosen uniformly at random from items in the data structure.
 *  (Coursera Princeton Algorithm Part 1)
 *****************************************************************************/

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] a;    // array of items
    private int n;       // number of elements in array

    public RandomizedQueue()                 // construct an empty randomized queue
    {
        a = (Item[]) new Object[2];
        n = 0;
    }

    public boolean isEmpty()                 // is the randomized queue empty?
    {
        return n == 0;
    }

    public int size()                        // return the number of items on the randomized queue
    {
        return n;
    }

    public void enqueue(Item item)           // add the item
    {
        if (item == null) throw new IllegalArgumentException("null input");
        if (n == a.length) resize(n * 2);
        a[n++] = item;
    }

    public Item dequeue()                    // remove and return a random item
    {
        if (isEmpty()) throw new NoSuchElementException("empty");
        int k = StdRandom.uniform(n);
        Item item = a[k];
        a[k] = a[n - 1];
        a[n - 1] = null;
        n--;
        if (n > 0 && n == a.length / 4) resize(a.length / 2);
        return item;
    }

    public Item sample()                     // return a random item (but do not remove it)
    {
        if (isEmpty()) throw new NoSuchElementException("empty");
        return a[StdRandom.uniform(n)];
    }

    public Iterator<Item> iterator()         // return an independent iterator over items in random order
    {
        return new ArrayIterator();
    }

    private void resize(int capacity) {
        assert capacity >= n;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) temp[i] = a[i];
        a = temp;
    }

    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;
        private int[] id;

        public ArrayIterator() {                           // generate random seq from 0 to n-1
            id = new int[n];                               // [0, 1, 2, ...]
            if (n > 0) {                                   // prevent from empty input
                id[0] = 0;                                 // initialize first element
                for (int x = 1; x < n; x++) {              // from 1 to n-1
                    int k = StdRandom
                            .uniform(x + 1);      // randomly insert at location from 0 to n
                    id[x] = id[k];                         // swap location x and k
                    id[k] = x;
                }
            }
        }

        public boolean hasNext() {
            return i < n;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return a[id[i++]];
        }
    }

    public static void main(String[] args)   // unit testing (optional)
    {
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        StdOut.println(
                "[Test] 1.enqueue; 2.dequeue; 3.isEmpty 4.size 5.sample 6.iterator 0.close: ");
        int loop = StdIn.readInt();
        while (loop != 0) {
            switch (loop) {
                case 1:
                    StdOut.println("Type a string: ");
                    String item = StdIn.readString();
                    q.enqueue(item);
                    StdOut.println("Enqueued!");
                    break;
                case 2:
                    StdOut.println(q.dequeue());
                    break;
                case 3:
                    StdOut.println(q.isEmpty());
                    break;
                case 4:
                    StdOut.println(q.size());
                    break;
                case 5:
                    StdOut.println(q.sample());
                    break;
                case 6:
                    StdOut.println(q.iterator());
                    break;
            }
            StdOut.println("-----");
            StdOut.println(
                    "[Test] 1.enqueue; 2.dequeue; 3.isEmpty 4.size 5.sample 6.iterator 0.close: ");
            loop = StdIn.readInt();
        }
        StdOut.println("(" + q.size() + " left on queue)");
    }
}
