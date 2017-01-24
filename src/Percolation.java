import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private enum SiteState {
        CLOSED,
        OPEN
    }

    private SiteState[][] grid;
    private WeightedQuickUnionUF qf;
    private int topRoot = -1;
    private int bottomRoot = -2;
    private boolean percolates = false;
    private int openSites = 0;

    private int n;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n){
        if(n<1) {
            throw new java.lang.IllegalArgumentException();
        }

        qf = new WeightedQuickUnionUF((n*n)+2);
        topRoot = n*n;
        bottomRoot = n*n+1;
        this.n = n;

        grid = new SiteState[n][n];

        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                grid[i][j] = SiteState.CLOSED;
            }
        }
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col){
        row--;
        col--;

        if(row>n-1 || col>n-1 || row<0 || col<0){
            throw new java.lang.IndexOutOfBoundsException();
        }

        if(grid[row][col] == SiteState.CLOSED){
            openSites++;
            grid[row][col] = SiteState.OPEN;
        }else{
            return;
        }

        int qfIndex = fromCoordinates(row, col);

        int[] neighbours = new int[]{-1, -1, -1, -1};

        if(row<n-1 && isOpen(row+2, col+1)) neighbours[0] = qf.find(fromCoordinates(row+1, col));
        if(row>0 && isOpen(row, col+1)) neighbours[1] = qf.find(fromCoordinates(row-1, col));
        if(col<n-1 && isOpen(row+1, col+2)) neighbours[2] = qf.find(fromCoordinates(row, col+1));
        if(col>0 && isOpen(row+1, col)) neighbours[3] = qf.find(fromCoordinates(row, col-1));

        boolean hasTopRoot = false;
        boolean hasBottomRoot = false;

        if(row == 0){
            qf.union(topRoot, qfIndex);
            hasTopRoot = true;
        }
        if(row == n-1){
            qf.union(bottomRoot, qfIndex);
            hasBottomRoot = true;
        }

        for(int i=0;i<4;i++){
            if(neighbours[i] == qf.find(topRoot)) hasTopRoot = true;
            if(neighbours[i] == qf.find(bottomRoot)) hasBottomRoot = true;
        }

        if(hasTopRoot && hasBottomRoot)
        {
            percolates = true;
        }

        for(int i=0;i<4;i++){
            if(neighbours[i] > 0 && qf.find(neighbours[i]) != qf.find(qfIndex))
            qf.union(neighbours[i], qfIndex);
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        row--;
        col--;

        if(row>n-1 || col>n-1 || row<0 || col<0){
            throw new java.lang.IndexOutOfBoundsException ();
        }

        return grid[row][col] == SiteState.OPEN;
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col){
        row--;
        col--;

        if(row>n-1 || col>n-1 || row<0 || col<0){
            throw new java.lang.IndexOutOfBoundsException();
        }

        return qf.find(fromCoordinates(row, col)) == qf.find(topRoot);
    }

    // number of open sites
    public int numberOfOpenSites(){
        return openSites;
    }

    // does the system percolate?
    public boolean percolates(){
        return percolates;
    }

    private Coordinate fromIndex(int index){
        return new Coordinate(index/n, index%n);
    }

    private int fromCoordinates(int row, int column){
        return row*n+column;
    }

    // test client (optional)
    public static void main(String[] args){

    }

    private class Coordinate{
        public int row;
        public int column;

        public Coordinate(int row, int column){
            this.row = row;
            this.column = column;
        }
    }
}