package ru.vspochernin.simulationmodel.research;

import lombok.Data;
import ru.vspochernin.simulationmodel.data.DeviceCharacteristicTableRow;
import ru.vspochernin.simulationmodel.data.InputCharacteristicTableRow;
import ru.vspochernin.simulationmodel.data.SimulationResult;
import ru.vspochernin.simulationmodel.simulation.Config;
import ru.vspochernin.simulationmodel.simulation.Simulator;

@Data
public class OutputCharacteristics {

    enum InputType {
        COW_CLASS_1,
        COW_CLASS_2,
        COW_CLASS_3
    }

    enum DeviceType {
        WITHOUT_LEAVEN,
        LEAVEN_CLASS_2,
        LEAVEN_CLASS_1
    }

    private final double avgFailProb;
    private final double avgStayTime;
    private final double avgBuffTime;
    private final double avgUtilRate;
    private final InputType inputType;
    private final DeviceType deviceType;
    private final Config config;
    private final double simulationTime;

    public OutputCharacteristics(Config config) {
        Simulator.config = config;
        SimulationResult simulationResult = Simulator.simulate();
        Double avgFailProb = simulationResult.getInputCharacteristicTableRows().stream()
                .map(InputCharacteristicTableRow::getFailureProbability)
                .mapToDouble(Double::doubleValue)
                .average().getAsDouble();
        Double avgStayTime = simulationResult.getInputCharacteristicTableRows().stream()
                .map(InputCharacteristicTableRow::getAvgStayTime)
                .mapToDouble(Double::doubleValue)
                .average().getAsDouble();
        Double avgBuffTime = simulationResult.getInputCharacteristicTableRows().stream()
                .map(InputCharacteristicTableRow::getAvgBufferTime)
                .mapToDouble(Double::doubleValue)
                .average().getAsDouble();
        Double avgUtilRate = simulationResult.getDeviceCharacteristicTableRows().stream()
                .map(DeviceCharacteristicTableRow::getUtilizationRate)
                .mapToDouble(Double::doubleValue)
                .average().getAsDouble();

        this.avgFailProb = avgFailProb;
        this.avgStayTime = avgStayTime;
        this.avgBuffTime = avgBuffTime;
        this.avgUtilRate = avgUtilRate;
        if (config.getA() == 10 && config.getB() == 12) {
            this.inputType = InputType.COW_CLASS_1;
        } else if (config.getA() == 12 && config.getB() == 15) {
            this.inputType = InputType.COW_CLASS_2;
        } else if (config.getA() == 15 && config.getB() == 24) {
            this.inputType = InputType.COW_CLASS_3;
        } else {
            throw new IllegalArgumentException();
        }
        if (config.getLambda() == 0.001) {
            this.deviceType = DeviceType.WITHOUT_LEAVEN;
        } else if (config.getLambda() == 0.003) {
            this.deviceType = DeviceType.LEAVEN_CLASS_2;
        } else if (config.getLambda() == 0.005) {
            this.deviceType = DeviceType.LEAVEN_CLASS_1;
        } else {
            throw new IllegalArgumentException();
        }
        this.config = config;
        this.simulationTime = simulationResult.getTotalSimulationTime();
    }

    @Override
    public String toString() {
        return "Средняя вероятность отказа: " + avgFailProb + "\n" +
                "Среднее время пребывания в СМО: " + avgStayTime + "\n" +
                "Среднее время пребывания в буфере: " + avgBuffTime + "\n" +
                "Средний коэффициент использования: " + avgUtilRate;
    }
}
