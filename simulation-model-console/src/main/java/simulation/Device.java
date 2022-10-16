package simulation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Device {

    private final int deviceNumber; // Порядковый номер прибора.

    private double timeWhenProcessDone; // Время, когда прибор обработает очередную заявку.
    private Request currentProcessedRequest; // Текущая обрабатываемая заявка.

    private double busyTime = 0; // Время занятости прибора.
}
