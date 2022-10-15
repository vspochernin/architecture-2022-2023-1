package utils;

import data.BufferTableRow;
import data.CalendarAndStateTableRow;
import data.DeviceCharacteristicTableRow;
import data.InputCharacteristicTableRow;
import data.SimulationResult;
import data.StepData;
import de.vandermeer.asciitable.AsciiTable;

public class Menu {
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
            calendarAndStateTable.addRow(row.getEvent(), row.getTime(), row.getRequestCount(), row.getRequestCount());
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
}
