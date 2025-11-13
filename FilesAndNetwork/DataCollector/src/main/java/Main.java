import metro.MetroLine;
import metro.MetroStation;
import parser.WebPageParser;
import writer.JsonWriter;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        WebPageParser parser = new WebPageParser();
        JsonWriter jsonWriter = new JsonWriter();
        try {
            parser.testParser();

            var doc = parser.getHtmlDocument();
            List<MetroLine> lines = parser.parseMetroLines(doc);
            List<MetroStation> stations = parser.parseMetroStations(doc);

            jsonWriter.writeLinesToJson(lines, "lines.json");
            jsonWriter.writeStationsToJson(stations, "stations.json");

            System.out.println("Данные успешно записаны в файлы!");
            System.out.println("lines.json");
            System.out.println("stations.json");
        } catch (IOException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}
