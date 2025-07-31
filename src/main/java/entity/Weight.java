package entity;

public enum Weight {
    KILOGRAM(1.0),
    POUND(0.453592),
    OUNCE(0.0283495);

    private final double toKilogramRatio;

    Weight(double toKilogramRatio) {
        this.toKilogramRatio = toKilogramRatio;
    }

    public double getToKilogramRatio() {
        return toKilogramRatio;
    }
}
