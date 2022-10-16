package simulation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Device {

    private final int deviceNumber; // Порядковый номер прибора.

    private double nextDoneTime; // Время, когда прибор обработает очередную заявку.
    private Request currentRequest; // Текущая обрабатываемая заявка.

    private double busyTime = 0; // Время занятости прибора.
    private int requestCount = 0; // Число заявок, побывавших на приборе.
}
