package simulation;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Event implements Comparable<Event>{

    private final double timeWhenHappen; // Время, когда произойдет событие.
    private final EventType type; // Тип события.

    @Override
    public int compareTo(Event event) {
        // TODO: Проверить, правильно ли поствлен знак.
        if (this.timeWhenHappen >= event.timeWhenHappen) {
            return 1;
        } else {
            return -1;
        }
    }
}
