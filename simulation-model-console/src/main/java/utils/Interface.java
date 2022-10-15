package utils;

import java.util.List;

import data.BufferTableRow;
import data.CalendarAndStateTableRow;
import data.DeviceCharacteristicTableRow;
import data.InputCharacteristicTableRow;
import data.SimulationResult;
import data.StepData;
import de.vandermeer.asciitable.AsciiTable;

public class Interface {
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
        inputCharacteristicsTable.addRow("Input №", "Request №", "p failure", "T stay", "T buffer", "T " +
                "service", "V buffer", "V service");
        inputCharacteristicsTable.addRule();
        for (InputCharacteristicTableRow row : simulation.getInputCharacteristicTableRows()) {
            inputCharacteristicsTable.addRow(row.getNumber(), row.getCountOfRequests(), row.getFailureProbability(),
                    row.getStayTime(), row.getBufferTime(), row.getServiceTime(), row.getBufferVariance(),
                    row.getServiceVariance());
            inputCharacteristicsTable.addRule();
        }

        AsciiTable deviceCharacteristicsTable = new AsciiTable();
        deviceCharacteristicsTable.addRule();
        deviceCharacteristicsTable.addRow("Device №", "Utilization Rate");
        deviceCharacteristicsTable.addRule();
        for (DeviceCharacteristicTableRow row : simulation.getDeviceCharacteristicTableRows()) {
            deviceCharacteristicsTable.addRow(row.getNumber(), row.getUtilizationRate());
            deviceCharacteristicsTable.addRule();
        }

