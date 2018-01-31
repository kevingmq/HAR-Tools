package classification;

import metrics.ConfusionMatrix;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.Prediction;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;


public class SubjectIndependentClassification {

    public static void main(String args[]) {

        String[] p_datasets_mdu = new String[]{
                //"WISDM-MDU",
                "UCI-MDI-OVER_FS1",
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
                "-s", "1",
                "-x", "10",
                "-W", "weka.classifiers.trees.RandomForest -do-not-check-capabilities"
        };

        for (int dataset_i = 0; dataset_i < p_datasets_mdu.length; dataset_i++) {

            RunSubjectIndependentClassification(p_datasets_mdu[dataset_i], args);

        }

    }


    public static void RunSubjectIndependentClassification(String dataset_name, String[] args) {


        //Main directory
        File directory_file = new File(System.getProperty("user.home") + "/datasets/transformed/leave-subject-out/" + dataset_name);

        if (directory_file.exists() && directory_file.isDirectory()) {


            // classifier


            RandomForest cls = new RandomForest();

            // other options
            int seed = 1;
            int folds = 10;

            // For each user do
            for (File dataset_user_d : directory_file.listFiles()) {

                try {
                    File dataset_user_train = new File(dataset_user_d.getAbsolutePath() + "/TRAIN.arff");
                    File dataset_user_test = new File(dataset_user_d.getAbsolutePath() + "/TEST.arff");
                    Instances data_train = new Instances(new BufferedReader(new FileReader(dataset_user_train)));
                    Instances data_test = new Instances(new BufferedReader(new FileReader(dataset_user_test)));

                    if (data_train.classIndex() == -1) {
                        data_train.setClassIndex(data_train.numAttributes() - 1);
                        data_test.setClassIndex(data_test.numAttributes() - 1);
                    }


                    // perform classification
                    // build and evaluate classifier
                    Classifier clsCopy = AbstractClassifier.makeCopy(cls);
                    clsCopy.buildClassifier(data_train);
                    Evaluation eval = new Evaluation(data_train);
                    eval.evaluateModel(clsCopy, data_test);


                    //System.out.println(eval.weightedTruePositiveRate() + "," + eval.weightedPrecision() + "," + eval.weightedRecall() + "," + eval.unweightedMacroFmeasure());
                    ArrayList<Prediction> predictions = eval.predictions();

                    ConfusionMatrix cm = new ConfusionMatrix();

                    for (Prediction p : predictions) {
                        cm.increaseValue(String.valueOf(p.actual()), String.valueOf(p.predicted()));
                    }

                    String name_out = dataset_user_d.getName();
                    System.out.print(name_out + ',');
                    System.out.print(String.valueOf(cm.getAccuracy()) + ",");
                    System.out.print(String.valueOf(cm.getAvgPrecision()) + ",");
                    System.out.print(String.valueOf(cm.getAvgRecall()) + ",");
                    System.out.print(String.valueOf(cm.getMacroFMeasure()) + ",");
                    System.out.print(String.valueOf(cm.getConfidence95Accuracy()) + ",");
                    System.out.print(String.valueOf(cm.getConfidence95MacroFM()) + ",");
                    System.out.print(String.valueOf(cm.getCohensKappa()));
                    System.out.println();

                    //System.out.println(eval.toSummaryString());

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }
    }
}
