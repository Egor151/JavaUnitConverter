package entity;

public enum Temperature {
    CELSIUS(-273.15),
    FAHRENHEIT(-459.67),
    KELVIN(0.0);

    private final double absoluteZero;

    Temperature(double absoluteZero) {
        this.absoluteZero = absoluteZero;
    }

    public double getAbsoluteZero() {
        return absoluteZero;
    }
}
