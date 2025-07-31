package converter;

import entity.Weight;
import exception.ConversionException;

public class WeightConverter implements BaseConverter<Weight> {
    @Override
    public double convert(double value, String fromUnit, String toUnit) throws ConversionException {
        if (value < 0) {
            throw new ConversionException("Weight value cannot be negative");
        }

        Weight from = parseUnit(fromUnit);
        Weight to = parseUnit(toUnit);

        double kilograms = value * from.getToKilogramRatio();
        return kilograms / to.getToKilogramRatio();
    }

    @Override
    public String[] getSupportedUnits() {
        Weight[] units = Weight.values();
        String[] result = new String[units.length];
        for (int i = 0; i < units.length; i++) {
            result[i] = units[i].name().toLowerCase();
        }
        return result;
    }

    private Weight parseUnit(String unit) throws ConversionException {
        try {
            return Weight.valueOf(unit.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ConversionException("Unknown weight unit: '" + unit + "'. Supported units: " +
                    String.join(", ", getSupportedUnits()));
        }
    }
}
