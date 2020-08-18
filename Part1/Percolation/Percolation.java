import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF grid;
    private boolean[][] openSite;
    private final int  top = 0;
    private final int bottom;
    private final int length;
    private int count = 0;

    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("'n' must be a positive integer");
        grid = new WeightedQuickUnionUF((n * n) + 2);
        bottom = n * n + 1;
        length = n;
        openSite = new boolean[length][length];
    }

    public void open(int row, int col) {
        if (isOpen(row, col)) return;
        openSite[row - 1][col - 1] = true;
        if (row == 1)
            grid.union(top, getIndex(row, col));
        if (row == length)
            grid.union(bottom, getIndex(row, col));
        if (col > 1 && isOpen(row, col - 1))
            grid.union(getIndex(row, col), getIndex(row, col - 1));
        if (col < length && isOpen(row, col + 1))
            grid.union(getIndex(row, col), getIndex(row, col + 1));
        if (row > 1 && isOpen(row - 1, col))
            grid.union(getIndex(row, col), getIndex(row - 1, col));
        if (row < length && isOpen(row + 1, col))
            grid.union(getIndex(row, col), getIndex(row + 1, col));
        count++;
    }
    public boolean isOpen(int row, int col) {
        return openSite[row - 1][col - 1];
    }
    public boolean isFull(int row, int col) {
        if (0 < row && row <= length && 0 < col && col <= length) {
            return grid.find(top) == grid.find(getIndex(row, col));
        } else {
            throw new IndexOutOfBoundsException();
        }
    }
    public int numberOfOpenSites() {
        return count;
    }
    public boolean percolates() {
        return grid.find(top) == grid.find(bottom);
    }
    private int getIndex(int row, int col) {
        return length * (row - 1) + col;
    }

    public static void main(String[] args) {
        Percolation perc = new Percolation(10);
        perc.open(1, 1);
        perc.open(2, 1);
        perc.open(3, 1);
        perc.open(4, 1);
        perc.open(5, 1);
        System.out.println(perc.percolates());
        System.out.println(perc.numberOfOpenSites());
    }

}
