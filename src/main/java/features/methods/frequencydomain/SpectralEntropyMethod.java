package features.methods.frequencydomain;

import features.methods.Method;
import org.apache.commons.math3.util.FastMath;
import org.jtransforms.fft.DoubleFFT_1D;

public class SpectralEntropyMethod extends Method {

    private int num_of_sources = 3;
    private int withDCComponent = 0;

    public void setNum_of_sources(int value){
        this.num_of_sources = value;
    }

    @Override
    public double[] extractFeature(final double[][] data) {
        double[] output = new double[num_of_sources];

        for(int i = 0; i < data.length; i++) {

           output[i] = calculeSpectralEntropy(data[i]);


        }
        return output;
    }

    public double calculeSpectralEntropy(double[] data) {

        DoubleFFT_1D fft = new DoubleFFT_1D(data.length);
        double[] fft_data = new double[data.length * 2];
        System.arraycopy(data, 0, fft_data, 0, data.length);
        fft.realForwardFull(fft_data);


        //calc sum of magnitudes and the fft normalized
        double sum = 0;
        double[] fft_magnitudes = new double[data.length];
        for(int i = 0; i < data.length; i++){
            fft_magnitudes[i] = FastMath.sqrt(FastMath.pow(fft_data[2*i],2) + FastMath.pow(fft_data[2*i+1],2));
            sum += fft_magnitudes[i];
        }

        double[] fft_normalized = new double[data.length];


        for(int i = 0; i < data.length; i++) {
            fft_normalized[i] = fft_magnitudes[i]/sum;
        }


        double sum2 = 0;

        for(int c = withDCComponent; c < data.length; c++){

            sum2 -= fft_normalized[c] * FastMath.log(10, fft_normalized[c]);
        }


        return sum2;
    }
}
