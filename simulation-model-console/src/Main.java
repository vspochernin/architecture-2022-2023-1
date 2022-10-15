import java.util.Scanner;

import static utils.Menu.showHelp;
import static utils.Menu.showIncorrectCommandMessage;

public class Main {
    public static void main(String[] args) {
        String command;
        Scanner scanner = new Scanner(System.in);

        showHelp();

        while (true) {
            System.out.println("Введите комманду: ");
            command = scanner.nextLine();

            if (command.equals("help")) {
                showHelp();
            } else if (command.equals("simulate")) {
                System.out.println("Просимулировали систему.");
                // TODO.
            } else if (command.equals("auto")) {
                System.out.println("Получили таблички авто-режима после симуляции.");
                // TODO.
            } else if (command.startsWith("step")) {
                String[] parts = command.split(" ");
                if (parts.length != 2) {
                    showIncorrectCommandMessage();
                } else {
                    try {
                        int n = Integer.parseInt(parts[1]);
                        System.out.println("Вывели " + n + "-й шаг.");
                        // TODO.
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
