package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dataset {
    private String name;
    private int num_sources;
    private int segment_length;
    private List<SegmentData> segments;


    public Dataset(String name, int num_sources, int segment_length){
        segments = new ArrayList<>();
        this.num_sources = num_sources;
        this.segment_length = segment_length;
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
        public SegmentData[] getSegments(){
        return segments.toArray(new SegmentData[]{});
    }

    public static boolean isNonEmptyColumn(String column) {
        return column != null && !"".equals(column) && !"NaN".equals(column) && !"\t".equals(column);
    }

    public void importDataFromTimeSeriesDataset(File file){

        if(file.exists() && file.canRead()){

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line = null;


                while ((line = br.readLine()) != null) {


                    String[] columns = line.split(",");
                    double[][] data = new double[num_sources][segment_length];


                    Double label = null;

                    // first is the label
                    int i = 0;
                    for (; i < columns.length; i++) {
                        String column = columns[i].trim();
                        if (isNonEmptyColumn(column)) {
                            label = Double.valueOf(column);
                            break;
                        }
                    }

                    // next the data
                    int idSource = 0;
                    int j = 0;
                    for (i = i + 1; i < columns.length && idSource < num_sources; i++) {
                        String column = columns[i].trim();
                        try {
                            if (isNonEmptyColumn(column)) {
                                if (j == segment_length) {
                                    idSource++;
                                    j = 0;
                                }
                                if (idSource < num_sources) {
                                    data[idSource][j++] = Double.parseDouble(column);
                                }
                            }
                        } catch (NumberFormatException nfe) {
                            nfe.printStackTrace();
                        }
                    }

                    SegmentData s = new SegmentData(data, label);
                    this.segments.add(s);


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
