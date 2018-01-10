package features.methods.timedomain;

import features.methods.Method;

public class RootMeanSquareMethod extends Method {
    private int num_of_sources = 3;

    @Override
    public double[] extractFeature(final double[][] data) {
        double[] output = new double[this.num_of_sources];

        for(int index_s = 0 ; index_s < num_of_sources; index_s++){
            output[index_s] = calculeRootMeanSquare(data[index_s]);
        }
        return output;
    }

    public double calculeRootMeanSquare(double[] data){

        double sum = 0;
        for (double value: data) {
            sum += Math.pow(value,2);
        }
        return Double.valueOf(Math.sqrt(sum/ data.length));


    }
}
