package classification;

import metrics.ConfusionMatrix;
import weka.classifiers.evaluation.Prediction;
import weka.classifiers.trees.RandomForest;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.AbstractClassifier;

import java.util.ArrayList;
import java.util.Random;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import weka.core.Utils;
import weka.classifiers.Classifier;


public class SubjectDependentClassification {

    public static void main(String args[]){

        String[] p_datasets_mdu = new String[]{
                //"WISDM-MDU",
                "UCI-MDU-OVER_FS1",
               // "UniMiB-MDU",
        };

        String[] p_segmentLenght = new String[]{
             //   "200",
                "128",
             //   "151",
        };
        String[] p_sources = new String[]{
             //   "3",
                "3",
             //   "3",
        };

        args = new String[]{
                "-s","1",
                "-x","10",
                "-W","weka.classifiers.trees.RandomForest -do-not-check-capabilities"
        };

        for(int dataset_i = 0; dataset_i < p_datasets_mdu.length; dataset_i++) {

            RunSubjectDependentClassification(p_datasets_mdu[dataset_i], p_segmentLenght[dataset_i], p_sources[dataset_i],args);

        }

    }



    public static void RunSubjectDependentClassification(String dataset_name, String segmentLength, String n_sources, String[] args){

        int num_sources = Integer.valueOf(n_sources);
        int segment_length = Integer.valueOf(segmentLength);


        //Main directory
        File directory_file = new File(System.getProperty("user.home") + "/datasets/transformed/cross-validation-subject/" + dataset_name);

        if(directory_file.exists() && directory_file.isDirectory()){


            // classifier


               RandomForest cls = new RandomForest();

            // other options
                int seed = 1;
                int folds = 10;

            // For each user do
            for (File dataset_user_d : directory_file.listFiles()) {

                try {
                    Instances data = new Instances(new BufferedReader(new FileReader(dataset_user_d)));

                    if(data.classIndex() == -1){
                        data.setClassIndex(data.numAttributes() - 1);
                    }



                    // randomize data
                    Random rand = new Random(seed);
                    Instances randData = new Instances(data);
                    randData.randomize(rand);
                    if (randData.classAttribute().isNominal())
                        randData.stratify(folds);

                    // perform cross-validation


                    for (int n = 0; n < folds; n++) {
                        Instances train = randData.trainCV(folds, n);
                        Instances test = randData.testCV(folds, n);
                        // the above code is used by the StratifiedRemoveFolds filter, the
                        // code below by the Explorer/Experimenter:
                        // Instances train = randData.trainCV(folds, n, rand);

                        // build and evaluate classifier
                        Classifier clsCopy = AbstractClassifier.makeCopy(cls);
                        clsCopy.buildClassifier(train);
                        Evaluation eval = new Evaluation(train);
                        eval.evaluateModel(clsCopy, test);


                        //System.out.println(eval.weightedTruePositiveRate() + "," + eval.weightedPrecision() + "," + eval.weightedRecall() + "," + eval.unweightedMacroFmeasure());
                        ArrayList<Prediction> predictions = eval.predictions();

                        ConfusionMatrix cm = new ConfusionMatrix();

                        for (Prediction p:predictions) {
                            cm.increaseValue(String.valueOf(p.actual()),String.valueOf(p.predicted()));
                        }

                        String name_out = dataset_user_d.getName().substring(0, dataset_user_d.getName().lastIndexOf('.'));
                        System.out.print( name_out + ',');
                        System.out.print(String.valueOf(cm.getAccuracy()) + ",");
                        System.out.print(String.valueOf(cm.getAvgPrecision()) + ",");
                        System.out.print(String.valueOf(cm.getAvgRecall()) + ",");
                        System.out.print(String.valueOf(cm.getMacroFMeasure()) + ",");
                        System.out.print(String.valueOf(cm.getConfidence95Accuracy()) + ",");
                        System.out.print(String.valueOf(cm.getConfidence95MacroFM())+ ",");
                        System.out.print(String.valueOf(cm.getCohensKappa()));
                        System.out.println();

                        //System.out.println(eval.toSummaryString());
                    }


                }catch (Exception e){
                    e.printStackTrace();
                }



            }
        }
    }
}
