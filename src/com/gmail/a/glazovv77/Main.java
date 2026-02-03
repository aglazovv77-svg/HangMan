package com.gmail.a.glazovv77;

import static com.gmail.a.glazovv77.ReadWords.readWords;
import static com.gmail.a.glazovv77.Renderer.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private final static String START = "N";
    private final static String QUIT = "E";
    private static final char MASK_SYMBOL = '*';

    private static final String REGEX = "[А-Яа-яЁё]";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    private static final Scanner scanner = new Scanner(System.in);

    private static String word = "";
    private static String mask = "";
    private static List<Character> errorLeters;//  = new ArrayList<>();

    private static int attemptCount = 0;

    public static void main(String[] args) {

        while (true) {

            printGreeting();

            String command = inputCommand();
            if (isQuit(command)) {
                System.out.println("Вы вышли из игры");
                return;
            }
            start();
        }
    }

    private static void printGreeting() {
        System.out.println("-----------------------------------");
        System.out.printf("Игра ВИСЕЛИЦА \nPush [%s]ew game or [%s]xit \n", START, QUIT);
        System.out.println("-----------------------------------");
    }

    private static boolean isStart(String command) {
        return command.equals(START);
    }

    private static boolean isQuit(String command) {
        return command.equals(QUIT);
    }

    private static String inputCommand() {
        while (true) {
            String command = scanner.nextLine().toUpperCase();
            if (isStart(command) || isQuit(command)) {
                return command;
            }
            System.out.println("Вы вводите неверное значение!!!");
        }
    }

    private static String getRandomWord(List<String> words) {
        Random random = new Random();
        int indexWord = random.nextInt(words.size());
        String randomWord = words.get(indexWord).toUpperCase();

        System.out.println(randomWord);  // для отладки

        return randomWord;
    }

    //игровая логика раунда
    private static void start() {

        List<String> words = readWords();
        errorLeters = new ArrayList<>();

        word =  getRandomWord(words);
        mask = getMaskWord(word);

        System.out.printf("Загаданное слово: %s \n", mask);
        attemptCount = HANGMAN_STAGES.length;
        System.out.printf("У вас попыток отгадать слово %d \n", attemptCount);

        int stageIndex = 0;

        while (!isGameOver()) {

            char letter = inputRussianLetter();

            if (isUsedLetter(letter)) {
                System.out.println("Вы уже вводили эту букву");
                continue;
            }

            mask = openLetter(word, mask, letter);

            currentWord(mask);

            if (!isWordLetter(letter)) {

                errorLeters = errorLeters(letter);
                attemptCount--;
                stageIndex++;

                System.out.println("Неверно! Ошибки: " + errorLeters);
                render(stageIndex);

                System.out.printf("У вас осталось попыток %s \n", attemptCount);

            } else {
                System.out.printf("Верно! У вас осталось попыток %s \n", attemptCount);
                System.out.printf("ошибки: %s \n", errorLeters);
            }

            if (isWon()) {
                System.out.println("-----------------------------------");
                System.out.printf("Поздравляем! Вы выиграли, загаданное слово: %s \n", word);
            } else if (isLose()) {
                System.out.println("-----------------------------------");
                System.out.printf("Вы проиграли, загаданное слово: %s \n", word);
            }
        }
    }

    private static char inputRussianLetter() {

        while (true) {
            System.out.println("Введите букву: ");
            String input = scanner.nextLine().toUpperCase();

            Matcher matcher = PATTERN.matcher(input);
            if (matcher.matches()) {
                return input.charAt(0);
            }
            System.out.println("Введите ОДНУ РУССКУЮ букву");
        }

    }

    private static boolean isUsedLetter(char letter) {
        return errorLeters.contains(letter) || mask.indexOf(letter) >= 0;
    }

    private static boolean isWordLetter(char letter) {
        return word.contains(String.valueOf(letter));
    }

    private static List<Character> errorLeters(char letter) {
        errorLeters.add(letter);
        return errorLeters;
    }

    private static String openLetter(String word, String mask, char letter) {
        char[] maskArray = mask.toCharArray();
        for (int i = 0; i < word.length(); i++) {
            if (letter == (word.charAt(i))) {
                maskArray[i] = letter;
            }
        }
        mask = String.valueOf(maskArray);
        return mask;
    }

    private static void currentWord(String mask) {
        System.out.printf("Текущее слово: %s \n", mask);
    }

    private static boolean isGameOver() {
        return isWon() || isLose();
    }

    private static boolean isLose() {
        if (attemptCount > 0) {
            return false;
        }
        return mask.contains(String.valueOf(MASK_SYMBOL));
    }

    private static boolean isWon() {
        return word.equals(mask);
    }

    private static String getMaskWord(String word) {
        String s = String.valueOf(MASK_SYMBOL);
        return mask = s.repeat(word.length());
    }
}