import static com.gmail.a.glazovv77.HangMan.*;
import static com.gmail.a.glazovv77.ReadWords.readWords;
import static com.gmail.a.glazovv77.Renderer.*;

void main() {
    List<String> words = readWords();

    try (Scanner sc = new Scanner(System.in)) {
        while (true) {

            int attemptCount = HANGMAN_STAGES.length;

            printGreeting();

            String command = inputCommand();
            if (isQuit(command)) {
                System.out.println("Вы вышли из игры");
                return;
            }
            start();

        String randomWord = getRandomWord(words);

        String masked = "*".repeat(randomWord.length());
        System.out.printf("Загаданное слово: %s \n", masked);

        System.out.printf("У вас %d попыток отгадать слово \n", attemptCount);

        List<Character> errors = new ArrayList<>();
        int stageIndex = 0;

        while (attemptCount > 0 && masked.contains("*")) {
            System.out.println("Введите букву: ");
            String input = sc.nextLine().toUpperCase();

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
        }

        if (!masked.contains("*")) {
            System.out.println("-----------------------------------");
            System.out.printf("Поздравляем! Вы выиграли, загаданное слово: %s \n", randomWord);
        } else {
            System.out.println("-----------------------------------");
            System.out.printf("Вы проиграли, загаданное слово: %s \n", randomWord);
        }
        System.out.printf("Хотите сыграть еще? \nНажмите [%s], либо любую другую букву для выхода. \n", REPEAT);
        String answer = sc.nextLine().toUpperCase();
        if (answer.equals("Y")) {
            continue;
        }
        System.out.println("Игра закончена!");
        return;
        }
    }
}


