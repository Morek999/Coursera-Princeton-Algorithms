/******************************************************************************
 *  Name: Ming-Ting Lu
 *  Date: 2019/01/03
 *  Description:
 *      Write a program BruteCollinearPoints.java that examines 4 points at a time and checks
 *      whether they all lie on the same line segment, returning all such line segments. To check
 *      whether the 4 points p, q, r, and s are collinear, check whether the three slopes between
 *      p and q, between p and r, and between p and s are all equal.
 *  (Coursera Princeton Algorithm Part 1)
 *****************************************************************************/

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> segments = new ArrayList<>();

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        for (Point p : points) if (p == null) throw new IllegalArgumentException();

        Point[] sg = new Point[4];
        Point[] aux = points.clone();
        Arrays.sort(aux);

        // check duplicate and null element
        for (int i = 0; i < aux.length - 1; i++) {
            if (aux[i].compareTo(aux[i + 1]) == 0) throw new IllegalArgumentException();
        }

        for (int a = 0; a < points.length; a++) {
            for (int b = a + 1; b < points.length; b++) {
                for (int c = b + 1; c < points.length; c++) {
                    for (int d = c + 1; d < points.length; d++) {
                        double ab = points[a].slopeTo(points[b]);
                        double ac = points[a].slopeTo(points[c]);
                        double ad = points[a].slopeTo(points[d]);
                        if (ab == ac && ab == ad) {
                            sg[0] = points[a];
                            sg[1] = points[b];
                            sg[2] = points[c];
                            sg[3] = points[d];

                            Arrays.sort(sg);
                            segments.add(new LineSegment(sg[0], sg[3]));
                        }
                    }
                }
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
    }
}
