package data;

import java.util.List;

import lombok.Builder;
import lombok.Data;

// Информация по конкретному шагу.
@Data
@Builder
public class StepData {

    private String description; // Описание шага.
    private List<CalendarAndStateTableRow> calendarAndStateTableRows; // Данные для таблицы "Календарь/текущее состояние".
    private List<BufferTableRow> bufferTableRows; // Данные для таблицы "Буфер".
}
