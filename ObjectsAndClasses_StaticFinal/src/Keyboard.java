public class Keyboard {
    public final String keyboardType;
    public final boolean keyboardBacklight;
    public final double keyboardWeight;

    public Keyboard(String keyboardType, boolean keyboardBacklight, double keyboardWeight) {
        this.keyboardType = keyboardType;
        this.keyboardBacklight = keyboardBacklight;
        this.keyboardWeight = keyboardWeight;
    }

    public String getKeyboardType() {
        return keyboardType;
    }

    public boolean isKeyboardBacklight() {
        return keyboardBacklight;
    }

    public double getKeyboardWeight() {
        return keyboardWeight;
    }

    public String toString() {
        return
                "Клавиатура: " + "\n" +
                "Тип: " + keyboardType + "\n" +
                "Подсветка: " + keyboardBacklight + "\n" +
                "Вес: " + keyboardWeight + " гр." + "\n";
    }
}
