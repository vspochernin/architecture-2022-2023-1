import java.util.Scanner;

import data.SimulationResult;

import static utils.Interface.getTestSimulationResult;
import static utils.Interface.showAutoResults;
import static utils.Interface.showHelp;
import static utils.Interface.showIncorrectCommandMessage;
import static utils.Interface.showStepResults;

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
                System.out.println("Просимулировали систему.");
                simulationResult = getTestSimulationResult();
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
