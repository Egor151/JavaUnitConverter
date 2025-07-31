package converter;

import exception.ConversionException;

public interface BaseConverter<T extends Enum<T>> {
    double convert(double value, String fromUnit, String toUnit) throws ConversionException;
    String[] getSupportedUnits();
}
