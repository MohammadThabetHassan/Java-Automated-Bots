package com.sendemail;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        String[] options = {"Noon Bot", "AliExpress Bot", "Exit"};
        int choice;

        do {
            choice = JOptionPane.showOptionDialog(
                    null,
                    "Choose an option:",
                    "Bot Selection",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    null
            );

            switch (choice) {
                case 0:
                    // Run Noon Bot
                    Noonbot.runNoonBot();
                    break;
                case 1:
                    // Run AliExpress Bot
                   AliExpressBot.runAliExpressBot();

                    break;
                case 2:
                    // Exit
                    System.exit(0);
                    break;
            }

        } while (choice != -1);
    }
}
