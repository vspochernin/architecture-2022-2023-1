package data;

import lombok.Builder;
import lombok.Data;

// Строчка в таблице "Характеристики источников ВС".
@Data
@Builder
public class InputCharacteristicTableRow {

    long number; // Номер источника.
    long countOfRequests; // Количество сгенерированных заявок.
    double failureProbability; // Вероятность отказа.
    double stayTime; // Среднее время пребывания заявки в системе.
    double bufferTime; // Среднее время пребывания заявки в буфере.
    double serviceTime; // Среднее время обслуживания заявки.
    double bufferVariance; // Дисперсия среднего времени пребывания заявки в буфере.
    double serviceVariance; // Дисперсия
}
