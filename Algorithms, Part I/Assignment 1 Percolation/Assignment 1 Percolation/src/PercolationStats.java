public class PercolationStats {

	private double thresholds[];
	// perform T independent computational experiments on an N-by-N grid
	public PercolationStats(int N, int T) {
		if(N<=0 || T<=0) 
			throw new IllegalArgumentException("N="+N+" should be more than 0 " +
					"and T="+T+" should be more than 0");
		
		this.thresholds = new double[T];
		for (int i = 0; i < T; i++) {
			Percolation p = new Percolation(N);
			int opened = 0;
			while(!p.percolates()) {
				int x = 1 + StdRandom.uniform(N);
				int y = 1 + StdRandom.uniform(N);
				if(!p.isOpen(x, y)) {
					p.open(x, y);
					opened++;
				}
			}
			
			thresholds[i] = (double)opened/(N*N);
		}
	}

	// sample mean of percolation threshold
	public double mean() {
		return StdStats.mean(thresholds);
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		return StdStats.stddev(thresholds);
	}

	// returns lower bound of the 95% confidence interval
	public double confidenceLo() {
		return mean() - 1.96*stddev()/Math.sqrt(thresholds.length);
	}

	// returns upper bound of the 95% confidence interval
	public double confidenceHi() {
		return mean() + 1.96*stddev()/Math.sqrt(thresholds.length);
	}

	// test client, described below
	public static void main(String[] args) {
		Stopwatch stopwatch = new Stopwatch();
		
		PercolationStats stats = new PercolationStats(2, 10000);
		System.out.println(stopwatch.elapsedTime());
	        
        StdOut.printf("mean\t %f\n", stats.mean());
        StdOut.printf("stddev\t %f\n", stats.stddev());
        StdOut.printf("95%% confidence interval\t %f %f", stats.confidenceLo(), stats.confidenceHi());
	}
}
