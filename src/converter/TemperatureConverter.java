package converter;

import converter.entity.Temperature;
import converter.exception.ConversionException;

public class TemperatureConverter implements BaseConverter<Temperature> {
    private static final double ABSOLUTE_ZERO_CELSIUS = -273.15;
    private static final double ABSOLUTE_ZERO_FAHRENHEIT = -459.67;
    private static final double ABSOLUTE_ZERO_KELVIN = 0.0;

    @Override
    public double convert(double value, Temperature fromUnit, Temperature toUnit) throws ConversionException {
        if (fromUnit == null || toUnit == null) {
            throw new ConversionException("Temperature unit cannot be null");
        }

        switch (fromUnit) {
            case CELSIUS -> {
                if (value < ABSOLUTE_ZERO_CELSIUS) {
                    throw new ConversionException(
                            String.format("Temperature cannot be below %.2f°C", ABSOLUTE_ZERO_CELSIUS)
                    );
                }
            }
            case FAHRENHEIT -> {
                if (value < ABSOLUTE_ZERO_FAHRENHEIT) {
                    throw new ConversionException(
                            String.format("Temperature cannot be below %.2f°F", ABSOLUTE_ZERO_FAHRENHEIT)
                    );
                }
            }
            case KELVIN -> {
                if (value < ABSOLUTE_ZERO_KELVIN) {
                    throw new ConversionException(
                            String.format("Temperature cannot be below %.2fK", ABSOLUTE_ZERO_KELVIN)
                    );
                }
            }
            default -> throw new ConversionException("Unknown temperature unit: " + fromUnit);
        }
        if (fromUnit == toUnit) {
            return value;
        }

        double celsius = toCelsius(value, fromUnit);
        return fromCelsius(celsius, toUnit);
    }

    private double toCelsius(double value, Temperature fromUnit) throws ConversionException {
        return switch (fromUnit) {
            case CELSIUS -> value;
            case FAHRENHEIT -> (value - 32) * 5 / 9;
            case KELVIN -> value - 273.15;
            default -> throw new ConversionException("Unknown source temperature unit: " + fromUnit);
        };
    }

    private double fromCelsius(double celsius, Temperature toUnit) throws ConversionException {
        return switch (toUnit) {
            case CELSIUS -> celsius;
            case FAHRENHEIT -> celsius * 9 / 5 + 32;
            case KELVIN -> celsius + 273.15;
            default -> throw new ConversionException("Unknown target temperature unit: " + toUnit);
        };
    }
}
