package features.methods.timedomain;

import features.methods.Method;
import org.apache.commons.math3.util.DoubleArray;

import java.util.Arrays;
import java.util.concurrent.atomic.DoubleAccumulator;

public class MedianMethod extends Method {
    private int num_of_sources = 3;

    @Override
    public double[] extractFeature(final double[][] data) {
        double[] output = new double[this.num_of_sources];

        for(int index_s = 0 ; index_s < num_of_sources; index_s++){
            output[index_s] = calculeMedian(data[index_s]);
        }
        return output;
    }

    public double calculeMedian(double[] data){

        Arrays.sort(data);
        double median;
        int length = data.length;

        if (length % 2 == 0) {
            median = Double.valueOf((data[length / 2] + data[length / 2 - 1]) / 2);
        }
        else {
            median = Double.valueOf(data[length / 2]);
        }

        return median;

    }
}
