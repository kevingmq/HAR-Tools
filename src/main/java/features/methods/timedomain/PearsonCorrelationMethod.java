package features.methods.timedomain;

import features.methods.Method;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

public class PearsonCorrelationMethod extends Method {

    private int num_sources_to_use = 3;

    @Override
    public double[] extractFeature(double[][] data) {

        //FIX TO MORE of 3 SOURCEs
        double[] output = new double[num_sources_to_use];
        PearsonsCorrelation method = new PearsonsCorrelation();
        output[0] = method.correlation(data[0],data[1]);
        output[1] = method.correlation(data[1],data[2]);
        output[2] = method.correlation(data[2],data[0]);

        return output;
    }

    public void setNum_sources_to_use(int value){
        this.num_sources_to_use = value;
    }

}
