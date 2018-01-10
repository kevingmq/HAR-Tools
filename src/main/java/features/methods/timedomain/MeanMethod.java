package features.methods.timedomain;

import features.methods.Method;

public class MeanMethod extends Method {

	private int num_sources_to_read = 3;

	public void setNum_sources_to_read(int value){
		this.num_sources_to_read = value;
	}

	@Override
	public double[] extractFeature(final double[][] data) {

		double[] output = new double[this.num_sources_to_read];
		for(int index_s = 0; index_s<this.num_sources_to_read; index_s++){
			double[] read = data[index_s];
			output[index_s] = calculeMean(read);
		}
		return output;

	}
	
	public double calculeMean(double[] data) {
		double sum = 0;
		for (double k : data) {
			sum += k;
		}
		return sum / data.length; 
	}

}
