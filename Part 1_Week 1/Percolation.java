/******************************************************************************
 *  Name: MingTing Lu
 *  Date: 20181212
 *  Description: Percolation testing algorithm.
 *  (Coursera Princeton Algorithm Part 1)
 *****************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] opened;  // saves the matrix of opened sites (true if opened)
    private WeightedQuickUnionUF uf;
    // check percolation with 2 virtual nodes
    private WeightedQuickUnionUF ufbw;
    // check back-wash percolation (only top virtual node)
    private int n;
    private int countOpen;
    private int vTop;     // virtual top node
    private int vBot;     // virtual bottom node

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        this.n = n;
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException("n should be > 0");
        }
        opened = new boolean[n][n];
        uf = new WeightedQuickUnionUF(n * n + 2);
        ufbw = new WeightedQuickUnionUF(n * n + 1);
        countOpen = 0;
        vTop = 0;
        vBot = n * n + 1;
    }

    // check whether the index is out of range or not
    private boolean checkIndex(int row, int col) {
        return (row > 0 && row <= n) && (col > 0 && col <= n);
    }

    // turn (row, col) to serial (cuz uf is serial structure)
    private int serial(int row, int col) {
        return (row - 1) * n + col;
    }

    // union both uf and ufbw
    private void unionBoth(int p, int q) {
        uf.union(p, q);
        ufbw.union(p, q);
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if (checkIndex(row, col)) {
            int site = serial(row, col);
            if (!isOpen(row, col)) {
                opened[row - 1][col - 1] = true;
                countOpen++;
                if (row == 1) unionBoth(site, vTop); // union the top node
                if (row == n) uf.union(site, vBot);  // only uf union the bottom node

                // union with the adjacent sites
                if (row > 1 && isOpen(row - 1, col)) unionBoth(site, serial(row - 1, col));
                if (row < n && isOpen(row + 1, col)) unionBoth(site, serial(row + 1, col));
                if (col > 1 && isOpen(row, col - 1)) unionBoth(site, serial(row, col - 1));
                if (col < n && isOpen(row, col + 1)) unionBoth(site, serial(row, col + 1));
            }
        } else {
            throw new java.lang.IllegalArgumentException("out the range");
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (checkIndex(row, col)) {
            return opened[row - 1][col - 1];
        } else {
            throw new java.lang.IllegalArgumentException("out the range");
        }
    }

    // is site (row, col) full?
    // check the connection between the site and the top, without back-wash
    public boolean isFull(int row, int col) {
        if (checkIndex(row, col)) {
            return ufbw.connected(serial(row, col), vTop);
        } else {
            throw new java.lang.IllegalArgumentException("out the range.");
        }
    }

    // number of open sites
    public int numberOfOpenSites() {
        return countOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(vTop, vBot);
    }

    public static void main(String[] args) {

    }
}
