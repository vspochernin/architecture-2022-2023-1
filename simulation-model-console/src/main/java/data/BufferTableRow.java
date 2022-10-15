package data;

import lombok.Builder;
import lombok.Data;

// Строка таблицы "Буфер".
@Data
@Builder
public class BufferTableRow {

    long position; // Позиция в буфере.
    double registrationTime; // Абсолютное время постановки в буфер.
    double inputNumber; // Номер источника, создавшего заявку.
    double requestNumber; // Порядковый номер заявки источника.
}
