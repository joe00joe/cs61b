package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {
    private int period;
    private int state;
    private double factor;
    public  AcceleratingSawToothGenerator(double period,double factor){
        state=0;
        this.period=(int)period;
        this.factor=factor;
    }
    public double next(){
        int sample=state;
        state=(state+1)%period;
        if(state==0){
            period=(int)(factor*period);
        }
        return normalize(sample);
    }
    private double normalize(int sample) {
        return 2 * ((double) (sample - period / 2)) / ((double) period);
    }

}
