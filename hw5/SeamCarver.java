import java.awt.*;
import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private  Picture picture;
    private  int width;
    private  int height;

    public SeamCarver(Picture picture){
        this.picture=picture;
        this.width=picture.width();
        this.height=picture.height();
    }

    public Picture picture(){
        return picture;
    }                       // current picture

    public     int width(){
        return width;
    }                         // width of current picture

    public     int height(){
        return height;
    }                     // height of current picture

    public  double energy(int x, int y){
        if(!vaildIndex(x,y)){
            throw new java.lang.IndexOutOfBoundsException();
        }
        double eX= energyX(x,y);
        double eY=energyY(x,y);
        return eX+eY;
    }           // energy of pixel at column x and row y

    private  double energyX(int x,int y){

        Color left=picture.get(Math.floorMod(x-1,width),y);
        Color right=picture.get(Math.floorMod(x+1,width),y);
        double rx= Math.abs(left.getRed()-right.getRed());
        double gx= Math.abs(left.getGreen()-right.getGreen());
        double bx= Math.abs(left.getBlue()-right.getBlue());
        return rx*rx+gx*gx+bx*bx;
    }

    private double energyY(int x,int y){
        Color upper=picture.get(x,Math.floorMod(y-1,height));
        Color bottom=picture.get(x,Math.floorMod(y+1,height));
        double ry=Math.abs(upper.getRed()-bottom.getRed());
        double gy=Math.abs(upper.getGreen()-bottom.getGreen());
        double by=Math.abs(upper.getBlue()-bottom.getBlue());
        return ry*ry+gy*gy+by*by;
    }

    private  boolean vaildIndex(int x,int y){
        return x>=0 && x<=width-1 && y>=0 && y<=height-1;

    }

    public   int[] findHorizontalSeam(){
        transpose();
        int[] ans = findVerticalSeam();
        transpose();
        return ans;
    }           // sequence of indices for horizontal seam

    private void transpose() {
        
        Picture temp = new Picture(height, width);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                temp.set(row, col, picture.get(col, row));
            }
        }

        picture = temp;
        int t = width;
        this.width = height;
        this.height = t;
    }

    public   int[] findVerticalSeam(){
        double[][] minCost=new double[height][width];
        int [] res= new int[height];
        // initial minCost
        for(int i=0;i<width;i++){
            minCost[0][i]=energy(i,0);
        }

        for(int h=1;h<height;h++){
            for (int w=0;w<width;w++){
                double energy=energy(w,h);
                minCost[h][w]=energy+getMin(minCost,h,w);
            }
        }
        double min=Double.MAX_VALUE;
        int minIdx=0;
        for(int w=0;w<width;w++){
            if(minCost[height-1][w]<min){
                min=minCost[height-1][w];
                minIdx=w;
            }
        }
        res[height-1]=minIdx;
        for(int h=height-2;h>=0;h--){
            if(minIdx-1>=0 && minCost[h][minIdx-1]==min-energy(minIdx,h+1)){
                min=minCost[h][minIdx-1];
                minIdx=minIdx-1;
            }
            if(minCost[h][minIdx]==min-energy(minIdx,h+1)){
                min=minCost[h][minIdx];
                minIdx=minIdx;
            }
            if(minIdx+1<=width && minCost[h][minIdx+1]==min-energy(minIdx,h+1)){
                min=minCost[h][minIdx+1];
                minIdx=minIdx+1;
            }
            res[h]=minIdx;
        }
        return res;

    }              // sequence of indices for vertical seam

    private  double getMin(double[][] minCost,int h,int w) {
        if(w==0){
            return Math.min(minCost[h-1][w],minCost[h-1][w+1]);
        }
        if(w==width-1){
            return Math.min(minCost[h-1][w-1],minCost[h-1][w]);
        }
        double min=Math.min(minCost[h-1][w],minCost[h-1][w-1]);
        return Math.min(min,minCost[h-1][w+1]);
    }

    public    void removeHorizontalSeam(int[] seam){
        if(checkSeam(seam)&& seam.length==width){
            this.picture=new Picture(SeamRemover.removeHorizontalSeam(this.picture,seam));
        }
        else{
            throw new java.lang.IllegalArgumentException();
        }
    }   // remove horizontal seam from picture

    public    void removeVerticalSeam(int[] seam){
        if(checkSeam(seam) && seam.length==height){
            this.picture = new Picture(SeamRemover.removeVerticalSeam(this.picture, seam));
        }
        else{
            throw new java.lang.IllegalArgumentException();
        }
    }     // remove vertical seam from picture

    private boolean checkSeam(int[] seam) {
        int len = seam.length;
        if (len <= 1) {
            return true;
        }
        for (int i = 1; i < len; i++) {
            if (Math.abs(seam[i] - seam[i - 1]) > 1) {
                return false;
            }
        }
        return true;
    }

}
