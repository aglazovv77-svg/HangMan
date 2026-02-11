package com.gmail.a.glazovv77;

import static com.gmail.a.glazovv77.Game.*;

public class Main {

    public static void main(String[] args) {

        while (true) {

            printGreeting();

            String command = inputCommand();
            if (isQuit(command)) {
                System.out.println("Вы вышли из игры");
                return;
            }
            start();
        }
    }
}