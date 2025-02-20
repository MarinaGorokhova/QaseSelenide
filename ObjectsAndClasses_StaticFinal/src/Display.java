public class Display {

    private final double displayDiagonal;
    private final DisplayType displayType;
    private final double displayWeight;

    public Display(double displayDiagonal, DisplayType displayType, double displayWeight) {
        this.displayDiagonal = displayDiagonal;
        this.displayType = displayType;
        this.displayWeight = displayWeight;
    }

    public double getDisplayDiagonal() {
        return displayDiagonal;
    }

    public DisplayType getDisplayType() {
        return displayType;
    }

    public double getDisplayWeight() {
        return displayWeight;
    }

    public String toString() {
        return
                "Монитор: " + "\n" +
                "Тип: " + displayType + "\n" +
                "Диагональ: " + displayDiagonal + "'" + "\n" +
                "Вес: " + displayWeight + " гр." + "\n";
    }
}
