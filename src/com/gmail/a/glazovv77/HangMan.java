package com.gmail.a.glazovv77;

import static com.gmail.a.glazovv77.Renderer.render;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class HangMan {

    public final static String START = "N";
    public final static String QUIT = "E";
    public final static String REPEAT = "Y";
    public static final String REGEX = "[А-Яа-яЁё]";
    public static final Pattern PATTERN = Pattern.compile(REGEX);

    public static List<String> readWords()  {
        String inputFile = "words.txt";

        List<String> words = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            int wordCount = 0;
            while ((line = reader.readLine()) != null) {
                String[] lineWords = line.split("\\s+");
                for (String word : lineWords) {
                    if (!word.matches(REGEX)) {
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

    public static int processError(List<Character> errors, char letter, int attemptCount, int stageIndex) {
        errors.add(letter);
        attemptCount--;

        render(0);

        System.out.println("Неверно! Ошибки: " + errors);
        stageIndex++;

        return attemptCount;
    }
}
