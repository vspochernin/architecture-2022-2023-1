package simulation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Event implements Comparable<Event> {

    private final double timeWhenHappen; // Время, когда произойдет событие.
    private final EventType type; // Тип события.
    private final int unitNumber; // Номер устройства, на котором произошло событие.

    @Override
    public int compareTo(Event event) {
        if (this.timeWhenHappen >= event.timeWhenHappen) {
            return 1;
        } else {
            return -1;
        }
    }
}
