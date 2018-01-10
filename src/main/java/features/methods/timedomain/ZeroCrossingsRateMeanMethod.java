package features.methods.timedomain;

import features.methods.FactoryMethod;
import features.methods.Method;

public class ZeroCrossingsRateMeanMethod extends Method{

    private int num_of_sources = 3;

    @Override
    public double[] extractFeature(double[][] data) {
        double[] output = new double[this.num_of_sources];

        for(int index_s = 0 ; index_s < num_of_sources; index_s++){
            output[index_s] = calculeZeroCrossingsRateMean(data[index_s]);
        }
        return output;
    }

    public double calculeZeroCrossingsRateMean(double[] data) {

        MedianMethod mean = new MedianMethod();
        double threhold = mean.calculeMedian(data);
        int numZC=0;
        int size=data.length;

        for (int i=0; i<size-1; i++){
            if((data[i]>=threhold && data[i+1]<threhold) || (data[i]<threhold && data[i+1]>=threhold)){
                numZC++;
            }
        }

        return Double.valueOf(numZC/(size-1));

    }
}
