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
            while ((line = reader.readLine()) != null) {
                String[] lineWords = line.split("\\s+");
                for (String word : lineWords) {
                    if (!word.matches("[А-Яа-яЁё]+")) {
                        continue;
                    }
                    words.add(word);
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException ("Файл не найден, работа программы будет прекращена");
        }
        if (words.isEmpty()) {
            throw new IllegalStateException("Файл не содержит слов, работа программы будет прекращена");
        }
        return words;
    }
}
