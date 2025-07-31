import converter.BaseConverter;
import converter.LengthConverter;
import converter.TemperatureConverter;
import converter.WeightConverter;
import entity.Length;
import entity.Temperature;
import entity.Weight;
import exception.ConversionException;

import javax.xml.crypto.dom.DOMCryptoContext;
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

                BaseConverter converter = getConverter(category);
                double result = converter.convert(value, parts[2], parts[3]);
                System.out.printf("%.2f %s = %.2f %s%n",
                        value, parts[2],
                        result, parts[3]);

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
}
