public class Main {
    public static void main(String[] args) {
        Processor processor = new Processor(1.6,8, "Intel", 40);
        Memory memory = new Memory("DDR4", 64, 15);
        Storage storage = new Storage(StorageType.SSD,500,700);
        Display display = new Display(32,DisplayType.IPS,5000);
        Keyboard keyboard = new Keyboard("Механическая", true, 300);
        Computer computer = new Computer("Samsung", "PC", processor, memory, storage, display, keyboard);
        System.out.println(computer);

        Processor processor2 = new Processor(3.70,4, "Intel", 40);
        Memory memory2 = new Memory("DDR", 64, 16);
        Storage storage2 = new Storage(StorageType.HDD,1000,2091);
        Display display2 = new Display(20,DisplayType.IPS,5000);
        Keyboard keyboard2 = new Keyboard("Механическая", false, 300);
        Computer computer2 = new Computer("Samsung", "PC", processor2, memory2, storage2, display2, keyboard2);
        System.out.println(computer2);
    }
}
