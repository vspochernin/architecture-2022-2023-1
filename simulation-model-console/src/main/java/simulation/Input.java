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

    private double nextRequestGeneratedTime = 0;

    private int countOfGeneratedRequests = 0; // Количество сгенерированных источником заявок.
    private int countOfFailureRequests = 0; // Количество отказов от источника.
    private double failureProbability = 0; // Вероятность отказа источника (считается в конце).
    private double avgStayTime = 0; // Среднее время пребывания заявки в системе.
    private double avgBufferTime = 0; // Среднее время пребывания заявки в буффере.
    private double avgServiceTime = 0; // Среднее время пребывания заявки на приборе.
    private double avgBufferTimeVariance = 0; // Дисперсия нахождения в буфере.
    private double avgServiceTimeVariance = 0; // Дисперсия нахождения на приборе.

}
