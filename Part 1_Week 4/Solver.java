import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/******************************************************************************
 *  Name: MingTing Lu
 *  Date: 2019/01/10
 *  Description: 8 puzzle - Solver
 *  (Coursera Princeton Algorithm Part 1)
 *****************************************************************************/

public class Solver {

    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private SearchNode prev;
        private int moves;
        private int priority;

        SearchNode(Board board) {
            this.board = board;
            this.prev = null;
            this.moves = 0;
            this.priority = -1;
        }

        public int calPriority() {     // Caching manhattan distance in node
            if (this.priority != -1) return this.priority;
            this.priority = this.board.manhattan() + this.moves;
            return this.priority;
        }

        public int compareTo(SearchNode that) {
            return Integer.compare(this.calPriority(), that.calPriority());
        }
    }

    private SearchNode solNode;
    private SearchNode twinNode;   // twin solution Node

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        solNode = new SearchNode(initial);
        twinNode = new SearchNode(initial.twin());

        MinPQ<SearchNode> solQ = new MinPQ<SearchNode>();
        MinPQ<SearchNode> twinQ = new MinPQ<SearchNode>();
        solQ.insert(solNode);
        twinQ.insert(twinNode);

        while (true) {
            solNode = solQ.delMin();
            twinNode = twinQ.delMin();

            if (solNode.board.isGoal()) return;

            if (twinNode.board.isGoal()) {
                solNode.moves = -1;
                solNode.prev = null;
                return;
            }

            addSearchNodes(solQ, solNode);
            addSearchNodes(twinQ, twinNode);

        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return moves() >= 0;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return solNode.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;

        Stack<Board> steps = new Stack<Board>();
        while (solNode != null) {
            steps.push(solNode.board);
            solNode = solNode.prev;
        }
        return steps;
    }

    private void addSearchNodes(MinPQ<SearchNode> q, SearchNode node) {
        assert node.board != null;

        for (Board b : node.board.neighbors()) {
            if (node.prev == null || !b.equals(node.prev.board)) {
                SearchNode candidate = new SearchNode(b);
                candidate.prev = node;
                candidate.moves = node.moves + 1;

                q.insert(candidate);
            }
        }
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
