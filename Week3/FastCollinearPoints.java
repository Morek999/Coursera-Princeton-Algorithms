/******************************************************************************
 *  Name: Ming-Ting Lu
 *  Date: 2019/01/03
 *  Description:
 *      Given a point p, the following method determines whether p participates in a set of 4
 *      or more collinear points.
 *      1. Think of p as the origin.
 *      2. For each other point q, determine the slope it makes with p.
 *      3. Sort the points according to the slopes they makes with p.
 *      4. Check if any 3 (or more) adjacent points in the sorted order have equal slopes with
 *          respect to p. If so, these points, together with p, are collinear.
 *  (Coursera Princeton Algorithm Part 1)
 *  Answer refference: (Github) Alex Joz
 *****************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<LineSegment> segments = new ArrayList<>();

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        // Check null array and null points
        if (points == null) throw new IllegalArgumentException();
        for (Point p : points) if (p == null) throw new IllegalArgumentException();

        Point[] aux = points.clone();
        Arrays.sort(aux);

        // Check duplicate points (sorted, so duplicate will occur in adjacent points
        for (int i = 0; i < aux.length - 1; i++) {
            if (aux[i].compareTo(aux[i + 1]) == 0) throw new IllegalArgumentException();
        }

        // No need to loop the last 3 points cuz collinear will occur with 4 points
        for (int i = 0; i < aux.length - 3; i++) {
            Arrays.sort(aux);  // back to orginal oder to get right aux[i]

            // Sort the points according to the slopes they makes with P.
            // Check if any 3 (or more) adjacent points in the sorted order
            // have equal slopes with respect to p. If so, these points,
            // together with P, are collinear.

            Arrays.sort(aux, aux[i].slopeOrder());  // side effect: aux[i] will be put to aux[0]

            for (int a = 1, b = 2; b < aux.length; b++) {
                // find last collinear point to point P
                while (b < aux.length
                        && Double.compare(aux[0].slopeTo(aux[a]), aux[0].slopeTo(aux[b])) == 0) {
                    b++;  // if slope(P,a) == slope(P,b), move b to next element
                }

                // Add to segment if at least 3 points collinear with point P (first condition)
                // Second condition is to ensure point P is one of the end point on the segment
                if (b - a >= 3 && aux[0].compareTo(aux[a]) < 0) {
                    segments.add(new LineSegment(aux[0], aux[b - 1]));
                }

                // Find next collinear (starting from b)
                a = b;
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

/*        System.out.println(Arrays.toString(points));
        Arrays.sort(points, points[0].slopeOrder());
        System.out.println(Arrays.toString(points));
        Arrays.sort(points, points[1].slopeOrder());
        System.out.println(Arrays.toString(points));*/

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
