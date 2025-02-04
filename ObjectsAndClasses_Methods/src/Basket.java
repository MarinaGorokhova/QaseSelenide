public class Basket {

    private static int count = 0;
    private String items = "";
    private int totalPrice = 0;
    private int limit;
    private double totalWeight;
    private static int totalBasketCount = 0;
    private static int totalBasketPrice = 0;
    private static double averagePriceAllBaskets = 0;
    private static double averageCostBasket = 0;


    public Basket() {
        increaseCount(1);
        items = "Список товаров:";
        this.limit = 1000000;
//        basketCount = basketCount + 1;
    }

    public Basket(int limit) {
        this();
        this.limit = limit;
    }

    public Basket(String items, int totalPrice) {
        this();
        this.items = this.items + items;
        this.totalPrice = totalPrice;
    }

    public static int getCount() {
        return count;
    }

    public static void increaseCount(int count) {
        Basket.count = Basket.count + count;
    }

    public void add(String name, int price) {
        add(name, price, 1);
    }

    public void add(String name, int price, int count) {
        add(name, price, count, 0);
    }

    public void add(String name, int price, int count, double weight) {
        boolean error = false;
        if (contains(name)) {
            error = true;
        }

        if (totalPrice + count * price >= limit) {
            error = true;
        }

        if (error) {
            System.out.println("Error occured :(");
            return;
        }

        items = items + "\n" + name + " - " +
            count + " шт. - " + price;
        totalPrice = totalPrice + count * price;
        totalWeight = totalWeight + weight;
        totalBasketCount = totalBasketCount + count;
        totalBasketPrice = totalBasketPrice + price;
        averagePriceAllBaskets = totalBasketPrice/totalBasketCount;
        averageCostBasket = totalBasketPrice/Basket.count;
    }

    public void clear() {
        items = "";
        totalPrice = 0;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public static int getTotalBasketCount() {
        return totalBasketCount;
    }

    public static int getTotalBasketPrice() {
        return totalBasketPrice;
    }

    public static double getAveragePriceAllBaskets() {
        return averagePriceAllBaskets;
    }

    public static double getAverageCostBasket() {
        return averageCostBasket;
    }

    public boolean contains(String name) {
        return items.contains(name);
    }

    public void print(String title) {
        System.out.println(title);
        if (items.isEmpty()) {
            System.out.println("Корзина пуста");
        } else {
            System.out.println(items);
        }
    }
}
