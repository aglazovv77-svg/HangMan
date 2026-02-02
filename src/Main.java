import static com.gmail.a.glazovv77.ReadWords.readWords;
import static com.gmail.a.glazovv77.Renderer.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

private final static String START = "N";
private final static String QUIT = "E";
private final static String REPEAT = "Y";

private static final String REGEX = "[А-Яа-яЁё]";
private static final Pattern PATTERN = Pattern.compile(REGEX);

private static Scanner scanner = new Scanner(System.in);

private static final char MASK_SYMBOL = '*';

private static String word = getRandomWord(readWords());
private static String masked = maskedWord(word);

void main() {

    while (true) {

        printGreeting();

        String command = inputCommand();
        if (isQuit(command)) {
            System.out.println("Вы вышли из игры");
            return;
        }
        start();
label:
        while (true) {
            System.out.printf("Хотите сыграть еще? \nНажмите [%s], либо любую другую букву для выхода. \n", REPEAT);
            String answer = scanner.nextLine().toUpperCase();
            if (answer.equals("Y")) {
                break label;
            }
            System.out.println("Игра закончена!");
            return;
        }
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
    return  command.equals(QUIT);
}

private static String inputCommand() {
    while(true) {
        String command = scanner.nextLine().toUpperCase();
        if(isStart(command) || isQuit(command)) {
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

    int attemptCount = HANGMAN_STAGES.length;

    String randomWord = getRandomWord(words);

    String masked = maskedWord(randomWord);
    System.out.printf("Загаданное слово: %s \n", masked);

    System.out.printf("У вас %d попыток отгадать слово \n", attemptCount);

    List<Character> errors = new ArrayList<>();
    int stageIndex = 0;

    while (!isGameOver()) {
        System.out.println("Введите букву: ");
        String input = scanner.nextLine().toUpperCase();

        Matcher matcher = PATTERN.matcher(input);
        if (!matcher.matches()) {
            System.out.println("Введите ОДНУ РУССКУЮ букву");
            continue;
        }

        char letter = input.charAt(0);

        if (errors.contains(letter) || masked.indexOf(letter) >= 0) {
            System.out.println("Вы уже вводили эту букву");
            continue;
        }

        masked = openLetter(randomWord, masked, letter);

        currentWord(masked);

        if (!masked.contains(String.valueOf(letter))) {

            attemptCount = processError(errors, letter, attemptCount);

            render(stageIndex);
            ++stageIndex;

            if (attemptCount == 0)
                break;

            System.out.printf("У вас осталось попыток %s \n", attemptCount);

        } else {

            System.out.printf("Верно! У вас осталось попыток %s \n", attemptCount);
            System.out.printf("ошибки: %s \n", errors);
        }

        if (isWon()) {
            System.out.println("-----------------------------------");
            System.out.printf("Поздравляем! Вы выиграли, загаданное слово: %s \n", randomWord);
        } else if (isLose()) {
            System.out.println("-----------------------------------");
            System.out.printf("Вы проиграли, загаданное слово: %s \n", randomWord);
        }
    }
}

private static String openLetter(String word, String masked, char letter) {
    char[] maskArray = masked.toCharArray();
    for (int i = 0; i < word.length(); i++) {
        if (letter == (word.charAt(i))) {
            maskArray[i] = letter;

        }
    }
    masked = new String(maskArray);
    return masked;
}

private static void currentWord(String masked) {
    System.out.printf("Текущее слово: %s \n", masked);
}

private static int processError(List<Character> errors, char letter, int attemptCount) {
    errors.add(letter);
    attemptCount--;

    System.out.println("Неверно! Ошибки: " + errors);

    return attemptCount;
}

private static boolean isGameOver() {
    return  isWon() || isLose();
}

private static boolean isLose() {
    if(HANGMAN_STAGES.length != 0) {
        return false;
    }
    return masked.contains(String.valueOf(MASK_SYMBOL));
}

private static boolean isWon() {
    return !masked.contains(String.valueOf(MASK_SYMBOL));
}

private static String maskedWord(String word) {
    return masked = "*".repeat(word.length());
}

