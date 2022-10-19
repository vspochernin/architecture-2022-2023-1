package ru.vspochernin.simulationmodel;

import java.util.Scanner;

import ru.vspochernin.simulationmodel.data.SimulationResult;
import ru.vspochernin.simulationmodel.simulation.Config;
import ru.vspochernin.simulationmodel.simulation.Simulator;

import static ru.vspochernin.simulationmodel.utils.UI.showAutoResults;
import static ru.vspochernin.simulationmodel.utils.UI.showHelp;
import static ru.vspochernin.simulationmodel.utils.UI.showIncorrectCommandMessage;
import static ru.vspochernin.simulationmodel.utils.UI.showStepResults;

public class Main {

    public static void main(String[] args) {
        String command;
        Scanner scanner = new Scanner(System.in);
        SimulationResult simulationResult = null;

        showHelp();
        while (true) {
            System.out.println("Введите комманду: ");
            command = scanner.nextLine();

            if (command.equals("help")) {
                showHelp();
            } else if (command.equals("simulate")) {
                try {
                    simulationResult = Simulator.simulate(Config.generateConfigFromProperties("src/main/resources" +
                            "/input.properties"));
                } catch (Exception e) {
                    System.out.println("Проблема при симуляции: " + e.getMessage());
                }
                System.out.println("Система успешно просимулирована.");
                System.out.println("Количество шагов: " + simulationResult.getSteps().size());
                System.out.println("Время окончания моделирования: " + simulationResult.getModelingTime());
                System.out.println("Общее время реализации: " + simulationResult.getTotalSimulationTime());
            } else if (command.equals("auto")) {
                if (simulationResult == null) {
                    System.out.println("Результаты еще не готовы.");
                } else {
                    showAutoResults(simulationResult);
                }
            } else if (command.startsWith("step")) {
                String[] parts = command.split(" ");
                if (parts.length != 2) {
                    showIncorrectCommandMessage();
                } else if (simulationResult == null) {
                    System.out.println("Результаты еще не готовы.");
                } else {
                    try {
                        int n = Integer.parseInt(parts[1]);
                        if (n < 0 || n >= simulationResult.getSteps().size()) {
                            System.out.println("Такой шаг не существует");
                        } else {
                            showStepResults(simulationResult, n);
                        }
                    } catch (NumberFormatException e) {
                        showIncorrectCommandMessage();
                    }
                }
            } else if (command.equals("exit")) {
                System.out.println("Программа завершается.");
                break;
            } else {
                showIncorrectCommandMessage();
            }
        }
    }
}
