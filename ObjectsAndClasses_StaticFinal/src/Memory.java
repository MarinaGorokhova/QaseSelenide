public class Memory {
    private final String type;
    private final int volume;
    private static double memoryWeight;

    public Memory(String type, int volume, double memoryWeight) {
        this.type = type;
        this.volume = volume;
        this.memoryWeight = memoryWeight;
    }

    public String getType() {
        return type;
    }

    public int getVolume() {
        return volume;
    }

    public static double getMemoryWeight() {
        return memoryWeight;
    }

    public String toString() {
        return
                "ОЗУ: " + "\n" +
                "Тип: " + type + "\n" +
                "Объем: " + volume + " Гб." + "\n" +
                "Вес: " + memoryWeight + " гр." + "\n";
    }
}
