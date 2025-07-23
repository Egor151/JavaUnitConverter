package converter;

import converter.entity.Weight;
import converter.exception.ConversionException;

public class WeightConverter implements BaseConverter<Weight> {
    @Override
    public double convert(double value, Weight fromUnit, Weight toUnit) throws ConversionException {
        if (fromUnit == null || toUnit == null) {
            throw new ConversionException("Weight unit cannot be null");
        }
        if (value < 0) {
            throw new ConversionException("Weight value cannot be negative");
        }
        double kilograms = value * fromUnit.getToKilogramRatio();
        return kilograms / toUnit.getToKilogramRatio();
    }
}
