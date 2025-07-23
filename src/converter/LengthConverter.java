package converter;

import converter.entity.Length;
import converter.exception.ConversionException;

public class LengthConverter implements BaseConverter<Length> {
    @Override
    public double convert(double value, Length fromUnit, Length toUnit) throws ConversionException {
        if (fromUnit == null || toUnit == null) {
            throw new ConversionException("Unit cannot be null");
        }
        if (value < 0) {
            throw new ConversionException("Length value cannot be negative");
        }
        double meters = value * fromUnit.getToMeterRatio();
        return meters / toUnit.getToMeterRatio();
    }
}
