package com.gmail.a.glazovv77;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadWords {
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
}
