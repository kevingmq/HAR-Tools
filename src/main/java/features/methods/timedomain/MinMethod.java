package features.methods.timedomain;

import features.methods.Method;

public class MinMethod extends Method {
    private int num_of_sources = 3;

    @Override
    public double[] extractFeature(final double[][] data) {
        double[] output = new double[this.num_of_sources];

        for(int index_s = 0 ; index_s < num_of_sources; index_s++){
            output[index_s] = calculeMin(data[index_s]);
        }
        return output;
    }

    public double calculeMin(double[] data) {
        double output = Double.MAX_VALUE;
        for (double t : data){
            if(t < output){
                output = t;
            }

        }
        return output;
    }
}
