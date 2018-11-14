package distance;

public class DistanceUtil {

    public static double cosinDistance(double[] x, double[] y) {

        double dot = 0;
        double magA = 0;
        double magB = 0;

        for (int i = 0; i < x.length; i++) {
            dot += x[i] * y[i];
            magA += x[i] * x[i];
            magB += y[i] * y[i];
        }

        magA = Math.sqrt(magA);
        magB = Math.sqrt(magB);

        double cosin_similarity = dot / (magA * magB);

//        System.out.println("cosin_similarity is " + cosin_similarity);

        return cosin_similarity;
    }

    public static double euclideanDistance(double[] x, double[] y) {
        double sum = 0;

        for (int i = 0; i < x.length; i++) {
            double value = x[i] - y[i];
            sum += value * value;
        }

        return Math.sqrt(sum);
    }
}
