import java.util.ArrayList;
import java.util.List;

import utils.Utils;

public class Test {

    public static void main(String[] args) {
        System.out.println("----------Случайные числа от 50 до 100, равномерное распределение----------");
        List<Double> uniformList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            uniformList.add(Utils.getUniformDistribution(50, 100));
        }
        uniformList.sort(null);
        for (Double d : uniformList) {
            System.out.println(d);
        }

        System.out.println("----------Случайные числа экспоненциального распределения с лямбда = 0.1----------");
        List<Double> exponentialList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            exponentialList.add(Utils.getExponentialDistribution(0.1));
        }
        exponentialList.sort(null);
        for (Double d : exponentialList) {
            System.out.println(d);
        }
    }
}
