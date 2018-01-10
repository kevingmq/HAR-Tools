// Copyright (c) 2016 - Patrick Schäfer (patrick.schaefer@zib.de)
// Distributed under the GLP 3.0 (See accompanying file LICENSE)
package timeseries;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.carrotsearch.hppc.DoubleArrayList;

public class TimeSeriesLoader {

    public static boolean DEBUG = false;

    /**
     * Loads the time series from a csv-file of the UCR time series archive.
     *
     * @param dataset
     * @return
     */
    public static TimeSeries[] loadDataset(String dataset) {
        return loadDataset(new File(dataset));
    }

    /**
     * Loads the time series from a csv-file of the UCR time series archive.
     *
     * @param dataset
     * @return
     */
    public static TimeSeries[] loadDataset(File dataset) {
        ArrayList<TimeSeries> samples = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(dataset))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("@")) {
                    continue;
                }
                String[] columns = line.split(" ");
                double[] data = new double[columns.length];
                int j = 0;
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
                for (i = i + 1; i < columns.length; i++) {
                    String column = columns[i].trim();
                    try {
                        if (isNonEmptyColumn(column)) {
                            data[j++] = Double.parseDouble(column);
                        }
                    } catch (NumberFormatException nfe) {
                        nfe.printStackTrace();
                    }
                }
                if (j > 0) {
                    TimeSeries ts = new TimeSeries(Arrays.copyOfRange(data, 0, j), label);
                    ts.norm();
                    samples.add(ts);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (DEBUG) {
            System.out.println("Done reading from " + dataset + " samples " + samples.size() + " queryLength " + samples.get(0).getLength());
        }
        return samples.toArray(new TimeSeries[]{});
    }

    public static MultiVariateTimeSeries[] loadMultivariateDataset(File[] datasets, File labels) throws FileNotFoundException {
        int numOfInstancias = 0;
        int numSources = datasets.length;
        ArrayList<String> labelsForEachWindowResult = new ArrayList<String>();
        ArrayList<MultiVariateTimeSeries> instancias = new ArrayList<MultiVariateTimeSeries>();

        try (BufferedReader br = new BufferedReader(new FileReader(labels))) {
            String line = null;
            String[] labelsForEachWindow = null;

            while ((line = br.readLine()) != null) {
                labelsForEachWindow = line.split(",");
                
                for (int i = 0; i < labelsForEachWindow.length; i++) {
                    String column = labelsForEachWindow[i].trim();
                    try {
                        if (isNonEmptyColumn(column)) {
                            labelsForEachWindowResult.add(column);
                        }
                    } catch (NumberFormatException nfe) {
                        nfe.printStackTrace();
                    }
                }
                numOfInstancias = labelsForEachWindowResult.size();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Signals
        BufferedReader[] readers = new BufferedReader[numSources];

        for (int sourceId = 0; sourceId < numSources; sourceId++) {

            readers[sourceId] = new BufferedReader(new FileReader(datasets[sourceId]));

        }

        try {
            for (int iLine = 0; iLine < numOfInstancias; iLine++) {
                double[][] lines = new double[numSources][];
                for (int ireaders = 0; ireaders < numSources; ireaders++) {
                    String line = readers[ireaders].readLine();
                    if (line != null) {
                        String[] columns = line.split(",");
                        double[] dataOfOneLine = new double[columns.length];
                        int j = 0;
                        for (int i = 0; i < columns.length; i++) {
                            String column = columns[i].trim();
                            try {
                                if (isNonEmptyColumn(column)) {
                                    dataOfOneLine[j++] = Double.parseDouble(column);
                                }
                            } catch (NumberFormatException nfe) {
                                nfe.printStackTrace();
                            }
                        }
                        lines[ireaders] = dataOfOneLine;
                    }
                }
                TimeSeries[] tsdata = new TimeSeries[numSources];
                for (int i = 0; i < numSources; i++) {
                    tsdata[i] = new TimeSeries(Arrays.copyOfRange(lines[i], 0, lines[i].length));
                }

                MultiVariateTimeSeries tsMD = new MultiVariateTimeSeries(tsdata, Double.parseDouble(labelsForEachWindowResult.get(iLine)));
                instancias.add(tsMD);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println("Done reading from " + dataset + " samples " + samples.size() + " length " + samples.get(0).getLength());
        return instancias.toArray(new MultiVariateTimeSeries[]{});

    }

    @SuppressWarnings("unchecked")
	public static MultiVariateTimeSeries[] loadMultivariateDatset(
            File dataset, boolean derivatives) throws IOException {

        List<MultiVariateTimeSeries> samples = new ArrayList<>();
        List<Double>[] mts = null;
        int lastId = -1;

        try (BufferedReader br = new BufferedReader(new FileReader(dataset))) {
            String line = null;
            double label = -1;
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(" ");

                // id
                int id = Integer.valueOf(columns[0].trim());
                if (id != lastId) {
                    addMTS(samples, mts, label);

                    // initialize
                    lastId = id;
                    mts = new ArrayList[columns.length - 3];
                    for (int i = 0; i < mts.length; i++) {
                        mts[i] = new ArrayList<>();
                    }
                    // label
                    label = Double.valueOf(columns[2].trim());
                }

                // timeStamp
        /* int timeStamp = Integer.valueOf(columns[1].trim()); */

                // the data
                for (int dim = 0; dim < columns.length - 3; dim++) { // all dimensions
                    String column = columns[dim + 3].trim();
                    try {
                        double d = Double.parseDouble(column);
                        mts[dim].add(d);
                    } catch (NumberFormatException nfe) {
                        nfe.printStackTrace();
                    }
                }
            }

            addMTS(samples, mts, label);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (DEBUG) {
            System.out.println("Done reading from " + dataset
                    + " samples " + samples.size()
                    + " dimensions " + samples.get(0).getDimensions());
        }
        MultiVariateTimeSeries[] m = samples.toArray(new MultiVariateTimeSeries[]{});
        return (derivatives) ? getDerivatives(m) : m;
    }

    protected static MultiVariateTimeSeries[] getDerivatives(MultiVariateTimeSeries[] mtsSamples) {
        for (MultiVariateTimeSeries mts : mtsSamples) {
            TimeSeries[] deltas = new TimeSeries[2 * mts.timeSeries.length];
            TimeSeries[] samples = mts.timeSeries;
            for (int a = 0; a < samples.length; a++) {
                TimeSeries s = samples[a];
                double[] d = new double[s.getLength() - 1];
                for (int i = 1; i < s.getLength(); i++) {
                    d[i - 1] = s.getData()[i] - s.getData()[i - 1];
                }
                deltas[2 * a] = samples[a];
                deltas[2 * a + 1] = new TimeSeries(d, mts.getLabel());
            }
            mts.timeSeries = deltas;
        }
        return mtsSamples;
    }

    protected static void addMTS(List<MultiVariateTimeSeries> samples, List<Double>[] mts, double label) {
        if (mts != null && mts[0].size() > 0) {
            TimeSeries[] dimensions = new TimeSeries[mts.length];
            for (int i = 0; i < dimensions.length; i++) {
                double[] rawdata = new double[mts[i].size()];
                int j = 0;
                for (double d : mts[i]) {
                    rawdata[j++] = d;
                }
                dimensions[i] = new TimeSeries(rawdata, label);
            }
            samples.add(new MultiVariateTimeSeries(dimensions, label));
        }
    }

    public static TimeSeries readSampleSubsequence(File dataset) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(dataset))) {
            DoubleArrayList data = new DoubleArrayList();
            String line = null;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                String[] values = line.split("[ \\t]");
                if (values.length > 0) {
                    for (String value : values) {
                        try {
                            value = value.trim();
                            if (isNonEmptyColumn(value)) {
                                data.add(Double.parseDouble(value));
                            }
                        } catch (NumberFormatException nfe) {
                            // Parse-Exception ignored
                        }
                    }
                }
            }
            return new TimeSeries(data.toArray());
        }
    }

    public static TimeSeries[] readSamplesQuerySeries(String dataset) throws IOException {
        return readSamplesQuerySeries(new File(dataset));
    }

    public static TimeSeries[] readSamplesQuerySeries(File dataset) throws IOException {
        List<TimeSeries> samples = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(dataset))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                DoubleArrayList data = new DoubleArrayList();
                line = line.trim();
                String[] values = line.split("[ \\t]");
                if (values.length > 0) {
                    for (String value : values) {
                        try {
                            value = value.trim();
                            if (isNonEmptyColumn(value)) {
                                data.add(Double.parseDouble(value));
                            }
                        } catch (NumberFormatException nfe) {
                            // Parse-Exception ignored
                        }
                    }
                    samples.add(new TimeSeries(data.toArray()));
                }
            }
        }
        return samples.toArray(new TimeSeries[]{});
    }

    public static boolean isNonEmptyColumn(String column) {
        return column != null && !"".equals(column) && !"NaN".equals(column) && !"\t".equals(column);
    }

    public static TimeSeries generateRandomWalkData(int maxDimension, Random generator) {
        double[] data = new double[maxDimension];

        // Gaussian Distribution
        data[0] = generator.nextGaussian();

        for (int d = 1; d < maxDimension; d++) {
            data[d] = data[d - 1] + generator.nextGaussian();
        }

        return new TimeSeries(data);
    }

    public static MultiVariateTimeSeries[] loadMultivariateDataset(File dataset, int num_sources, int segment_length) {
        ArrayList<MultiVariateTimeSeries> samples = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(dataset))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("@")) {
                    continue;
                }

                String[] columns = line.split(",");


                double[][] data = new double[num_sources][segment_length];
                int idSource = 0;
                int j = 0;
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
                ArrayList<TimeSeries> tempArray = new ArrayList<>();
                for (idSource = 0; idSource < num_sources; idSource++) {
                    TimeSeries ts = new TimeSeries(Arrays.copyOf(data[idSource], segment_length), label);
                    ts.norm();
                    tempArray.add(ts);
                }

                MultiVariateTimeSeries r = new MultiVariateTimeSeries(tempArray.toArray(new TimeSeries[]{}), label);
                samples.add(r);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (DEBUG) {
            System.out.println("Done reading from " + dataset + " samples " + samples.size() + " queryLength " + samples.get(0).getLength() + " num_sources " + num_sources);
        }
        return samples.toArray(new MultiVariateTimeSeries[]{});
    }
}
