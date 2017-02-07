import java.awt.*;

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


        //Collinear
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        StdDraw.setPenColor(StdDraw.BLUE);
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

//        StdDraw.setPenRadius(0.05);
//        StdDraw.setPenColor(StdDraw.BLUE);
//        StdDraw.point(0.5, 0.5);
//        StdDraw.setPenColor(StdDraw.MAGENTA);
//        StdDraw.line(0.2, 0.2, 0.8, 0.2);
    }
}