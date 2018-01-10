package features.methods.timedomain;

import features.FeatureSet;
import features.methods.Method;

public class StandardDeviationMethod extends Method {

	private int num_of_sources = 3;

	@Override
	public double[] extractFeature(final double[][] data) {
		double[] output = new double[this.num_of_sources];

		for(int index_s = 0 ; index_s < num_of_sources; index_s++){
			output[index_s] = calculeStandardDeviation(data[index_s]);
		}
		return output;
	}
	
	public double calculeStandardDeviation(double[] data) {
		VarianceMethod varianceMethod = new VarianceMethod();
		double result = varianceMethod.calculeVariance(data);
		return Math.sqrt(result);
	}

}
