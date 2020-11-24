package view;

import java.util.Scanner;

import constants.Color;

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

    public static Scanner sc = new Scanner(System.in);
    public static String read() {
        System.out.print(Color.CYAN);
        String text = sc.next();
        System.out.print(Color.RESET);
        return text;
    }
    public static String readLine() {
        System.out.print(Color.CYAN);
        String text = sc.nextLine();
        System.out.print(Color.RESET);
        return text;
    }
}
