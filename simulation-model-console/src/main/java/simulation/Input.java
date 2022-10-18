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

    private double nextRequestGeneratedTime = -1; // Время, когда сгенерируется очередной запрос.
    private int countOfFailureRequests = 0; // Количество отказов от источника.

    // Табличные характеристики источника.
    private int countOfGeneratedRequests = 0; // Количество сгенерированных источником заявок.
    private double failureProbability = 0; // Вероятность отказа источника (считается в конце).
    private double avgStayTime = 0; // Среднее время пребывания заявки в системе.
    private double avgBufferTime = 0; // Среднее время пребывания заявки в буффере.
    private double avgServiceTime = 0; // Среднее время пребывания заявки на приборе.
    private double avgBufferTimeVariance = 0; // Дисперсия нахождения в буфере.
    private double avgServiceTimeVariance = 0; // Дисперсия нахождения на приборе.


    // Сгенерировать запрос.
    public Request generateRequest(double currentTime) {
        Request generatedRequest = new Request(inputNumber, countOfGeneratedRequests, this, currentTime);
        countOfGeneratedRequests++;
        nextRequestGeneratedTime = -1;
        return generatedRequest;
    }

    public void startGenerating(double generationTime) {
        nextRequestGeneratedTime = generationTime;
    }
}
