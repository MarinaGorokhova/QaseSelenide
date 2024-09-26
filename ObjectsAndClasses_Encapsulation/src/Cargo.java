public class Cargo {
    private final Dimensions dimensions;
    private final double weight;
    private final String address;
    private final boolean flip;
    private final String number;
    private final boolean fragile ;

    public Cargo(Dimensions dimensions, double weight, String address, boolean flip, String number, boolean fragile) {
        this.dimensions = dimensions;
        this.weight = weight;
        this.address = address;
        this.flip = flip;
        this.number = number;
        this.fragile = fragile;
    }

    public Dimensions getDimensions() {
        return dimensions;
    }
    public double getWeight() {
        return weight;
    }
    public String getAddress() {
        return address;
    }
    public boolean isFlip() {
        return flip;
    }
    public String getNumber() {
        return number;
    }
    public boolean isFragile() {
        return fragile;
    }
    public Cargo setDimensions(Dimensions newDimensions) {
        return new Cargo(newDimensions, this.weight, this.address, this.flip , this.number, this.fragile);
    }
    public Cargo setWeight(double newWeight) {
        return new Cargo(this.dimensions, newWeight, this.address, this.flip , this.number, this.fragile);
    }
    public Cargo setAddress(String newAddress) {
        return new Cargo(this.dimensions, this.weight, newAddress, this.flip , this.number, this.fragile);
    }
    public Cargo setNumber(String newNumber) {
        return new Cargo(this.dimensions, this.weight, this.address, this.flip , newNumber, this.fragile);
    }

    @Override
    public String toString() {
        return "Cargo{" +
            "dimensions=" + dimensions +
            ", weight=" + weight +
            ", deliveryAddress='" + address + '\'' +
            ", isReversible=" + flip +
            ", registrationNumber='" + number + '\'' +
            ", isFragile=" + fragile +
            '}';
}
}
