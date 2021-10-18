package lab14;


import lab14lib.Generator;

public class SawToothGenerator implements Generator {
    private int period;
    private int state;

    public SawToothGenerator(int period) {
        state = 0;
        this.period = period;
    }

    public double next() {
        int sample=state;
        state=(state+1)%period;
        return normalize(sample);
    }
    private double normalize(int sample) {
        return 2 * ((double) (sample - period / 2)) / ((double) period);
    }

}
