package simulation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// Класс заявки.
@RequiredArgsConstructor
@Getter
public class Request {

    private final int inputNumber; // Номер источника, который сгенерировал эту заявку.
    private final int requestNumber; // Порядковый номер заявки.
    private final Input input; // Источник, сгенерировавший заявку.

    private BufferPlace bufferPlace = null; // Место в буфере, на котором оказалась заявка.
    private Device device = null; // Прибор, на котором оказалась заявка.

    @Override
    public String toString() {
        return "(" + inputNumber + "; " + requestNumber + ")";
    }
}
