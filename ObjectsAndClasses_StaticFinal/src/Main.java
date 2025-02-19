public class Main {
    public static void main(String[] args) {
        Processor processor = new Processor(1.6,8, "Intel", 40);
        Memory memory = new Memory("DDR4", 64, 15);
        Storage storage = new Storage(StorageType.SSD,500,700);
        Display display = new Display(32,DisplayType.IPS,5000);
        Keyboard keyboard = new Keyboard("Механическая", true, 300);
        Computer computer = new Computer("Samsung", "PC", processor, memory, storage, display, keyboard);
        System.out.println(computer);
    }
}
