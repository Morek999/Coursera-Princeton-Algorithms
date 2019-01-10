import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/******************************************************************************
 *  Name: MingTing Lu
 *  Date: 2019/01/10
 *  Description: 8 puzzle - Board
 *  (Coursera Princeton Algorithm Part 1)
 *****************************************************************************/

public class Board {
    private int[][] blocks;  // blocks n-by-n
    private int n;           // dimension
    private int disH = -1;        // Hamming distance
    private int disM = -1;        // Manhattan distance

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.blocks = blocks;
        n = blocks.length;
        // disM = -1;
        // disH = -1;
    }

    private int[][] swap(int input[][], int fromI, int fromJ, int toI, int toJ) {
        int[][] aux = new int[input.length][input.length];
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input.length; j++) {
                aux[i][j] = input[i][j];
            }
        }
        int temp = aux[fromI][fromJ];
        aux[fromI][fromJ] = aux[toI][toJ];
        aux[toI][toJ] = temp;

        return aux;
    }

    private int calMan(int e, int row, int col) {
        int eRow = (e - 1) / n;
        int eCol = (e - 1) % n;
        return Math.abs(eRow - row) + Math.abs(eCol - col);
    }

    private int[] searchZero() {
        int[] zero = new int[2];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0) {
                    zero[0] = i;   // row of zero
                    zero[1] = j;   // col of zero
                    break;
                }
            }
        }
        return zero;
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of blocks out of place
    // i is row, j is column
    // (i*n + j + 1) is the default value at that slot
    public int hamming() {
        if (disH != -1) return disH;
        disH = 0;      // initialize disH
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0) continue;
                if (blocks[i][j] != (i * n + j + 1)) disH++;
            }
        }
        return disH;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        if (disM != -1) return disM;
        disM = 0;      // initialize disM
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0) continue;
                disM += calMan(blocks[i][j], i, j);
                // k/n = row; k%n = column
            }
        }
        return disM;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (blocks[i][j] != 0 && blocks[i][j + 1] != 0) {
                    return new Board(swap(blocks, i, j, i, j + 1));
                }
            }
        }
        return null;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        return Arrays.deepEquals(this.blocks, that.blocks);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> queue = new Queue<Board>();

        // Step1: Search for 0
        int[] zero = searchZero();
        int x = zero[0];
        int y = zero[1];

        // Step2: Enqueue all possible neighboring of [x][y]
        // Down : [x+1][ y ]
        if (x + 1 < n) queue.enqueue(new Board(swap(blocks, x, y, x + 1, y)));
        // up   : [x-1][ y ]
        if (x - 1 >= 0) queue.enqueue(new Board(swap(blocks, x, y, x - 1, y)));
        // Right: [ x ][y+1]
        if (y + 1 < n) queue.enqueue(new Board(swap(blocks, x, y, x, y + 1)));
        // Left: [ x ][y-1]
        if (y - 1 >= 0) queue.enqueue(new Board(swap(blocks, x, y, x, y - 1)));

        return queue;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) {

        int[][] blocks;
        blocks = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                blocks[i][j] = 3 * i + j + 1;
            }
        }
        blocks[1][1] = 9;
        blocks[2][2] = 8;
        blocks[2][1] = 0;

        Board node = new Board(blocks);
        StdOut.println(
                node.disM + " | " + node.manhattan() + " | " + node.disM + " > ");
        StdOut.println("------");

        for (Board b : node.neighbors())
            StdOut.println(b.disM + " | " + b.manhattan() + " | " + b.disM + " > ");


    }
}
