package utils;


public class Utils {

    public static double getUniformDistribution(double a, double b) {
        return Math.random() * (b - a) + a;
    }

    public static double getExponentialDistribution(double lambda) {
        return -(1 / lambda) * Math.log(1 - Math.random());
    }
}
