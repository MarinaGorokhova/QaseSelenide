import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
//        Elevator elevator = new Elevator(-3, 26);
//        while (true) {
//            System.out.print("Введите номер этажа: ");
//            int floor = new Scanner(System.in).nextInt();
//            elevator.move(floor);
//        }

        Dimensions dimensions = new Dimensions(10, 5, 50);
        Cargo cargo = new Cargo(dimensions, 300, "Moscow", true, "ABC12345", true);

        System.out.println("Обьем " + dimensions.getVolume());
        System.out.println("Габариты " + cargo.getDimensions());
        System.out.println("Масса " + cargo.getWeight());
        System.out.println("Адрес доставки " + cargo.getAddress());
        System.out.println("Можно ли переворачивать " + cargo.isFlip());
        System.out.println("Регистрационный номер " + cargo.getNumber());
        System.out.println("Хрупкость " + cargo.isFragile());

        Cargo copy = cargo.setNumber("12345AQW");

        System.out.println(copy);

    }
    }
