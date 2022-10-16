package data;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import simulation.Buffer;
import simulation.BufferPlace;
import simulation.Device;
import simulation.DeviceKit;
import simulation.Input;
import simulation.InputKit;

// Результат моделирования.

@Builder
@Data
public class SimulationResult {

    private List<StepData> steps; // Данные для каждого шага.
    private List<InputCharacteristicTableRow> inputCharacteristicTableRows; // Данные для таблицы характеристик
    // источников ВС.
    private List<DeviceCharacteristicTableRow> deviceCharacteristicTableRows; // Данные для таблицы характеристик
    // приборов ВС.

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
                            .time(input.getNextRequestTime())
                            .requestCount(input.getRequestCount())
                            .failureCount(input.getFailureCount())
                    .build());
        }
        for (Device device : deviceKit.getDevices()) {
            stepData.getCalendarAndStateTableRows().add(CalendarAndStateTableRow.builder()
                            .event("D"+device.getDeviceNumber())
                            .time(device.getCurrentRequest() == null ? -1 : device.getNextDoneTime())
                            .requestCount(device.getCurrentRequest() == null ? -1 : device.getCurrentRequest().getInputNumber())
                            .failureCount(device.getCurrentRequest() == null ? -1 : device.getCurrentRequest().getRequestNumber())
                    .build());
        }

        // Заполнение таблицы буфер.
        for (BufferPlace bufferPlace : buffer.getBufferPlaces()) {
            stepData.getBufferTableRows().add(BufferTableRow.builder()
                    .position(bufferPlace.getPositionNumber())
                    .registrationTime(bufferPlace.getRegistrationTime())
                    .inputNumber(bufferPlace.getInputNumber())
                    .requestNumber(bufferPlace.getRequestNumber())
                    .build());
        }

        steps.add(stepData);
    }
}
