package com.gmail.a.glazovv77;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class WordReader {

    private WordReader() {
    }

    public static List<String> read(String filePath, String regex)  {

        Pattern pattern = Pattern.compile(regex);
        List<String> words = new ArrayList<>();

        File inputFile = new File(filePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] lineWords = line.split("\\s+");
                for (String word : lineWords) {
                    Matcher matcher = pattern.matcher(word);
                    if (!matcher.matches()) {
                        continue;
                    }
                    words.add(word);
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException ("File not found: " + filePath);
        }
        if (words.isEmpty()) {
            throw new IllegalStateException("File does not contain words, program will terminate");
        }
        return words;
    }
}
