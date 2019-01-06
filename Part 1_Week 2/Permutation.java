/******************************************************************************
 *  Name: MingTing Lu
 *  Date: 20181223
 *  Description:
 *      Write a client program Permutation.java that takes an integer k as a
 *      command-line argument; reads in a sequence of strings from standard
 *      input using StdIn.readString(); and prints exactly k of them, uniformly
 *      at random. Print each item from the sequence at most once.
 *  (Coursera Princeton Algorithm Part 1)
 *****************************************************************************/

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> q = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            q.enqueue(StdIn.readString());
        }

        for (int i = 0; i < k; i++) {
            System.out.println(q.dequeue());
        }

    }
}
