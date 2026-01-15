package com.gmail.a.glazovv77;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HangMan {
    public static List<String> readWords() throws IOException {
        String inputFile = "words.txt";

        File file = new File("C:\\Users\\aglaz\\eclipse-workspace\\HangMan1\\words.txt");
        System.out.println(file.getCanonicalPath());

        List<String> words = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            int wordCount = 0;
            while ((line = reader.readLine()) != null) {
                String[] lineWords = line.split("\\s+");
                for (String word : lineWords) {
                    if (!word.matches("[А-Яа-яЁё]+")) {
                        continue;
                    }
                    words.add(word);
                    wordCount++;
                }
            }
            System.out.println(wordCount + " слов");
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }
        if (words.isEmpty()) {
            System.out.println("Файл не содержит слов.");
        }
        return words;
    }

    public static String getRandomWord(List<String> words) {
        Random random = new Random();
        String randomWord = words.get(random.nextInt(words.size())).toUpperCase();

        // System.out.println("Загаданное слово: " + randomWord); // для отладки

        return randomWord;
    }

    public static boolean openLetter(String randomWord, char[] masked, char letter) {
        boolean found = false;
        for (int i = 0; i < randomWord.length(); i++) {
            if (letter == (randomWord.charAt(i))) {
                masked[i] = letter;
                found = true;
            }
        }
        return found;
    }

    public static int processError(List<Character> errors, char letter, int attemptCount, int[] stageIndex) {
        errors.add(letter);
        attemptCount--;

        System.out.println(STAGESHANGMAN[stageIndex[0]]);

        System.out.println("Неверно! Ошибки: " + errors);
        stageIndex[0]++;

        return attemptCount;
    }

    public static final String[] STAGESHANGMAN = {

            // 0 — основание
            """





					=========
					""",

            // 1 — вертикальная стойка
            """
					   |
					   |
					   |
					   |
					   |
					=========
					""",

            // 2 — горизонтальная балка + верёвка
            """
					   +---+
					   |   |
					   |
					   |
					   |
					=========
					""",

            // 3 — голова
            """
					   +---+
					   |   |
					   |   O
					   |
					   |
					=========
					""",

            // 4 — туловище
            """
					   +---+
					   |   |
					   |   O
					   |   |
					   |
					=========
					""",

            // 5 — левая рука
            """
					   +---+
					   |   |
					   |   O
					   |  /|
					   |
					=========
					""",

            // 6 — правая рука
            """
					   +---+
					   |   |
					   |   O
					   |  /|\\
					   |
					=========
					""",

            // 7 — левая нога
            """
					   +---+
					   |   |
					   |   O
					   |  /|\\
					   |  /
					=========
					""",

            // 8 — правая нога (финал)
            """
					   +---+
					   |   |
					   |   O
					   |  /|\\
					   |  / \\
					=========
					""" };
}
