import converter.BaseConverter;
import converter.LengthConverter;
import converter.TemperatureConverter;
import converter.WeightConverter;
import converter.entity.Length;
import converter.entity.Temperature;
import converter.entity.Weight;
import converter.exception.ConversionException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Unit Converter");
        System.out.println("Available categories: length, temperature, weight");
        System.out.println("Format: category value from_unit to_unit");
        System.out.println("Example: length 10 meter kilometer");
        System.out.println("Enter 'exit' to quit");

        while (true) {
            System.out.println("> ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            try {
                String[] parts = input.split(" ");
                if (parts.length != 4) {
                    throw new ConversionException("Invalid input format. Expected: category value from_unit to_unit");
                }

                String category = parts[0].toLowerCase();
                double value;
                try {
                    value = Double.parseDouble(parts[1]);
                } catch (NumberFormatException e) {
                    throw new ConversionException("Invalid value: " + parts[1]);
                }
                BaseConverter<?> converter = getConverter(category);
                String result = convert(converter, value, parts[2], parts[3]);
                System.out.println(result);

            } catch (ConversionException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private static BaseConverter<?> getConverter(String category) throws ConversionException {
        return switch (category) {
            case "length" -> new LengthConverter();
            case "temperature" -> new TemperatureConverter();
            case "weight" -> new WeightConverter();
            default -> throw new ConversionException("Unknown category: '" + category + "'");
        };
    }

    private static <T extends Enum<T>> String convert(BaseConverter<T> converter, double value, String fromUnitStr,
                                                      String toUnitStr) throws ConversionException {
        Class<T> enumClass = getEnumClass(converter);
        T fromUnit = parseUnit(enumClass, fromUnitStr);
        T toUnit = parseUnit(enumClass, toUnitStr);

        double result = converter.convert(value, fromUnit, toUnit);
        return String.format("%.2f %s = %.2f %s",
                value, fromUnit.toString().toUpperCase(),
                result, toUnit.toString().toUpperCase());
    }

    @SuppressWarnings("unchecked")
    private static <T extends Enum<T>> Class<T> getEnumClass(BaseConverter<T> converter) {
        if (converter instanceof LengthConverter) {
            return (Class<T>) Length.class;
        } else if (converter instanceof TemperatureConverter) {
            return (Class<T>) Temperature.class;
        } else if (converter instanceof WeightConverter) {
            return (Class<T>) Weight.class;
        }
        throw new IllegalArgumentException("Unknown converter type");
    }

    private static <T extends Enum<T>> T parseUnit(Class<T> enumClass, String unitStr) throws ConversionException {
        try {
            return Enum.valueOf(enumClass, unitStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ConversionException("Unknown unit: '" + unitStr + "' for category");
        }
    }
}
