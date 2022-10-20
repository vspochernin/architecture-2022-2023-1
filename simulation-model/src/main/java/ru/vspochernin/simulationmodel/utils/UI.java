package ru.vspochernin.simulationmodel.utils;

import java.util.List;

import ru.vspochernin.simulationmodel.data.BufferTableRow;
import ru.vspochernin.simulationmodel.data.CalendarAndStateTableRow;
import ru.vspochernin.simulationmodel.data.DeviceCharacteristicTableRow;
import ru.vspochernin.simulationmodel.data.InputCharacteristicTableRow;
import ru.vspochernin.simulationmodel.data.SimulationResult;
import ru.vspochernin.simulationmodel.data.StepData;
import de.vandermeer.asciitable.AsciiTable;

public class UI {

    public static void showHelp() {
        System.out.println("Доступные команды:");
        System.out.println("help - показать помощь");
        System.out.println("simulate - запустить процесс симуляции");
        System.out.println("auto - получить результаты симуляции (автоматический режим)");
        System.out.println("step n - получить информацию n-го шага");
        System.out.println("exit - завершить программу");
    }

    public static void showIncorrectCommandMessage() {
        System.out.println("Некорректная команда.");
    }

    public static void showAutoResults(SimulationResult simulation) {
        AsciiTable inputCharacteristicsTable = new AsciiTable();
        inputCharacteristicsTable.addRule();
        inputCharacteristicsTable.addRow(
                "Input №",
                "Request Count",
                "p failure",
                "T stay",
                "T buffer",
                "T service",
                "V buffer",
                "V service");
        inputCharacteristicsTable.addRule();
        for (InputCharacteristicTableRow row : simulation.getInputCharacteristicTableRows()) {
            inputCharacteristicsTable.addRow(
                    row.getInputNumber(),
                    row.getCountOfGeneratedRequests(),
                    row.getFailureProbability(),
                    row.getAvgStayTime(),
                    row.getAvgBufferTime(),
                    row.getAvgServiceTime(),
                    row.getAvgBufferTimeVariance(),
                    row.getAvgServiceTimeVariance());
            inputCharacteristicsTable.addRule();
        }

        AsciiTable deviceCharacteristicsTable = new AsciiTable();
        deviceCharacteristicsTable.addRule();
        deviceCharacteristicsTable.addRow(
                "Device №",
                "Utilization Rate");
        deviceCharacteristicsTable.addRule();
        for (DeviceCharacteristicTableRow row : simulation.getDeviceCharacteristicTableRows()) {
            deviceCharacteristicsTable.addRow(
                    row.getDeviceNumber(),
                    row.getUtilizationRate());
            deviceCharacteristicsTable.addRule();
        }

        System.out.println("Характеристики источников ВС:");
        System.out.println(inputCharacteristicsTable.render(200));
        System.out.println("Характеристики приборов ВС:");
        System.out.println(deviceCharacteristicsTable.render(200));
    }

    public static void showStepResults(SimulationResult simulation, int step) {
        StepData stepData = simulation.getSteps().get(step);
        String desctiption = stepData.getDescription();

        AsciiTable calendarAndStateTable = new AsciiTable();
        calendarAndStateTable.addRule();
        calendarAndStateTable.addRow(
                "Event",
                "Time",
                "Request count / Input Number",
                "Failure count / Request Number");
        calendarAndStateTable.addRule();
        for (CalendarAndStateTableRow row : stepData.getCalendarAndStateTableRows()) {
            calendarAndStateTable.addRow(
                    row.getEvent(),
                    (row.getTimeWhenHappen() != -1) ? row.getTimeWhenHappen() : "",
                    (row.getRequestCount() != -1) ? row.getRequestCount() : "",
                    (row.getFailureCount() != -1) ? row.getFailureCount() : "");
            calendarAndStateTable.addRule();
        }

        AsciiTable bufferTable = new AsciiTable();
        bufferTable.addRule();
        bufferTable.addRow(
                "Position",
                "Registration time",
                "Input number",
                "Request number");
        bufferTable.addRule();
        for (BufferTableRow row : stepData.getBufferTableRows()) {
            bufferTable.addRow(
                    row.getPositionNumber(),
                    (row.getRegistrationTime() != -1) ? row.getRegistrationTime() : "",
                    (row.getInputNumber() != -1) ? row.getInputNumber() : "",
                    (row.getRequestNumber() != -1) ? row.getRequestNumber() : "");
            bufferTable.addRule();
        }

        System.out.println("Описание текущего шага: " + desctiption);
        System.out.println("Календарь событий/текущее состояние:");
        System.out.println(calendarAndStateTable.render(100));
        System.out.println("Буфер:");
        System.out.println(bufferTable.render(100));
    }

