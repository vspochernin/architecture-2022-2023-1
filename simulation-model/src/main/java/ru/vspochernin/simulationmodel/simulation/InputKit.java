package ru.vspochernin.simulationmodel.simulation;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

// Набор всех источников.
@Getter
public class InputKit {

    private final List<Input> inputs;

    private int activeInputCount = 0; // Источники, которые в данный момент генерируют заявки.

    public InputKit(int count) {
        inputs = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            inputs.add(new Input(i));
        }
    }

    // Получить количество источников.
    public int getCount() {
        return inputs.size();
    }

    // Начать генерировать заявку i-м источником.
    public void startGenerating(int i, double generationTime) {
        inputs.get(i).startGenerating(generationTime);
        activeInputCount++;
    }

    // Сгенерировать заявку.
    public Request generateRequest(int inputNumber, double currentTime) {
        activeInputCount--;
        return inputs.get(inputNumber).generateRequest(currentTime);
    }
}
