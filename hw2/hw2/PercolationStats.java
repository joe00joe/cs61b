package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;


public class PercolationStats {
    private  double mean;
    private double stddev;
    private double confidenceLow;
    private  double confidenceHigh;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf){
        if(N<=0 || T<=0) throw new java.lang.IllegalArgumentException();
        double [] results =new double[T];
        for (int i=0;i<T;i++){
            Percolation p =pf.make(N);
            int openSiteNum=0;
            while(!p.percolates()){
                int row=StdRandom.uniform(0,N);
                int col=StdRandom.uniform(0,N);
                if (!p.isOpen(row,col)){
                    p.open(row,col);
                    openSiteNum+=1;
                }

            }
            results[i]=(double)openSiteNum/(N*N);
        }
        mean=StdStats.mean(results);
        stddev=StdStats.stddev(results);
        confidenceLow=mean-1.96*stddev/Math.sqrt(T);
        confidenceHigh=mean+1.96*stddev/Math.sqrt(T);
    }
    public double mean(){
        return mean;
    }                                           // sample mean of percolation threshold
    public double stddev(){
        return stddev;
    }                                         // sample standard deviation of percolation threshold
    public double confidenceLow(){ return confidenceLow;}// low endpoint of 95% confidence interval
    public double confidenceHigh(){return confidenceHigh;}                                // high endpoint of 95% confidence interval
    /**
    public static void main(String[] args){
        PercolationStats ps=new PercolationStats(50,20,new PercolationFactory());
        System.out.print(ps.mean());
    }
     */
}
