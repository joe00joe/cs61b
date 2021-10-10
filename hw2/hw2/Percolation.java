package hw2;


import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private  boolean [][] grid;
    private  WeightedQuickUnionUF wqf;
    private  int gridSides;
    private  int topSite;
    private  int bottomSite;
    private  int openSites;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N){
      if(N<=0){ throw new java.lang.IllegalArgumentException();}
      grid=new boolean[N][N];
      gridSides=N;
      wqf=new WeightedQuickUnionUF(N*N+2);
      topSite=N*N;
      bottomSite=N*N+1;
      openSites=0;
      for(int i=0;i<N;i++){
          for (int j=0;j<N;j++){
              grid[i][j]=false;
          }
      }
      for(int i=0;i<N;i++){
          wqf.union(topSite,xyto1D(0,i));
      }
      for(int i=0;i<N;i++){
          wqf.union(bottomSite,xyto1D(N-1,i));
      }
    }
    // open the site (row, col) if it is not open already
    public void open(int row, int col){
        if(row<0 && row>gridSides-1 || col<0 && col>gridSides-1){
            throw new java.lang.IndexOutOfBoundsException();
        }
        if(!isOpen(row,col)){
            grid[row][col]=true;
            openSites+=1;
            if(col-1>=0 && isOpen(row,col-1)){
                wqf.union(xyto1D(row,col-1),xyto1D(row,col));
            }
            if(col+1<=gridSides-1 && isOpen(row,col+1)){
                wqf.union(xyto1D(row,col+1),xyto1D(row,col));
            }
            if(row-1>=0 && isOpen(row-1,col)){
                wqf.union(xyto1D(row-1,col),xyto1D(row,col));
            }
            if(row+1<=gridSides-1 && isOpen(row+1,col)){
                wqf.union(xyto1D(row+1,col),xyto1D(row,col));
            }
        }
    }
    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        if(row<0 && row>gridSides-1 || col<0 && col>gridSides-1){
            throw new java.lang.IndexOutOfBoundsException();
        }
        return grid[row][col];

    }
    public boolean isFull(int row, int col){
        if(row<0 && row>gridSides-1 || col<0 && col>gridSides-1){
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (isOpen(row,col)){
            return wqf.connected(topSite,xyto1D(row,col));
        }
        else{
            return false;
        }
     }  // is the site (row, col) full?
    public int numberOfOpenSites(){
        return openSites;
    }           // number of open sites
    public boolean percolates(){
        return wqf.connected(topSite,bottomSite);
    }             // does the system percolate?
    private int xyto1D(int i, int j)
    {
        return i*gridSides+j;
    }
    // use for unit testing (not required)
    public static void main(String[] args){
        Percolation p=new Percolation(10);
        for(int i=0;i<10;i++){
            p.open(0,i);
        }
        System.out.print(p.percolates());

    }


}


