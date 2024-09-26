public class Dimensions {
    private final double width;
    private final double height;
    private final double length;

    public Dimensions(double width, double height, double length) {
        this.width = width;
        this.height = height;
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getLength() {
        return length;
    }

    public Dimensions setWidth(double newWidth) {
        return new Dimensions(newWidth, this.height, this.length);
    }
    public Dimensions setHeight(double newHeight) {
        return new Dimensions(this.width, newHeight, this.length);
    }
    public Dimensions setLength(double newLength) {
        return new Dimensions(this.width, this.height, newLength);
    }
    public double getVolume() {
        return width * height * length;
    }

    public String toString() {
        return
                "Ширина: " + width + "\n" +
                        "Высота:" + height + "\n" +
                        "Длина:" + length;
    }
}
