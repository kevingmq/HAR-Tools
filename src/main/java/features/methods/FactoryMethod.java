package features.methods;

import features.methods.Method.MethodsName;
import features.methods.frequencydomain.DCComponetMethod;
import features.methods.frequencydomain.SpectralEntropyMethod;
import features.methods.frequencydomain.NormalizedSpectralEnergyMethod;
import features.methods.frequencydomain.SumOfFFTCoef;
import features.methods.timedomain.*;

public class FactoryMethod {

	private FactoryMethod(){}

	public static Method build(MethodsName string) {

		switch (string){
			case AvgMagnitudeXYZ:
				return new AvgMagnitudeXYZMethod();
			case Covariance:
				return new CovarianceMethod();
			case KURTOSIS:
				return new KurtosisMethod();
			case MAX:
				return new MaxMethod();
			case MEAN:
				return new MeanMethod();
			case MEDIAN:
				return new MedianMethod();
			case MIN:
				return new MinMethod();
			case Range:
				return new RangeMethod();
			case PearsonCorrelation:
				return new PearsonCorrelationMethod();
			case RootMeanSquare:
				return new RootMeanSquareMethod();
			case SignalMagnitudeArea:
				return new SignalMagnitudeAreaMethod();
			case SKEWNESS:
				return new SkewnessMethod();
			case STANDARD_DEVIATION:
				return new StandardDeviationMethod();
			case VARIANCE:
				return new VarianceMethod();
			case ZeroCrossingsRateMean:
				return new ZeroCrossingsRateMeanMethod();

				//Frequency
			case DCComponent:
				return new DCComponetMethod();
			case SumOfFFTCoef:
				return new SumOfFFTCoef();
			case SpectralEnergy:
				return new NormalizedSpectralEnergyMethod();
			case InformationEntropy:
				return new SpectralEntropyMethod();

		}//TO DO: rising exception here
		return null;
	}

}
