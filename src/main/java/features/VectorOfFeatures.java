package features;

import java.util.ArrayList;
import java.util.List;

public class VectorOfFeatures {


	private List<Double> features;
	private double label;

	public VectorOfFeatures(){
		this.features = new ArrayList<>();
	}
	
	public double[] getFeatures() {

		int size = features.size();
		double[] output = new double[size];
		for(int i = 0; i < size; i++){
			output[i] = features.get(i).doubleValue();
		}
		return output;

	}
	public void addFeatures(double[] features) {
		int size = features.length;

		for(int i = 0; i < size; i++){
			this.features.add(Double.valueOf(features[i]));
		}
	}
	public double getLabel() {
		return label;
	}
	public void setLabel(double label) {
		this.label = label;
	}
	public int getLength(){
		return features.size();

	}



}
