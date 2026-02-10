package com.gmail.a.glazovv77;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.gmail.a.glazovv77.Renderer.HANGMAN_STAGES;
import static com.gmail.a.glazovv77.Renderer.render;
import static com.gmail.a.glazovv77.WordReader.read;

public class GameService {
    private final static String START = "N";
    private final static String QUIT = "E";
    private static final char MASK_SYMBOL = '*';

    private static final String REGEX = "[А-Яа-яЁё]";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    private static final Scanner scanner = new Scanner(System.in);

    private static String word = "";
    private static String mask = "";
    private static List<Character> errorLetters;

    private static int attemptCount = 0;

    static void printGreeting() {
        System.out.println("-----------------------------------");
        System.out.printf("Игра ВИСЕЛИЦА \nPush [%s]ew game or [%s]xit \n", START, QUIT);
        System.out.println("-----------------------------------");
    }

    private static boolean isStart(String command) {

        return command.equals(START);
    }

    static boolean isQuit(String command) {

        return command.equals(QUIT);
    }

    static String inputCommand() {
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
        return words.get(indexWord).toUpperCase();
    }

    //игровая логика раунда
    static void start() {

        initRound();

        System.out.println("Загаданное слово: " + mask);
        attemptCount = HANGMAN_STAGES.length;
        System.out.println("У вас попыток отгадать слово " + attemptCount);

        int stageIndex = 0;

        while (!isGameOver()) {

            char letter = inputRussianLetter();

            if (isUsedLetter(letter)) {
                System.out.println("Вы уже вводили эту букву");
                continue;
            }

            openLetter(letter);

            currentWord(mask);

            if (!isWordLetter(letter)) {

                errorLetters = addErrorLetter(letter);
                attemptCount--;
                stageIndex++;

                System.out.printf("Неверно! Ошибки: %s \n У вас осталось попыток: %d \n ", errorLetters, attemptCount);
                render(stageIndex);

            } else {
                System.out.println("Верно! У вас осталось попыток " + attemptCount);
                System.out.println("ошибки: " + errorLetters);
            }

            if (isWon()) {
                System.out.println("-----------------------------------");
                System.out.println("Поздравляем! Вы выиграли, загаданное слово: " + word);
            } else if (isLose()) {
                System.out.println("-----------------------------------");
                System.out.println("Вы проиграли, загаданное слово: " + word);
            }
        }
    }

    private static void initRound() {

        List<String> words = read("words", "[А-Яа-яЁё]+");
        errorLetters = new ArrayList<>();

        word =  getRandomWord(words);
        mask = getMaskWord(word);

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
        return errorLetters.contains(letter) || mask.indexOf(letter) >= 0;
    }

    private static boolean isWordLetter(char letter) {
        return word.contains(String.valueOf(letter));
    }

    private static List<Character> addErrorLetter(char letter) {
        errorLetters.add(letter);
        return errorLetters;
    }

    private static void openLetter(char letter) {
        char[] maskArray = mask.toCharArray();
        for (int i = 0; i < word.length(); i++) {
            if (letter == (word.charAt(i))) {
                maskArray[i] = letter;
            }
        }
       mask = String.valueOf(maskArray);
    }

    private static void currentWord(String mask) {
        System.out.println("Текущее слово: " + mask);
    }

    private static boolean isGameOver() {
        return isWon() || isLose();
    }

    private static boolean isLose() {
        return attemptCount <= 0;
    }

    private static boolean isWon() {
        return word.equals(mask);
    }

    private static String getMaskWord(String word) {
        String s = String.valueOf(MASK_SYMBOL);
        return s.repeat(word.length());
    }
}
