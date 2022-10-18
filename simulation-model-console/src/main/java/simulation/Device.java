package simulation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Device {

    private final int deviceNumber; // Порядковый номер прибора.

    private double timeWhenProcessDone = -1; // Время, когда прибор обработает очередную заявку.
    private Request currentProcessedRequest = null; // Текущая обрабатываемая заявка.

    private double busyTime = 0; // Время занятости прибора.

    public void putRequest(Request request, double timeWhenDone) {
        currentProcessedRequest = request;
        timeWhenProcessDone =  timeWhenDone;
    }

    public void releaseDevice() {
        currentProcessedRequest = null;
        timeWhenProcessDone = -1;
    }

    // Свободен ли прибор.
    public boolean isFree() {
        return currentProcessedRequest == null;
    }
}
