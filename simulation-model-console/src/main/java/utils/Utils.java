package utils;

import java.util.List;

import data.BufferTableRow;
import data.CalendarAndStateTableRow;
import data.DeviceCharacteristicTableRow;
import data.InputCharacteristicTableRow;
import data.SimulationResult;
import data.StepData;

public class Utils {

    public static SimulationResult getTestSimulationResult() {
        return SimulationResult.builder()
                .steps(List.of(
                        StepData.builder()
                                .description("Начало моделирования")
                                .calendarAndStateTableRows(List.of(
                                        CalendarAndStateTableRow.builder()
                                                .event("И1")
                                                .time(4.3)
                                                .requestCount(0)
                                                .failureCount(0)
                                                .build(),
                                        CalendarAndStateTableRow.builder()
                                                .event("И2")
                                                .time(5.3)
                                                .requestCount(0)
                                                .failureCount(0)
                                                .build(),
                                        CalendarAndStateTableRow.builder()
                                                .event("И3")
                                                .time(6.3)
                                                .requestCount(0)
                                                .failureCount(0)
                                                .build(),
                                        CalendarAndStateTableRow.builder()
                                                .event("П1")
                                                .time(0.0)
                                                .requestCount(0)
                                                .failureCount(0)
                                                .build(),
                                        CalendarAndStateTableRow.builder()
                                                .event("И2")
                                                .time(0.0)
                                                .requestCount(0)
                                                .failureCount(0)
                                                .build(),
                                        CalendarAndStateTableRow.builder()
                                                .event("И3")
                                                .time(0.0)
                                                .requestCount(0)
                                                .failureCount(0)
                                                .build(),
                                        CalendarAndStateTableRow.builder()
                                                .event("Конец мод.")
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
                                                .event("И1")
                                                .time(44.3)
                                                .requestCount(0)
                                                .failureCount(0)
                                                .build(),
                                        CalendarAndStateTableRow.builder()
                                                .event("И2")
                                                .time(55.3)
                                                .requestCount(0)
                                                .failureCount(0)
                                                .build(),
                                        CalendarAndStateTableRow.builder()
                                                .event("И3")
                                                .time(666.3)
                                                .requestCount(0)
                                                .failureCount(0)
                                                .build(),
                                        CalendarAndStateTableRow.builder()
                                                .event("П1")
                                                .time(1.0)
                                                .requestCount(0)
                                                .failureCount(0)
                                                .build(),
                                        CalendarAndStateTableRow.builder()
                                                .event("И2")
                                                .time(2.0)
                                                .requestCount(0)
                                                .failureCount(0)
                                                .build(),
                                        CalendarAndStateTableRow.builder()
                                                .event("И3")
                                                .time(3.0)
                                                .requestCount(0)
                                                .failureCount(0)
                                                .build(),
                                        CalendarAndStateTableRow.builder()
                                                .event("Конец мод.")
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
