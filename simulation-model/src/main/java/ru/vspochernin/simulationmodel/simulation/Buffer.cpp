package ru.vspochernin.simulationmodel.simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import lombok.Getter;

// Буфер.
public class Buffer {

    @Getter
    private final List<BufferPlace> bufferPlaces;
    private final int bufferPlacesCount; // Количество ячеек буфера.

    private int busyBufferPlaces = 0; // Количество занятыйх ячеек буфера.
    private int currentPointer = 0; // Текущий указатель для прохода по кольцу.

    public Buffer(int size) {
        bufferPlacesCount = size;
        bufferPlaces = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            bufferPlaces.add(new BufferPlace(i));
        }
    }

    // Есть ли в буфере свободные ячейки.
    public boolean isThereFreeBufferPlace() {
        return busyBufferPlaces < bufferPlacesCount;
    }

    // Положить запрос в буфер.
    public int putRequestToBuffer(Request request, double currentTime) {
        if(!isThereFreeBufferPlace()) {
            throw new NoSuchElementException("Критическая ошибка");
        }

        // Идем до конца в поисках свободного места.
        while (currentPointer < bufferPlacesCount) {
            if (bufferPlaces.get(currentPointer).isFree()) {
                bufferPlaces.get(currentPointer).putRequest(request, currentTime);
                busyBufferPlaces++;
                return currentPointer++;
            }
            currentPointer++;
        }

        // Если так и не нашли - делаем круг.
        currentPointer = 0;
        while(currentPointer < bufferPlacesCount) {
            if (bufferPlaces.get(currentPointer).isFree()) {
                bufferPlaces.get(currentPointer).putRequest(request, currentTime);
                busyBufferPlaces++;
                return currentPointer++;
            }
            currentPointer++;
        }

        // Что-то пошло не так.
        throw new IllegalArgumentException("Критическая ошибка");
    }

    // Работает дисциплина отказа.
    public int forcePutRequestToBuffer(Request request, double currentTime) {
        double oldestRegistrationTime = Double.MAX_VALUE;
        int oldestBufferPlace = -1;

        // Ищем самую старую ячейку в буфере.
        for (int i = 0; i < bufferPlacesCount; i++) {
            if (bufferPlaces.get(i).getRegistrationTime() < oldestRegistrationTime) {
                oldestRegistrationTime = bufferPlaces.get(i).getRegistrationTime();
                oldestBufferPlace = i;
            }
        }

        bufferPlaces.get(oldestBufferPlace).forcePutRequest(request, currentTime);
        return oldestBufferPlace;
    }

    // Пуст ли буфер.
    public boolean isEmpty() {
        return busyBufferPlaces == 0;
    }

    // Достать из буфера самый младший запрос.
    public Request extractNewestRequest(double currentTime) {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        double newestRegistrationTime = -1;
        int newestBufferPlace = -1;

        for (int i = 0; i < bufferPlacesCount; i++) {
            if (bufferPlaces.get(i).getStashedRequest() != null && bufferPlaces.get(i).getRegistrationTime() > newestRegistrationTime) {
                newestRegistrationTime = bufferPlaces.get(i).getRegistrationTime();
                newestBufferPlace = i;
            }
        }

        busyBufferPlaces--;
        return bufferPlaces.get(newestBufferPlace).extractRequest(currentTime);
    }
}
