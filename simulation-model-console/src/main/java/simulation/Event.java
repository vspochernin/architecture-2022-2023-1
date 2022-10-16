package simulation;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Event implements Comparable<Event>{

    private final double time; // Время, когда произойдет событие.
    private final EventType type; // Тип события.

    @Override
    public int compareTo(Event event) {
        if (this.time >= event.time) {
            return 1;
        } else {
            return -1;
        }
    }
}
