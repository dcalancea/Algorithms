import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args)   // test client (optional)
    {
//        int count = Integer.parseInt(args[0]);
//        RandomizedQueue<String> queue = new RandomizedQueue<>();
//
//        System.out.println("Please enter: " + count + " numbers: ");
//        try {
//
//            for (int i = 0; i < count; i++) {
//                queue.enqueue(StdIn.readString());
//            }
//        } catch (Exception e) {
//
//        }
//
//        for (String i : queue) {
//            for (String j : queue)
//            System.out.print(j + " ");
//            System.out.println();
//        }
        //endof comment 1

//        int size = queue.size();
//        for (int i = 0; i < size; i++) {
//            String element = queue.removeLast();
//            System.out.println(element);
//        }


//        PercolationStats ps = new PercolationStats(1000, 100);
//        System.out.println("mean: "+ ps.mean());
//        System.out.println("stddev: " + ps.stddev());
//        System.out.println("95% confidence interval: " + ps.confidenceLo() + " " + ps.confidenceHi());


//        //Collinear
//        In in = new In(args[0]);
//        int n = in.readInt();
//        Point[] points = new Point[n];
//        for (int i = 0; i < n; i++) {
//            int x = in.readInt();
//            int y = in.readInt();
//            points[i] = new Point(x, y);
//        }
//
//        StdDraw.setPenColor(StdDraw.BLUE);
//        // draw the points
//        StdDraw.enableDoubleBuffering();
//        StdDraw.setXscale(0, 32768);
//        StdDraw.setYscale(0, 32768);
//        for (Point p : points) {
//            p.draw();
//        }
//        StdDraw.show();
//
//        // print and draw the line segments
//        FastCollinearPoints collinear = new FastCollinearPoints(points);
//        for (LineSegment segment : collinear.segments()) {
//            StdOut.println(segment);
//            segment.draw();
//        }
//        StdDraw.show();

        // create initial board from file
//        In in = new In(args[0]);
//        int n = in.readInt();
//        int[][] blocks = new int[n][n];
//        for (int i = 0; i < n; i++)
//            for (int j = 0; j < n; j++)
//                blocks[i][j] = in.readInt();
//        Board initial = new Board(blocks);
//        initial.manhattan();
//
//        // solve the puzzle
//        Solver solver = new Solver(initial);
//
//        // print solution to standard output
//        if (!solver.isSolvable())
//            StdOut.println("No solution possible");
//        else {
//            StdOut.println("Minimum number of moves = " + solver.moves());
//            for (Board board : solver.solution())
//                StdOut.println(board);
//        }

        KdTree set = new KdTree();
        Point2D[] points = (Point2D[])Arrays.asList (
            new Point2D(0.1, 0.1),
            new Point2D(0.2, 0.2),
            new Point2D(0.3, 0.3),
            new Point2D(0.4, 0.3),
            new Point2D(0.5, 0.3),
            new Point2D(0.6, 0.3),
            new Point2D(0.7, 0.3),
            new Point2D(0.6, 0.4),
            new Point2D(0.5, 0.4),
            new Point2D(0.3, 0.4)
        ).toArray();

        for(Point2D point: points){
            set.insert(point);
        }
        set.insert(points[9]);
        Random r = new Random();
        for(int i=0;i<1000; i++){
            int index = r.nextInt(points.length);
            set.insert(points[index]);
            if(set.size() > points.length){
                int x = 0;
            }
        }

        RectHV rect = new RectHV(0,0, 0.31, 0.31);
        Iterable<Point2D> list = set.range(rect);
        StdOut.println(set.size());
    }
}