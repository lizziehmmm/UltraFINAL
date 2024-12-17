package org.example.analyzer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ChartGenerator {
    public static void createChart(Map<String, Integer> statistics, String fileName) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        statistics.forEach((k, v) -> dataset.addValue(v, k, ""));

        JFreeChart chart = ChartFactory.createBarChart("Статистика", "Темы", "Количество", dataset);
        try {
            ChartUtils.saveChartAsPNG(new File(fileName), chart, 800, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
