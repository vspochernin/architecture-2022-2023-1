package data;

import lombok.Builder;
import lombok.Data;

// Строчка в таблице "Характеристики источников ВС".
@Data
@Builder
public class InputCharacteristicTableRow {

    int inputNumber; // Номер источника.
    int countOfGeneratedRequests; // Количество сгенерированных заявок.
    double failureProbability; // Вероятность отказа.
    double avgStayTime; // Среднее время пребывания заявки в системе.
    double avgBufferTime; // Среднее время пребывания заявки в буфере.
    double avgServiceTime; // Среднее время обслуживания заявки.
    double avgBufferTimeVariance; // Дисперсия среднего времени пребывания заявки в буфере.
    double avgServiceTimeVariance; // Дисперсия среднего времени обслуживания заявки.
}