    public static SimulationResult getTestSimulationResult() {
        return SimulationResult.builder()
                .steps(List.of(
                        StepData.builder()
                                .description("Начало моделирования")
                                .calendarAndStateTableRows(List.of(
                                        CalendarAndStateTableRow.builder()
                                                .event("I1")
                                                .timeWhenHappen(4.3)
                                                .requestCount(0)
                                                .failureCount(0)
                                                .build(),
                                        CalendarAndStateTableRow.builder()
                                                .event("I2")
                                                .timeWhenHappen(5.3)
                                                .requestCount(0)
                                                .failureCount(0)
                                                .build(),
                                        CalendarAndStateTableRow.builder()
                                                .event("I3")
                                                .timeWhenHappen(6.3)
                                                .requestCount(0)
                                                .failureCount(0)
                                                .build(),
                                        CalendarAndStateTableRow.builder()
                                                .event("D1")
                                                .timeWhenHappen(0.0)
                                                .requestCount(0)
                                                .failureCount(-1)
                                                .build(),
                                        CalendarAndStateTableRow.builder()
                                                .event("D2")
                                                .timeWhenHappen(0.0)
                                                .requestCount(0)
                                                .failureCount(-1)
                                                .build(),
                                        CalendarAndStateTableRow.builder()
                                                .event("D3")
                                                .timeWhenHappen(0.0)
                                                .requestCount(0)
                                                .failureCount(-1)
                                                .build(),
                                        CalendarAndStateTableRow.builder()
                                                .event("End of simulate")
                                                .timeWhenHappen(999.0)
                                                .requestCount(-1)
                                                .failureCount(-1)
                                                .build()
                                ))
                                .bufferTableRows(List.of(
                                        BufferTableRow.builder()
                                                .positionNumber(1)
                                                .registrationTime(2.2)
                                                .inputNumber(3)
                                                .requestNumber(2)
                                                .build(),
                                        BufferTableRow.builder()
                                                .positionNumber(2)
                                                .registrationTime(-1)
                                                .inputNumber(-1)
                                                .requestNumber(-1)
                                                .build()
                                ))
                                .build(),
                        StepData.builder()
                                .description("Какой-то шаг моделирования")
                                .calendarAndStateTableRows(List.of(
                                        CalendarAndStateTableRow.builder()
                                                .event("I1")
                                                .timeWhenHappen(44.3)
                                                .requestCount(0)
                                                .failureCount(0)
                                                .build(),
                                        CalendarAndStateTableRow.builder()
                                                .event("I2")
                                                .timeWhenHappen(55.3)
                                                .requestCount(0)
                                                .failureCount(0)
                                                .build(),
                                        CalendarAndStateTableRow.builder()
                                                .event("I3")
                                                .timeWhenHappen(666.3)
                                                .requestCount(0)
                                                .failureCount(0)
                                                .build(),
                                        CalendarAndStateTableRow.builder()
                                                .event("D1")
                                                .timeWhenHappen(1.0)
                                                .requestCount(0)
                                                .failureCount(0)
                                                .build(),
                                        CalendarAndStateTableRow.builder()
                                                .event("D2")
                                                .timeWhenHappen(2.0)
                                                .requestCount(0)
                                                .failureCount(0)
                                                .build(),
                                        CalendarAndStateTableRow.builder()
                                                .event("D3")
                                                .timeWhenHappen(3.0)
                                                .requestCount(0)
                                                .failureCount(0)
                                                .build(),
                                        CalendarAndStateTableRow.builder()
                                                .event("End of simulate")
                                                .timeWhenHappen(999.0)
                                                .requestCount(0)
                                                .failureCount(0)
                                                .build()
                                ))
                                .bufferTableRows(List.of(
                                        BufferTableRow.builder()
                                                .positionNumber(1)
                                                .registrationTime(2.2)
                                                .inputNumber(3)
                                                .requestNumber(2)
                                                .build(),
                                        BufferTableRow.builder()
                                                .positionNumber(2)
                                                .registrationTime(3.2)
                                                .inputNumber(4)
                                                .requestNumber(5)
                                                .build()
                                ))
                                .build()
                ))
                .inputCharacteristicTableRows(List.of(
                        InputCharacteristicTableRow.builder()
                                .inputNumber(1)
                                .countOfGeneratedRequests(11)
                                .failureProbability(0.11)
                                .avgStayTime(11.11)
                                .avgBufferTime(111.111)
                                .avgServiceTime(1111.1111)
                                .avgBufferTimeVariance(11111.11111)
                                .avgServiceTimeVariance(111111.111111)
                                .build(),
                        InputCharacteristicTableRow.builder()
                                .inputNumber(2)
                                .countOfGeneratedRequests(22)
                                .failureProbability(0.22)
                                .avgStayTime(22.22)
                                .avgBufferTime(222.222)
                                .avgServiceTime(2222.2222)
                                .avgBufferTimeVariance(22222.22222)
                                .avgServiceTimeVariance(222222.222222)
                                .build(),
                        InputCharacteristicTableRow.builder()
                                .inputNumber(3)
                                .countOfGeneratedRequests(33)
                                .failureProbability(0.33)
                                .avgStayTime(33.33)
                                .avgBufferTime(333.333)
                                .avgServiceTime(3333.3333)
                                .avgBufferTimeVariance(33333.33333)
                                .avgServiceTimeVariance(333333.333333)
                                .build()
                ))
                .deviceCharacteristicTableRows(List.of(
                        DeviceCharacteristicTableRow.builder()
                                .deviceNumber(1)
                                .utilizationRate(0.11)
                                .build(),
                        DeviceCharacteristicTableRow.builder()
                                .deviceNumber(2)
                                .utilizationRate(0.22)
                                .build(),
                        DeviceCharacteristicTableRow.builder()
                                .deviceNumber(3)
                                .utilizationRate(0.33)
                                .build()
                ))
                .build();
    }
}
