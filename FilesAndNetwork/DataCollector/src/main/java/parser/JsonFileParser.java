package parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import metro.MetroLine;
import metro.MetroStation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class JsonFileParser {

    private final Gson gson;

    public JsonFileParser() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    /**
     * Парсит JSON файл с линиями метро
     */
    public List<MetroLine> parseLines(String filePath) throws IOException {
        System.out.println("Поиск файла по пути: " + new File(filePath).getAbsolutePath());

        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("Файл не найден: " + filePath +
                    "Абсолютный путь: " + file.getAbsolutePath());
        }

        try (FileReader reader = new FileReader(file)) {
            Type listType = new TypeToken<List<MetroLine>>() {
            }.getType();
            List<MetroLine> lines = gson.fromJson(reader, listType);
            System.out.println("Успешно распаршено линий: " + (lines != null ? lines.size() : 0));
            return lines;
        }
    }

    /**
     * Парсит JSON файл со станциями метро
     */
    public List<MetroStation> parseStations(String filePath) throws IOException {
        System.out.println("Поиск файла по пути: " + new File(filePath).getAbsolutePath());

        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("Файл не найден: " + filePath +
                    "Абсолютный путь: " + file.getAbsolutePath());
        }

        try (FileReader reader = new FileReader(file)) {
            Type listType = new TypeToken<List<MetroStation>>() {
            }.getType();
            List<MetroStation> stations = gson.fromJson(reader, listType);
            System.out.println("Успешно распаршено станций: " + (stations != null ? stations.size() : 0));
            return stations;
        }
    }

    /**
     * МЕТОД САМОПРОВЕРКИ - проверяет работу парсера
     */
    public void selfTest() {
        System.out.println("ЗАПУСК САМОПРОВЕРКИ JSON ПАРСЕРА");

        // Показываем текущую рабочую директорию
        String currentDir = System.getProperty("user.dir");
        System.out.println("Текущая рабочая директория: " + currentDir);

        // Пробуем разные возможные пути к файлам
        String[] possiblePaths = {
                "DataCollector/data/lines.json",  // относительный путь из корня проекта
                "data/lines.json",                // относительный путь из DataCollector
                "./DataCollector/data/lines.json", // с указанием текущей директории
                "lines.json"                      // если файл в текущей директории
        };

        String linesPath = null;
        String stationsPath = null;

        // Ищем существующие файлы
        for (String path : possiblePaths) {
            File linesFile = new File(path);
            File stationsFile = new File(path.replace("lines.json", "stations.json"));

            if (linesFile.exists()) {
                linesPath = path;
                stationsPath = path.replace("lines.json", "stations.json");
                System.out.println("Найден файл lines.json: " + linesFile.getAbsolutePath());
                break;
            }
        }

        if (linesPath == null) {
            System.out.println("Файлы lines.json и stations.json не найдены по следующим путям:");
            for (String path : possiblePaths) {
                System.out.println("   - " + new File(path).getAbsolutePath());
            }
            System.out.println("РЕКОМЕНДАЦИИ:");
            System.out.println("   1. Убедитесь, что файлы lines.json и stations.json существуют");
            System.out.println("   2. Запустите WebPageParser для создания этих файлов");
            System.out.println("   3. Проверьте текущую рабочую директорию");
            return;
        }

        try {
            // Тест 1: Проверка парсинга lines.json
            System.out.println("ТЕСТ 1: Парсинг " + linesPath);
            List<MetroLine> lines = parseLines(linesPath);

            if (lines == null || lines.isEmpty()) {
                System.out.println("ТЕСТ ПРОВАЛЕН: Не удалось распарсить lines.json");
                return;
            }

            // Проверяем структуру данных
            System.out.println("Проверка структуры данных линий:");
            for (int i = 0; i < Math.min(3, lines.size()); i++) {
                MetroLine line = lines.get(i);
                System.out.println("   Линия " + (i + 1) + ": номер='" + line.getNumber() + "', название='" + line.getName() + "'");
            }

            // Тест 2: Проверка парсинга stations.json
            System.out.println("ТЕСТ 2: Парсинг " + stationsPath);
            List<MetroStation> stations = parseStations(stationsPath);

            if (stations == null || stations.isEmpty()) {
                System.out.println("ТЕСТ ПРОВАЛЕН: Не удалось распарсить stations.json");
                return;
            }

            // Проверяем структуру данных станций
            System.out.println("Проверка структуры данных станций:");
            for (int i = 0; i < Math.min(3, stations.size()); i++) {
                MetroStation station = stations.get(i);
                System.out.println("   Станция " + (i + 1) + ": название='" + station.getName() + "', линия='" + station.getLineNumber() + "'");
            }

            // Тест 3: Проверка связи данных
            System.out.println("ТЕСТ 3: Проверка целостности данных");
            boolean dataIntegrity = checkDataIntegrity(lines, stations);
            if (!dataIntegrity) {
                System.out.println("ТЕСТ ПРОВАЛЕН: Нарушена целостность данных");
                return;
            }

            System.out.println("ВСЕ ТЕСТЫ ПРОЙДЕНЫ УСПЕШНО!");
            printFinalReport(lines, stations);

        } catch (IOException e) {
            System.err.println("ОШИБКА ВВОДА-ВЫВОДА: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("НЕИЗВЕСТНАЯ ОШИБКА: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Проверяет целостность данных между линиями и станциями
     */
    private boolean checkDataIntegrity(List<MetroLine> lines, List<MetroStation> stations) {
        if (lines == null || stations == null) {
            return false;
        }

        boolean integrityOk = true;

        // Проверяем, что все номера линий из станций существуют в списке линий
        for (MetroStation station : stations) {
            String lineNumber = station.getLineNumber();
            boolean lineExists = lines.stream()
                    .anyMatch(line -> line.getNumber().equals(lineNumber));

            if (!lineExists) {
                System.out.println("Нарушение целостности: станция '" + station.getName() +
                        "' ссылается на несуществующую линию '" + lineNumber + "'");
                integrityOk = false;
            }
        }

        // Проверяем статистику по линиям
        System.out.println("Статистика по линиям:");
        for (MetroLine line : lines) {
            long stationCount = stations.stream()
                    .filter(station -> station.getLineNumber().equals(line.getNumber()))
                    .count();
            System.out.printf("   %-25s: %2d станций\n", line.getName(), stationCount);
        }

        return integrityOk;
    }

    /**
     * Выводит финальный отчет после успешной проверки
     */
    private void printFinalReport(List<MetroLine> lines, List<MetroStation> stations) {
        System.out.println("ФИНАЛЬНЫЙ ОТЧЕТ:");
        System.out.println("   Всего линий метро: " + lines.size());
        System.out.println("   Всего станций метро: " + stations.size());
    }

    /**
     * Вспомогательный метод для поиска всех JSON файлов в проекте
     */
    public void findJsonFiles() {
        System.out.println("ПОИСК JSON ФАЙЛОВ В ПРОЕКТЕ");

        String currentDir = System.getProperty("user.dir");
        System.out.println("Текущая директория: " + currentDir);

        searchJsonFiles(new File(currentDir), 0);
    }

    private void searchJsonFiles(File dir, int depth) {
        if (depth > 5) return; // Ограничиваем глубину поиска

        File[] files = dir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                searchJsonFiles(file, depth + 1);
            } else if (file.getName().toLowerCase().endsWith(".json")) {
                System.out.println(file.getAbsolutePath() + " (размер: " + file.length() + " байт)");
            }
        }
    }

    /**
     * Точка входа для самостоятельного тестирования
     */
    public static void main(String[] args) {
        JsonFileParser parser = new JsonFileParser();
        parser.findJsonFiles();
        System.out.println("\n");
        parser.selfTest();
    }
}