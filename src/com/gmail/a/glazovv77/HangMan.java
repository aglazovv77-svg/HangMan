package com.gmail.a.glazovv77;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HangMan {
    static Scanner scanner = new Scanner(System.in);
    public final static String START = "N";
    public final static String QUIT = "E";
    public final static String REPEAT = "Y";
    public static final String REGEX = "[А-Яа-яЁё]";
    public static final Pattern PATTERN = Pattern.compile(REGEX);

    public static void printGreeting() {
        System.out.println("-----------------------------------");
        System.out.printf("Игра ВИСЕЛИЦА \nPush [%s]ew game or [%s]xit \n", START, QUIT);
        System.out.println("-----------------------------------");
    }

    private static boolean isStart(String command) {
        return command.equals(START);
    }

    public static boolean isQuit(String command) {
        return  command.equals(QUIT);
    }

    public static String inputCommand() {
        while(true) {
            //Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine().toUpperCase();
            if(isStart(command) || isQuit(command)) {
                return command;
            }
            System.out.println("Вы вводите неверное значение!!!");
        }
    }

    public static String getRandomWord(List<String> words) {
        Random random = new Random();
        int indexWord = random.nextInt(words.size());
        String randomWord = words.get(indexWord).toUpperCase();

        System.out.println(randomWord);

        return randomWord;
    }

    public static void start() {
        //игровая логика раунда
    }

    public static String openLetter(String word, String masked, char letter) {
      char[] maskArray = masked.toCharArray();
        for (int i = 0; i < word.length(); i++) {
            if (letter == (word.charAt(i))) {
                maskArray[i] = letter;

            }
        }
        masked = new String(maskArray);
        return masked;
    }

    public static void currentWord(String masked) {
        System.out.printf("Текущее слово: %s \n", masked);
    }

    public static int processError(List<Character> errors, char letter, int attemptCount) {
        errors.add(letter);
        attemptCount--;

        System.out.println("Неверно! Ошибки: " + errors);

        return attemptCount;
    }
}
