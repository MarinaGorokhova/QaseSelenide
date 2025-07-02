import java.util.*;
import java.util.stream.Collectors;

public class Company {
        private List<Employee> employees = new ArrayList<>();
        private double income = 0;

        public void hire(Employee employee) {
            employees.add(employee);
            recalcIncome();
        }

        public void hireAll(Collection<Employee> employeesToHire) {
            employees.addAll(employeesToHire);
            recalcIncome();
        }

        public void fire(Employee employee) {
            employees.remove(employee);
            recalcIncome();
        }

        public double getIncome() {
            return income;
        }

        // Пересчитываем доход компании по менеджерам
        private void recalcIncome() {
            // Суммируем доход, который приносят менеджеры
            income = employees.stream()
                    .filter(e -> e instanceof Manager)
                    .mapToDouble(e -> ((Manager) e).getSalesIncome())
                    .sum();
        }

        public List<Employee> getTopSalaryStaff(int count) {
            if (count <= 0) return Collections.emptyList();

            return employees.stream()
                    .sorted((e1, e2) -> Double.compare(e2.getMonthSalary(), e1.getMonthSalary()))
                    .limit(Math.min(count, employees.size()))
                    .collect(Collectors.toList());
        }

        public List<Employee> getLowestSalaryStaff(int count) {
            if (count <= 0) return Collections.emptyList();

            return employees.stream()
                    .sorted(Comparator.comparingDouble(Employee::getMonthSalary))
                    .limit(Math.min(count, employees.size()))
                    .collect(Collectors.toList());
        }
    }
