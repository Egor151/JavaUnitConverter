package converter;

import entity.Length;
import exception.ConversionException;

public class LengthConverter implements BaseConverter<Length> {
    @Override
    public double convert(double value, String fromUnit, String toUnit) throws ConversionException {
        if (value < 0) {
            throw new ConversionException("Length value cannot be negative");
        }

        Length from = parseUnit(fromUnit);
        Length to = parseUnit(toUnit);

        double meters = value * from.getToMeterRatio();
        return meters / to.getToMeterRatio();
    }

    @Override
    public String[] getSupportedUnits() {
        Length[] units = Length.values();
        String[] result = new String[units.length];
        for (int i = 0; i < units.length; i++) {
            result[i] = units[i].name().toLowerCase();
        }
        return result;
    }

    private Length parseUnit(String unit) throws ConversionException {
        try {
            return Length.valueOf(unit.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ConversionException("Unknown length unit: '" + unit + "'. Supported units: " +
                    String.join(", ", getSupportedUnits()));
        }
    }
}
