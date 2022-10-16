package simulation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

// Класс источника.
@RequiredArgsConstructor
@Getter
@Setter
public class Input {

    private final int inputNumber; // Порядковый номер источника.

    private double nextRequestTime = 0;

    private int requestCount = 0; // Количество сгенерированных источником заявок.
    private int failureCount = 0; // Количество отказов от источника.
    private double failureProbability = 0; // Вероятность отказа источника (считается в конце).
    private double stayTime = 0; // Среднее время пребывания заявки в системе.
    private double bufferTime = 0; // Среднее время пребывания заявки в буффере.
    private double serviceTime = 0; // Среднее время пребывания заявки на приборе.
    private double bufferVariance = 0; // Дисперсия нахождения в буфере.
    private double serviceVariance = 0; // Дисперсия нахождения на приборе.

}
