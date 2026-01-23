//import com.gmail.a.glazovv77.HangMan.*;
import static com.gmail.a.glazovv77.HangMan.*;
import static com.gmail.a.glazovv77.Renderer.HANGMAN_STAGES;

void main() {
    List<String> words = readWords();

    try (Scanner sc = new Scanner(System.in)) {
        while (true) {

            int attemptCount = HANGMAN_STAGES.length;

            System.out.println("-----------------------------------");
            System.out.printf("Игра ВИСЕЛИЦА \nPush [%s]ew game or [%s]xit \n", START, QUIT);
            System.out.println("-----------------------------------");

            String command;
            do {
                command = sc.nextLine().toUpperCase();
                if (command.equals(QUIT)) {
                    System.out.println("Вы вышли из игры");
                    return;
                } else if (!command.equals(START)) {
                    System.out.println("Вы вводите неверное значение!!!");
                }
            } while (!command.equals(START));

            String randomWord = getRandomWord(words);
            char[] masked = "*".repeat(randomWord.length()).toCharArray();
            System.out.printf("Загаданное слово: %s \n", String.valueOf(masked));

            System.out.printf("У вас %d попыток отгадать слово \n", attemptCount);

            List<Character> errors = new ArrayList<>();
            int stageIndex = 0;

            while (attemptCount > 0 && new String(masked).contains("*")) {
                System.out.println("Введите букву: ");
                String input = sc.nextLine().toUpperCase();

                Matcher matcher = PATTERN.matcher(input);
                if (!matcher.matches()) {
                    System.out.println("Введите ОДНУ РУССКУЮ букву");
                    continue;
                }

                char letter = input.charAt(0);

                if (errors.contains(letter) || String.valueOf(masked).indexOf(letter) >= 0) {
                    System.out.println("Вы уже вводили эту букву");
                    continue;
                }

                boolean found = openLetter(randomWord, masked, letter);

                System.out.printf("Текущее слово: %s \n", String.valueOf(masked));

                if (!found) {

                    attemptCount = processError(errors, letter, attemptCount, stageIndex);
                    System.out.printf("Текущее слово: %s \n", String.valueOf(masked));

                    if (attemptCount == 0)
                        break;

                    System.out.printf("У вас осталось попыток %s \n", attemptCount);

                } else {

                    System.out.printf("Верно! У вас осталось попыток %s \n", attemptCount );
                    System.out.println("ошибки: " + errors);
                }
            }

            if (!new String(masked).contains("*")) {
                System.out.println("-----------------------------------");
                System.out.printf("Поздравляем! Вы выиграли, загаданное слово: %s \n", randomWord);
            } else {
                System.out.println("-----------------------------------");
                System.out.printf("Вы проиграли, загаданное слово: %s \n", randomWord);
            }
            System.out.printf("Хотите сыграть еще? \nНажмите [%s], либо любую другую букву для выхода.", REPEAT);
            String answer = sc.nextLine().toUpperCase();
            if (answer.equals("Y")) {
                continue;
            }
            System.out.println("Игра закончена!");
            return;
        }
    }

}
