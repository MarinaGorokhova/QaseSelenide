package parser;

import metro.CsvLine;
import metro.CsvStation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvFileParser {

    public CsvFileParser() {
        System.out.println("ИНИЦИАЛИЗАЦИЯ CSV ПАРСЕРА");
        List<File> csvFiles = CsvFileFinder.findCsvFiles(".");

        if (csvFiles.isEmpty()) {
            System.out.println("CSV файлы не найдены. Парсер будет работать в режиме ожидания файлов.");
        } else {
            System.out.println("Найдено CSV файлов: " + csvFiles.size());
            for (File file : csvFiles) {
                System.out.println(file.getName() + " (" + file.length() + " байт)");
            }
        }
        System.out.println();
    }

    /**
     * Парсит CSV файл со станциями метро
     */
    public List<CsvStation> parseStationsCsv(String filePath) throws IOException {
        System.out.println("Чтение CSV файла станций: " + filePath);

        List<CsvStation> stations = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            throw new IOException("Файл не найден: " + filePath);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true;
            String[] headers = null;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (isFirstLine) {
                    headers = parseCsvLine(line);
                    System.out.println("Заголовки: " + String.join(" | ", headers));
                    isFirstLine = false;
                    continue;
                }

                // Парсим строку данных
                String[] values = parseCsvLine(line);
                if (values.length >= 4) {
                    CsvStation station = createStationFromCsv(values, lineNumber);
                    if (station != null) {
                        stations.add(station);
                    }
                } else {
                    System.out.println("Пропущена строка " + lineNumber + ": недостаточно данных");
                }
            }
        }

        System.out.println("Успешно распаршено станций: " + stations.size());
        return stations;
    }

    /**
     * Создает объект станции из CSV строки
     */
    private CsvStation createStationFromCsv(String[] values, int lineNumber) {
        try {
            CsvStation station = new CsvStation();
            station.setName(values[0].trim());
            station.setLineNumber(values[1].trim());
            station.setDate(values[2].trim());
            station.setDepth(values[3].trim());

            if (values.length > 4) {
                String connectionValue = values[4].trim().toLowerCase();
                station.setHasConnection(
                        connectionValue.equals("true") ||
                                connectionValue.equals("да") ||
                                connectionValue.equals("1") ||
                                connectionValue.contains("пересадка") ||
                                connectionValue.contains("connection")
                );
            }

            return station;
        } catch (Exception e) {
            System.err.println("Ошибка создания станции из строки " + lineNumber + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * Парсит CSV файл с линиями метро
     */
    public List<CsvLine> parseLinesCsv(String filePath) throws IOException {
        System.out.println("Чтение CSV файла линий: " + filePath);
        List<CsvLine> lines = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            throw new IOException("Файл не найден: " + filePath);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true;
            String[] headers = null;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (isFirstLine) {
                    headers = parseCsvLine(line);
                    System.out.println("Заголовки: " + String.join(" | ", headers));
                    isFirstLine = false;
                    continue;
                }

                String[] values = parseCsvLine(line);
                if (values.length >= 2) {
                    CsvLine metroLine = createLineFromCsv(values, lineNumber);
                    if (metroLine != null) {
                        lines.add(metroLine);
                    }
                } else {
                    System.out.println("Пропущена строка " + lineNumber + ": недостаточно данных");
                }
            }
        }

        System.out.println("Успешно распаршено линий: " + lines.size());
        return lines;
    }

    /**
     * Создает объект линии из CSV строки
     */
    private CsvLine createLineFromCsv(String[] values, int lineNumber) {
        try {
            CsvLine metroLine = new CsvLine();
            metroLine.setNumber(values[0].trim());
            metroLine.setName(values[1].trim());

            if (values.length > 2) {
                metroLine.setColor(values[2].trim());
            }
            if (values.length > 3) {
                metroLine.setOpeningYear(values[3].trim());
            }

            return metroLine;
        } catch (Exception e) {
            System.err.println("Ошибка создания линии из строки " + lineNumber + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * Обрабатывает значения в кавычках и запятые внутри значений
     */
    private String[] parseCsvLine(String line) {
        List<String> result = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder field = new StringBuilder();

        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(field.toString());
                field.setLength(0);
            } else {
                field.append(c);
            }
        }

        // Добавляем последнее поле
        result.add(field.toString());
        return result.toArray(new String[0]);
    }

    /**
     * Универсальный парсер CSV файлов
     */
    public List<String[]> parseGenericCsv(String filePath) throws IOException {
        System.out.println("Чтение универсального CSV файла: " + filePath);
        List<String[]> result = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            throw new IOException("Файл не найден: " + filePath);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = parseCsvLine(line);
                result.add(values);
            }
        }

        System.out.println("Успешно распаршено строк: " + result.size());
        return result;
    }

    /**
     * Этот метод вызывается автоматически при создании парсера для демонстрации
     */
    public void runBuiltInSelfCheck() {
        System.out.println("ЗАПУСК ВСТРОЕННОЙ САМОПРОВЕРКИ CSV ПАРСЕРА");

        List<File> csvFiles = CsvFileFinder.findCsvFiles(".");

        if (csvFiles.isEmpty()) {
            System.out.println("Нет CSV файлов для тестирования");
            System.out.println("Создайте тестовые файлы с помощью TestCsvCreator");
            return;
        }

        System.out.println("Обнаружено CSV файлов: " + csvFiles.size());

        boolean allTestsPassed = true;

        for (File csvFile : csvFiles) {
            System.out.println("ТЕСТ ФАЙЛА: " + csvFile.getName());

            boolean fileTestPassed = testCsvFile(csvFile);
            if (!fileTestPassed) {
                allTestsPassed = false;
            }
        }

        // Финальный отчет
        if (allTestsPassed) {
            System.out.println("ВСЯ САМОПРОВЕРКА ПРОЙДЕНА УСПЕШНО!");
        } else {
            System.out.println("Самопроверка завершена с ошибками");
        }
    }

    /**
     * Тестирует конкретный CSV файл
     */
    private boolean testCsvFile(File csvFile) {
        String fileName = csvFile.getName().toLowerCase();

        try {
            if (fileName.contains("station") || fileName.contains("станц")) {
                return testStationsFile(csvFile);
            } else if (fileName.contains("line") || fileName.contains("линия")) {
                return testLinesFile(csvFile);
            } else {
                return testGenericFile(csvFile);
            }
        } catch (IOException e) {
            System.err.println("Ошибка тестирования файла " + csvFile.getName() + ": " + e.getMessage());
            return false;
        }
    }

    private boolean testStationsFile(File csvFile) throws IOException {
        System.out.println("ТИП: Файл станций метро");

        List<CsvStation> stations = parseStationsCsv(csvFile.getAbsolutePath());

        if (stations.isEmpty()) {
            System.out.println("ТЕСТ ПРОВАЛЕН: Файл не содержит данных о станциях");
            return false;
        }

        // Проверяем корректность данных
        boolean dataValid = true;
        for (CsvStation station : stations) {
            if (station.getName() == null || station.getName().isEmpty()) {
                System.out.println("Найдена станция без названия");
                dataValid = false;
            }
            if (station.getLineNumber() == null || station.getLineNumber().isEmpty()) {
                System.out.println("Найдена станция без номера линии");
                dataValid = false;
            }
        }

        if (!dataValid) {
            return false;
        }

        // Выводим результаты
        System.out.println("РЕЗУЛЬТАТЫ ПАРСИНГА СТАНЦИЙ:");
        System.out.println("   Всего станций: " + stations.size());

        long stationsWithConnection = stations.stream()
                .filter(CsvStation::isHasConnection)
                .count();
        System.out.println("   Станций с пересадками: " + stationsWithConnection);

        // Показываем примеры данных
        System.out.println("ПРИМЕРЫ ДАННЫХ (первые 3 станции):");
        for (int i = 0; i < Math.min(3, stations.size()); i++) {
            CsvStation station = stations.get(i);
            System.out.println("   " + (i + 1) + ". " + station.getName() +
                    " (линия " + station.getLineNumber() +
                    ", глубина: " + station.getDepth() +
                    ", пересадка: " + (station.isHasConnection() ? "да" : "нет") + ")");
        }

        // Статистика по линиям
        System.out.println("РАСПРЕДЕЛЕНИЕ СТАНЦИЙ ПО ЛИНИЯМ:");
        stations.stream()
                .collect(java.util.stream.Collectors.groupingBy(CsvStation::getLineNumber, java.util.stream.Collectors.counting()))
                .forEach((line, count) -> System.out.println("   Линия " + line + ": " + count + " станций"));

        System.out.println("ТЕСТ ФАЙЛА СТАНЦИЙ ПРОЙДЕН УСПЕШНО!");
        return true;
    }

    private boolean testLinesFile(File csvFile) throws IOException {
        System.out.println("ТИП: Файл линий метро");

        List<CsvLine> lines = parseLinesCsv(csvFile.getAbsolutePath());

        if (lines.isEmpty()) {
            System.out.println("ТЕСТ ПРОВАЛЕН: Файл не содержит данных о линиях");
            return false;
        }

        // Проверяем корректность данных
        boolean dataValid = true;
        for (CsvLine line : lines) {
            if (line.getNumber() == null || line.getNumber().isEmpty()) {
                System.out.println("Найдена линия без номера");
                dataValid = false;
            }
            if (line.getName() == null || line.getName().isEmpty()) {
                System.out.println("Найдена линия без названия");
                dataValid = false;
            }
        }

        if (!dataValid) {
            return false;
        }

        // Выводим результаты
        System.out.println("РЕЗУЛЬТАТЫ ПАРСИНГА ЛИНИЙ:");
        System.out.println("   Всего линий: " + lines.size());

        System.out.println("СПИСОК ЛИНИЙ:");
        for (CsvLine line : lines) {
            System.out.println("  Линия " + line.getNumber() + ": " + line.getName() +
                    (line.getColor() != null && !line.getColor().isEmpty() ?
                            " (цвет: " + line.getColor() + ")" : ""));
        }

        System.out.println("ТЕСТ ФАЙЛА ЛИНИЙ ПРОЙДЕН УСПЕШНО!");
        return true;
    }

    private boolean testGenericFile(File csvFile) throws IOException {
        System.out.println("ТИП: Универсальный CSV файл");

        List<String[]> data = parseGenericCsv(csvFile.getAbsolutePath());

        if (data.isEmpty()) {
            System.out.println("ТЕСТ ПРОВАЛЕН: Файл не содержит данных");
            return false;
        }

        System.out.println("СТРУКТУРА ДАННЫХ:");
        System.out.println("   Заголовки: " + String.join(" | ", data.get(0)));
        System.out.println("   Всего строк данных: " + (data.size() - 1));
        System.out.println("   Количество колонок: " + data.get(0).length);

        System.out.println("ПРИМЕРЫ ДАННЫХ (первые 3 строки):");
        for (int i = 1; i < Math.min(4, data.size()); i++) {
            System.out.println("   " + i + ". " + String.join(" | ", data.get(i)));
        }

        System.out.println("ТЕСТ УНИВЕРСАЛЬНОГО ФАЙЛА ПРОЙДЕН УСПЕШНО!");
        return true;
    }

    /**
     * Демонстрационный метод, показывающий работу парсера
     */
    public void demonstrateParser() {
        System.out.println("ДЕМОНСТРАЦИЯ РАБОТЫ CSV ПАРСЕРА");
        runBuiltInSelfCheck();
    }

    /**
     * Точка входа - при запуске класса автоматически проводится полная проверка
     */
    public static void main(String[] args) {
        System.out.println("ЗАПУСК CSV ПАРСЕРА С АВТОМАТИЧЕСКОЙ ПРОВЕРКОЙ");
        CsvFileParser parser = new CsvFileParser();
        parser.demonstrateParser();
        System.out.println("РАБОТА CSV ПАРСЕРА ЗАВЕРШЕНА");
    }
}