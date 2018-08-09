package a01;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private int n;
	private double totalOpen;
	private int totalPercs;
	private int times;
	double[] numOpen;



	public PercolationStats(int N, int T) {
		
		this.n = N;
		//double rand = StdRandom.uniform(0, N - 1);
		numOpen = new double[T];
		Percolation percolation = new Percolation(n);
		double totalNumberOfSites = n*n;

		for (times = 0; times < T; times++) {
			while (percolation.percolates() == false) {
				int r = StdRandom.uniform(N);
				int r2 = StdRandom.uniform(N);
				percolation.open(r, r2);
			}
			totalOpen = (double)percolation.numberOfOpenSites();
			numOpen[times] = totalOpen/totalNumberOfSites;
			percolation = new Percolation(n);		
		}	
	}
		
	public double mean() {
		double mean = StdStats.mean(numOpen);
		return mean;
	}
	
	public double stddev() {
		
		double stdDev = StdStats.stddev(numOpen);
		return stdDev;
	}
	
	public double confidenceLow() {
		
		return mean() - (1.96 * stddev()/ Math.sqrt(times));
	}
	 // low endpoint of 95% confidence interval
	
	
	
	public double confidenceHigh() {
		
		return mean() + (1.96 * stddev()/ Math.sqrt(times));
	}
	 // high endpoint of 95% confidence interval

    public static void main(String[] args) {
    	PercolationStats stats = new PercolationStats(2, 100000);
    	System.out.println("the mean: " + stats.mean());
    	System.out.println("The standard deviation: " + stats.stddev());
    	System.out.println("Confidence Low: " + stats.confidenceLow());
    	System.out.println("Confidence high: " + stats.confidenceHigh());

    }
} // sample standard deviation of percolation threshold
	
	

