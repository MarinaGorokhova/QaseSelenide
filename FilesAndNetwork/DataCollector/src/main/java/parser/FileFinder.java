package parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileFinder {
    public static List<File> findFiles(String rootPath) {
        List<File> foundFiles = new ArrayList<>();
        File rootDir = new File(rootPath);
        if (!rootDir.exists() || !rootDir.isDirectory()) {
            System.out.println("Указанный путь не существует или не является директорией: " + rootPath);
            return foundFiles;
        }
        // Рекурсивный обход
        traverseDirectory(rootDir, foundFiles);
        return foundFiles;
    }

    private static void traverseDirectory(File dir, List<File> foundFiles) {
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                traverseDirectory(file, foundFiles);
            } else {
                String fileName = file.getName().toLowerCase();
                if (fileName.endsWith(".json") || fileName.endsWith(".csv")) {
                    foundFiles.add(file);
                }
            }
        }
    }

    // Для проверки
    public static void main(String[] args) {
        String path = "DataCollector/data"; // относительно корня проекта
        List<File> files = findFiles(path);
        System.out.println("Найдено файлов: " + files.size());
        for (File file : files) {
            System.out.println(file.getAbsolutePath());
        }
    }
}
