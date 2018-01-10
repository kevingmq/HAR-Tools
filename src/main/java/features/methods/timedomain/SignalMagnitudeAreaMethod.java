package features.methods.timedomain;

import features.methods.Method;
import org.apache.commons.math3.util.FastMath;

public class SignalMagnitudeAreaMethod extends Method {
    @Override
    public double[] extractFeature(final double[][] data) {
        double[] output = new double[1];
        output[0] = calculeSMA(data);
        return output;

    }

    public double calculeSMA(double[][] data){


        double output = 0;
        int sources = data.length;
        int length = data[0].length;

        for(int s = 0; s < sources; s++){
            double sum = 0;
            for(int i = 0; i < length; i++){
                sum += FastMath.abs(data[s][i]);
            }
            output += sum;
        }

        output = output / data.length;

        return output;

    }
}
