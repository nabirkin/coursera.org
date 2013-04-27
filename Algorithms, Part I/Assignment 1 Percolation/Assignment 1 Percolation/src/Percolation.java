public class Percolation {
	private WeightedQuickUnionUF percolated;
	private WeightedQuickUnionUF filled;
	private boolean[][] grid;
	private int N;

	public Percolation(int N) {

		this.N = N;
		this.grid = new boolean[N][N];
		this.percolated = new WeightedQuickUnionUF(N * N + 2);
		this.filled = new WeightedQuickUnionUF(N * N + 2);
	}

	public void open(int i, int j) {

		if (!isOpen(i, j)) {

			grid[i - 1][j - 1] = true;

			int p = to1DIndex(i, j);

			if (j > 1 && isOpen(i, j - 1)) {
				percolated.union(p, p - 1);
				filled.union(p, p - 1);
			}

			if (j < N && isOpen(i, j + 1)) {
				percolated.union(p, p + 1);
				filled.union(p, p + 1);
			}

			if (i > 1 && isOpen(i - 1, j)) {
				percolated.union(p, p - N);
				filled.union(p, p - N);
			}

			if (i < N && isOpen(i + 1, j)) {
				percolated.union(p, p + N);
				filled.union(p, p + N);
			}

			// union virtual top site and first row
			if (i == 1) {
				percolated.union(0, p);
				filled.union(0, p);
			}
			// union virtual botton site and last row
			if (i == N)
				percolated.union(p, N * N + 1);

		}
	}


	private int to1DIndex(int i, int j) {
		return (i - 1) * N + j;
	}

	public boolean isOpen(int i, int j) {
		return grid[i - 1][j - 1];
	}

	public boolean isFull(int i, int j) {
		return isOpen(i, j) && filled.connected(0, to1DIndex(i, j));
	}

	public boolean percolates() {
		return percolated.connected(0, N * N + 1);
	}

}
