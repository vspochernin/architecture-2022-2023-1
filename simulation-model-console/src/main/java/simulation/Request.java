package simulation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// Класс заявки.
@RequiredArgsConstructor
@Getter
public class Request {

    private final int inputNumber; // Номер источника, который сгенерировал эту заявку.
    private final int requestNumber; // Порядковый номер заявки.
}
