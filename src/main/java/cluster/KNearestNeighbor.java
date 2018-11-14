package cluster;

import distance.DistanceUtil;
import matrix.MatrixUtils;
import train.TrainData;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class KNearestNeighbor {

    private int k;
    private int numClusters;
    private TrainData trainData = new TrainData();
    private Map<Integer, Float> percentages = new HashMap<>();

    public KNearestNeighbor(int k, int numClusters) {
        this.k = k;
        this.numClusters = numClusters;
    }

    public Map<Integer, Float> getPercentages() {
        return percentages;
    }

    public int kNearestNeighbor(String fileName, int[] label) throws IOException {

        double[][] tfidf = trainData.getTfidfOfTrainedDataAndNewFile(fileName);

        MatrixUtils.write2DMatrixToCSV(tfidf, "matrix");

        Map<Double, Integer> distancesToLabel = new TreeMap<>(Collections.reverseOrder());

        for (int i = 0; i < label.length; i++) {
            distancesToLabel.put(DistanceUtil.cosinDistance(tfidf[i], tfidf[tfidf.length - 1]), label[i]);
        }

        int count = 0;
        TreeMap<Integer, Integer> kBestLabels = new TreeMap<>(Collections.reverseOrder());
        for (Map.Entry<Double, Integer> entry : distancesToLabel.entrySet()) {
            if (count < k) {
                kBestLabels.merge(entry.getValue(), 1, Integer::sum);
            } else {
                break;
            }
            count++;
        }

        for (Map.Entry<Integer, Integer> entry : kBestLabels.entrySet()) {
            float percent = (float) entry.getValue() / (float) k;
            percentages.put(entry.getKey(), percent);
        }

        return kBestLabels.firstKey();
    }
}
