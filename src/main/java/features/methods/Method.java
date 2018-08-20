package features.methods;

import features.FeatureSet;

public abstract class Method {

	public abstract double[] extractFeature(final double[][] data);

	public enum MethodsName{
		AvgMagnitudeXYZ,
		Covariance,
		KURTOSIS,
		MAX,
		MEAN,
		MEDIAN,
		MIN,
		PearsonCorrelation,
		RootMeanSquare,
		SignalMagnitudeArea,
		SKEWNESS,
		STANDARD_DEVIATION,
		VARIANCE,
		ZeroCrossingsRateMean,
		Range,
		//Frequency
		DCComponent,
		SumOfFFTCoef,
		SpectralEnergy,
		InformationEntropy,


		//To implemente


		PeakToPeakSignalValue,
		PeakToPeakTime,
		PeakToPeakSlope,



	}
}
