package utils;

public class Menu {
    public static void showHelp() {
        System.out.println("Доступные команды:");
        System.out.println("help - показать помощь");
        System.out.println("simulate - запустить процесс симуляции");
        System.out.println("auto - получить результаты симуляции (автоматический режим)");
        System.out.println("step n - получить информацию n-го шага");
        System.out.println("exit - завершить программу");
    }

    public static void showIncorrectCommandMessage() {
        System.out.println("Некорректная команда.");
    }
}
