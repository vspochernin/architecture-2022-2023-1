package ru.vspochernin.simulationmodel.simulation;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import lombok.Getter;
import lombok.Setter;

// Конфигурация моделирования.
@Getter
@Setter
public class Config {

    private static final String PROPERTIES_PATH = "src/main/resources/input.properties";

    public int inputCount; // Количество источников.
    public int bufferSize; // Размер буфера.
    public int deviceCount; // Количество приборов.
    public int requestCount; // Количество запросов.
    public double a; // Параметр a равномерного распределения.
    public double b; // Параметр b равномерного распределения.
    public double lambda; // Параметр lambda экспоненциального распределения.

    private Config() {

    }

    public Config(int inputCount, int bufferSize, int deviceCount, int requestCount, double a, double b,
                  double lambda) {
        this.inputCount = inputCount;
        this.bufferSize = bufferSize;
        this.deviceCount = deviceCount;
        this.requestCount = requestCount;
        this.a = a;
        this.b = b;
        this.lambda = lambda;
    }

    public static Config generateConfigFromProperties(String propertiesPath) throws IOException {
        Properties configProperties = new Properties();
        configProperties.load(new FileInputStream(propertiesPath));

        int inputCount = Integer.parseInt(configProperties.getProperty("input-count"));
        if (inputCount <= 0) {
            throw new IllegalArgumentException("Неположительное значение количества источников");
        }
        int bufferSize = Integer.parseInt(configProperties.getProperty("buffer-size"));
        if (bufferSize <= 0) {
            throw new IllegalArgumentException("Неположительное значение размера буфера");
        }
        int deviceCount = Integer.parseInt(configProperties.getProperty("device-count"));
        if (deviceCount <= 0) {
            throw new IllegalArgumentException("Неположительное значение количества приборов");
        }
        int requestCount = Integer.parseInt(configProperties.getProperty("request-count"));
        if (requestCount <= 0) {
            throw new IllegalArgumentException("Неположительное значение количества запросов");
        }
        double a = Double.parseDouble(configProperties.getProperty("a-param"));
        if (a < 0) {
            throw new IllegalArgumentException("Отрицательное значение параметра a");
        }
        double b = Double.parseDouble(configProperties.getProperty("b-param"));
        if (b < 0) {
            throw new IllegalArgumentException("Отрицательное значение параметра b");
        }
        if (a >= b) {
            throw new IllegalArgumentException("Параметр b не больше, чем параметр a");
        }
        double lambda = Double.parseDouble(configProperties.getProperty("lambda-param"));
        if (lambda == 0.0) {
            throw new IllegalArgumentException("Нулевой параметр lambda");
        }

        return new Config(inputCount, bufferSize, deviceCount, requestCount, a, b, lambda);
    }

    public static Config generateConfigFromProperties() {
        try {
            return generateConfigFromProperties(PROPERTIES_PATH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
