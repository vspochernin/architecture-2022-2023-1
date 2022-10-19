package ru.vspochernin.simulationmodel.simulation;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

import ru.vspochernin.simulationmodel.data.SimulationResult;
import ru.vspochernin.simulationmodel.utils.Utils;

// Класс, производящий симуляцию.
public class Simulator {

    private static double currentTime = 0; // Текущее время симуляции.
    private static int currentRequests = 0; // Текущее количество заявок (готовых + генерирующихся).
    private static int requiredRequests = 0; // Необходимое в симуляции количество заявок.
    private static StringBuilder description; // Описание каждого шага.
    private static InputKit inputKit; // Набор входов.
    private static DeviceKit deviceKit; // Набор приборов.
    private static Buffer buffer; // Буфер (набор мест буфера).
    private static double lambda; // Параметр lambda.
    private static double a; // Параметр a.
    private static double b; // Параметр b.
    private static Queue<Event> events; // Список событий (по нему симулируем систему).
    private static boolean isModelingStop; // Сгенерировалась ли последняя заявка?
    private static SimulationResult simulationResult; // Результат симуляции.

    class PlaceDispatcher {

        public static void handleRequest(Request createdRequest) {
            if (deviceKit.isThereFreeDevice()) {
                double nextReleaseTime = getNextReleaseTime();
                int deviceNumber = deviceKit.putRequestToDevice(createdRequest, currentTime, nextReleaseTime);
                events.add(new Event(nextReleaseTime, EventType.DEVICE_RELEASE, deviceNumber));
                description.append("\n- Созданная заявка поступила на свободный прибор " + deviceNumber + ".");
            } else if (buffer.isThereFreeBufferPlace()) {
                int bufferPlaceNumber = buffer.putRequestToBuffer(createdRequest, currentTime);
                description.append("\n- Для заявки не нашлось свободного прибора и она встала в буфер на место: " +
                        bufferPlaceNumber + ".");
            } else {
                int bufferPlaceNumber = buffer.forcePutRequestToBuffer(createdRequest, currentTime);
                description.append("\n- Для заявки не нашлось свободного прибора и свободного места в буфере. " +
                        "Сработала дисциплина отказа. Новая заявка заняла " + bufferPlaceNumber + "место в буфере");
            }
        }
    }

    class SelectionDispatcher {

        public static void handleRelease(int deviceNumber) {
            Device releasedDevice = deviceKit.getDevices().get(deviceNumber);
            description.append("\n- Прибор " + deviceNumber + " закончил обрабатывать заявку" +
                    releasedDevice.getCurrentProcessedRequest().toString() + ".");
            deviceKit.releaseDevice(deviceNumber);

            if (buffer.isEmpty()) {
                description.append("\n- Буфер был пуст, поэтому прибор остался свободным.");
            } else {
                Request newestRequest = buffer.extractNewestRequest(currentTime);
                double nextReleaseTime = getNextReleaseTime();
                deviceKit.putRequestToDevice(newestRequest, currentTime, nextReleaseTime);
                events.add(new Event(nextReleaseTime, EventType.DEVICE_RELEASE, deviceNumber));
                description.append("\n- Буфер был не пуст и на прибор отправилась самая младшая заявка в буфере " +
                        newestRequest.toString() + ".");
            }
        }
    }

    public static void init(Config config) {
        currentTime = 0;
        currentRequests = 0;
        requiredRequests = config.getRequestCount();
        description = new StringBuilder();
        inputKit = new InputKit(config.getInputCount());
        deviceKit = new DeviceKit(config.getDeviceCount());
        buffer = new Buffer(config.getBufferSize());
        lambda = config.getLambda();
        a = config.getA();
        b = config.getB();
        events = new PriorityQueue<>();
        isModelingStop = false;
        simulationResult = SimulationResult.builder()
                .steps(new ArrayList<>())
                .deviceCharacteristicTableRows(new ArrayList<>())
                .inputCharacteristicTableRows(new ArrayList<>())
                .build();
    }

    // Сгенерировать время, когда очередной источник сгенерирует заявку.
    public static double getNextGenerationTime() {
        return currentTime + Utils.getUniformDistribution(a, b);
    }

    // Сгенерировать время, когда очередной прибор обработает заявку и освободится.
    public static double getNextReleaseTime() {
        return currentTime + Utils.getExponentialDistribution(lambda);
    }

    public static SimulationResult simulate(Config config) {
        init(config); // Инициализация симуляции.

        // Изначально опрашиваем все источники, когда они готовы дать нам запрос.
        for (int i = 0; i < inputKit.getCount() && currentRequests < requiredRequests; i++) {
            double nextGenerationTime = getNextGenerationTime();
            events.add(new Event(nextGenerationTime, EventType.REQUEST_GENERATION, i));
            inputKit.startGenerating(i, nextGenerationTime);
            currentRequests++;
        }

        // Записываем нулевой шаг.
        description.append("\n- Текущее время моделирования: " + currentTime + ".")
                .append("\n- Начался процесс моделирования.")
                .append("\n- Опросили все источники.");
        simulationResult.addStep(inputKit, deviceKit, buffer, description.toString());
        description.setLength(0);

        // Выполняем процесс симуляции, пока не закончатся события.
        while (!events.isEmpty()) {
            Event currentEvent = events.poll(); // Получаем очередное событие.

            currentTime = currentEvent.getTimeWhenHappen(); // Прошло какое-то время.
            description.append("\n- Текущее время моделирования: " + currentTime + ".");

            // Сгенерировалась заявка.
            if (currentEvent.getType() == EventType.REQUEST_GENERATION) {
                // Получили новую заявку.
                Request generatedRequest = inputKit.generateRequest(currentEvent.getUnitNumber(), currentTime);
                description.append("\n- Источник ").append(generatedRequest.getInputNumber()).append(" сгенерировал " +
                        "заявку ").append(generatedRequest).append(".");

                // Снова опрашиваем источник, если нужны еще заявки.
                if (currentRequests < requiredRequests) {
                    double nextGenerationTime = getNextGenerationTime();
                    events.add(new Event(nextGenerationTime, EventType.REQUEST_GENERATION, currentEvent.getUnitNumber()));
                    inputKit.startGenerating(currentEvent.getUnitNumber(), nextGenerationTime);
                    currentRequests++;
                }

                if (!isModelingStop && inputKit.getActiveInputCount() == 0) {
                    description.append("\n- Сейчас была сгенерирована последняя заявка, следовательно произошло " +
                            "окончание процесса моделирования (окончание процесса генерации заявок).");
                    isModelingStop = true;
                    simulationResult.setModelingTime(currentTime);
                }

                // Обработали сгенерированную заявку.
                PlaceDispatcher.handleRequest(generatedRequest);
            } else if (currentEvent.getType() == EventType.DEVICE_RELEASE) {
                // Обработали освобождение прибора.
                SelectionDispatcher.handleRelease(currentEvent.getUnitNumber());
            }

            // Записываем шаг в результат.
            simulationResult.addStep(inputKit, deviceKit, buffer, description.toString());
            description.setLength(0);
        }

        simulationResult.setTotalSimulationTime(currentTime);
        simulationResult.calculateStatistics(inputKit, deviceKit);
        return simulationResult;
    }
}
