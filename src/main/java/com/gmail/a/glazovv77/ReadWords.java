package com.gmail.a.glazovv77;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadWords {

    private final static String FILE_PATH = "C:\\Users\\aglaz\\ProjectsIdea\\HangMan\\src\\main\\resources\\words.txt";

    public static List<String> readWords()  {
        List<String> words = new ArrayList<>();

        File inputFile = new File(FILE_PATH);

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
