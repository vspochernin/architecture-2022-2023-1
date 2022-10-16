package simulation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import lombok.Getter;

// Конфигурация моделирования.
@Getter
public class Config {

    private final long inputCount; // Количество источников.
    private final long bufferSize; // Размер буфера.
    private final long deviceCount; // Количество приборов.
    private final long requestCount; // Количество запросов.
    private final double a; // Параметр a равномерного распределения.
    private final double b; // Параметр b равномерного распределения.
    private final double lambda; // Параметр lambda экспоненциального распределения.

    private Config(long inputCount, long bufferSize, long deviceCount, long requestCount, double a, double b,
                  double lambda) {
        this.inputCount = inputCount;
        this.bufferSize = bufferSize;
        this.deviceCount = deviceCount;
        this.requestCount = requestCount;
        this.a = a;
        this.b = b;
        this.lambda = lambda;
    }

    public static Config generateConfigFromFile(String fileName) throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            long inputCount = Long.parseLong(br.readLine());
            if (inputCount <= 0) {
                throw new IllegalArgumentException("Неположительное значение количества источников");
            }
            long bufferSize = Long.parseLong(br.readLine());
            if (bufferSize <= 0) {
                throw new IllegalArgumentException("Неположительное значение размера буфера");
            }
            long deviceCount = Long.parseLong(br.readLine());
            if (deviceCount <= 0) {
                throw new IllegalArgumentException("Неположительное значение количества приборов");
            }
            long requestCount = Long.parseLong(br.readLine());
            if (requestCount <= 0) {
                throw new IllegalArgumentException("Неположительное значение количества запросов");
            }
            double a = Double.parseDouble(br.readLine());
            if (a < 0) {
                throw new IllegalArgumentException("Отрицательное значение параметра a");
            }
            double b = Double.parseDouble(br.readLine());
            if (b < 0) {
                throw new IllegalArgumentException("Отрицательное значение параметра b");
            }
            if (a >= b) {
                throw new IllegalArgumentException("Параметр b не больше, чем параметр a");
            }
            double lambda = Double.parseDouble(br.readLine());
            if (lambda == 0.0) {
                throw new IllegalArgumentException("Нулевой параметр lambda");
            }

            return new Config(inputCount, bufferSize, deviceCount, requestCount, a, b, lambda);
        }
    }
}
