/******************************************************************************
 *  Name: MingTing Lu
 *  Date: 20181217
 *  Description: Percolation testing algorithm, statistic check.
 *  (Coursera Princeton Algorithm Part 1)
 *****************************************************************************/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] x;    // saves the open ratio when percolates
    private int trials;    // the trials will be used across methods
    private double m;      // for mean
    private double s;      // for standard deviation

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException("n and trials should be > 0");
        }
        this.trials = trials;    // Assign trials into global variable
        x = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (!p.isOpen(row, col)) p.open(row, col);
            }

            // Solution A: turn all int into double first, then divide
            double doubleOpen = p.numberOfOpenSites();    // the method will return int
            double doubleTotal = n * n;                   // n is int
            x[i] = doubleOpen / doubleTotal;              // x[] is double

            // Solution B: cast the division result (GOOD CODE SHOULD BE ZERO CAST!!!)
            //x[i] = (double) p.numberOfOpenSites() / (n * n);
        }
        m = -1;    // Reset m and s to an impossible (default) value after every execution
        s = -1;
    }

    // sample mean of percolation threshold
    public double mean() {
        if (m == -1) m = StdStats.mean(x);    // Calculate if m is default value. Else do nothing
        return m;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (s == -1) s = StdStats.stddev(x);
        return s;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(trials);
    }

    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        String confidence = "[" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]";
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }
}
