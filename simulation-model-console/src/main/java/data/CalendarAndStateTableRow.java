package data;

import lombok.Builder;
import lombok.Data;

// Строка таблицы "Календарь/текущее состояние".
@Data
@Builder
public class CalendarAndStateTableRow {


    String event; // Описание события.
    double time; // Абсолютное время, когда случится событие.
    int requestCount; // Число заявок.
    int failureCount; // Число отказов.
}
