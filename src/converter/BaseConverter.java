package converter;

import converter.exception.ConversionException;

public interface BaseConverter<T extends Enum<T>> {
    double convert(double value, T fromUnit, T toUnit) throws ConversionException;
}
