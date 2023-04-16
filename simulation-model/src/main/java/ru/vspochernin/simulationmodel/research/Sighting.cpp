package ru.vspochernin.simulationmodel.research;

import ru.vspochernin.simulationmodel.simulation.Config;

public class Sighting {

    private static Config config;

    private static int getNewN(double p) {
        double t = 1.643;
        double sigma = 0.1;

        return (int) ((t * t * (1 - p)) / (p * sigma * sigma));
    }

    private static OutputCharacteristics getOutputCharacteristics(int N) {
        config.setRequestCount(N);
        return new OutputCharacteristics(config);
    }

    public static void doSighting(Config config) {
        Sighting.config = config;

        System.out.println("Пристрелка\n");

        int newN = 10000;
        System.out.println("Изначальный N = " + newN);
        OutputCharacteristics newOutputCharacteristics = getOutputCharacteristics(newN);
        double newP = newOutputCharacteristics.getAvgFailProb();

        int curN;
        double curP;

        double leftPart;
        double rightPart;
        do {
            // 1) Назначили некоторое N.
            curN = newN;
            curP = newP;

            // 2) Подставили полученные характеристики в (1) и получили новое N.
            newN = getNewN(curP);
            System.out.println("Следующий N = " + newN);

            // 3) Провели с ним аналогичный процесс моделирования, поличиили новое p.
            newP = getOutputCharacteristics(newN).getAvgFailProb();

            leftPart = Math.abs(curP - newP);
            rightPart = 0.1 * curP;
            System.out.println("p0 = " + curP);
            System.out.println("p1 = " + newP);
            System.out.println("|p0-p1| = " + leftPart);
            System.out.println("0.1p0 = " + rightPart);
        } while (leftPart >= rightPart);

        System.out.println("curN = " + curN);
        System.out.println("newN = " + newN);

        System.out.println("---------------------------------------------------------");
        System.out.println("При p = " + curP + " нужно N = " + newN);
        System.out.println("---------------------------------------------------------");
    }

    public static void main(String[] args) {
        doSighting(new Config(1, 40, 40, 10000, 12, 15, 0.001));

        doSighting(new Config(3, 32, 47, 10000, 10, 12, 0.005));

        doSighting(new Config(3, 33, 53, 10000, 10, 12, 0.005));
    }
}
