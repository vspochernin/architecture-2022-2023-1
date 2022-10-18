package simulation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BufferPlace {

    private final int positionNumber; // Номер позиции.

    private Request stashedRequest = null; // Текущая запись в буфере.
    private double registrationTime = -1; // Время постановки в буфер.

    public void putRequest(Request request, double currentTime) {
        request.setBufferPlace(this);
        request.setBufferPutTime(currentTime);

        stashedRequest = request;
        registrationTime = currentTime;
    }

    public void forcePutRequest(Request request, double currentTime) {
        stashedRequest.setBufferReleaseTime(currentTime);
        stashedRequest.calculate();

        request.setBufferPlace(this);
        request.setBufferPutTime(currentTime);

        stashedRequest = request;
        registrationTime = currentTime;
    }

    public Request extractRequest(double currentTime) {
        stashedRequest.setBufferReleaseTime(currentTime);
        stashedRequest.calculate();

        Request result = stashedRequest;

        stashedRequest = null;
        registrationTime = -1;

        return result;
    }

    // Свободна ли ячейка буфера.
    public boolean isFree() {
        return stashedRequest == null;
    }
}
