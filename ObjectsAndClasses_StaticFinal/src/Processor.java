public class Processor {
    private final double mhz;
    private final int cores;
    private final String manufacturer;
    private final double weight;

    public Processor(double mhz, int cores, String manufacturer, double weight) {
        this.mhz = mhz;
        this.cores = cores;
        this.manufacturer = manufacturer;
        this.weight = weight;
    }

    public double getMhz() {
        return mhz;
    }

    public int getCores() {
        return cores;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public double getWeight() {
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
