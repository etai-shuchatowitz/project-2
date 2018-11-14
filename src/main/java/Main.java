import cluster.KNearestNeighbor;
import matrix.MatrixUtils;
import model.StatData;
import train.TrainData;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws Exception {

        File folder = new File("src/main/resources/unknown");

        Map<Integer, String> integerToFolderName = new HashMap<>();
        integerToFolderName.put(0, "C1");
        integerToFolderName.put(1, "C4");
        integerToFolderName.put(2, "C7");

        TrainData trainData = new TrainData();
        trainData.trainDataAndGetLabels();
        int[] labels = trainData.getBestLabels();



        int[] actualLabels = {0, 0, 2, 0, 1, 1, 2, 2, 1, 1};
        int[] predictedLabels = new int[actualLabels.length];


        int count = 0;
        for (final File fileEntry : folder.listFiles()) {
            KNearestNeighbor kNearestNeighbor = new KNearestNeighbor(5, 3);
            int prediction = kNearestNeighbor.kNearestNeighbor(fileEntry.toString(), labels);
            predictedLabels[count] = prediction;
            Map<Integer, Float> fuzzyResults = kNearestNeighbor.getPercentages();
            System.out.println(fileEntry.toString());
            for (Map.Entry<Integer, Float> fuzzyEntry : fuzzyResults.entrySet()) {
                System.out.println(integerToFolderName.get(fuzzyEntry.getKey()) + ": " + fuzzyEntry.getValue());
            }
            count++;
        }



        int[][] confusionMatrix = MatrixUtils.generateConfusionMatrix(predictedLabels, actualLabels, 3);

        System.out.println("Printing confusion matrix");
        System.out.println("------------------------------------");
        for (int i = 0; i < confusionMatrix.length; i++) {
            for (int j = 0; j < confusionMatrix[0].length; j++) {
                System.out.print(confusionMatrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("------------------------------------");

        StatData[] statDatas = MatrixUtils.getPrecisionAndRecall(confusionMatrix);
        for (StatData statData : statDatas) {
            System.out.println("vals are: " + statData);
        }



    }

}
