public class BruteCollinearPoints {
    private Point[] points;
    private boolean segmentsRetrieved = false;

    private LineSegment[] lineSegments = new LineSegment[2];
    private int lineSegmentIndex = 0;
    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
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
        int count = 0;
        Point[] collinearPoints = new Point[4];
        lineSegments = new LineSegment[2];
        lineSegmentIndex = 0;

        for(int i=0; i<points.length - 3; i++){
            for(int j=i+1; j<points.length - 2; j++){
                for(int k = j+1; k<points.length - 1; k++){
                    for(int l=k+1; l<points.length; l++){
                        if(points[i] == null || points[j] == null || points[k] == null || points[l] == null)
                            throw new NullPointerException();
                        if(points[i].slopeTo(points[j]) == points[j].slopeTo(points[k])
                                && points[j].slopeTo(points[k]) == points[k].slopeTo(points[l])){
                            count++;

                            collinearPoints[0] = points[i];
                            collinearPoints[1] = points[j];
                            collinearPoints[2] = points[k];
                            collinearPoints[3] = points[l];
                            java.util.Arrays.sort(collinearPoints, 0, 4);

                            LineSegment lineSegment = new LineSegment(collinearPoints[0], collinearPoints[3]);
                            addLineSegment(lineSegment);
                        }
                    }
                }
            }
        }

        segmentsRetrieved = true;
        return count;
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
        return returnSegments.clone();
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