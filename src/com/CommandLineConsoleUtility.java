package com;
import java.util.regex.Pattern;


public class CommandLineConsoleUtility {


    private static final Pattern INPUT_PATTERN = Pattern.compile("^(<|[qQ]|\\d+)$");


    public static String getStringFromKeyboard(String prompt) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        String input;

        while (true) {
            System.out.print(prompt + " >>> ");
            input = scanner.nextLine().trim();


            if (INPUT_PATTERN.matcher(input).matches()) {
                return input;
            }


            System.out.println("输入错误！只能输入：< 、q/Q 、数字序号");
        }
    }

    // 提示消息
    public static void printNoticeMessage(String msg) {
        System.out.println("\n[提示] " + msg + "\n");
    }
}