package features.methods.frequencydomain;

import features.methods.Method;
import org.apache.commons.math3.util.FastMath;
import org.jtransforms.fft.DoubleFFT_1D;

public class SumOfFFTCoef extends Method {
    private int num_of_sources = 3;
    private int num_coef = 5;

    @Override
    public double[] extractFeature(double[][] data) {
        double[] output = new double[num_of_sources];

        for(int i = 0; i < num_of_sources; i++){
            output[i] = calculeDCComponent(data[i]);
        }
        return output;
    }
    public double calculeDCComponent(final double[] data) {
        DoubleFFT_1D fft = new DoubleFFT_1D(data.length);
        double[] fft_data = new double[data.length * 2];
        System.arraycopy(data, 0, fft_data, 0, data.length);
        fft.realForwardFull(fft_data);

        double sum = fft_data[0] / data.length;
        for(int i =1; i<num_coef;i++) {
            sum += FastMath.sqrt(FastMath.pow(fft_data[2*i],2) + FastMath.pow(fft_data[2*i+1],2));
        }

        return sum;


    }
}
