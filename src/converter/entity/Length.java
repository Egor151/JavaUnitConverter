package converter.entity;

public enum Length {
    METER(1.0),
    KILOMETER(1000.0),
    MILE(1609.34);

    private final double toMeterRatio;

    Length(double toMeterRatio) {
        this.toMeterRatio=toMeterRatio;
    }

    public double getToMeterRatio() {
        return toMeterRatio;
    }
 }
