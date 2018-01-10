package features.methods.timedomain;

import features.methods.Method;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class KurtosisMethod extends Method {

    private int num_of_sources = 3;

    @Override
    public double[] extractFeature(double[][] data) {

        double[] output = new double[this.num_of_sources];

        for(int index_s = 0 ; index_s < num_of_sources; index_s++){
            output[index_s] = calculeKurtosis(data[index_s]);
        }
        return output;
    }

    public double calculeKurtosis(double[] data) {
        DescriptiveStatistics dataset = new DescriptiveStatistics(data);
        return  dataset.getKurtosis();
    }
}
