public class TopManager implements Employee {
    private final double fixedSalary;
    private Company company;

    public TopManager(double fixedSalary, Company company) {
        this.fixedSalary = fixedSalary;
        this.company = company;
    }

    @Override
    public double getMonthSalary() {
        if (company.getIncome() > 10_000_000) {
            return fixedSalary + fixedSalary * 1.5;
        }
        return fixedSalary;
    }
}
