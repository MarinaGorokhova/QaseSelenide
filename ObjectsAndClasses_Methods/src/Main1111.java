public class Main {

    public static void main(String[] args) {
        Basket basket = new Basket();
        basket.add("Milk", 40);
        basket.print("Milk");

        int a = 3;
        int b = 7;

        Arithmetic ar = new Arithmetic();

        System.out.println("Сумма чисел " + a + " + " + b + " = " + ar.sum());
        System.out.println("Произведение чисел " + a + " * " + b + " = " + ar.composition());
        System.out.println("Максимальное из чисел " + a + " и " + b + " = " + ar.max());
        System.out.println("Минимальное из чисел " + a + " и " + b + " = " + ar.min());

    }
}
