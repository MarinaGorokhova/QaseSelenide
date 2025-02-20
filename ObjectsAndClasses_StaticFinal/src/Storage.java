public class Storage {
    public final StorageType storageType;
    private final int storageVolume;
    private final double storageWeight;

    public Storage(StorageType storageType, int storageVolume, double storageWeight) {
        this.storageType = storageType;
        this.storageVolume = storageVolume;
        this.storageWeight = storageWeight;
    }

    public StorageType getStorageType() {
        return storageType;
    }

    public int getStorageVolume() {
        return storageVolume;
    }

    public double getStorageWeight() {
        return storageWeight;
    }

    public String toString() {
        return
                "Накопитель информации: " + "\n" +
                "Тип: " + storageType + "\n" +
                "Объем: " + storageVolume + "\n" +
                "Вес: " + storageWeight + " гр." + "\n";
    }
}
