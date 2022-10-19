package ru.vspochernin.simulationmodel.simulation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class Device {

    private final int deviceNumber; // Порядковый номер прибора.

    private double timeWhenProcessDone = -1; // Время, когда прибор обработает очередную заявку.
    private Request currentProcessedRequest = null; // Текущая обрабатываемая заявка.

    private double busyTime = 0; // Время занятости прибора.
    private double handledRequestCount = 0; // Количество обслуженных прибором заявок.

    public void putRequest(Request request, double currentTime, double timeWhenDone) {
        request.setDevice(this);
        request.setDevicePutTime(currentTime);
        request.setDeviceReleaseTime(timeWhenDone);

        currentProcessedRequest = request;
        timeWhenProcessDone =  timeWhenDone;
    }

    public void releaseDevice() {
        currentProcessedRequest.calculate();

        currentProcessedRequest = null;
        timeWhenProcessDone = -1;
    }

    // Свободен ли прибор.
    public boolean isFree() {
        return currentProcessedRequest == null;
    }

    // Учесть запрос, когда тот уходит с прибора.
    public void considerRequest(double devicePutTime, double deviceReleaseTime) {
        busyTime += deviceReleaseTime - devicePutTime;
        handledRequestCount++;
    }
}
