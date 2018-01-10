package features;

import java.util.ArrayList;
import java.util.List;

import data.SegmentData;
import features.methods.Method;

public class FeaturesTransform {

	private FeaturesTransform(){}

	public static VectorOfFeatures[] extractFeatures(SegmentData[] trainSamples,
													 FeatureSet featureSet) {
		
		VectorOfFeatures[] vectors = new VectorOfFeatures[trainSamples.length];
		
		//Aqui pode se parallelizar
		for(int index = 0; index < trainSamples.length; index++){
			
			int n_sources = trainSamples[index].getDimensionOfData();
			double[][] data = trainSamples[index].getData();
			VectorOfFeatures vf = new VectorOfFeatures();


			for(Method i_method : featureSet.getAllMethods()){

				double[] result = i_method.extractFeature(data);

				vf.addFeatures(result);

			}
			vf.setLabel(trainSamples[index].getLabel());
			vectors[index] = vf;
		}

		return vectors;
	}


}
