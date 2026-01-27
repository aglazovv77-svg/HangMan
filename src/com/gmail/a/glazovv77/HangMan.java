package com.gmail.a.glazovv77;

import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class HangMan {

    public final static String START = "N";
    public final static String QUIT = "E";
    public final static String REPEAT = "Y";
    public static final String REGEX = "[А-Яа-яЁё]";
    public static final Pattern PATTERN = Pattern.compile(REGEX);

//    private static String word;
//    private static char[] masked;

    public static void printGreeting() {
        System.out.println("-----------------------------------");
        System.out.printf("Игра ВИСЕЛИЦА \nPush [%s]ew game or [%s]xit \n", START, QUIT);
        System.out.println("-----------------------------------");
    }

    public static String getRandomWord(List<String> words) {
        Random random = new Random();
        int indexWord = random.nextInt(words.size());
        String randomWord = words.get(indexWord).toUpperCase();

        System.out.println(randomWord);

        return randomWord;
    }

    public static boolean openLetter(String word, char[] masked, char letter) {
        boolean found = false;
        for (int i = 0; i < word.length(); i++) {
            if (letter == (word.charAt(i))) {
                masked[i] = letter;
                found = true;
            }
        }
        return found;
    }

    public static void currentWord(char[] masked) {
        System.out.printf("Текущее слово: %s \n", String.valueOf(masked));
    }

    public static int processError(List<Character> errors, char letter, int attemptCount) {
        errors.add(letter);
        attemptCount--;

        System.out.println("Неверно! Ошибки: " + errors);

        return attemptCount;
    }
}
