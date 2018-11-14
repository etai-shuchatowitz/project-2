import train.TrainData;

public class Main {

    public static void main(String[] args) throws Exception {

        TrainData trainData = new TrainData();
        trainData.trainData();
        double[][] tfidf = trainData.getTfidf();
        double[][] centroids = trainData.getBestClusters();
        int[] labels = trainData.getBestLabels();


    }

}
