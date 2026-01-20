package com.gmail.a.glazovv77;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HangMan {

    public static final String[] HANGMAN_STAGES = {

            """





					=========
					""",

            """
					   |
					   |
					   |
					   |
					   |
					=========
					""",

            """
					   +---+
					   |   |
					   |
					   |
					   |
					=========
					""",

            """
					   +---+
					   |   |
					   |   O
					   |
					   |
					=========
					""",

            """
					   +---+
					   |   |
					   |   O
					   |   |
					   |
					=========
					""",

            """
					   +---+
					   |   |
					   |   O
					   |  /|
					   |
					=========
					""",

            """
					   +---+
					   |   |
					   |   O
					   |  /|\\
					   |
					=========
					""",

            """
					   +---+
					   |   |
					   |   O
					   |  /|\\
					   |  /
					=========
					""",

            """
					   +---+
					   |   |
					   |   O
					   |  /|\\
					   |  / \\
					=========
					""" };

    public static List<String> readWords()  {
        String inputFile = "words.txt";

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
        int indexWord = random.nextInt(words.size());
        String randomWord = words.get(indexWord).toUpperCase();

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

        System.out.println(HANGMAN_STAGES[stageIndex[0]]);

        System.out.println("Неверно! Ошибки: " + errors);
        stageIndex[0]++;

        return attemptCount;
    }
}
