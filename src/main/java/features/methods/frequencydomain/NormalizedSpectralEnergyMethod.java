package features.methods.frequencydomain;

import features.methods.Method;
import org.apache.commons.math3.util.FastMath;
import org.jtransforms.fft.DoubleFFT_1D;

//Power Spectral Density
public class NormalizedSpectralEnergyMethod extends Method {
    private int num_of_sources = 3;

    @Override
    public double[] extractFeature(double[][] data) {
        double[] output = new double[this.num_of_sources];

        for(int index_s = 0 ; index_s < num_of_sources; index_s++){
            output[index_s] = calculeNormalizedSpectralEnergy(data[index_s]);
        }
        return output;
    }

    public double calculeNormalizedSpectralEnergy(double[] data) {

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
        //Spectral energy
        sum = 0;
        for (int i = 0; i < data.length; i++) {
            sum += FastMath.pow(fft_normalized[i],2);
        }

        return sum;
    }

}
