package converter;

import entity.Temperature;
import exception.ConversionException;

public class TemperatureConverter implements BaseConverter<Temperature> {
    private static final double ABSOLUTE_ZERO_CELSIUS = -273.15;
    private static final double ABSOLUTE_ZERO_FAHRENHEIT = -459.67;
    private static final double ABSOLUTE_ZERO_KELVIN = 0.0;

    @Override
    public double convert(double value, String fromUnit, String toUnit) throws ConversionException {
        Temperature from = parseUnit(fromUnit);
        Temperature to = parseUnit(toUnit);

        validateTemperature(value, from);
        if (from == to) {
            return value;
        }

        double celsius = toCelsius(value, from);
        return fromCelsius(celsius, to);
    }

    @Override
    public String[] getSupportedUnits() {
        Temperature[] units = Temperature.values();
        String[] result = new String[units.length];
        for (int i = 0; i < units.length; i++) {
            result[i] = units[i].name().toLowerCase();
        }
        return result;
    }

    private Temperature parseUnit(String unit) throws ConversionException {
        try {
            return Temperature.valueOf(unit.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ConversionException("Unknown temperature unit: '" + unit + "'. Supported units: " +
                    String.join(", ", getSupportedUnits()));
        }
    }

    private void validateTemperature(double value, Temperature unit) throws ConversionException {
        switch (unit) {
            case CELSIUS:
                if (value < ABSOLUTE_ZERO_CELSIUS) {
                    throw new ConversionException(
                            String.format("Temperature cannot be below %.2f°C", ABSOLUTE_ZERO_CELSIUS)
                    );
                }
                break;
            case FAHRENHEIT:
                if (value < ABSOLUTE_ZERO_FAHRENHEIT) {
                    throw new ConversionException(
                            String.format("Temperature cannot be below %.2f°F", ABSOLUTE_ZERO_FAHRENHEIT)
                    );
                }
                break;
            case KELVIN:
                if (value < ABSOLUTE_ZERO_KELVIN) {
                    throw new ConversionException(
                            String.format("Temperature cannot be below %.2fK", ABSOLUTE_ZERO_KELVIN)
                    );
                }
                break;
            default:
                throw new ConversionException("Unknown temperature unit: " + unit);
        }
    }

    private double toCelsius(double value, Temperature fromUnit) {
        return switch (fromUnit) {
            case CELSIUS -> value;
            case FAHRENHEIT -> (value - 32) * 5 / 9;
            case KELVIN -> value - 273.15;
        };
    }

    private double fromCelsius(double celsius, Temperature toUnit) {
        return switch (toUnit) {
            case CELSIUS -> celsius;
            case FAHRENHEIT -> celsius * 9 / 5 + 32;
            case KELVIN -> celsius + 273.15;
        };
    }
}
