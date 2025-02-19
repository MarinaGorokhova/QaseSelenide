public class Computer {

    public Display display;
    public Keyboard keyboard;
    public Memory memory;
    public Processor processor;
    public Storage storage;
    public final String vendor;
    public final String name;
    public double totalWeight;

    public Computer(String vendor, String name,Processor processor,Memory memory, Storage storage, Display display, Keyboard keyboard) {
        this.display = display;
        this.keyboard = keyboard;
        this.memory = memory;
        this.processor = processor;
        this.storage = storage;
        this.vendor = vendor;
        this.name = name;
        this.totalWeight = totalWeight;
    }
    public void setDisplay(Display display) {
        this.display = display;
    }

    public void setKeyboard(Keyboard keyboard) {
        this.keyboard = keyboard;
    }

    public void setMemory(Memory memory) {
        this.memory = memory;
    }

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public double getTotalWeight() {
        return totalWeight = Processor.getWeight() + Display.getDisplayWeight() + Keyboard.getKeyboardWeight() + Storage.getStorageWeight() + Memory.getMemoryWeight();
    }

    public String toString() {
        return
                "ПК: " + "\n" +
                "Производитель: " + vendor + "\n" +
                "Название: " + name + "\n" +
                processor + "\n" +
                memory + "\n" +
                storage + "\n" +
                display + "\n" +
                keyboard + "\n" +
                "Общий вес: " + getTotalWeight();
    }
}
