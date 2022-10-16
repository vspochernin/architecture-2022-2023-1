package simulation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BufferPlace {

    private final int positionNumber; // Номер позиции.

    private double registrationTime = -1; // Время постановки в буфер.
    private int inputNumber = -1; // Номер источника, создавшего заявку.
    private int requestNumber = -1; // Порядковый номер заявки источника.
}
