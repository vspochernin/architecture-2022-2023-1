package ru.vspochernin.simulationmodel.research;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ru.vspochernin.simulationmodel.simulation.Config;

public class Tests {

    public static double[] aArray = {10, 12, 15};
    public static double[] bArray = {12, 15, 24};
    public static double[] lambdaArray = {0.001, 0.003, 0.005};
    public static int requestNumber = 10000;

    public static void main(String[] args) {
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Results");

        Row header = sheet.createRow(0);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Номер конфигурации");

        headerCell = header.createCell(1);
        headerCell.setCellValue("Количество источников");

        headerCell = header.createCell(2);
        headerCell.setCellValue("Количество мест буфера");

        headerCell = header.createCell(3);
        headerCell.setCellValue("Количество приборов");

        headerCell = header.createCell(4);
        headerCell.setCellValue("Количество заявок");

        headerCell = header.createCell(5);
        headerCell.setCellValue("Параметр a");

        headerCell = header.createCell(6);
        headerCell.setCellValue("Параметр b");

        headerCell = header.createCell(7);
        headerCell.setCellValue("Параметр лямбда");

        headerCell = header.createCell(8);
        headerCell.setCellValue("Тип коров");

        headerCell = header.createCell(9);
        headerCell.setCellValue("Тип заквасок");

        headerCell = header.createCell(10);
        headerCell.setCellValue("Средняя вероятность отказа");

        headerCell = header.createCell(11);
        headerCell.setCellValue("Среднее время в СМО");

        headerCell = header.createCell(12);
        headerCell.setCellValue("Среднее время в буфере");

        headerCell = header.createCell(13);
        headerCell.setCellValue("Коэффициент использования прибора");

        headerCell = header.createCell(14);
        headerCell.setCellValue("Общее время симуляции");

        int counter = 1;
        for (int inputCount = 1; inputCount <= 3; inputCount++) {
            for (int bufferCount = 30; bufferCount <= 50; bufferCount++) {
                for (int deviceCount = 40; deviceCount <= 60; deviceCount++) {
                    for (int abNumber = 0; abNumber <= 2; abNumber++) {
                        for (int lambdaNumber = 0; lambdaNumber <= 2; lambdaNumber++) {
                            Config config = new Config(
                                    inputCount,
                                    bufferCount,
                                    deviceCount,
                                    requestNumber,
                                    aArray[abNumber],
                                    bArray[abNumber],
                                    lambdaArray[lambdaNumber]
                            );
                            OutputCharacteristics outputCharacteristics = new OutputCharacteristics(config);


                            Row newRow = sheet.createRow(counter++);


                            Cell newCell = newRow.createCell(0);
                            newCell.setCellValue(counter - 1);

                            newCell = newRow.createCell(1);
                            newCell.setCellValue(inputCount);

                            newCell = newRow.createCell(2);
                            newCell.setCellValue(bufferCount);

                            newCell = newRow.createCell(3);
                            newCell.setCellValue(deviceCount);

                            newCell = newRow.createCell(4);
                            newCell.setCellValue(requestNumber);

                            newCell = newRow.createCell(5);
                            newCell.setCellValue(aArray[abNumber]);

                            newCell = newRow.createCell(6);
                            newCell.setCellValue(bArray[abNumber]);

                            newCell = newRow.createCell(7);
                            newCell.setCellValue(lambdaArray[lambdaNumber]);

                            newCell = newRow.createCell(8);
                            newCell.setCellValue(outputCharacteristics.getInputType().ordinal());

                            newCell = newRow.createCell(9);
                            newCell.setCellValue(outputCharacteristics.getDeviceType().ordinal());

                            newCell = newRow.createCell(10);
                            newCell.setCellValue(outputCharacteristics.getAvgFailProb());

                            newCell = newRow.createCell(11);
                            newCell.setCellValue(outputCharacteristics.getAvgStayTime());

                            newCell = newRow.createCell(12);
                            newCell.setCellValue(outputCharacteristics.getAvgBuffTime());

                            newCell = newRow.createCell(13);
                            newCell.setCellValue(outputCharacteristics.getAvgUtilRate());

                            newCell = newRow.createCell(14);
                            newCell.setCellValue(outputCharacteristics.getSimulationTime());

                            System.out.println(counter);
                        }
                    }
                }
            }
        }
        System.out.println(counter);

        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(Path.of("test.xlsx").toFile());
            workbook.write(outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
