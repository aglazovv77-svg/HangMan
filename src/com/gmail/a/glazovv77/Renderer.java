package com.gmail.a.glazovv77;

public class Renderer {

    public static final String[] HANGMAN_STAGES = {

            """





					=========
					""",

            """
					   |
					   |
					   |
					   |
					   |
					=========
					""",

            """
					   +---+
					   |   |
					   |
					   |
					   |
					=========
					""",

            """
					   +---+
					   |   |
					   |   O
					   |
					   |
					=========
					""",

            """
					   +---+
					   |   |
					   |   O
					   |   |
					   |
					=========
					""",

            """
					   +---+
					   |   |
					   |   O
					   |  /|
					   |
					=========
					""",

            """
					   +---+
					   |   |
					   |   O
					   |  /|\\
					   |
					=========
					""",

            """
					   +---+
					   |   |
					   |   O
					   |  /|\\
					   |  /
					=========
					""",

            """
					   +---+
					   |   |
					   |   O
					   |  /|\\
					   |  / \\
					=========
					""" };

    public static void render(int stageIndex) {

        System.out.println(HANGMAN_STAGES[stageIndex]);

    }


}
