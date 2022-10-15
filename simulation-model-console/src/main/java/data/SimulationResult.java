package data;

import java.util.List;

import lombok.Builder;
import lombok.Data;

// Результат моделирования.

@Builder
@Data
public class SimulationResult {

    private List<StepData> steps; // Данные для каждого шага.
    private List<InputCharacteristicTableRow> inputCharacteristicTableRows; // Данные для таблицы характеристик источников ВС.
    private List<DeviceCharacteristicTableRow> deviceCharacteristicTableRows; // Данные для таблицы характеристик приборов ВС.
}
