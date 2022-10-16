package data;

import lombok.Builder;
import lombok.Data;

// Строка в таблице "Характеристики приборов ВС".
@Data
@Builder
public class DeviceCharacteristicTableRow {

    int deviceNumber; // Номер прибора.
    double utilizationRate; // Коэффициент использования.
}
