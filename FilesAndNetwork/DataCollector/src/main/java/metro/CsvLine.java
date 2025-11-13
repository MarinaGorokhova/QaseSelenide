package metro;

public class CsvLine {
    private String number;
    private String name;
    private String color;
    private String openingYear;

    public CsvLine() {
    }

    public CsvLine(String number, String name, String color, String openingYear) {
        this.number = number;
        this.name = name;
        this.color = color;
        this.openingYear = openingYear;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getOpeningYear() {
        return openingYear;
    }

    public void setOpeningYear(String openingYear) {
        this.openingYear = openingYear;
    }

    @Override
    public String toString() {
        return "CsvLine{number='" + number + "', name='" + name +
                "', color='" + color + "', openingYear='" + openingYear + "'}";
    }
}
