public class Processor {
    private final double mhz;
    private final int cores;
    private final String manufacturer;
    private static int weight;

    public Processor(double mhz, int cores, String manufacturer, int weight) {
        this.mhz = mhz;
        this.cores = cores;
        this.manufacturer = manufacturer;
        this.weight = weight;
    }

    public double mhz() {
        return mhz;
    }

    public int cores() {
        return cores;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public static int getWeight() {
        return weight;
    }

    public String toString() {
        return
                "Процессор: " + "\n" +
                "Производитель: " + manufacturer + "\n" +
                "Тактовая частота: " + mhz + " Mhz" + "\n" +
                "Количество ядер: " + cores + "\n" +
                "Вес: " + weight + " гр." + "\n";
    }
}
