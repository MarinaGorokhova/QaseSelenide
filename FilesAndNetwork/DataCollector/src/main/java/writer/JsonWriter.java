package writer;

import metro.MetroLine;
import metro.MetroStation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JsonWriter {
    private Gson gson;

    public JsonWriter() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void writeLinesToJson(List<MetroLine> lines, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(lines, writer);
        }
    }

    public void writeStationsToJson(List<MetroStation> stations, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(stations, writer);
        }
    }
}
