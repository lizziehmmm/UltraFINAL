package org.example;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.example.analyzer.TextAnalyzer;
import org.example.analyzer.TextReader;
import org.example.analyzer.ChartGenerator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class MainApp {
    private static final Logger logger = LogManager.getLogger(MainApp.class);

    public static void main(String[] args) {

        logger.info("Запуск приложения");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите путь к файлу:");
        String filePath = scanner.nextLine().replace("\"", "");

        logger.info("Получен путь к файлу: " + filePath);

        // Чтение файла
        String text = TextReader.readFile(filePath);
        if (text == null || text.isEmpty()) {
            System.out.println("Файл пуст или не может быть прочитан.");
            logger.error("Ошибка чтения файла: файл пуст или не существует.");
            return;
        }

        // Анализ текста
        Map<String, Integer> analysisResult = TextAnalyzer.analyzeText(text);
        System.out.println("Результат анализа:");
        analysisResult.forEach((k, v) -> System.out.println(k + " - " + v));

        // Сохранение результатов статистики
        saveStatisticsToFile(analysisResult, "statistics.txt");

        // Генерация графика
        String chartFile = "chart.png";
        ChartGenerator.createChart(analysisResult, chartFile);
        logger.info("График сохранён в файл: " + chartFile);
    }

    private static void saveStatisticsToFile(Map<String, Integer> statistics, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            logger.info("Попытка сохранения статистики в файл: " + fileName);
            for (Map.Entry<String, Integer> entry : statistics.entrySet()) {
                writer.write(entry.getKey() + " - " + entry.getValue());
                writer.newLine();
            }
            logger.info("Статистика успешно сохранена в файл: " + fileName);
        } catch (IOException e) {
            logger.error("Ошибка при сохранении статистики в файл: " + fileName, e);
        }
    }
}