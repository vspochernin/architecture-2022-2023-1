package simulation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

// Класс заявки.
@RequiredArgsConstructor
@Getter
@Setter
public class Request {

    private final int inputNumber; // Номер источника, который сгенерировал эту заявку.
    private final int requestNumber; // Порядковый номер заявки.
    private final Input input; // Источник, сгенерировавший заявку.
    private final double creationTime; // Время создания заявки.

    private BufferPlace bufferPlace = null; // Место в буфере, на котором оказалась заявка.
    private Device device = null; // Прибор, на котором оказалась заявка.


    private double bufferPutTime = -1; // Время постановки заявки в буфер.
    private double bufferReleaseTime = -1; // Время снятия заявки из буфера.

    private double devicePutTime = -1; // Время постановки заявки на прибор.
    private double deviceReleaseTime = -1; // Время снятия заявки с прибора.

    // Подсчитать результаты пребывания заявки в системе.
    public void calculate() {
        // TODO: подсчитать.
    }

    @Override
    public String toString() {
        return "(" + inputNumber + "; " + requestNumber + ")";
    }
}
