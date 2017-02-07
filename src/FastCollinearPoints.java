public class FastCollinearPoints {
    private Point[] points;

    private LineSegment[] lineSegments = new LineSegment[2];
    private int lineSegmentIndex = 0;
    private boolean segmentsRetrieved = false;

    public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
    {
        if(points == null){
            throw new NullPointerException();
        }

        this.points = points;
    }

    public int numberOfSegments()        // the number of line segments
    {
        if(points.length < 4){
            return 0;
        }

        int segmentsCount = 0;
        lineSegments = new LineSegment[2];
        lineSegmentIndex = 0;

        for(Point point:points){
            Point[] pointsCopy = new Point[points.length];
            for(int i=0; i<points.length; i++){
                pointsCopy[i] = points[i];
            }

            java.util.Arrays.sort(pointsCopy, point.slopeOrder());

            Point minPoint = pointsCopy[0];
            Point maxPoint = pointsCopy[0];

            Point segmentStartPoint = pointsCopy[0];
            double initialSlope = segmentStartPoint.slopeTo(pointsCopy[1]);
            int segmentLength = 1;

            for(int i=1; i<points.length; i++){
                if(segmentStartPoint.slopeTo(points[i]) != initialSlope){
                    if(segmentLength >= 3){
                        addLineSegment(new LineSegment(minPoint, maxPoint));
                    }

                    segmentLength = 0;
                    segmentStartPoint = points[i];
                    minPoint = maxPoint = pointsCopy[i];
                }
                segmentLength++;

                minPoint = pointsCopy[i].compareTo(minPoint) < 0 ? pointsCopy[i] : minPoint;
                maxPoint = pointsCopy[i].compareTo(maxPoint) > 0 ? pointsCopy[i] : maxPoint;
            }

            if(segmentLength >= 4){
                addLineSegment(new LineSegment(minPoint, maxPoint));
            }
        }

        segmentsRetrieved = true;
        return segmentsCount;
    }

    public LineSegment[] segments()                // the line segments
    {
        if(!segmentsRetrieved){
            numberOfSegments();
            segmentsRetrieved = true;
        }

        LineSegment[] returnSegments = new LineSegment[lineSegmentIndex];
        for(int i=0; i<lineSegmentIndex; i++){
            returnSegments[i] = lineSegments[i];
        }
        lineSegments = returnSegments;
        return lineSegments;
    }

    private void addLineSegment(LineSegment lineSegment){
        if(lineSegmentIndex == lineSegments.length){
            lineSegments = doubleSegmentsArray(lineSegments);
        }

        lineSegments[lineSegmentIndex++] = lineSegment;
    }

    private LineSegment[] doubleSegmentsArray(LineSegment[] lineSegments){
        LineSegment[] newArray = new LineSegment[lineSegments.length*2];
        for(int i=0; i<lineSegments.length; i++){
            newArray[i] = lineSegments[i];
        }
        return newArray;
    }
}