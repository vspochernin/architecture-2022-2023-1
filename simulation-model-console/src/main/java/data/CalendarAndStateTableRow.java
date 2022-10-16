package data;

import lombok.Builder;
import lombok.Data;

// Строка таблицы "Календарь/текущее состояние".
@Data
@Builder
public class CalendarAndStateTableRow {


    String event; // Описание события.
    double timeWhenHappen; // Абсолютное время, когда случится событие.
    int requestCount; // Число заявок (номер источника в случае прибора).
    int failureCount; // Число отказов (номер заявки в случае прибора).
}
