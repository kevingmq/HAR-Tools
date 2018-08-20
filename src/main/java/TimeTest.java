import data.Dataset;
import features.FeatureSet;
import features.FeaturesTransform;
import features.VectorOfFeatures;
import metrics.ConfusionMatrix;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.evaluation.Prediction;
import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class TimeTest {
    public static void main(String[] args) {

        String home = System.getProperty("user.home") + "/datasets/21e9";

        File dir = new File(home);

        File train = new File(dir.getAbsolutePath() + "/" + "UCI" + "/TRAIN");

        File test = new File(dir.getAbsolutePath() + "/" + "UCI" + "/TEST");

        int num_sources = 3;
        int segment_length = 128;

        Dataset trainDataset = new Dataset("train", num_sources, segment_length);
        trainDataset.importDataFromTimeSeriesDataset(train);

        Dataset testDataset = new Dataset("test", num_sources, segment_length);
        testDataset.importDataFromTimeSeriesDataset(test);

        long startTime = System.currentTimeMillis();

        FeatureSet fs = Main.getFeatureSetByName(FeatureSet.SelectFeatures.FS3);
        VectorOfFeatures[] segmentsProcessed = FeaturesTransform.extractFeatures(trainDataset.getSegments(), fs);

        double time_extract_train = (System.currentTimeMillis() - startTime) / 1000.0;
        //System.out.println("\tTime Extract Features to Train: \t" + time_extract_train + " s");


        startTime = System.currentTimeMillis();

        VectorOfFeatures[] segmentsProcessedTest = FeaturesTransform.extractFeatures(testDataset.getSegments(), fs);

        double time_extract_test = (System.currentTimeMillis() - startTime) / 1000.0;
        System.out.println("\tTime Extract Features: \t" + time_extract_test + " s");


        ArrayList<Attribute> attInfo = new ArrayList<>();
        for (int i = 0; i < segmentsProcessed[0].getLength(); i++) {
            attInfo.add(new Attribute("f" + (i + 1)));
        }
        ArrayList<String> labls = new ArrayList<>();
        labls.add("1");
        labls.add("2");
        labls.add("3");
        labls.add("4");
        labls.add("5");
        labls.add("6");
        attInfo.add(new Attribute("class", labls));


        //Train pre-processin
        Instances data_train = new Instances("train", attInfo, 0);
        Instances data_test = new Instances("test", attInfo, 0);

        for (int i_sample = 0; i_sample < segmentsProcessed.length; i_sample++) {

            double[] temp = segmentsProcessed[i_sample].getFeatures();
            double[] att = Arrays.copyOf(temp,attInfo.size());
            att[attInfo.size()-1] = segmentsProcessed[i_sample].getLabel()-1;
            data_train.add(new DenseInstance(1.0,att));

        }
        data_train.setClassIndex(data_train.numAttributes() - 1);


        for (int i_sample = 0; i_sample < segmentsProcessedTest.length; i_sample++) {

            double[] temp = segmentsProcessedTest[i_sample].getFeatures();
            double[] att = Arrays.copyOf(temp,attInfo.size());
            att[attInfo.size()-1] = segmentsProcessedTest[i_sample].getLabel()-1;
            data_test.add(new DenseInstance(1.0,att));

        }
        data_test.setClassIndex(data_train.numAttributes() - 1);

        // classifier
        startTime = System.currentTimeMillis();
        try {
            //J48 cls = new J48();
            //BayesNet cls = new BayesNet();
            //IBk cls = new IBk();
            //cls.setKNN(1);
            SMO cls = new SMO();
            //RandomForest cls = new RandomForest();

            ConfusionMatrix cm = new ConfusionMatrix();


            // perform classification
            // build and evaluate classifier
            Classifier clsCopy = AbstractClassifier.makeCopy(cls);
            clsCopy.buildClassifier(data_train);

            System.out.println("\tTime Train: \t" + (time_extract_train + (System.currentTimeMillis() - startTime) / 1000.0) + " s");


            // Classification

            startTime = System.currentTimeMillis();

            Evaluation eval = new Evaluation(data_train);
            eval.evaluateModel(clsCopy, data_test);

            System.out.println("\tTime Classify: \t" + (System.currentTimeMillis() - startTime) / 1000.0 + " s");


            System.out.println(eval.weightedTruePositiveRate() + "," + eval.weightedPrecision() + "," + eval.weightedRecall() + "," + eval.unweightedMacroFmeasure());
            //ArrayList<Prediction> predictions = eval.predictions();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

