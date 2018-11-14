import cluster.KNearestNeighbor;
import train.TrainData;

import java.io.File;

public class Main {

    public static void main(String[] args) throws Exception {

        File folder = new File("src/main/resources/unknown");

        TrainData trainData = new TrainData();
        trainData.trainDataAndGetLabels();
        int[] labels = trainData.getBestLabels();

        KNearestNeighbor kNearestNeighbor = new KNearestNeighbor(3);


        for (final File fileEntry : folder.listFiles()) {
            String prediction = kNearestNeighbor.kNearestNeighbor(fileEntry.toString(), labels);
            System.out.println(fileEntry.toString() + ": " + prediction);
        }


    }

}
