import java.io.File;
import java.util.ArrayList;
import java.util.List;
import data.Dataset;
import data.DatasetVFeatures;
import features.*;
import features.methods.FactoryMethod;
import features.methods.Method.MethodsName;
import timeseries.TimeSeriesLoader;

public class Main {

    public static void main(String[] args) {
        String[] p_datasets_mdi = new String[]{
                "WISDM-MDI-X",
                "UCI-MDI-OVER",
                "UniMiB-MDI",
        };
        String[] p_datasets_mdu = new String[]{
                //"WISDM-MDU",
                "WISDM-MDU-6C",
                "UCI-MDU-OVER",
                "UniMiB-MDU",
        };
        String[] p_datasets = new String[]{
                "UCI",
        };
        String[] p_segmentLenght = new String[]{
                "200",
                "128",
                "151",
        };
        String[] p_sources = new String[]{
                "3",
                "3",
                "3",
        };

        //for(int dataset_i = 0; dataset_i < p_datasets_mdu.length; dataset_i++) {

         // SubjectDependent(p_datasets_mdu[dataset_i], p_segmentLenght[dataset_i], p_sources[dataset_i]);

        //}

        //for(int dataset_i = 0; dataset_i < p_datasets_mdi.length; dataset_i++) {

            //SubjectIndependent(p_datasets_mdi[dataset_i], p_segmentLenght[dataset_i], p_sources[dataset_i]);

        //}
        //for(int dataset_i = 0; dataset_i < p_datasets_mdi.length; dataset_i++) {

            //split21e9(p_datasets[dataset_i], p_segmentLenght[dataset_i+1], p_sources[dataset_i]);

        //}


        timeProcessing();



    }
    public static void timeProcessing(){

        FeatureSet.SelectFeatures featureSet_p = FeatureSet.SelectFeatures.FS4;

        File file = new File(System.getProperty("user.home") + "/datasets/21e9/UCI/TRAIN");

        if(file.exists()){

                int num_sources = 3;
                int segment_length = 128;
                long initial_time = 0;
                long final_time = 0;

                Dataset train = new Dataset("dataset1", num_sources, segment_length);
                train.importDataFromTimeSeriesDataset(file);

                FeatureSet fs = getFeatureSetByName(featureSet_p);

                initial_time = System.currentTimeMillis();
                VectorOfFeatures[] segmentsProcessed = FeaturesTransform.extractFeatures(train.getSegments(), fs);

                System.out.println("\tTime Extract features : \t" + (System.currentTimeMillis() - initial_time) / 1000.0 + " s");


        }

    }

    public static void split21e9(String dataset_name, String segmentLength, String n_sources){

        FeatureSet.SelectFeatures featureSet_p = FeatureSet.SelectFeatures.FS4;

        File d_file = new File(System.getProperty("user.home") + "/datasets/21e9/" + dataset_name);

        if(d_file.exists() && d_file.isDirectory()){



                File dataset_user_train = new File(d_file.getAbsolutePath() + "/TRAIN");
                File dataset_user_test = new File(d_file.getAbsolutePath() + "/TEST");
                String directory_path = System.getProperty("user.home") + "/datasets/transformed/21e9/" + dataset_name + "_" + featureSet_p.toString();
                File dir_file = new File(directory_path);
                dir_file.mkdir();
                //directory_path += "/" + d_file.getName();

                String output_filename = d_file.getName();

                int num_sources = Integer.valueOf(n_sources);
                int segment_length = Integer.valueOf(segmentLength);

                Dataset train = new Dataset(dataset_name.toString() + "/" + d_file.getName(), num_sources, segment_length);
                train.importDataFromTimeSeriesDataset(dataset_user_train);
                FeatureSet fs1 = getFeatureSetByName(featureSet_p);
                VectorOfFeatures[] segmentsProcessed = FeaturesTransform.extractFeatures(train.getSegments(), fs1);
                DatasetVFeatures dVFeatures_train = new DatasetVFeatures(train.getName(), segmentsProcessed);

                dVFeatures_train.writeARFF(directory_path, "TRAIN");

                Dataset test = new Dataset(dataset_name.toString() + "/" + d_file.getName(), num_sources, segment_length);
                test.importDataFromTimeSeriesDataset(dataset_user_test);
                VectorOfFeatures[] segmentsProcessed_test = FeaturesTransform.extractFeatures(test.getSegments(), fs1);
                DatasetVFeatures dVFeatures_test = new DatasetVFeatures(test.getName(), segmentsProcessed_test);


                dVFeatures_test.writeARFF(directory_path, "TEST");

        }

    }

