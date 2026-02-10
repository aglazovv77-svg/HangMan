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

    private static final String FILE_PATH = String.valueOf(Path.of("src","main","resources","words.txt"));
    private static final String REGEX = "[А-Яа-яЁё]+";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    private WordReader() {
    }

    public static List<String> read(String filePath, String regex)  {
        List<String> words = new ArrayList<>();

        File inputFile = new File(FILE_PATH);

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] lineWords = line.split("\\s+");
                for (String word : lineWords) {
                    Matcher matcher = PATTERN.matcher(word);
                    if (!matcher.matches()) {
                        continue;
                    }
                    words.add(word);
                }
            }
        } catch (IOException e) {
            String path = String.valueOf(Path.of("C","Users","aglaz","ProjectsIdea","HangMan","src","main","resources","words.txt"));
            throw new IllegalArgumentException ("File not found: " + path);
        }
        if (words.isEmpty()) {
            throw new IllegalStateException("File does not contain words, program will terminate");
        }
        return words;
    }
}
