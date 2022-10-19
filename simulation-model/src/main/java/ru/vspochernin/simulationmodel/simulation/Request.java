package ru.vspochernin.simulationmodel.simulation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

// Класс заявки.
@RequiredArgsConstructor
@Getter
@Setter
public class Request {

    private final int inputNumber; // Номер источника, который сгенерировал эту заявку.
    private final int requestNumber; // Порядковый номер заявки.
    private final Input input; // Источник, сгенерировавший заявку.
    private final double creationTime; // Время создания заявки.

    private BufferPlace bufferPlace = null; // Место в буфере, на котором оказалась заявка.
    private Device device = null; // Прибор, на котором оказалась заявка.


    private double bufferPutTime = -1; // Время постановки заявки в буфер.
    private double bufferReleaseTime = -1; // Время снятия заявки из буфера.

    private double devicePutTime = -1; // Время постановки заявки на прибор.
    private double deviceReleaseTime = -1; // Время снятия заявки с прибора.

    // Подсчитать результаты пребывания заявки в системе.
    public void calculate() {
        // TODO: Проверить весь код, что правильно прибавляешь. device.getBusyTime() += - не правильно.

        // Заявка была и в буфере, и на приборе.
        if (device != null && bufferPlace != null) {
            device.considerRequest(devicePutTime, deviceReleaseTime);
            bufferPlace.considerRequest(bufferPutTime, bufferReleaseTime);
            input.considerRequestBoth(creationTime, bufferPutTime, bufferReleaseTime, devicePutTime, deviceReleaseTime);
        } else if (device != null) { // Заявка была только на приборе, но не на буфере.
            device.considerRequest(devicePutTime, deviceReleaseTime);
            input.considerRequestDevice(creationTime, devicePutTime, deviceReleaseTime);
        } else if (bufferPlace != null) { // Заявка была только в буфере, но не на приборе.
            bufferPlace.considerRequest(bufferPutTime, bufferReleaseTime);
            input.considerRequestBuffer(creationTime, bufferPutTime, bufferReleaseTime);
        } else {
            throw new IllegalStateException("Критическая ошибка");
        }
    }

    @Override
    public String toString() {
        return "(" + inputNumber + "; " + requestNumber + ")";
    }
}
