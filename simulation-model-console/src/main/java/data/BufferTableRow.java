package data;

import lombok.Builder;
import lombok.Data;

// Строка таблицы "Буфер".
@Data
@Builder
public class BufferTableRow {

    int positionNumber; // Позиция в буфере.
    double registrationTime; // Абсолютное время постановки заявки в буфер.
    int inputNumber; // Номер источника, создавшего заявку.
    int requestNumber; // Порядковый номер заявки источника.
}