        System.out.println("Характеристики источников ВС:");
        System.out.println(inputCharacteristicsTable.render(300));
        System.out.println("Характеристики приборов ВС:");
        System.out.println(deviceCharacteristicsTable.render(300));
    }

    public static void showStepResults(SimulationResult simulation, int step) {
        StepData stepData = simulation.getSteps().get(step);
        String desctiption = stepData.getDescription();

        AsciiTable calendarAndStateTable = new AsciiTable();
        calendarAndStateTable.addRule();
        calendarAndStateTable.addRow("Event", "Time", "Request count", "Failure count");
        calendarAndStateTable.addRule();
        for (CalendarAndStateTableRow row : stepData.getCalendarAndStateTableRows()) {
            calendarAndStateTable.addRow(row.getEvent(), row.getTime(), row.getRequestCount(), row.getFailureCount());
            calendarAndStateTable.addRule();
        }

        AsciiTable bufferTable = new AsciiTable();
        bufferTable.addRule();
        bufferTable.addRow("Position", "Registration time", "Input number", "Request number");
        for (BufferTableRow row : stepData.getBufferTableRows()) {
            bufferTable.addRow(row.getPosition(), row.getRegistrationTime(), row.getInputNumber(),
                    row.getRequestNumber());
            bufferTable.addRule();
        }

        System.out.println("Описание текущего шага: " + desctiption);
        System.out.println("Календарь событий/текущее состояние:");
        System.out.println(calendarAndStateTable.render(300));
        System.out.println("Буфер:");
        System.out.println(bufferTable.render(300));
    }

    public static SimulationResult getTestSimulationResult() {
        return SimulationResult.builder()
                .steps(List.of(
                        StepData.builder()
                                .description("Начало моделирования")
                                .calendarAndStateTableRows(List.of(
                                        CalendarAndStateTableRow.builder()
                                                .event("I1")
                                                .time(4.3)
                                                .requestCount(0)
                                                .failureCount(0)
                                                .build(),
                                        CalendarAndStateTableRow.builder()
                                                .event("I2")
                                                .time(5.3)
                                                .requestCount(0)
                                                .failureCount(0)
                                                .build(),
                                        CalendarAndStateTableRow.builder()
                                                .event("I3")
                                                .time(6.3)
                                                .requestCount(0)
                                                .failureCount(0)
                                                .build(),
                                        CalendarAndStateTableRow.builder()
                                                .event("D1")
                                                .time(0.0)
                                                .requestCount(0)
                                                .failureCount(0)
                                                .build(),
                                        CalendarAndStateTableRow.builder()
                                                .event("D2")
                                                .time(0.0)
                                                .requestCount(0)
                                                .failureCount(0)
                                                .build(),
                                        CalendarAndStateTableRow.builder()
                                                .event("D3")
                                                .time(0.0)
                                                .requestCount(0)
                                                .failureCount(0)
                                                .build(),
                                        CalendarAndStateTableRow.builder()
                                                .event("End of simulate")
                                                .time(999.0)
                                                .requestCount(0)
                                                .failureCount(0)
                                                .build()
                                ))
                                .bufferTableRows(List.of(
                                        BufferTableRow.builder()
                                                .position(1)
                                                .registrationTime(2.2)
                                                .inputNumber(3)
                                                .requestNumber(2)
                                                .build(),
                                        BufferTableRow.builder()
                                                .position(2)
                                                .registrationTime(3.2)
                                                .inputNumber(4)
                                                .requestNumber(5)
                                                .build()
                                ))
                                .build(),
                        StepData.builder()
                                .description("Какой-то шаг моделирования")
                                .calendarAndStateTableRows(List.of(
                                        CalendarAndStateTableRow.builder()
                                                .event("I1")
                                                .time(44.3)
                                                .requestCount(0)
                                                .failureCount(0)
                                                .build(),
                                        CalendarAndStateTableRow.builder()
                                                .event("I2")
                                                .time(55.3)
                                                .requestCount(0)
                                                .failureCount(0)
                                                .build(),
                                        CalendarAndStateTableRow.builder()
                                                .event("I3")
                                                .time(666.3)
                                                .requestCount(0)
                                                .failureCount(0)
                                                .build(),
                                        CalendarAndStateTableRow.builder()
                                                .event("D1")
                                                .time(1.0)
                                                .requestCount(0)
                                                .failureCount(0)
                                                .build(),
                                        CalendarAndStateTableRow.builder()
                                                .event("D2")
                                                .time(2.0)
                                                .requestCount(0)
                                                .failureCount(0)
                                                .build(),
                                        CalendarAndStateTableRow.builder()
                                                .event("D3")
                                                .time(3.0)
                                                .requestCount(0)
                                                .failureCount(0)
                                                .build(),
                                        CalendarAndStateTableRow.builder()
                                                .event("End of simulate")
                                                .time(999.0)
                                                .requestCount(0)
                                                .failureCount(0)
                                                .build()
                                ))
                                .bufferTableRows(List.of(
                                        BufferTableRow.builder()
                                                .position(1)
                                                .registrationTime(2.2)
                                                .inputNumber(3)
                                                .requestNumber(2)
                                                .build(),
                                        BufferTableRow.builder()
                                                .position(2)
                                                .registrationTime(3.2)
                                                .inputNumber(4)
                                                .requestNumber(5)
                                                .build()
                                ))
                                .build()
                ))
                .inputCharacteristicTableRows(List.of(
                        InputCharacteristicTableRow.builder()
                                .number(1)
                                .countOfRequests(11)
                                .failureProbability(0.11)
                                .stayTime(11.11)
                                .bufferTime(111.111)
                                .serviceTime(1111.1111)
                                .bufferVariance(11111.11111)
                                .serviceVariance(111111.111111)
                                .build(),
                        InputCharacteristicTableRow.builder()
                                .number(2)
                                .countOfRequests(22)
                                .failureProbability(0.22)
                                .stayTime(22.22)
                                .bufferTime(222.222)
                                .serviceTime(2222.2222)
                                .bufferVariance(22222.22222)
                                .serviceVariance(222222.222222)
                                .build(),
                        InputCharacteristicTableRow.builder()
                                .number(3)
                                .countOfRequests(33)
                                .failureProbability(0.33)
                                .stayTime(33.33)
                                .bufferTime(333.333)
                                .serviceTime(3333.3333)
                                .bufferVariance(33333.33333)
                                .serviceVariance(333333.333333)
                                .build()
                ))
                .deviceCharacteristicTableRows(List.of(
                        DeviceCharacteristicTableRow.builder()
                                .number(1)
                                .utilizationRate(0.11)
                                .build(),
                        DeviceCharacteristicTableRow.builder()
                                .number(2)
                                .utilizationRate(0.22)
                                .build(),
                        DeviceCharacteristicTableRow.builder()
                                .number(3)
                                .utilizationRate(0.33)
                                .build()
                ))
                .build();
    }
}
