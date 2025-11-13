package writer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import metro.MetroLine;
import metro.MetroStation;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MetroDataWriter {

    private final List<MetroLine> lines;
    private final List<MetroStation> stations;
    private final Gson gson;

    public MetroDataWriter(List<MetroLine> lines, List<MetroStation> stations) {
        this.lines = lines != null ? lines : new ArrayList<>();
        this.stations = stations != null ? stations : new ArrayList<>();
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    // Внутренние классы для JSON структур
    private static class MetroMap {
        Map<String, List<String>> stations = new HashMap<>();
        List<List<Connection>> connections = new ArrayList<>();
        List<LineInfo> lines = new ArrayList<>();
    }

    private static class LineInfo {
        String number;
        String name;
        String color;

        LineInfo(String number, String name, String color) {
            this.number = number;
            this.name = name;
            this.color = color;
        }
    }

    private static class Connection {
        String line;
        String station;

        Connection(String line, String station) {
            this.line = line;
            this.station = station;
        }
    }

    private static class StationsData {
        List<StationDetails> stations = new ArrayList<>();
    }

    private static class StationDetails {
        String name;
        String line;
        String date;
        Double depth;
        Boolean hasConnection;

        StationDetails(String name, String line, String date, Double depth, Boolean hasConnection) {
            this.name = name;
            this.line = line;
            this.date = date;
            this.depth = depth;
            this.hasConnection = hasConnection;
        }
    }

    /**
     * СОЗДАНИЕ ФАЙЛА map.json в формате SPBMetro
     */
    public void writeMapJson(String filePath) throws IOException {
        MetroMap metroMap = new MetroMap();

        // 1. Формируем станции по линиям
        Map<String, List<String>> stationsByLine = new TreeMap<>();

        for (MetroLine line : lines) {
            String lineNumber = line.getNumber();
            List<String> lineStations = stations.stream()
                    .filter(station -> lineNumber.equals(station.getLineNumber()))
                    .map(MetroStation::getName)
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());
            stationsByLine.put(lineNumber, lineStations);
        }
        metroMap.stations = stationsByLine;

        // 2. Формируем информацию о линиях
        List<LineInfo> lineInfos = new ArrayList<>();
        for (MetroLine line : lines) {
            String color = generateColorForLine(line.getNumber());
            lineInfos.add(new LineInfo(line.getNumber(), line.getName(), color));
        }
        metroMap.lines = lineInfos;

        // 3. Находим пересадочные станции
        metroMap.connections = findConnections();

        // Записываем в файл
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(metroMap, writer);
        }
    }

    /**
     * СОЗДАНИЕ ФАЙЛА stations.json с детальной информацией
     */
    public void writeStationsJson(String filePath) throws IOException {
        StationsData stationsData = new StationsData();

        // Определяем пересадочные станции
        Set<String> transferStations = findTransferStations();

        for (MetroStation station : stations) {
            String stationName = station.getName();
            String lineNumber = station.getLineNumber();

            // Находим название линии по номеру
            String lineName = lines.stream()
                    .filter(line -> lineNumber.equals(line.getNumber()))
                    .map(MetroLine::getName)
                    .findFirst()
                    .orElse("Неизвестная линия");

            // Создаем детальную информацию о станции
            StationDetails details = createStationDetails(stationName, lineName, lineNumber, transferStations);
            stationsData.stations.add(details);
        }

        // Записываем в файл
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(stationsData, writer);

            // Показываем примеры созданных данных
            for (int i = 0; i < Math.min(3, stationsData.stations.size()); i++) {
                StationDetails example = stationsData.stations.get(i);
                System.out.print("   " + (i + 1) + ". " + example.name + " (линия: " + example.line);
                if (example.date != null) System.out.print(", дата: " + example.date);
                if (example.depth != null) System.out.print(", глубина: " + example.depth);
                if (example.hasConnection != null) System.out.print(", пересадка: " + example.hasConnection);
                System.out.println(")");
            }
        }
    }

    /**
     * ВСТРОЕННАЯ ПРОВЕРКА СОЗДАННЫХ ФАЙЛОВ
     */
    public void selfTest(String outputDir) {
        System.out.println("ЗАПУСК ВСТРОЕННОЙ ПРОВЕРКИ СОЗДАННЫХ ФАЙЛОВ");

        String mapJsonPath = outputDir + "/map.json";
        String stationsJsonPath = outputDir + "/stations.json";

        boolean mapTestPassed = testMapJson(mapJsonPath);
        boolean stationsTestPassed = testStationsJson(stationsJsonPath);

        System.out.println("ИТОГИ ПРОВЕРКИ:");
        System.out.println("   map.json: " + (mapTestPassed ? " ПРОЙДЕН" : " НЕ ПРОЙДЕН"));
        System.out.println("   stations.json: " + (stationsTestPassed ? " ПРОЙДЕН" : " НЕ ПРОЙДЕН"));

        if (mapTestPassed && stationsTestPassed) {
            System.out.println("ВСЕ ПРОВЕРКИ ПРОЙДЕНЫ УСПЕШНО!");
        } else {
            System.out.println("Обнаружены проблемы в созданных файлах!");
        }
    }

    /**
     * ПРОВЕРКА ФАЙЛА map.json
     */
    private boolean testMapJson(String filePath) {
        System.out.println("ПРОВЕРКА map.json");

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println(" Файл не существует: " + filePath);
                return false;
            }

            JsonObject jsonObject = JsonParser.parseReader(new FileReader(file)).getAsJsonObject();

            // Проверяем обязательные поля
            boolean hasStations = jsonObject.has("stations");
            boolean hasConnections = jsonObject.has("connections");
            boolean hasLines = jsonObject.has("lines");

            System.out.println("📋 Проверка структуры:");
            System.out.println("   stations: " + (hasStations ? "✅" : "❌"));
            System.out.println("   connections: " + (hasConnections ? "✅" : "❌"));
            System.out.println("   lines: " + (hasLines ? "✅" : "❌"));

            if (!hasStations || !hasConnections || !hasLines) {
                return false;
            }

            // Проверяем структуру stations
            JsonObject stationsObj = jsonObject.getAsJsonObject("stations");
            System.out.println(" Станции по линиям: " + stationsObj.keySet().size() + " линий");

            int totalStations = 0;
            for (String lineNumber : stationsObj.keySet()) {
                JsonArray lineStations = stationsObj.getAsJsonArray(lineNumber);
                int stationCount = lineStations.size();
                totalStations += stationCount;
                System.out.println("   Линия " + lineNumber + ": " + stationCount + " станций");

                // Проверяем, что станции - это строки
                for (JsonElement station : lineStations) {
                    if (!station.isJsonPrimitive() || !station.getAsJsonPrimitive().isString()) {
                        System.out.println("Станция не является строкой: " + station);
                        return false;
                    }
                }
            }
            System.out.println("Всего станций в файле: " + totalStations);

            // Проверяем структуру connections
            JsonArray connectionsArray = jsonObject.getAsJsonArray("connections");
            System.out.println("Пересадочные узлы: " + connectionsArray.size());

            // Проверяем структуру lines
            JsonArray linesArray = jsonObject.getAsJsonArray("lines");
            System.out.println("Информация о линиях: " + linesArray.size() + " линий");

            for (JsonElement lineElem : linesArray) {
                JsonObject lineObj = lineElem.getAsJsonObject();
                boolean hasNumber = lineObj.has("number");
                boolean hasName = lineObj.has("name");
                boolean hasColor = lineObj.has("color");

                if (!hasNumber || !hasName || !hasColor) {
                    System.out.println("Линия не содержит всех обязательных полей");
                    return false;
                }
            }

            // Проверяем соответствие данных
            boolean dataConsistency = checkMapDataConsistency(jsonObject);
            if (!dataConsistency) {
                System.out.println("Нарушена целостность данных в map.json");
                return false;
            }

            System.out.println("map.json соответствует формату SPBMetro");
            return true;

        } catch (Exception e) {
            System.err.println("Ошибка при проверке map.json: " + e.getMessage());
            return false;
        }
    }

    /**
     * ПРОВЕРКА ФАЙЛА stations.json
     */
    private boolean testStationsJson(String filePath) {
        System.out.println("ПРОВЕРКА stations.json");

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("Файл не существует: " + filePath);
                return false;
            }

            JsonObject jsonObject = JsonParser.parseReader(new FileReader(file)).getAsJsonObject();

            if (!jsonObject.has("stations")) {
                System.out.println("Отсутствует поле stations");
                return false;
            }

            JsonArray stationsArray = jsonObject.getAsJsonArray("stations");
            System.out.println("Всего станций в файле: " + stationsArray.size());

            int validStations = 0;
            int stationsWithDate = 0;
            int stationsWithDepth = 0;
            int stationsWithConnection = 0;

            for (JsonElement stationElem : stationsArray) {
                if (!stationElem.isJsonObject()) {
                    System.out.println("Станция не является объектом");
                    continue;
                }

                JsonObject stationObj = stationElem.getAsJsonObject();

                // Проверяем обязательные поля
                boolean hasName = stationObj.has("name");
                boolean hasLine = stationObj.has("line");

                if (!hasName || !hasLine) {
                    System.out.println("Станция без обязательных полей: " + stationObj);
                    continue;
                }

                // Проверяем опциональные поля
                if (stationObj.has("date")) {
                    stationsWithDate++;
                    String date = stationObj.get("date").getAsString();
                    if (!date.matches("\\d{2}\\.\\d{2}\\.\\d{4}")) {
                        System.out.println("Неверный формат даты: " + date);
                    }
                }

                if (stationObj.has("depth")) {
                    stationsWithDepth++;
                    try {
                        stationObj.get("depth").getAsDouble();
                    } catch (Exception e) {
                        System.out.println("Глубина не является числом: " + stationObj.get("depth"));
                    }
                }

                if (stationObj.has("hasConnection")) {
                    stationsWithConnection++;
                    if (!stationObj.get("hasConnection").isJsonPrimitive() ||
                            !stationObj.get("hasConnection").getAsJsonPrimitive().isBoolean()) {
                        System.out.println("hasConnection не является boolean: " + stationObj.get("hasConnection"));
                    }
                }

                validStations++;
            }

            System.out.println("Статистика stations.json:");
            System.out.println("   Валидных станций: " + validStations + "/" + stationsArray.size());
            System.out.println("   С датой: " + stationsWithDate);
            System.out.println("   С глубиной: " + stationsWithDepth);
            System.out.println("   С пересадкой: " + stationsWithConnection);

            if (validStations == stationsArray.size()) {
                System.out.println("stations.json соответствует формату");
                return true;
            } else {
                System.out.println("Не все станции в stations.json валидны");
                return false;
            }

        } catch (Exception e) {
            System.err.println("Ошибка при проверке stations.json: " + e.getMessage());
            return false;
        }
    }

    /**
     * ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ
     */

    private List<List<Connection>> findConnections() {
        Map<String, List<MetroStation>> stationsByName = stations.stream()
                .collect(Collectors.groupingBy(MetroStation::getName));

        List<List<Connection>> connections = new ArrayList<>();

        for (Map.Entry<String, List<MetroStation>> entry : stationsByName.entrySet()) {
            String stationName = entry.getKey();
            List<MetroStation> sameNameStations = entry.getValue();

            if (sameNameStations.size() > 1) {
                List<Connection> connectionNode = new ArrayList<>();

                for (MetroStation station : sameNameStations) {
                    connectionNode.add(new Connection(station.getLineNumber(), stationName));
                }

                connections.add(connectionNode);
            }
        }

        return connections;
    }

    private Set<String> findTransferStations() {
        return stations.stream()
                .collect(Collectors.groupingBy(MetroStation::getName, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    private StationDetails createStationDetails(String stationName, String lineName, String lineNumber, Set<String> transferStations) {
        // Генерируем данные для демонстрации
        String date = generateDateForStation(stationName, lineNumber);
        Double depth = generateDepthForStation(stationName, lineNumber);
        Boolean hasConnection = transferStations.contains(stationName) ? true : null;

        // Создаем объект, пропуская null значения (они не будут записаны в JSON)
        return new StationDetails(
                stationName,
                lineName,
                date,
                depth,
                hasConnection
        );
    }

    private boolean checkMapDataConsistency(JsonObject mapJson) {
        System.out.println("Проверка целостности данных...");

        try {
            JsonObject stationsObj = mapJson.getAsJsonObject("stations");
            JsonArray linesArray = mapJson.getAsJsonArray("lines");

            // Проверяем, что все номера линий в stations есть в lines
            Set<String> stationsLineNumbers = stationsObj.keySet();
            Set<String> linesNumbers = new HashSet<>();

            for (JsonElement lineElem : linesArray) {
                String lineNumber = lineElem.getAsJsonObject().get("number").getAsString();
                linesNumbers.add(lineNumber);
            }

            for (String stationLineNumber : stationsLineNumbers) {
                if (!linesNumbers.contains(stationLineNumber)) {
                    System.out.println("Линия " + stationLineNumber + " есть в stations, но отсутствует в lines");
                    return false;
                }
            }

            System.out.println("Целостность данных проверена");
            return true;

        } catch (Exception e) {
            System.err.println("Ошибка при проверке целостности: " + e.getMessage());
            return false;
        }
    }

    private String generateColorForLine(String lineNumber) {
        Map<String, String> colorMap = Map.of(
                "1", "red", "2", "blue", "3", "green", "4", "orange", "5", "purple",
                "6", "brown", "7", "pink", "8", "teal", "9", "lime", "10", "cyan"
        );
        return colorMap.getOrDefault(lineNumber, "gray");
    }

    private String generateDateForStation(String stationName, String lineNumber) {
        int baseYear = 1935 + (safeParseInt(lineNumber) - 1) * 5;
        Random random = new Random(stationName.hashCode());
        int day = random.nextInt(28) + 1;
        int month = random.nextInt(12) + 1;
        int year = Math.min(2023, baseYear + random.nextInt(10));
        return String.format("%02d.%02d.%d", day, month, year);
    }

    private Double generateDepthForStation(String stationName, String lineNumber) {
        Random random = new Random(stationName.hashCode());
        double depth = 5 + random.nextDouble() * 45;
        return Math.round(depth * 10.0) / 10.0;
    }

    private int safeParseInt(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return 1;
        }
    }

    /**
     * ОСНОВНОЙ МЕТОД ДЛЯ СОЗДАНИЯ И ПРОВЕРКИ ВСЕХ ФАЙЛОВ
     */
    public void writeAndVerifyAllFiles(String outputDir) throws IOException {
        System.out.println("ЗАПУСК СОЗДАНИЯ И ПРОВЕРКИ JSON ФАЙЛОВ");

        // Создаем директорию
        File dir = new File(outputDir);
        if (!dir.exists()) {
            dir.mkdirs();
            System.out.println("Создана директория: " + outputDir);
        }

        String mapJsonPath = outputDir + "/map.json";
        String stationsJsonPath = outputDir + "/stations.json";

        // Создаем файлы
        writeMapJson(mapJsonPath);
        writeStationsJson(stationsJsonPath);

        System.out.println("Файлы созданы:");
        System.out.println("   - " + mapJsonPath);
        System.out.println("   - " + stationsJsonPath);
        System.out.println("   - Размер map.json: " + new File(mapJsonPath).length() + " байт");
        System.out.println("   - Размер stations.json: " + new File(stationsJsonPath).length() + " байт");

        // Автоматическая проверка
        selfTest(outputDir);
    }

    /**
     * ТЕСТОВЫЕ ДАННЫЕ И ЗАПУСК
     */
    public static List<MetroLine> createTestLines() {
        return Arrays.asList(
                new MetroLine("1", "Сокольническая линия"),
                new MetroLine("2", "Замоскворецкая линия"),
                new MetroLine("3", "Арбатско-Покровская линия"),
                new MetroLine("4", "Филёвская линия"),
                new MetroLine("5", "Кольцевая линия"),
                new MetroLine("6", "Калужско-Рижская линия")
        );
    }

    public static List<MetroStation> createTestStations() {
        List<MetroStation> stations = new ArrayList<>();

        // Линия 1
        String[] line1Stations = {"Бульвар Рокоссовского", "Черкизовская", "Преображенская площадь",
                "Сокольники", "Красносельская", "Комсомольская", "Красные Ворота",
                "Чистые пруды", "Лубянка", "Охотный ряд", "Библиотека им. Ленина",
                "Кропоткинская", "Парк культуры", "Фрунзенская", "Спортивная", "Воробьёвы горы"};
        for (String station : line1Stations) {
            stations.add(new MetroStation(station, "1"));
        }

        // Линия 2
        String[] line2Stations = {"Ховрино", "Беломорская", "Речной Вокзал", "Водный Стадион",
                "Войковская", "Сокол", "Аэропорт", "Динамо", "Белорусская",
                "Маяковская", "Тверская", "Театральная", "Новокузнецкая", "Павелецкая"};
        for (String station : line2Stations) {
            stations.add(new MetroStation(station, "2"));
        }

        // Линия 3
        String[] line3Stations = {"Пятницкое шоссе", "Митино", "Волоколамская", "Мякинино",
                "Строгино", "Крылатское", "Молодёжная", "Кунцевская",
                "Славянский бульвар", "Парк Победы", "Киевская", "Смоленская"};
        for (String station : line3Stations) {
            stations.add(new MetroStation(station, "3"));
        }

        // Пересадочные станции (добавляем на другие линии)
        stations.add(new MetroStation("Комсомольская", "5"));
        stations.add(new MetroStation("Чистые пруды", "10"));
        stations.add(new MetroStation("Лубянка", "10"));
        stations.add(new MetroStation("Белорусская", "5"));
        stations.add(new MetroStation("Кунцевская", "4"));
        stations.add(new MetroStation("Киевская", "4"));
        stations.add(new MetroStation("Киевская", "5"));
        stations.add(new MetroStation("Парк Победы", "8"));

        return stations;
    }

    /**
     * Автоматическое создание и проверка файлов
     */
    public static void main(String[] args) {
        System.out.println("METRO DATA WRITER - СОЗДАНИЕ И ПРОВЕРКА JSON ФАЙЛОВ");

        // Создаем тестовые данные
        List<MetroLine> testLines = createTestLines();
        List<MetroStation> testStations = createTestStations();

        System.out.println(" ИСХОДНЫЕ ДАННЫЕ:");
        System.out.println("   Линий: " + testLines.size());
        System.out.println("   Станций: " + testStations.size());
        System.out.println();

        MetroDataWriter writer = new MetroDataWriter(testLines, testStations);

        try {
            writer.writeAndVerifyAllFiles("DataCollector/output");
            System.out.println("ПРОГРАММА ЗАВЕРШЕНА УСПЕШНО!");
        } catch (IOException e) {
            System.err.println(" Ошибка при работе программы: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
