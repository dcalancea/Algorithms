import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class PointSET {
    private Set<Point2D> pointSet = new TreeSet<>();

    public PointSET()                               // construct an empty set of points
    {
    }

    public boolean isEmpty()                      // is the set empty?
    {
        return pointSet.isEmpty();
    }

    public int size()                         // number of points in the set
    {
        return pointSet.size();
    }

    public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        pointSet.add(p);
    }

    public boolean contains(Point2D p)            // does the set contain point p?
    {
        return pointSet.contains(p);
    }

    public void draw()                         // draw all points to standard draw
    {
        for (Point2D p : pointSet) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle
    {
        ArrayList<Point2D> result = new ArrayList<>();
        for (Point2D p : pointSet) {
            if (rect.contains(p)) {
                result.add(p);
            }
        }
        return result;
    }

    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        if (pointSet.isEmpty()) {
            return null;
        }
        Point2D nearest = pointSet.iterator().next();

        for (Point2D point : pointSet) {
            if (p.compareTo(point) < nearest.compareTo(point)) {
                nearest = point;
            }
        }

        return nearest;
    }

    public static void main(String[] args)                  // unit testing of the methods (optional)
    {

    }
}