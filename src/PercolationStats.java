import static edu.princeton.cs.algs4.StdRandom.uniform;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] results;
    private int trials;

    public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
    {
        if(n<=0 || trials <= 0){
            throw new IllegalArgumentException();
        }
        this.trials = trials;
        results = new double[trials];
        for(int i=0; i<trials; i++){
            Percolation percolation = new Percolation(n);
            while(!percolation.percolates())
            {
                int row = uniform(n);
                int col = uniform(n);
                percolation.open(row+1, col+1);
            }

            results[i] = (double)percolation.numberOfOpenSites()/(n*n);
        }
    }

    public double mean()                          // sample mean of percolation threshold
    {
        return StdStats.mean(results);
    }

    public double stddev()                        // sample standard deviation of percolation threshold
    {
        return StdStats.stddev(results);
    }

    public double confidenceLo()                  // low  endpoint of 95% confidence interval
    {
        return mean()-((1.96*stddev())/Math.sqrt(trials));
    }

    public double confidenceHi()                  // high endpoint of 95% confidence interval
    {
        return mean()+((1.96*stddev())/Math.sqrt(trials));
    }

    public static void main(String[] args)        // test client (described below)
    {
        new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
    }
}