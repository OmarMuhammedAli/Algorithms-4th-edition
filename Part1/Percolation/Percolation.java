import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final int  TOP = 0;
    private final WeightedQuickUnionUF grid;
    private final WeightedQuickUnionUF full;
    private boolean[][] openSite;
    private final int bottom;
    private final int length;
    private int count = 0;

    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("'n' must be a positive integer");
        grid = new WeightedQuickUnionUF((n * n) + 2);
        full = new WeightedQuickUnionUF(n * n + 1);
        bottom = n * n + 1;
        length = n;
        openSite = new boolean[length][length];
    }

    public void open(int row, int col) {
        if (row <= 0 || col <= 0 || row > length || col > length)
            throw new IllegalArgumentException("'row' and 'col' must be confined within [1, n]");
        if (isOpen(row, col)) return;
        openSite[row - 1][col - 1] = true;
        if (row == 1) {
            grid.union(TOP, getIndex(row, col));
            full.union(TOP, getIndex(row, col));
        }
        if (row == length)
            grid.union(bottom, getIndex(row, col));
        if (col > 1 && isOpen(row, col - 1)) {
            grid.union(getIndex(row, col), getIndex(row, col - 1));
            full.union(getIndex(row, col), getIndex(row, col - 1));
        }
        if (col < length && isOpen(row, col + 1)) {
            grid.union(getIndex(row, col), getIndex(row, col + 1));
            full.union(getIndex(row, col), getIndex(row, col + 1));
        }
        if (row > 1 && isOpen(row - 1, col)) {
            grid.union(getIndex(row, col), getIndex(row - 1, col));
            full.union(getIndex(row, col), getIndex(row - 1, col));
        }
        if (row < length && isOpen(row + 1, col)) {
            grid.union(getIndex(row, col), getIndex(row + 1, col));
            full.union(getIndex(row, col), getIndex(row + 1, col));
        }
        count++;
    }
    public boolean isOpen(int row, int col) {
        if (row <= 0 || col <= 0 || row > length || col > length)
            throw new IllegalArgumentException("'row' and 'col' must be confined within [1, n]");
        return openSite[row - 1][col - 1];
    }
    public boolean isFull(int row, int col) {
        if (row <= 0 || col <= 0 || row > length || col > length)
            throw new IllegalArgumentException("'row' and 'col' must be confined within [1, n]");
        return full.find(TOP) == full.find(getIndex(row, col));
    }
    public int numberOfOpenSites() {
        return count;
    }
    public boolean percolates() {
        return grid.find(TOP) == grid.find(bottom);
    }
    private int getIndex(int row, int col) {
        return length * (row - 1) + col;
    }
}
