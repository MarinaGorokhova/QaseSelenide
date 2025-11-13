package parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CsvFileFinder {

    /**
     * Находит все CSV файлы в указанной директории и поддиректориях
     */
    public static List<File> findCsvFiles(String rootPath) {
        List<File> foundFiles = new ArrayList<>();
        File rootDir = new File(rootPath);

        if (!rootDir.exists() || !rootDir.isDirectory()) {
            System.out.println("Указанный путь не существует или не является директорией: " + rootPath);
            return foundFiles;
        }

        traverseDirectory(rootDir, foundFiles);
        return foundFiles;
    }

    private static void traverseDirectory(File dir, List<File> foundFiles) {
        File[] files = dir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                traverseDirectory(file, foundFiles);
            } else {
                String fileName = file.getName().toLowerCase();
                if (fileName.endsWith(".csv")) {
                    foundFiles.add(file);
                }
            }
        }
    }

    /**
     * Показывает информацию о найденных CSV файлах
     */
    public static void analyzeCsvFiles(String rootPath) {
        System.out.println("АНАЛИЗ CSV ФАЙЛОВ В ПРОЕКТЕ");
        List<File> csvFiles = findCsvFiles(rootPath);

        if (csvFiles.isEmpty()) {
            System.out.println("CSV файлы не найдены");
            return;
        }

        System.out.println("Найдено CSV файлов: " + csvFiles.size());

        for (File csvFile : csvFiles) {
            System.out.println("Файл: " + csvFile.getName());
            System.out.println("   Путь: " + csvFile.getAbsolutePath());
            System.out.println("   Размер: " + csvFile.length() + " байт");
        }
    }

    public static void main(String[] args) {
        analyzeCsvFiles(".");
    }
}
