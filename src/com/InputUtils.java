package com;


import java.util.Scanner;

public class InputUtils {
    private static final Scanner scanner = new Scanner(System.in);


    public static String getNonEmptyInput(String prompt) {
        String input;
        while (true) {
            System.out.print(">>>" + prompt);
            input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("未能按照提示要求输入非空字符串，系统将等待您的继续输入......! <<<");
        }
    }


    public static String getOptionalInput(String prompt) {
        System.out.print(">>>" + prompt);
        return scanner.nextLine().trim();
    }


    public static void close() {
        scanner.close();
    }
}