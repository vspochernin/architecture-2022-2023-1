package ru.vspochernin.simulationmodel.data;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import ru.vspochernin.simulationmodel.simulation.Buffer;
import ru.vspochernin.simulationmodel.simulation.BufferPlace;
import ru.vspochernin.simulationmodel.simulation.Device;
import ru.vspochernin.simulationmodel.simulation.DeviceKit;
import ru.vspochernin.simulationmodel.simulation.Input;
import ru.vspochernin.simulationmodel.simulation.InputKit;

// Результат моделирования.
@Builder
@Data
public class SimulationResult {

    private List<StepData> steps; // Данные для каждого шага.
    private List<InputCharacteristicTableRow> inputCharacteristicTableRows; // Данные для таблицы характеристик
    // источников ВС.
    private List<DeviceCharacteristicTableRow> deviceCharacteristicTableRows; // Данные для таблицы характеристик
    // приборов ВС.

    private double modelingTime; // Окончание моделирования (время генерации последней заявки).
    private double totalSimulationTime; // Общее время реализации (выход из системы последней заявки).

    // Добавить шаг в результаты.
    public void addStep(InputKit inputKit, DeviceKit deviceKit, Buffer buffer, String description) {
        StepData stepData = StepData.builder()
                .description(description)
                .bufferTableRows(new ArrayList<>())
                .calendarAndStateTableRows(new ArrayList<>())
                .build();

        // Заполнение таблицы календарь событий/текущее состояние.
        for (Input input : inputKit.getInputs()) {
            stepData.getCalendarAndStateTableRows().add(CalendarAndStateTableRow.builder()
                            .event("I"+input.getInputNumber())
                            .timeWhenHappen(input.getNextRequestGeneratedTime())
                            .requestCount(input.getCountOfGeneratedRequests())
                            .failureCount(input.getCountOfFailureRequests())
                    .build());
        }
        for (Device device : deviceKit.getDevices()) {
            stepData.getCalendarAndStateTableRows().add(CalendarAndStateTableRow.builder()
                            .event("D"+device.getDeviceNumber())
                            .timeWhenHappen(device.getCurrentProcessedRequest() == null ? -1 : device.getTimeWhenProcessDone())
                            .requestCount(device.getCurrentProcessedRequest() == null ? -1 : device.getCurrentProcessedRequest().getInputNumber())
                            .failureCount(device.getCurrentProcessedRequest() == null ? -1 : device.getCurrentProcessedRequest().getRequestNumber())
                    .build());
        }

        // Заполнение таблицы буфер.
        for (BufferPlace bufferPlace : buffer.getBufferPlaces()) {
            stepData.getBufferTableRows().add(BufferTableRow.builder()
                    .positionNumber(bufferPlace.getPositionNumber())
                    .registrationTime(bufferPlace.getRegistrationTime())
                    .inputNumber(bufferPlace.getStashedRequest() != null ? bufferPlace.getStashedRequest().getInputNumber() : -1)
                    .requestNumber(bufferPlace.getStashedRequest() != null ? bufferPlace.getStashedRequest().getRequestNumber() : -1)
                    .build());
        }

        steps.add(stepData);
    }

    public void calculateStatistics(InputKit inputKit, DeviceKit deviceKit) {
        for (Device device : deviceKit.getDevices()) {
            DeviceCharacteristicTableRow deviceCharacteristicTableRow = DeviceCharacteristicTableRow.builder()
                    .deviceNumber(device.getDeviceNumber())
                    .utilizationRate(device.getBusyTime() / totalSimulationTime)
                    .build();
            deviceCharacteristicTableRows.add(deviceCharacteristicTableRow);
        }

        for (Input input : inputKit.getInputs()) {
            InputCharacteristicTableRow inputCharacteristicTableRow = InputCharacteristicTableRow.builder()
                    .inputNumber(input.getInputNumber())
                    .countOfGeneratedRequests(input.getCountOfGeneratedRequests())
                    .failureProbability((double) input.getCountOfFailureRequests() / input.getCountOfGeneratedRequests())
                    .avgStayTime(input.getStayTime() / input.getCountOfGeneratedRequests())
                    .avgBufferTime(input.getBufferTime() / input.getCountOfGeneratedRequests())
                    .avgServiceTime(input.getServiceTime() / input.getCountOfGeneratedRequests())
                    .avgBufferTimeVariance(input.getBufferVariance())
                    .avgServiceTimeVariance(input.getServiceVariance())
                    .build();
            inputCharacteristicTableRows.add(inputCharacteristicTableRow);
        }
    }
}