    public static void SubjectIndependent(String dataset_name, String segmentLength, String n_sources){

        FeatureSet.SelectFeatures featureSet_p = FeatureSet.SelectFeatures.FS4;

        File d_file = new File(System.getProperty("user.home") + "/datasets/leave-subject-out/" + dataset_name);

        if(d_file.exists() && d_file.isDirectory()){

            for (File dataset_user_directory : d_file.listFiles()) {

                File dataset_user_train = new File(dataset_user_directory.getAbsolutePath() + "/TRAIN");
                File dataset_user_test = new File(dataset_user_directory.getAbsolutePath() + "/TEST");
                String directory_path = System.getProperty("user.home") + "/datasets/transformed/leave-subject-out/" + dataset_name + "_" + featureSet_p.toString();
                File dir_file = new File(directory_path);
                dir_file.mkdir();
                directory_path += "/" + dataset_user_directory.getName();

                String output_filename = dataset_user_directory.getName();

                int num_sources = Integer.valueOf(n_sources);
                int segment_length = Integer.valueOf(segmentLength);

                Dataset train = new Dataset(dataset_name.toString() + "/" + dataset_user_directory.toString(), num_sources, segment_length);
                train.importDataFromTimeSeriesDataset(dataset_user_train);
                FeatureSet fs1 = getFeatureSetByName(featureSet_p);
                VectorOfFeatures[] segmentsProcessed = FeaturesTransform.extractFeatures(train.getSegments(), fs1);
                DatasetVFeatures dVFeatures_train = new DatasetVFeatures(train.getName(), segmentsProcessed);

                dVFeatures_train.writeARFF(directory_path, "TRAIN");

                Dataset test = new Dataset(dataset_name.toString() + "/" + dataset_user_directory.toString(), num_sources, segment_length);
                test.importDataFromTimeSeriesDataset(dataset_user_test);
                VectorOfFeatures[] segmentsProcessed_test = FeaturesTransform.extractFeatures(test.getSegments(), fs1);
                DatasetVFeatures dVFeatures_test = new DatasetVFeatures(test.getName(), segmentsProcessed_test);


                dVFeatures_test.writeARFF(directory_path, "TEST");
            }
        }

    }

    public static void SubjectDependent(String dataset_name, String segmentLength, String n_sources){

        FeatureSet.SelectFeatures featureSet_p = FeatureSet.SelectFeatures.FS3;

        File d_file = new File(System.getProperty("user.home") + "/datasets/cross-validation-subject/" + dataset_name);

        if(d_file.exists() && d_file.isDirectory()){

            for (File dataset_user : d_file.listFiles()) {

                String directory_path = System.getProperty("user.home") + "/datasets/transformed/cross-validation-subject/" + dataset_name + "_" + featureSet_p.toString();

                String output_filename = dataset_user.getName();

                int num_sources = Integer.valueOf(n_sources);
                int segment_length = Integer.valueOf(segmentLength);

                Dataset d = new Dataset(dataset_name.toString() + "/" + dataset_user.toString(), num_sources, segment_length);
                d.importDataFromTimeSeriesDataset(dataset_user);
                FeatureSet fs1 = getFeatureSetByName(featureSet_p);
                VectorOfFeatures[] segmentsProcessed = FeaturesTransform.extractFeatures(d.getSegments(), fs1);
                DatasetVFeatures dVFeatures = new DatasetVFeatures(d.getName(), segmentsProcessed);


                dVFeatures.writeARFF(directory_path, output_filename);
            }
        }

    }

    public static FeatureSet getFeatureSetByName(FeatureSet.SelectFeatures e) {
        FeatureSet fs = new FeatureSet(e.name());
        MethodsName[] methods = null;
        switch (e){
            case FS1:
                methods = new MethodsName[]{
                        MethodsName.MEAN,
                        MethodsName.STANDARD_DEVIATION,
                        MethodsName.MEDIAN,
                        MethodsName.VARIANCE,
                        MethodsName.ZeroCrossingsRateMean,
                        MethodsName.RootMeanSquare,
                        //MethodsName.MAX,
                        //MethodsName.MIN,
                        //MethodsName.AvgMagnitudeXYZ,
                        //MethodsName.Covariance,
                        //MethodsName.KURTOSIS,
                        //MethodsName.SKEWNESS,
                        //MethodsName.SignalMagnitudeArea,
                        //MethodsName.PearsonCorrelation,




                };
                break;
            case FS2:
                methods = new MethodsName[]{
                        MethodsName.DCComponent,
                        MethodsName.SumOfFFTCoef,
                        MethodsName.SpectralEnergy,
                        MethodsName.InformationEntropy,

                };
                break;
            case FS3:
                methods = new MethodsName[]{
                        MethodsName.MEAN,
                        MethodsName.STANDARD_DEVIATION,
                        MethodsName.MEDIAN,
                        MethodsName.VARIANCE,
                        MethodsName.ZeroCrossingsRateMean,
                        MethodsName.RootMeanSquare,
                        MethodsName.MAX,
                        MethodsName.MIN,

                        MethodsName.DCComponent,
                        MethodsName.SumOfFFTCoef,
                        MethodsName.SpectralEnergy,
                        MethodsName.InformationEntropy,
                };
                break;
            case FS4:
                methods = new MethodsName[]{
                        MethodsName.MEAN,
                        MethodsName.STANDARD_DEVIATION,
                        MethodsName.MEDIAN,
                        MethodsName.VARIANCE,
                        MethodsName.ZeroCrossingsRateMean,
                        MethodsName.RootMeanSquare,
                        MethodsName.MAX,
                        MethodsName.MIN,
                        MethodsName.Range,
                        MethodsName.AvgMagnitudeXYZ,
                        //MethodsName.Covariance,
                        MethodsName.KURTOSIS,
                        MethodsName.SKEWNESS,
                        MethodsName.SignalMagnitudeArea,
                        //MethodsName.PearsonCorrelation,
                        MethodsName.RootMeanSquare, //Freq
                        MethodsName.DCComponent,
                        MethodsName.SumOfFFTCoef,
                        MethodsName.SpectralEnergy,
                        MethodsName.InformationEntropy,
                };
                break;
            default:
                break;
        }
        if (methods != null) {
            for (MethodsName m : methods) {
                fs.addMethod(FactoryMethod.build(m));
            }
        }

        return fs;
    }

}
