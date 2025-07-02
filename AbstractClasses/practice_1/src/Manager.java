import java.util.concurrent.ThreadLocalRandom;

public class Manager implements Employee {
    private final double fixedSalary;
    private final double salesIncome;

    public Manager(double fixedSalary) {
        this.fixedSalary = fixedSalary;
        this.salesIncome = ThreadLocalRandom.current().nextDouble(115000, 140001);
    }

    public double getSalesIncome() {
        return salesIncome;
    }

    @Override
    public double getMonthSalary() {
        return fixedSalary + salesIncome * 0.05;
    }
}
