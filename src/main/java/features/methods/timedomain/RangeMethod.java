package features.methods.timedomain;

import features.methods.Method;

public class RangeMethod extends Method {
    @Override
    public double[] extractFeature(double[][] data) {
        double[] output = new double[data.length];
        for(int i = 0; i < data.length; i++){
            output[i] = calculeRange(data[i]);
        }
        return output;
    }

    public double calculeRange(double[] data){
        MaxMethod maxMethod = new MaxMethod();
        MinMethod minMethod = new MinMethod();

        double maxValue= maxMethod.calculeMax(data);
        double minValue = minMethod.calculeMin(data);

        return maxValue - minValue;
    }
}
