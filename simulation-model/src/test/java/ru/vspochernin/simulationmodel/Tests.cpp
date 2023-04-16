package ru.vspochernin.simulationmodel;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import ru.vspochernin.simulationmodel.utils.Utils;

class Tests {

    @Test
    void showExponentialTime() {
        double lambda = 0.005;
        List<Double> results = Stream.generate(() -> Utils.getExponentialDistribution(lambda))
                .limit(10000)
                .sorted()
                .toList();
        var stat = results.stream().mapToDouble(Double::valueOf).summaryStatistics();
        System.out.println(stat);
    }

}
