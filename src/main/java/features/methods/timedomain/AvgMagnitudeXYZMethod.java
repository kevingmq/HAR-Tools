package features.methods.timedomain;

import features.methods.Method;

public class AvgMagnitudeXYZMethod extends Method {

    @Override
    public double[] extractFeature(double[][] data) {
        double[] output = new double[1];
        output[0] = getAvr(getMagnitude(data));

        return output;
    }

    public double[] getMagnitude(double[][] data){

        int count = data[0].length;

        double [] values = new double [count];
        double sum = 0;

        for (int i = 0; i < count; i++){
            sum = ((Math.pow(data[0][i],2)) + (Math.pow(data[1][i],2)) + (Math.pow(data[2][i],2)));
            values[i] = (Math.sqrt(sum));
        }

        return values;
    }

    public double getAvr(double[] data){
        double avr = 0;
        for (int i = 0; i < data.length; i++) {
            avr += data[i];
        }
        avr = avr / data.length;

        return avr;
    }
}
