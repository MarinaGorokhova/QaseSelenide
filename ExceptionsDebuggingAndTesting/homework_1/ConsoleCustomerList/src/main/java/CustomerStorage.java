import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class CustomerStorage {
    private final Map<String, Customer> storage;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("\\d{11}");
    private static final String ERROR_LOG = "errors.log";

    public CustomerStorage() {
        storage = new HashMap<>();
    }

public void addCustomer(String data) throws InvalidDataLengthException, InvalidPhoneFormatException, InvalidEmailFormatException {
    final int INDEX_NAME = 0;
    final int INDEX_SURNAME = 1;
    final int INDEX_EMAIL = 2;
    final int INDEX_PHONE = 3;
    final int EXPECTED_PARTS = 4;
    String[] components = data.trim().split("\\s+");
    String phone = components[INDEX_PHONE].replaceAll("\\D", "");

    if (components.length != EXPECTED_PARTS) {
        throw new InvalidDataLengthException("Ошибка: Ожидалось " + EXPECTED_PARTS + " части(ей), найдено " + components.length);
    }

    if (!PHONE_PATTERN.matcher(phone).matches()) {
        throw new InvalidPhoneFormatException("Ошибка: Телефон должен содержать ровно 11 цифр.");
    }

    if (!EMAIL_PATTERN.matcher(components[INDEX_EMAIL]).matches()) {
        throw new InvalidEmailFormatException("Ошибка: Формат email неверен.");
    }

    String name = components[INDEX_NAME] + " " + components[INDEX_SURNAME];
    storage.put(name, new Customer(name, components[INDEX_PHONE], components[INDEX_EMAIL]));
}
    private void logError(Exception e) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ERROR_LOG, true))) {
            pw.println("[" + java.time.LocalDateTime.now() + "] " + e.getClass().getSimpleName() + ": " + e.getMessage());
            for (StackTraceElement ste : e.getStackTrace()) {
                pw.println("\t" + ste.toString());
            }
        } catch (IOException ioEx) {
            System.out.println("Не удалось записать ошибку в лог-файл.");
        }
    }

    public void listCustomers() {
        storage.values().forEach(System.out::println);
    }

    public void removeCustomer(String name) {
        storage.remove(name);
    }

    public Customer getCustomer(String name) {
        return storage.get(name);
    }

    public int getCount() {
        return storage.size();
    }
}