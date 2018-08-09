package a01;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * @author Dillon Embreus
 * @author Timothy Lawrence
 * @category Percolation
 * @version Last modified 2/17/18
 */

public class Percolation {
	private int n = 0;//number of row and columns
	private int[][] site;//n*n grid
	private int blocked;//block the sites
	private int open = 1;//open the sites
	private int last;//last integer in the row or column
	private int top;//virtual top site
	private int bottom;//virtual bottom site
	private int count;//count of sites opened
	WeightedQuickUnionUF wquuf, wquuf2;//WeightedQuickUnionUF object that has a virtual bottom and one that doesn't

	/**
	 * creates a grid with N amount of rows and columns. Sets the sites state to blocked
	 * initializes the grids, the virtual top and virtual bottom
	 * @param N number of rows and columns
	 */
	public Percolation(int N) {
		try{
			this.n = N;
			site = new int[n][n];
			wquuf = new WeightedQuickUnionUF(n*n+2);
			wquuf2 = new WeightedQuickUnionUF(n*n+1);
			last  = n - 1;
			blocked = 0;
			top = n * n;
			bottom = n * n + 1;
			for(int i = 0; i < last; i++) {
				for(int j = 0; j < last; j++) {
					site[i][j] = blocked;
				}
			}
		}catch(IllegalArgumentException e) {
			System.out.println("illegal argument");
		}
	}
	/**
	 * Opens the site[i][j] if it is not open already, converts it into a 1d array, and then unions it with any open sites that it 
	 * touches. If its on the top row it unions it with the virtual top site. If it is on the bottom row it unions it with the virtual 
	 * bottom site.
	 * @param i rows
	 * @param j columns
	 */
	public void open(int i, int j) {
		try {
		int q = xyTo1d(i, j);
		int left = q-1;
		int right = q+1;
		int down = q+n;
		int up = q-n;
		
			if(!isOpen(i, j) && !isFull(i,j)) {
				site[i][j] = open;
				count++;
				if(i == 0) {
					wquuf.union(q, top);
					wquuf2.union(q, top);
				}
				//union the left site with the current site
				if(j != 0 && site[i][j-1] != blocked) {
					wquuf.union(q, left);
					wquuf2.union(q, left);
				}
				//union the right site with the current site
				if(j != last && site[i][j+1] != blocked) {
					wquuf.union(q, right);
					wquuf2.union(q, right);
				}
				//union the site above with the current site
				if(i != 0 && site[i-1][j] != blocked) {
					wquuf.union(q, up);
					wquuf2.union(q, up);
				}
				//union the site below with the current site
				if(i != last && site[i+1][j] != blocked) {
					wquuf.union(q,down);
					wquuf2.union(q,down);
				}
				//Check the bottom row
				if(i == last) {
					wquuf.union(q, bottom);
				}
			}
		}catch(IndexOutOfBoundsException e) {
			System.out.println("out of bounds");
		}
	}
	/**
	 * Is site (row i, column j) open?
	 * @param i row
	 * @param j column
	 * @return boolean
	 */
	public boolean isOpen(int i, int j) {
		try {
			if(site[i][j] == open) {
				return true;
			}
		}catch(IndexOutOfBoundsException e) {
			System.out.println("out of range");
		}
		return false;
	}
	/**
	 * Is site (row i, column j) full?
	 * If the site is connected to the top for both of the WeightedQuickUnionUF objects then it is considered filled.
	 * @param i row
	 * @param j column
	 * @return boolean
	 */
	public boolean isFull(int i, int j) {
		try {
			int q = xyTo1d(i, j);
			return wquuf.connected(top, q) && wquuf2.connected(top,q);
		}catch(IndexOutOfBoundsException e) {
			System.out.println("out of ranged");
		}
		return false;
	}
	/**
	 * Does the system percolate?
	 * @return boolean whether or not the top and bottom are connected.
	 */
	public boolean percolates() {
		return wquuf.connected(bottom, top);
	}
	/**
	 * Returns the amount of sites that have been opened.
	 * @return count
	 */
	public int numberOfOpenSites() {
		return count;
	}
	/**
	 * Converts the two dimensional array into one dimensional.
	 * @param x the rows
	 * @param y the columns
	 * @return index
	 */
	public int xyTo1d(int x, int y) {
		int index = x * n + y;
		return index;
	}


}
