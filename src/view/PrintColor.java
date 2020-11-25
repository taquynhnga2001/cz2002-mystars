package src.view;

import src.constants.Color;

/**
 * Display color to the console
 */
public class PrintColor {
    public static void print(String text, String color) {
        switch (color.toUpperCase()) {
            case "RED": color = Color.RED;
                break;
            case "GREEN": color = Color.GREEN;
                break;
            case "YELLOW": color = Color.YELLOW;
                break;
            case "CYAN": color = Color.CYAN;
                break;
            case "CYAN_BOLD": color = Color.CYAN_BOLD;
                break;
            case "BLUE_BOLD": color = Color.BLUE_BOLD;
                break;
            case "PURPLE": color = Color.PURPLE;
                break;
            default: color = Color.RESET;
        }
        System.out.print(color + text + Color.RESET);
    }

    public static void println(String text, String color) {
        switch (color.toUpperCase()) {
            case "RED": color = Color.RED;
                break;
            case "GREEN": color = Color.GREEN;
                break;
            case "YELLOW": color = Color.YELLOW;
                break;
            case "CYAN": color = Color.CYAN;
                break;
            case "CYAN_BOLD": color = Color.CYAN_BOLD;
                break;
            case "BLUE_BOLD": color = Color.BLUE_BOLD;
                break;
            case "PURPLE": color = Color.PURPLE;
                break;
            default: color = Color.RESET;
        }
        System.out.print(color + text + Color.RESET + "\n");
    }
}
