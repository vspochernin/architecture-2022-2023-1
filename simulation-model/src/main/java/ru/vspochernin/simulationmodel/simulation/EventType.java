package ru.vspochernin.simulationmodel.simulation;

public enum EventType {
    REQUEST_GENERATION, // Поступление заявки в СМО.
    DEVICE_RELEASE, // Освобождение прибора (готовность взять заявку на обслуживание).
}
