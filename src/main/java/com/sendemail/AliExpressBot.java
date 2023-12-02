package com.sendemail;

import com.microsoft.playwright.*;
import javax.swing.*;
import java.awt.*;

public class AliExpressBot {

    public static void main(String[] args) {
        runAliExpressBot();
    }

    public static void runAliExpressBot() {
        boolean continueExecution = true;

        while (continueExecution) {
            createAndShowGui();

            try (Playwright playwright = Playwright.create()) {
                String searchItem = getUserInput("Enter item:");

                if (searchItem.trim().isEmpty()) {
                    showMessage("Please enter a non-empty product name.");
                    continue;
                } else if (searchItem == null) {
                    showMessage("Thank you, have a nice day (●'◡'●)");
                    continue;
                }

                BrowserType browserType = playwright.chromium();
                Browser browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(false));
                BrowserContext context = browser.newContext();
                Page page = context.newPage();
                StringBuilder stringBuilder = new StringBuilder();
                page.navigate("https://best.aliexpress.com/");
                Thread.sleep(5550);


                page.fill("#search-words", searchItem);
                page.click("body > div:nth-child(15) > div > div._1-aaU > div._1ZwH_ > div.Sk1_X._1-SOk");
                page.click("//*[@id=\"_global_header_23_\"]/div/div/div[2]/div[1]/div[1]/div/input[2]");

                int userNumber = getUserNumberInput("Enter a number between 1 and 10:");

                if (userNumber < 1 || userNumber > 10) {
                    showMessage("Please enter a number between 1 and 10.");
                    continue;
                }

                System.out.println("User entered: " + userNumber);

                for (int i = 1; i <= userNumber; i++) {
                    Object elementText = page.evaluate("document.querySelector('#card-list > div:nth-child(" + i + ")').innerText");
                   // Object elementText1 = page.evaluate("document.querySelector('#card-list > div:nth-child(" + i + ") > div > a > div.multi--content--11nFIBL > div.multi--price--1okBCly > div.multi--price-sale--U-S0jtj > span:nth-child(2))'.innerText");
                    String link = page.evaluate("document.querySelector('#card-list > div:nth-child(" + i + ") > div > a').getAttribute('href')").toString();
                    link = link.replaceFirst("^//", "https://");
                    stringBuilder.append("The ").append(i).append("item is ").append(elementText).append("\n").append("The link is ").append(link).append("\n");
                    System.out.println(elementText);
                    System.out.println(link);
                    System.out.println("############################");
                    Page newTab = context.newPage();
                    newTab.navigate(link);
                }

                boolean validAnswer = false;

                while (!validAnswer) {
                    String answer = getUserInput("Do you want to send it on Email 'yes' or 'no'?").toLowerCase();

                    if (answer.equals("yes")) {
                        String smtpHost = "smtp.gmail.com";
                        String smtpPort = "587";
                        String username = "test.java.153@gmail.com";
                        String password = "dxjottaxlhpcpzqi";
                        EmailSender emailSender = new EmailSender(smtpHost, smtpPort, username, password);
                        String to = "test.java.153@gmail.com";
                        String subject = "This Email about " + searchItem;
                        String body = stringBuilder.toString();
                        emailSender.sendEmail(to, subject, body);
                        validAnswer = true;
                    } else if (answer.equals("no")) {
                        showMessage("Thank you, have a nice day ^_^");
                        validAnswer = true;
                    } else {
                        showMessage("Invalid answer. Please enter 'yes' or 'no'.");
                    }
                }
                boolean isValidInput1 = false;
                while (!isValidInput1) {
                    String answer = JOptionPane.showInputDialog("Do you want to send it on Whatsapp 'yes' or 'no'?").toLowerCase();

                    if (answer.equals("yes")) {
                        Notificar whatsapp= new Notificar();
                        whatsapp.Whatsapp(stringBuilder.toString());


                        isValidInput1 = true;
                    } else if (answer.equals("no")) {
                        JOptionPane.showMessageDialog(null, "Thank you, have a nice day ^_^");
                        isValidInput1 = true;
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid answer. Please enter 'yes' or 'no'.");
                    }
                }

                int choice = showConfirmDialog("Do you want to run the AliExpress bot again?", "Confirmation");

                if (choice != 0) {
                    continueExecution = false;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void createAndShowGui() {
        JFrame frame = new JFrame("AliExpress Bot");
        ImageIcon customIcon = new ImageIcon(new ImageIcon("src/pic/image.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        JLabel label = new JLabel("Click 'Start' to begin", customIcon, JLabel.CENTER);
        JButton startButton = new JButton("Start");
        JButton cancelButton = new JButton("Cancel");

        startButton.addActionListener(e -> {
            frame.dispose();
        });
        cancelButton.addActionListener(e -> {
            System.exit(0);
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(startButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static String getUserInput(String message) {
        return JOptionPane.showInputDialog(message);
    }

    private static int getUserNumberInput(String message) {
        int userNumber = -1;

        do {
            String userNumberInput = getUserInput(message);
            try {
                userNumber = Integer.parseInt(userNumberInput);
            } catch (NumberFormatException ex) {
                showMessage("Please enter a valid number.");
            }
        } while (userNumber < 1 || userNumber > 10);

        return userNumber;
    }

    private static void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    private static int showConfirmDialog(String message, String title) {
        return JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
    }
}
