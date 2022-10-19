package simulation;

import java.util.ArrayList;
import java.util.List;

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
    private double stayTime = 0; // Время пребывания всех заявок в системе.
    private double bufferTime = 0; // Время пребывания всех заявок в буфере.
    private double serviceTime = 0; // Время пребывания всех заявок на приборах.

    // Вычисление дисперсии.
    private List<Double> bufferTimes = new ArrayList<>();
    private List<Double> serviceTimes = new ArrayList<>();


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

    // Обработать ушедшую заявку, когда она побывала и в буфере, и на приборе.
    public void considerRequestBoth(double creationTime, double bufferPutTime, double bufferReleaseTime, double devicePutTime, double deviceReleaseTime) {
        stayTime += deviceReleaseTime - creationTime;
        bufferTime += bufferReleaseTime - bufferPutTime;
        serviceTime += deviceReleaseTime - devicePutTime;


        bufferTimes.add(bufferReleaseTime - bufferPutTime);
        serviceTimes.add(deviceReleaseTime - devicePutTime);
    }

    // Обработать ушедшую заявку, когда она побывала только на приборе.
    public void considerRequestDevice(double creationTime, double devicePutTime, double deviceReleaseTime) {
        stayTime += deviceReleaseTime - creationTime;
        serviceTime += deviceReleaseTime - devicePutTime;

        bufferTimes.add(0.0);
        serviceTimes.add(deviceReleaseTime - devicePutTime);
    }

    // Обработать ушедшую заявку, когда она побывала только в буфере (сработала дисциплина отказа).
    public void considerRequestBuffer(double creationTime, double bufferPutTime, double bufferReleaseTime) {
        countOfFailureRequests++;

        stayTime += bufferReleaseTime - creationTime;
        bufferTime += bufferReleaseTime - bufferPutTime;

        bufferTimes.add(bufferReleaseTime - bufferPutTime);
        serviceTimes.add(0.0);
    }

    public double getBufferVariance() {
        double sumOfSquares = 0;
        double avgBufferTime = bufferTime / countOfGeneratedRequests;

        for (int i = 0; i < countOfGeneratedRequests; i++) {
            sumOfSquares += Math.pow(bufferTimes.get(i) - avgBufferTime, 2);
        }

        return sumOfSquares / countOfGeneratedRequests;
    }

    public double getServiceVariance() {
        double sumOfSquares = 0;
        double avgServiceTime = serviceTime / countOfGeneratedRequests;

        for (int i = 0; i < countOfGeneratedRequests; i++) {
            sumOfSquares += Math.pow(serviceTimes.get(i) - avgServiceTime, 2);
        }

        return sumOfSquares / countOfGeneratedRequests;
    }
}
