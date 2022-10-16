package simulation;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

// Буфер.
public class Buffer {

    @Getter
    private final List<BufferPlace> bufferPlaces;

    public Buffer(int size) {
        bufferPlaces = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            bufferPlaces.add(new BufferPlace(i));
        }
    }
}
