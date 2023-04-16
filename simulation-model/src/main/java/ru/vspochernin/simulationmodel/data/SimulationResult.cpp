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

    private double modelingTime = 0.0; // Окончание моделирования (время генерации последней заявки).
    private double totalSimulationTime = 0.0; // Общее время реализации (выход из системы последней заявки).
    private int stepsCount = 0;

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
                            .event("И"+input.getInputNumber())
                            .timeWhenHappen(input.getNextRequestGeneratedTime() != -1 ? String.valueOf(input.getNextRequestGeneratedTime()) : "")
                            .requestCount(input.getCountOfGeneratedRequests() != -1 ? String.valueOf(input.getCountOfGeneratedRequests()): "")
                            .failureCount(input.getCountOfFailureRequests() != -1 ? String.valueOf(input.getCountOfFailureRequests()) : "")
                    .build());
        }
        for (Device device : deviceKit.getDevices()) {
            stepData.getCalendarAndStateTableRows().add(CalendarAndStateTableRow.builder()
                            .event("Д"+device.getDeviceNumber())
                            .timeWhenHappen(device.getCurrentProcessedRequest() == null ? "" : String.valueOf(device.getTimeWhenProcessDone()))
                            .requestCount(device.getCurrentProcessedRequest() == null ? "" : String.valueOf(device.getCurrentProcessedRequest().getInputNumber()))
                            .failureCount(device.getCurrentProcessedRequest() == null ? "" : String.valueOf(device.getCurrentProcessedRequest().getRequestNumber()))
                    .build());
        }

        // Заполнение таблицы буфер.
        for (BufferPlace bufferPlace : buffer.getBufferPlaces()) {
            stepData.getBufferTableRows().add(BufferTableRow.builder()
                    .positionNumber(String.valueOf(bufferPlace.getPositionNumber()))
                    .registrationTime(bufferPlace.getRegistrationTime() != -1 ? String.valueOf(bufferPlace.getRegistrationTime()) : "")
                    .inputNumber(bufferPlace.getStashedRequest() != null ? String.valueOf(bufferPlace.getStashedRequest().getInputNumber()) : "")
                    .requestNumber(bufferPlace.getStashedRequest() != null ? String.valueOf(bufferPlace.getStashedRequest().getRequestNumber()) : "")
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
