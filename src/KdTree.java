import edu.princeton.cs.algs4.*;

import java.awt.*;
import java.util.ArrayList;

public class KdTree {
    private Node root = null;
    private int size = 0;

    public KdTree()                               // construct an empty set of points
    {

    }

    public boolean isEmpty()                      // is the set empty?
    {
        return root == null;
    }

    public int size()                         // number of points in the set
    {
        return size;
    }

    public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        if (isEmpty()) {
            root = new Node(p, Direction.VERTICAL);
            return;
        }

        Node nodeToAdd = new Node(p, null);
        Node parentNode = getParentNode(root, nodeToAdd);

        if (parentNode.direction == Direction.HORIZONTAL) {
            nodeToAdd.direction = Direction.VERTICAL;
        } else {
            nodeToAdd.direction = Direction.HORIZONTAL;
        }

        if (parentNode.compareTo(nodeToAdd) <= 0) {
            parentNode.leftNode = nodeToAdd;
        } else {
            parentNode.rightNode = nodeToAdd;
        }
    }

    public boolean contains(Point2D p)            // does the set contain point p?
    {
        Node current = root;
        while (current.point.compareTo(p) != 0) {
            if (p.compareTo(current.point) < 0) {
                current = current.leftNode;
            } else {
                current = current.rightNode;
            }

            if (current == null) {
                return false;
            }
        }
        return true;
    }

    public void draw()                         // draw all points to standard draw
    {
        drawWithDescendants(root, 0, 0, 1, 1);
    }

    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle
    {
        ArrayList<Point2D> foundNodes = new ArrayList<>();
        searchRange(root, foundNodes, rect);
        return foundNodes;
    }

    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        if (root == null) {
            return null;
        }

        return findNearest(root, root.point, p);
    }

    public static void main(String[] args)                  // unit testing of the methods (optional)
    {
    }

    private Node getParentNode(Node currentParent, Node node) {
        Node foundNode;
        if (currentParent.compareTo(node) <= 0) {
            foundNode = currentParent.leftNode;
        } else {
            foundNode = currentParent.rightNode;
        }

        if (foundNode == null) {
            return currentParent;
        } else {
            return getParentNode(foundNode, node);
        }
    }

    private void drawWithDescendants(Node node, double limitX1, double limitX2, double limitY1, double limitY2) {
        if (node == null) {
            return;
        }

        Point2D p = node.point;
        p.draw();

        if (node.direction == Direction.VERTICAL) {
            edu.princeton.cs.algs4.StdDraw.setPenColor(Color.RED);
            edu.princeton.cs.algs4.StdDraw.line(p.x(), limitY1, p.x(), limitY2);
            drawWithDescendants(node.leftNode, limitX1, p.x(), limitY1, limitY2);
            drawWithDescendants(node.leftNode, p.x(), limitX2, limitY1, limitY2);
        } else {
            edu.princeton.cs.algs4.StdDraw.setPenColor(Color.BLUE);
            edu.princeton.cs.algs4.StdDraw.line(limitX1, p.y(), limitX2, p.y());
            drawWithDescendants(node.leftNode, limitX1, limitX2, limitY1, p.y());
            drawWithDescendants(node.leftNode, limitX1, limitX2, p.y(), limitY2);
        }
    }

    private void searchRange(Node currentNode, ArrayList<Point2D> foundNodes, RectHV rect) {
        if (rect.contains(currentNode.point)) {
            foundNodes.add(currentNode.point);
        }

        if (isLeftValid(currentNode.leftNode, rect)) {
            searchRange(currentNode.leftNode, foundNodes, rect);
        }

        if (isRightValid(currentNode.rightNode, rect)) {
            searchRange(currentNode.rightNode, foundNodes, rect);
        }
    }

    private boolean isLeftValid(Node current, RectHV rect) {
        if (current == null) {
            return false;
        }

        if (current.direction == Direction.VERTICAL) {
            return current.point.x() - rect.xmin() < 0;
        } else {
            return current.point.y() - rect.ymin() < 0;
        }
    }

    private boolean isRightValid(Node current, RectHV rect) {
        if (current.direction == Direction.VERTICAL) {
            return current.point.x() - rect.xmax() > 0;
        } else {
            return current.point.y() - rect.ymax() > 0;
        }
    }

    private Point2D findNearest(Node currentNode, Point2D currentMin, Point2D targetPoint) {
        Point2D min = currentMin;

        if (currentNode.point.distanceTo(targetPoint) < currentMin.distanceTo(targetPoint)) {
            min = currentNode.point;
        }

        double theoreticalMin;
        if (currentNode.direction == Direction.VERTICAL) {
            theoreticalMin = currentNode.point.x() - targetPoint.x();
        } else {
            theoreticalMin = currentNode.point.y() - targetPoint.y();
        }

        if (theoreticalMin > 0) {
            min = findNearest(currentNode.leftNode, min, targetPoint);
            if (Math.abs(theoreticalMin) < min.distanceTo(targetPoint)) {
                min = findNearest(currentNode.rightNode, min, targetPoint);
            }
            // min = checkLeft

            //if(verify theoreticalMin<min checkRight
        } else {
            min = findNearest(currentNode.rightNode, min, targetPoint);
            if (Math.abs(theoreticalMin) < min.distanceTo(targetPoint)) {
                min = findNearest(currentNode.leftNode, min, targetPoint);
            }
            //min = checkRight
            //if(verify Math.abs(theoreticalMin)<min checkLeft
        }

        return min;
    }

    private enum Direction {
        HORIZONTAL,
        VERTICAL
    }

    private class Node {
        public Point2D point;
        public Node leftNode;
        public Node rightNode;
        public Direction direction;

        public Node(Point2D point, Direction direction) {
            this.point = point;
            this.direction = direction;
        }

        public int compareTo(Node otherNode) {
            double term1, term2;
            if (direction == null) {
                throw new NullPointerException();
            }

            if (direction == Direction.VERTICAL) {
                term1 = point.x();
                term2 = otherNode.point.x();
            } else {
                term1 = point.y();
                term2 = otherNode.point.y();
            }

            double diff = term1 - term2;
            return diff < 0 ? -1 : diff > 0 ? 1 : 0;
        }
    }
}
