package data;

import lombok.Builder;
import lombok.Data;

// Строчка в таблице "Характеристики приборов ВС".
@Data
@Builder
public class DeviceCharacteristicTableRow {

    int number; // Номер прибора.
    double utilizationRate; // Коэффициент использования.
}
