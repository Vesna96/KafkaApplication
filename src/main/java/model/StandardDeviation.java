package model;

import java.util.List;

public class StandardDeviation {

    double sum = 0.0;
    double standardDeviation = 0.0;
    double mean = 0.0;
    double res = 0.0;
    double sq = 0.0;


    public double calculate(List<Double> arr)
    {

        int n = arr.size();

        for (int i = 0; i < n; i++) {
            sum = sum + arr.get(i);
        }

        mean = sum / (n);

        for (int i = 0; i < n; i++) {

            standardDeviation = standardDeviation + Math.pow((arr.get(i) - mean), 2);

        }

        sq = standardDeviation / n;
        res = Math.sqrt(sq);
        return res;
    }
}

