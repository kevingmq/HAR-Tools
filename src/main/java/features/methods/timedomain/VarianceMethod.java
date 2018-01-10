package features.methods.timedomain;

import features.FeatureSet;
import features.methods.Method;

public class VarianceMethod extends Method {

	private int num_of_sources = 3;

	public void setNum_of_sources(int value){
		this.num_of_sources = value;
	}
	@Override
	public double[] extractFeature(final double[][] data){

		double[] output = new double[this.num_of_sources];

		for(int index_s = 0 ; index_s < num_of_sources; index_s++){
			output[index_s] = calculeVariance(data[index_s]);
		}
		return output;
	}
	
	public double calculeVariance(double[] data) {
		MeanMethod meanMethod = new MeanMethod();

		double meanN = meanMethod.calculeMean(data);

		double sum = 0;
		for (double k : data) {
			sum += Math.pow(k - meanN, 2);
		}
		return sum / (data.length - 1); //Bessel's correction
	}

}
