package metro;

public class CsvStation {
    private String name;
    private String lineNumber;
    private String date;
    private String depth;
    private boolean hasConnection;

    public CsvStation() {
    }

    public CsvStation(String name, String lineNumber, String date, String depth, boolean hasConnection) {
        this.name = name;
        this.lineNumber = lineNumber;
        this.date = date;
        this.depth = depth;
        this.hasConnection = hasConnection;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public boolean isHasConnection() {
        return hasConnection;
    }

    public void setHasConnection(boolean hasConnection) {
        this.hasConnection = hasConnection;
    }

    @Override
    public String toString() {
        return "CsvStation{name='" + name + "', line='" + lineNumber +
                "', date='" + date + "', depth='" + depth +
                "', connection=" + hasConnection + "}";
    }
}
