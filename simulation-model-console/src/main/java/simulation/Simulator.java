package simulation;

import java.util.ArrayList;
import java.util.PriorityQueue;

import data.SimulationResult;
import utils.Utils;

// Класс, производящий симуляцию.
public class Simulator {

    private double currentTime = 0; // Текущее время симуляции.

    public static SimulationResult simulate(Config config) {
        // Инициализация нашего результата.
        SimulationResult simulationResult = SimulationResult.builder()
                .steps(new ArrayList<>())
                .deviceCharacteristicTableRows(new ArrayList<>())
                .inputCharacteristicTableRows(new ArrayList<>())
                .build();

        // Инициализация источников.
        InputKit inputKit = new InputKit(config.getInputCount());
        // Инициализация приборов.
        DeviceKit deviceKit = new DeviceKit(config.getDeviceCount());
        // Инициализация буфера.
        Buffer buffer = new Buffer(config.getBufferSize());

        // Инициализация списка событий.
        PriorityQueue<Event> events = new PriorityQueue<>();

        // Опрашиваем все источники, когда они готовы дать нам запрос.
        for (int i = 0; i < inputKit.getCount(); i++) {
            double generationTime = Utils.getUniformDistribution(config.getA(), config.getB());
            events.add(new Event(generationTime, EventType.REQUEST_GENERATION));
            inputKit.startGenerating(i, generationTime);
        }

        // Записываем нулевой шаг.
        simulationResult.addStep(inputKit, deviceKit, buffer, "Начали моделирование, опросив все источники.");


        return simulationResult;
    }
}
