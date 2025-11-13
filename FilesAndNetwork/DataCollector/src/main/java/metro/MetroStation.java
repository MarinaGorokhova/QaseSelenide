package metro;

public class MetroStation {
    private String name;
    private String lineNumber;

    public MetroStation(String name, String lineNumber) {
        this.name = name;
        this.lineNumber = lineNumber;
    }

    public String getName() {
        return name;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    @Override
    public String toString() {
        return "Station: " + name + " (Line " + lineNumber + ")";
    }
}
