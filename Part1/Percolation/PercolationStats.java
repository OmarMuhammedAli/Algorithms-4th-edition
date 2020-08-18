import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] samples;
    private final int trials;
    /**
     * Performs independent trials on an n-by-n grid and obtain the threshold samples.
     *
     * @param n length of a side of the grid
     * @param trials number of trials performed to then obtain the percolation threshold p*
     * @throws IllegalArgumentException if {n <= 0 && trials <= 0}
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("Both 'n' and 'trials' must be positive integers");
        this.trials = trials;
        samples = new double[trials];
        int row;
        int col;
        Percolation perc;
        for (int i = 0; i < trials; i++) {
            // create new grid to perform on
            perc = new Percolation(n);
            // open sites at random in the grid until it percolates
            while (!perc.percolates()) {
                row = StdRandom.uniform(1, n + 1);
                col = StdRandom.uniform(1, n + 1);
                if (!perc.isOpen(row, col))
                    perc.open(row, col);
            }
            // add the sample to the array of samples for further calculations
            samples[i] = (double) perc.numberOfOpenSites() / (n * n);
        }
    }

    /**
     * Returns the sample mean of the fractions of open sites over the number of trials.
     * @return The sample mean (The Percolation Threshold)
     */
    public double mean() {
        return StdStats.mean(samples);
    }
    public double stddev() {
        return StdStats.stddev(samples);
    }
    public double confidenceLo() {
        return mean() - (1.96 * stddev()) / Math.sqrt(this.trials);
    }
    public double confidenceHi() {
        return mean() + (1.96 * stddev()) / Math.sqrt(this.trials);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, trials);
        StdOut.println("mean                    = " + percolationStats.mean());
        StdOut.println("stddev                  = " + percolationStats.stddev());
        StdOut.println("95% confidence interval = [" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");
    }
}
