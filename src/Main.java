import static com.gmail.a.glazovv77.HangMan.*;

void main() {
    List<String> words = readWords();

    try (Scanner sc = new Scanner(System.in)) {
        while (true) {

            int attemptCount = HANGMAN_STAGES.length;

            System.out.println("-----------------------------------");
            System.out.println("Игра ВИСЕЛИЦА \nPush [N]ew game or [E]xit\n");
            System.out.println("-----------------------------------");

            String s;
            do {
                s = sc.nextLine().toUpperCase();
                if (s.equals("E")) {
                    System.out.println("Вы вышли из игры");
                    return;
                } else if (!s.equals("N")) {
                    System.out.println("Вы вводите неверное значение!!!");
                }
            } while (!s.equals("N"));

            String randomWord = getRandomWord(words);
            char[] masked = "*".repeat(randomWord.length()).toCharArray();
            System.out.println("Загаданное слово: " + new String(masked));

            System.out.println("У вас " + attemptCount + " попыток отгадать слово");

            List<Character> errors = new ArrayList<>();
            int[] stageIndex = { 0 };

            while (attemptCount > 0 && new String(masked).contains("*")) {
                System.out.println("Введите букву: ");
                String input = sc.nextLine().toUpperCase();

                if (!input.matches("[А-ЯЁ]")) {
                    System.out.println("Введите ОДНУ РУССКУЮ букву");
                    continue;
                }

                char letter = input.charAt(0);

                if (errors.contains(letter) || new String(masked).indexOf(letter) >= 0) {
                    System.out.println("Вы уже вводили эту букву");
                    continue;
                }

                boolean found = openLetter(randomWord, masked, letter);

                System.out.println("Текущее слово: " + new String(masked));

                if (!found) {

                    attemptCount = processError(errors, letter, attemptCount, stageIndex);
                    System.out.println("Текущее слово: " + new String(masked));

                    if (attemptCount == 0)
                        break;

                    String attempt = switch (attemptCount) {
                        case 1 -> "попытка";
                        case 2, 3, 4 -> "попытки";
                        default -> "попыток";
                    };

                    System.out.println("Осталось " + attemptCount + " " + attempt + " отгадать слово");

                } else {
                    // понимаю что код задвоен(из-за перехода от 5 к 4, при attempt = 4, падеж был от 5), не смог реализовать иначе.
                    // switch выносил из блока if параллельно выносил счетчик из метода, но все равно работало некорректно и метод получался слепым
                    String attempt = switch (attemptCount) {
                        case 1 -> "попытка";
                        case 2, 3, 4 -> "попытки";
                        default -> "попыток";
                    };

                    System.out.println("Верно! Осталось " + attemptCount + " " + attempt + " отгадать слово");
                    System.out.println("ошибки: " + errors);
                }
            }

            if (!new String(masked).contains("*")) {
                System.out.println("-----------------------------------");
                System.out.println("Поздравляем! Вы выиграли, загаданное слово: " + randomWord);
            } else {
                System.out.println("-----------------------------------");
                System.out.println("Вы проиграли, загаданное слово: " + randomWord);
            }
            System.out.println("Хотите сыграть еще? y/n");
            String answer = sc.nextLine().toUpperCase();
            if (answer.equals("Y")) {
                continue;
            }
            System.out.println("Игра закончена!");
            return;
        }
    }

}
