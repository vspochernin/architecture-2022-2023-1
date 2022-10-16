package simulation;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

// Набор всех источников.
public class InputKit {

    @Getter
    private final List<Input> inputs;

    public InputKit(int count) {
        inputs = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            inputs.add(new Input(i));
        }
    }

    public int getCount() {
        return inputs.size();
    }

    public void startGenerating(int i, double generationTime) {
        inputs.get(i).setNextRequestGeneratedTime(generationTime);
    }
}
