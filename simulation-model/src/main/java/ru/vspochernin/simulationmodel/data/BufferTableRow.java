package ru.vspochernin.simulationmodel.data;

import lombok.Builder;
import lombok.Data;

// Строка таблицы "Буфер".
@Data
@Builder
public class BufferTableRow {

    String positionNumber; // Позиция в буфере.
    String registrationTime; // Абсолютное время постановки заявки в буфер.
    String inputNumber; // Номер источника, создавшего заявку.
    String requestNumber; // Порядковый номер заявки источника.
}
