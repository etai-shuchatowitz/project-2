package train;

import cluster.KMeans;
import matrix.MatrixUtils;
import preprocess.PreProcess;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrainData {

    private double[][] bestClusters;
    private int[] bestLabels;

    public double[][] getBestClusters() {
        return bestClusters;
    }

    public int[] getBestLabels() {
        return bestLabels;
    }

    public double[][] getTfidfOfTrainedDataAndNewFile(String fileName) throws IOException {
        String extension = "txt";
        String pathName = "src/main/resources/data";
        PreProcess preProcess = new PreProcess();
        PreProcess.fillStopWords();
        Map<String, String> documents = preProcess.kNNPreprocessDocument(extension, pathName, fileName);
        List<String> phrases = preProcess.getAllPhrasesInDocuments(documents);

        MatrixUtils matrixUtils = new MatrixUtils();

        double[][] documentMatrix = matrixUtils.calculateDocumentMatrix(documents, phrases);
        return matrixUtils.convertToTfIdf(documentMatrix, documentMatrix.length, documentMatrix[0].length);
    }

    public void trainDataAndGetLabels() throws IOException {
        String extension = "txt";
        String pathName = "src/main/resources/data";
        PreProcess preProcess = new PreProcess();
        PreProcess.fillStopWords();
        Map<String, String> documents = preProcess.preprocessDocument(extension, pathName);
        List<String> phrases = preProcess.getAllPhrasesInDocuments(documents);

        MatrixUtils matrixUtils = new MatrixUtils();

        double[][] documentMatrix = matrixUtils.calculateDocumentMatrix(documents, phrases);
        double[][] tfidf = matrixUtils.convertToTfIdf(documentMatrix, documentMatrix.length, documentMatrix[0].length);

        Map<Integer, Integer> documentNumberToLabelNumber = matrixUtils.getDocumentNumberToLabelNumber();
        Map<Integer, List<Integer>> folderToListOfIs = matrixUtils.getFolderToListOfIs();

        int k = 3;

        int highestMin = Integer.MIN_VALUE;
        bestLabels = new int[tfidf.length];
        bestClusters = new double[k][tfidf[0].length];

        for (int iter = 0; iter < 10; iter++) {
            KMeans kMeans = new KMeans(tfidf, k, 10, "cosin", documentNumberToLabelNumber, folderToListOfIs);
            kMeans.kmeans();

            int[] labels = kMeans.getLabel();
            double[][] centroids = kMeans.getCentroids();

            Map<Integer, Integer> numberPerLabel = new HashMap<>();


            for (int i = 0; i < labels.length; i++) {
                numberPerLabel.merge(labels[i], 1, Integer::sum);
            }

            int localMin = Integer.MAX_VALUE;

            for (Map.Entry<Integer, Integer> number : numberPerLabel.entrySet()) {
                if (number.getValue() < localMin) {
                    localMin = number.getValue();
                }
            }

            if (localMin > highestMin && numberPerLabel.size() == k) {
                highestMin = localMin;
                bestLabels = labels;
                bestClusters = centroids;
            }
        }
    }
}
