package org.example.analyzer;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TextReader {
    private static final Logger logger = LogManager.getLogger(TextReader.class);

    public static String readFile(String filePath) {
        try {
            if (filePath.endsWith(".txt")) {
                logger.info("Чтение текстового файла: " + filePath);
                return new String(Files.readAllBytes(Paths.get(filePath)));
            } else if (filePath.endsWith(".pdf")) {
                logger.info("Чтение PDF файла: " + filePath);
                return readPdf(filePath);
            } else if (filePath.endsWith(".docx")) {
                logger.info("Чтение Word файла: " + filePath);
                return readDocx(filePath);
            } else {
                logger.warn("Формат файла не поддерживается: " + filePath);
            }
        } catch (IOException e) {
            logger.error("Ошибка при чтении файла: " + filePath, e);
        }
        return null;
    }

    private static String readPdf(String filePath) {
        try (PDDocument document = PDDocument.load(Paths.get(filePath).toFile())) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        } catch (IOException e) {
            logger.error("Ошибка при чтении PDF файла: " + filePath, e);
        }
        return null;
    }

    private static String readDocx(String filePath) {
        StringBuilder content = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(filePath);
             XWPFDocument document = new XWPFDocument(fis)) {

            List<XWPFParagraph> paragraphs = document.getParagraphs();
            for (XWPFParagraph paragraph : paragraphs) {
                content.append(paragraph.getText()).append("\n");
            }
        } catch (IOException e) {
            logger.error("Ошибка при чтении DOCX файла: " + filePath, e);
        }
        return content.toString();
    }
}
