package com.hospital.appointment.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputUtil {

    private static final Scanner scanner = new Scanner(System.in);

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm");

    private InputUtil() {
        // Utility class
    }

    public static String readString(String message) {

        System.out.print(message);

        return scanner.nextLine().trim();
    }

    public static int readInt(String message) {

        while (true) {

            try {

                System.out.print(message);

                return Integer.parseInt(
                        scanner.nextLine().trim()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "❌ Please enter a valid number."
                );
            }
        }
    }

    public static double readDouble(String message) {

        while (true) {

            try {

                System.out.print(message);

                return Double.parseDouble(
                        scanner.nextLine().trim()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "❌ Please enter a valid amount."
                );
            }
        }
    }

    public static LocalDate readDate(String message) {

        while (true) {

            try {

                System.out.print(message);

                String input = scanner.nextLine().trim();

                return LocalDate.parse(
                        input,
                        DATE_FORMATTER
                );

            } catch (DateTimeParseException e) {

                System.out.println(
                        "❌ Invalid date. Use format DD-MM-YYYY."
                );
            }
        }
    }

    public static LocalTime readTime(String message) {

        while (true) {

            try {

                System.out.print(message);

                String input = scanner.nextLine().trim();

                return LocalTime.parse(
                        input,
                        TIME_FORMATTER
                );

            } catch (DateTimeParseException e) {

                System.out.println(
                        "❌ Invalid time. Use 24-hour format HH:mm."
                );
            }
        }
    }

    public static void pause() {

        System.out.println();
        System.out.println(
                "Press ENTER to continue..."
        );

        scanner.nextLine();
    }

    public static void close() {

        scanner.close();
    }
}