package data;

import lombok.Builder;
import lombok.Data;

// Строчка в таблице "Характеристики приборов ВС".
@Data
@Builder
public class DeviceCharacteristicTableRow {

    long number; // Номер прибора.
    double utilizationRate; // Коэффициент использования.
}
