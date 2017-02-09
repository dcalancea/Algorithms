import java.util.Comparator;

public class FastCollinearPoints {
    private Point[] points;

    private LineSegment[] lineSegments = new LineSegment[0];
    private boolean segmentsRetrieved = false;

    public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
    {
        if (points == null) {
            throw new NullPointerException();
        }

        Point[] pointsClone = points.clone();
        java.util.Arrays.sort(pointsClone, points[0].slopeOrder());

        //check for duplicates
        for(int i = 0; i < pointsClone.length - 1; i++){
            if(pointsClone[i].slopeTo(pointsClone[i+1]) == Double.NEGATIVE_INFINITY){
                throw new IllegalArgumentException();
            }
        }

        this.points = pointsClone;
    }

    public int numberOfSegments()        // the number of line segments
    {
        if (points.length < 4) {
            return 0;
        }

        MySegment[] mySegments = new MySegment[2];
        int mySegmentIndex = 0;

        for (Point point : points) {
            Point[] pointsCopy = new Point[points.length];
            for (int i = 0; i < points.length; i++) {
                pointsCopy[i] = points[i];
            }

            java.util.Arrays.sort(pointsCopy, point.slopeOrder());

            Point minPoint = pointsCopy[0];
            Point maxPoint = pointsCopy[0];

            //Point segmentStartPoint = pointsCopy[0];
            double slope = point.slopeTo(pointsCopy[0]);
            int segmentLength = 1;

            for (int i = 0; i < pointsCopy.length; i++) {
                if (point.slopeTo(pointsCopy[i]) != slope) {
                    if (segmentLength >= 4) {
                        mySegments = addElementToArray(mySegments, new MySegment(minPoint, maxPoint), mySegmentIndex++);
                    }

                    segmentLength = 1;
                    slope = point.slopeTo(pointsCopy[i]);
                    minPoint = maxPoint = point;
                }
                segmentLength++;

                minPoint = pointsCopy[i].compareTo(minPoint) < 0 ? pointsCopy[i] : minPoint;
                maxPoint = pointsCopy[i].compareTo(maxPoint) > 0 ? pointsCopy[i] : maxPoint;
            }

            if (segmentLength >= 4) {
                mySegments = addElementToArray(mySegments, new MySegment(minPoint, maxPoint), mySegmentIndex++);
            }
        }

        lineSegments = mySegmentsToLineSegments(mySegments, mySegmentIndex - 1);

        segmentsRetrieved = true;
        return lineSegments.length;
    }

    public LineSegment[] segments()                // the line segments
    {
        if (!segmentsRetrieved) {
            numberOfSegments();
            segmentsRetrieved = true;
        }

        return lineSegments.clone();
    }

//    private <T> T[] addElementToArray(T[] array, T element, int insertIndex) {
//        if (insertIndex == array.length) {
//            array = doubleArray(array);
//        }
//
//        array[insertIndex] = element;
//        return array;
//    }

//    private <T> T[] doubleArray(T[] lineSegments) {
//        T[] newArray = (T[]) new Object[lineSegments.length * 2];
//        for (int i = 0; i < lineSegments.length; i++) {
//            newArray[i] = lineSegments[i];
//        }
//        return newArray;
//    }

    private MySegment[] addElementToArray(MySegment[] array, MySegment element, int insertIndex) {
        if (insertIndex == array.length) {
            array = doubleArray(array);
        }

        array[insertIndex] = element;
        return array;
    }

    private MySegment[] doubleArray(MySegment[] lineSegments) {
        MySegment[] newArray = new MySegment[lineSegments.length * 2];
        for (int i = 0; i < lineSegments.length; i++) {
            newArray[i] = lineSegments[i];
        }
        return newArray;
    }

//    private LineSegment[] doubleArray(LineSegment[] lineSegments) {
//        LineSegment[] newArray = new LineSegment[lineSegments.length * 2];
//        for (int i = 0; i < lineSegments.length; i++) {
//            newArray[i] = lineSegments[i];
//        }
//        return newArray;
//    }

    private class MySegment implements Comparable<MySegment> {
        public Point p1;
        public Point p2;

        public MySegment(Point p1, Point p2){
            this.p1 = p1;
            this.p2 = p2;
        }

        public int compareTo(MySegment s2) {
            if(s2 == null || s2.p1 == null || s2.p2 == null) {
                return 1;
            }

            int compareP1 = p1.compareTo(s2.p1);
            int compareP2 = p2.compareTo(s2.p2);

            return compareP1 != 0 ? compareP1 : compareP2;
        }

        public LineSegment toLineSegment(){
            return new LineSegment(p1, p2);
        }
    }

    private class MySegmentComparator implements Comparator<MySegment>{
        public int compare(MySegment s1, MySegment s2) {

            if(s2 == null || s2.p1 == null || s2.p2 == null) {
                return 1;
            }

            int compareP1 = s1.p1.compareTo(s2.p1);
            int compareP2 = s1.p2.compareTo(s2.p2);

            return compareP1 != 0 ? compareP1 : compareP2;
        }
    }

    private LineSegment[] mySegmentsToLineSegments(MySegment[] mySegments, int endIndex){
        if(endIndex == -1) {
            return new LineSegment[0];
        }

        java.util.Arrays.sort(mySegments, 0, endIndex + 1, new MySegmentComparator());

        int index = 0;
        MySegment[] destinationArray = new MySegment[2];
        for(int i=0; i<=endIndex; i++){
            if(i == endIndex || mySegments[i].compareTo(mySegments[i+1]) != 0){
                destinationArray = addElementToArray(destinationArray, mySegments[i], index++);
            }
        }

        LineSegment[] returnSegments = new LineSegment[index];
        for (int i = 0; i < index; i++) {
            returnSegments[i] = destinationArray[i].toLineSegment();
        }
        return returnSegments;
    }
}