package com.hospital.appointment.util;

public class DisplayUtil {

    private DisplayUtil() {
        // Utility class
    }

    public static void printMainHeader() {

        System.out.println();
        System.out.println(
                "=========================================================================="
        );
        System.out.println(
                "             HOSPITAL APPOINTMENT MANAGEMENT SYSTEM"
        );
        System.out.println(
                "=========================================================================="
        );
    }

    public static void printSectionHeader(String title) {

        System.out.println();
        System.out.println(
                "------------------------------------------------------------------------"
        );
        System.out.println(
                "                         " + title
        );
        System.out.println(
                "------------------------------------------------------------------------"
        );
    }

    public static void printSuccess(String message) {

        System.out.println();
        System.out.println("✅ " + message);
    }

    public static void printError(String message) {

        System.out.println();
        System.out.println("❌ " + message);
    }

    public static void printInfo(String message) {

        System.out.println();
        System.out.println("ℹ️ " + message);
    }

    public static void printSeparator() {

        System.out.println(
                "------------------------------------------------------------------------------------------------"
        );
    }
}