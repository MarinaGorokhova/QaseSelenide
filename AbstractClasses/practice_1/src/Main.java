import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Company company = new Company();

        // Создаём и нанимаем 180 операторов
        List<Employee> operators = new ArrayList<>();
        for (int i = 0; i < 180; i++) {
            operators.add(new Operator(30000));
        }
        company.hireAll(operators);

        // Создаём и нанимаем 80 менеджеров
        List<Employee> managers = new ArrayList<>();
        for (int i = 0; i < 80; i++) {
            managers.add(new Manager(50000));
        }
        company.hireAll(managers);

        // Создаём и нанимаем 10 топ-менеджеров
        List<Employee> topManagers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            topManagers.add(new TopManager(100000, company));
        }
        company.hireAll(topManagers);

        printSalaries(company, 15, 30);

        // Увольняем 50% сотрудников
        List<Employee> currentEmployees = new ArrayList<>(company.getTopSalaryStaff(company.getLowestSalaryStaff(company.getTopSalaryStaff(Integer.MAX_VALUE).size()).size()));
        int fireCount = company.getTopSalaryStaff(Integer.MAX_VALUE).size() / 2;
        for (int i = 0; i < fireCount && i < currentEmployees.size(); i++) {
            company.fire(currentEmployees.get(i));
        }

        printSalaries(company, 15, 30);
    }

    private static void printSalaries(Company company, int topCount, int lowCount) {
        System.out.println("Топ " + topCount + " зарплат:");
        company.getTopSalaryStaff(topCount).forEach(emp ->
                System.out.printf("%.0f руб.\n", emp.getMonthSalary())
        );

        System.out.println("\nНизшие " + lowCount + " зарплат:");
        company.getLowestSalaryStaff(lowCount).forEach(emp ->
                System.out.printf("%.0f руб.\n", emp.getMonthSalary())
        );
        System.out.println("-------------------------------------------------");
    }
}
