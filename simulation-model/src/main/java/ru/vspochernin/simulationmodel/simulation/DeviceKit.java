package ru.vspochernin.simulationmodel.simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import lombok.Getter;

// Набор всех приборов.
public class DeviceKit {

    @Getter
    private final List<Device> devices;
    private final int deviceCount;

    private int busyDeviceCount = 0; // Количество занятый приборов.
    private int currentPointer = 0; // Указатель, с которого будем идти по кольцу.

    public DeviceKit(int count) {
        deviceCount = count;
        devices = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            devices.add(new Device(i));
        }
    }

    // Есть ли свободные приборы.
    public boolean isThereFreeDevice() {
        return busyDeviceCount < deviceCount;
    }

    // Ставим заявку на прибор и возвращаем номер, куда поставили.
    public int putRequestToDevice(Request request, double currentTime, double timeWhenDone) {
        if (!isThereFreeDevice()) {
            throw new NoSuchElementException("Критическая ошибка");
        }

        // Идем до конца в поисках пустого прибора.
        while (currentPointer < deviceCount) {
            if (devices.get(currentPointer).isFree()) {
                devices.get(currentPointer).putRequest(request, currentTime, timeWhenDone);
                busyDeviceCount++;
                return currentPointer++;
            }
            currentPointer++;
        }

        // Если так и не нашли - делаем круг.
        currentPointer = 0;
        while (currentPointer < deviceCount) {
            if (devices.get(currentPointer).isFree()) {
                devices.get(currentPointer).putRequest(request, currentTime, timeWhenDone);
                busyDeviceCount++;
                return currentPointer++;
            }
            currentPointer++;
        }

        // Что-то пошло не так.
        throw new IllegalArgumentException("Критическая ошибка");
    }

    // Освободить прибор.
    public void releaseDevice(int deviceNumber) {
        devices.get(deviceNumber).releaseDevice();
        busyDeviceCount--;
    }
}
