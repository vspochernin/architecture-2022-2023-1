package ru.vspochernin.simulationmodel.data;

import lombok.Builder;
import lombok.Data;

// Строка таблицы "Календарь/текущее состояние".
@Data
@Builder
public class CalendarAndStateTableRow {


    String event; // Описание события.
    String timeWhenHappen; // Абсолютное время, когда случится событие.
    String requestCount; // Число заявок (номер источника в случае прибора).
    String failureCount; // Число отказов (номер заявки в случае прибора).
}
