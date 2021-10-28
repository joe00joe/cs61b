import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    public Rasterer() {
        // YOUR CODE HERE



    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        // System.out.println(params);
        Map<String, Object> results = new HashMap<>();
        /**
        System.out.println("Since you haven't implemented getMapRaster, nothing is displayed in "
                           + "your browser.");

         */
        double  queryULlon=params.get("ullon");
        double  queryULlat=params.get("ullat");
        double  queryLrlon=params.get("lrlon");
        double  queryLrlat=params.get("lrlat");
        double  queryW=params.get("w");
        double  queryH=params.get("h");

        double  queryLonDPP=(queryLrlon-queryULlon)/queryW;
        int depth= getDepth(queryLonDPP);

        results=getQuery(queryULlon,queryULlat,queryLrlon,queryLrlat,depth);


        return results;

    }

    private int getDepth(double queryLonDPP){
        int curDepth=0;
        while(curDepth<7){
            double curLonDPP=(MapServer.ROOT_LRLON-MapServer.ROOT_ULLON)/(Math.pow(2,curDepth)*MapServer.TILE_SIZE);
            if(curLonDPP<queryLonDPP){
                return curDepth;
            }
            curDepth++;
        }
        return curDepth;
    }

    private  Map<String, Object> getQuery(double queryULlon,double queryULlat,
                                double queryLrlon,double queryLrlat,int depth){
        Map<String, Object> results = new HashMap<>();
        if(queryLrlon<queryULlon || queryLrlat>queryULlat){
            results.put("query_success", false);
            return results;
        }
        double lonLenPerImg=Math.abs(MapServer.ROOT_LRLON-MapServer.ROOT_ULLON)/Math.pow(2,depth);
        double latLenPerImg=Math.abs(MapServer.ROOT_LRLAT-MapServer.ROOT_ULLAT)/Math.pow(2,depth);
        int colUL=(int)((queryULlon-MapServer.ROOT_ULLON)/lonLenPerImg);
        int rowUL=(int)((MapServer.ROOT_ULLAT-queryULlat)/latLenPerImg);
        int colLR=(int)((queryLrlon-MapServer.ROOT_ULLON)/lonLenPerImg);
        int rowLR=(int)((MapServer.ROOT_ULLAT-queryLrlat)/latLenPerImg);
        boolean isQSeccuss=false;
        String[][] grid=new String[rowLR-rowUL+1][colLR-colUL+1];
        for(int row=rowUL;row<=rowLR;row++){
            for(int col=colUL;col<=colLR;col++){
                if(validIdx(row,col,(int)Math.pow(2,depth))){
                    grid[row-rowUL][col-colUL]="d"+depth+"_"+"x"+col+"_"+"y"+row+".png";
                    isQSeccuss=true;
                }
            }
        }

        double ullon=MapServer.ROOT_ULLON+colUL*lonLenPerImg;
        double ullat=MapServer.ROOT_ULLAT-rowUL*latLenPerImg;
        double lrlon=MapServer.ROOT_ULLON+(colLR+1)*lonLenPerImg;
        double lrlat=MapServer.ROOT_ULLAT-(rowLR+1)*latLenPerImg;
        results.put("render_grid", grid);
        results.put("raster_ul_lon", ullon);
        results.put("raster_ul_lat", ullat);
        results.put("raster_lr_lon", lrlon);
        results.put("raster_lr_lat", lrlat);
        results.put("depth", depth);
        results.put("query_success", isQSeccuss);
        return results;

    }
    private  boolean validIdx(int row,int col,int len){
        return row>=0 && row<=len-1 && col>=0 && col<len-1;
    }

}

