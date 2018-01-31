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
                "WISDM-MDI",
                "UCI-MDI-OVER",
                "UniMiB-MDI",
        };
        String[] p_datasets_mdu = new String[]{
                "WISDM-MDU",
                "UCI-MDU-OVER",
                "UniMiB-MDU",
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

        for(int dataset_i = 0; dataset_i < p_datasets_mdu.length; dataset_i++) {

          //  SubjectDependent(p_datasets_mdu[dataset_i], p_segmentLenght[dataset_i], p_sources[dataset_i]);

        }

        for(int dataset_i = 0; dataset_i < p_datasets_mdi.length; dataset_i++) {

           SubjectIndependent(p_datasets_mdi[dataset_i], p_segmentLenght[dataset_i], p_sources[dataset_i]);

        }


    }
    public static void SubjectIndependent(String dataset_name, String segmentLength, String n_sources){

        File d_file = new File(System.getProperty("user.home") + "/datasets/leave-subject-out/" + dataset_name);

        if(d_file.exists() && d_file.isDirectory()){

            for (File dataset_user_directory : d_file.listFiles()) {

                File dataset_user_train = new File(dataset_user_directory.getAbsolutePath() + "/TRAIN");
                File dataset_user_test = new File(dataset_user_directory.getAbsolutePath() + "/TEST");
                String directory_path = System.getProperty("user.home") + "/datasets/transformed/leave-subject-out/" + dataset_name + "_" + FeatureSet.SelectFeatures.FS1.toString();
                File dir_file = new File(directory_path);
                dir_file.mkdir();
                directory_path += "/" + dataset_user_directory.getName();

                String output_filename = dataset_user_directory.getName();

                int num_sources = Integer.valueOf(n_sources);
                int segment_length = Integer.valueOf(segmentLength);

                Dataset train = new Dataset(dataset_name.toString() + "/" + dataset_user_directory.toString(), num_sources, segment_length);
                train.importDataFromTimeSeriesDataset(dataset_user_train);
                FeatureSet fs1 = getFeatureSetByName(FeatureSet.SelectFeatures.FS1);
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

        File d_file = new File(System.getProperty("user.home") + "/datasets/cross-validation-subject/" + dataset_name);

        if(d_file.exists() && d_file.isDirectory()){

            for (File dataset_user : d_file.listFiles()) {

                String directory_path = System.getProperty("user.home") + "/datasets/transformed/cross-validation-subject/" + dataset_name + "_" + FeatureSet.SelectFeatures.FS1.toString();

                String output_filename = dataset_user.getName();

                int num_sources = Integer.valueOf(n_sources);
                int segment_length = Integer.valueOf(segmentLength);

                Dataset d = new Dataset(dataset_name.toString() + "/" + dataset_user.toString(), num_sources, segment_length);
                d.importDataFromTimeSeriesDataset(dataset_user);
                FeatureSet fs1 = getFeatureSetByName(FeatureSet.SelectFeatures.FS1);
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
                        MethodsName.MAX,
                        MethodsName.MIN,
                        MethodsName.STANDARD_DEVIATION,
                        MethodsName.MEDIAN,
                        MethodsName.RootMeanSquare,
                        MethodsName.AvgMagnitudeXYZ,
                        MethodsName.Covariance,
                        MethodsName.KURTOSIS,
                        MethodsName.SKEWNESS,
                        MethodsName.MEDIAN,
                        MethodsName.SignalMagnitudeArea,
                        MethodsName.PearsonCorrelation,
                        MethodsName.ZeroCrossingsRateMean,
                        MethodsName.VARIANCE,


                };
                break;
            case FS2:
                methods = new MethodsName[]{
                        MethodsName.DCComponent,
                        MethodsName.SpectralEnergy,
                        MethodsName.InformationEntropy,

                };
                break;
            case FS3:
                methods = new MethodsName[]{
                        MethodsName.MEAN,
                        MethodsName.MAX,
                        MethodsName.MIN,
                        MethodsName.STANDARD_DEVIATION,
                        MethodsName.MEDIAN,
                        MethodsName.RootMeanSquare,
                        MethodsName.AvgMagnitudeXYZ,
                        MethodsName.Covariance,
                        MethodsName.KURTOSIS,
                        MethodsName.SKEWNESS,
                        MethodsName.MEDIAN,
                        MethodsName.SignalMagnitudeArea,
                        MethodsName.PearsonCorrelation,
                        MethodsName.ZeroCrossingsRateMean,
                        MethodsName.VARIANCE,

                        MethodsName.DCComponent,
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

    public static void DoSegmentationgProcess(String[] datasets, String[] lenghts, String[] sources) {

        boolean DEBUG = true;
        String home = System.getProperty("user.home") + "/datasets/";
        File dir = new File(home);

        for (int parm_index = 0; parm_index < datasets.length; parm_index++) {

            File d = new File(dir.getAbsolutePath() + "/" + datasets[parm_index]);

            //System.out.println("dataset,userId,numSources,samples,accuracy,precision,recall,f-measure");

            if (d.exists() && d.isDirectory()) {

                int num_sources = Integer.valueOf(sources[parm_index]);
                int segment_length = Integer.valueOf(lenghts[parm_index]);

                TimeSeriesLoader.DEBUG = DEBUG;

                int countUsers = 0;

                for (File userFile : d.listFiles()) {
                    if (userFile.exists() && userFile.isDirectory()) {

                     //   File trainFile = new File(dir.getAbsolutePath() + "/" + s + "/" + userFile.getName() + "/TRAIN");
                       // File testFile = new File(dir.getAbsolutePath() + "/" + s + "/" + userFile.getName() + "/TEST");

                       // Dataset dataset_train = new Dataset(datasets[parm_index] + userFile.getName());
                     //   dataset_train.importDataFromTimeSeriesDataset(trainFile);

                        //Select witch features will be extract

                        List<FactoryMethod> listOfFeaturesMethods = new ArrayList<>();

                        // Add Mean
                       // listOfFeaturesMethods.add(FactoryMethod.build("Mean"));

                        //Add StandardDeviantion
                      //  listOfFeaturesMethods.add(FactoryMethod.build("StandardDeviantion"));

                        // Create vetor of features
                      //  VectorOfFeatures[] trainFeatures = Features.extractFeatures(trainSamples, listOfFeaturesMethods);


                        countUsers++;
                        if (DEBUG) {
                            // System.out.println(result.outputString);
                        }

                    }

                }
            }
        }
    }

}
