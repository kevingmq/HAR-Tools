package data;

import features.VectorOfFeatures;

import java.io.*;
import java.util.*;

import weka.core.*;
import weka.core.converters.ArffSaver;


public class DatasetVFeatures {
    private String name;
    private List<VectorOfFeatures> listOfVFeatures;
    private List<Double> uniqueLabels;

    public DatasetVFeatures(String name) {
        this.name = name;
        this.listOfVFeatures = new ArrayList<>();
    }

    public DatasetVFeatures(String name, VectorOfFeatures[] v) {
        this.name = name;
        this.listOfVFeatures = new ArrayList<>();
        this.uniqueLabels = new ArrayList<>();
        this.setUniqueLabels(v);
        this.setVectorOfFeatures(v);
    }

    public VectorOfFeatures[] getVFeatures() {
        return listOfVFeatures.toArray(new VectorOfFeatures[]{});
    }

    private void setVectorOfFeatures(VectorOfFeatures[] vf) {
        for (VectorOfFeatures v : vf) {
            this.listOfVFeatures.add(v);
        }
    }

    public String getName() {
        return name;
    }

    public void writeARFF(String directory_path, String filename_out){
        try {
            File directory_file = new File(directory_path);
            File saveFile = new File(directory_path + "/" + filename_out + ".arff");
            if (!directory_file.exists()) {

                directory_file.mkdir();
            }
            if (!saveFile.exists()) {
                saveFile.createNewFile();
            }


            FileWriter sFile = new FileWriter(saveFile, false);

            final VectorOfFeatures[] vf = this.getVFeatures();


            PrintWriter pw = new PrintWriter(sFile);
            StringBuilder sb = new StringBuilder();

            int num_fetures = vf[0].getFeatures().length;

            sb.append("@relation ");
            sb.append(filename_out);
            sb.append('\n');
            sb.append('\n');




            for(int i_f = 1; i_f <= num_fetures; i_f++){
                sb.append("@attribute ");
                sb.append("f" + String.valueOf(i_f));
                sb.append(" numeric");
                sb.append('\n');

            }

            sb.append("@attribute class {");

            String[] labels = new String[this.uniqueLabels.size()];
            int i_L = 0;
            for(Double label : this.uniqueLabels){
                labels[i_L] = String.valueOf(label);
                i_L++;
            }
            for(i_L = 0; i_L < labels.length; i_L++){

                sb.append(labels[i_L]);
                if(i_L != (labels.length - 1)){
                    sb.append(',');
                }
            }
            sb.append("}");
            sb.append('\n');
            pw.write(sb.toString());
            sb.setLength(0);

            sb.append('\n');
            sb.append("@DATA");
            sb.append('\n');
            pw.write(sb.toString());
            sb.setLength(0);
            for (VectorOfFeatures v : vf) {



                for (double att : v.getFeatures()) {

                    sb.append(String.valueOf(att));
                    sb.append(',');
                }

                sb.append(String.valueOf(v.getLabel()));

                sb.append('\n');
                pw.write(sb.toString());
                sb.setLength(0);

            }

            pw.close();
        } catch (IOException e) {

        }
    }

    public void writeCSV(String directory_path, String filename_out) {

        try {
            File directory_file = new File(directory_path);
            File saveFile = new File(directory_path + "/" + filename_out);
            if (!directory_file.exists()) {

                directory_file.mkdir();
            }
            if (!saveFile.exists()) {
                saveFile.createNewFile();
            }


            FileWriter sFile = new FileWriter(saveFile, false);

            final VectorOfFeatures[] vf = this.getVFeatures();


            PrintWriter pw = new PrintWriter(sFile);
            StringBuilder sb = new StringBuilder();

            for (VectorOfFeatures v : vf) {

                sb.append(String.valueOf(v.getLabel()));

                for (double att : v.getFeatures()) {
                    sb.append(',');
                    sb.append(String.valueOf(att));
                }

                sb.append('\n');
                pw.write(sb.toString());
                sb.setLength(0);
            }

            pw.close();
        } catch (IOException e) {

        }
    }

    private void setUniqueLabels(VectorOfFeatures[] vf) {
        Set<Double> dict = new HashSet<>();
        for (VectorOfFeatures v : vf) {
            dict.add(Double.valueOf(v.getLabel()));
        }


        for (Double c : dict) {
            this.uniqueLabels.add(c);
        }
        Collections.sort(this.uniqueLabels);

    }
}
