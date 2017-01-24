public class Percolation_old {
    public enum SiteState {
        CLOSED,
        OPEN,
        FILLED
    }

    int n;
    SiteState[][] grid;
    boolean precolates = false;
    int openSites = 0;

    public Percolation_old(int n)                // create n-by-n grid, with all sites blocked
    {
        if(n<0) {
            throw new java.lang.IllegalArgumentException();
        }

        this.n = n;
        grid = new SiteState[n][n];

        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                grid[i][j] = SiteState.CLOSED;
            }
        }
    }

    public void open(int row, int col)    // open site (row, col) if it is not open already
    {
        if(row>n || col>n || row<0 || col<0){
            throw new java.lang.IllegalArgumentException();
        }

        if(grid[row][col] == SiteState.CLOSED){
            grid[row][col] = SiteState.OPEN;
            openSites++;
        }

        if(shouldFill(row, col)){
            floodFill(row, col);
        }
    }

    private void floodFill(int row, int col){
        if(row>n-1 || col>n-1 || row<0 || col<0 || grid[row][col] != SiteState.OPEN){
            return;
        }

        grid[row][col]= SiteState.FILLED;
        if(row == n-1){
            precolates = true;
        }
        floodFill(row-1, col);
        floodFill(row+1, col);
        floodFill(row, col-1);
        floodFill(row, col+1);

        return;
    }

    //checks if site should be filled
    private boolean shouldFill(int row, int col){
        if(row>n || col>n || row<0 || col<0){
            return false;
        }

        boolean dir1 = row>0 && grid[row-1][col] == SiteState.FILLED;
        boolean dir2 = row<n-1 && grid[row+1][col] == SiteState.FILLED;
        boolean dir3 = col>0 && grid[row][col-1] == SiteState.FILLED;
        boolean dir4 = col<n-1 && grid[row][col+1] == SiteState.FILLED;

        return dir1||dir2||dir3||dir4||row==0;
    }

    public boolean isOpen(int row, int col)  // is site (row, col) open?
    {
        if(row>n || col>n || row<0 || col<0){
            throw new java.lang.IllegalArgumentException();
        }

        return grid[row][col] == SiteState.OPEN;
    }

    public boolean isFull(int row, int col)  // is site (row, col) full?
    {
        if(row>n || col>n || row<0 || col<0){
            throw new java.lang.IllegalArgumentException();
        }

        return grid[row][col]==SiteState.FILLED;
    }

    public int numberOfOpenSites()       // number of open sites
    {
        return openSites;
    }

    public boolean percolates()              // does the system percolate?
    {
        return precolates;
    }
}