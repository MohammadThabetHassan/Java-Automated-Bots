package com.sendemail;
import com.microsoft.playwright.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Noonbot extends Notificar {
    public static void runNoonBot() {
        boolean continueExecution = true;

        while (continueExecution) {
            showStartDialog();
            try (Playwright playwright = Playwright.create()) {
                BrowserType browserType = playwright.chromium();

                boolean isValidProduct = false;
                boolean isValidQuantity = false;
                String userInputProduct = "";
                int userInputQuantity = 0;

                while (!isValidProduct) {
                    userInputProduct = showInputDialog("Enter product:");

                    if (userInputProduct == null) {
                        showMessageDialog("Thank you, have a nice day");
                        System.exit(0);
                    } else if (userInputProduct.trim().isEmpty()) {
                        showMessageDialog("Error: Please enter a non-empty product name.");
                    } else {
                        isValidProduct = true;
                    }
                }

                while (!isValidQuantity) {
                    String userInputQuantityString = showInputDialog("Enter number of products (1 to 10):");

                    if (userInputQuantityString == null) {
                        showMessageDialog("Thank you, have a nice day");
                        System.exit(0);
                    } else if (userInputQuantityString.trim().isEmpty()) {
                        showMessageDialog("Error: Please enter a number between 1 and 10.");
                    } else {
                        try {
                            userInputQuantity = Integer.parseInt(userInputQuantityString);
                            if (userInputQuantity >= 1 && userInputQuantity <= 10) {
                                isValidQuantity = true;

                                Browser browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(false));
                                BrowserContext context = browser.newContext();
                                Page page = context.newPage();
                                String url = "https://www.noon.com/search/?q=" + userInputProduct;

                                page.navigate(url);
                                page.waitForLoadState();
                                String price = "strong.amount";
                                String name = "div[data-qa='product-name']";
                                List<ElementHandle> Product = page.querySelectorAll(name);
                                List<ElementHandle> Price = page.querySelectorAll(price);
                                List<String> links = new ArrayList<>();

                                int j = 0;
                                for (ElementHandle elementHandle : page.querySelectorAll("a")) {
                                    String id = elementHandle.getAttribute("id");
                                    if (id != null && id.startsWith("productBox") && j <= userInputQuantity) {
                                        String href = elementHandle.getAttribute("href");
                                        links.add(" https://www.noon.com/" + href);
                                        j++;
                                    }
                                }

                                StringBuilder x = new StringBuilder();
                                StringBuilder q = new StringBuilder();

                                int limit = Math.min(userInputQuantity, Product.size());
                                for (int i = 0; i < limit; i++) {
                                    ElementHandle z = Product.get(i);
                                    ElementHandle m = Price.get(i);
                                    Page newTab = context.newPage();
                                    newTab.navigate(links.get(i));

                                    String n = z.textContent();
                                    String p = m.textContent();
                                    q.append("item").append(i + 1).append(": ").append(n).append(" price= ").append(p).append("\n\t");


                                    x.append("item ").append(i + 1).append(": ").append(n).append(" price= ").append(p).append("\n\t").append(links.get(i)).append("\n").append(" ");
                                }

                                showMessageDialog(q.toString());
                                page.close();

                                int choice;
                                 boolean isValidInput = false;
                                while (!isValidInput) {
                                    String answer = JOptionPane.showInputDialog("Do you want to send it on Email 'yes' or 'no'?").toLowerCase();

                                    if (answer.equals("yes")) {
                                        String smtpHost = "smtp.gmail.com";
                                        String smtpPort = "587";
                                        String username = "test.java.153@gmail.com";
                                        String password = "dxjottaxlhpcpzqi";
                                        EmailSender emailSender = new EmailSender(smtpHost, smtpPort, username, password);
                                        String to = "test.java.153@gmail.com";
                                        String subject = "This Email about " + userInputProduct;
                                        String body = x.toString();
                                        emailSender.sendEmail(to, subject, body);
                                        isValidInput = true;
                                    } else if (answer.equals("no")) {
                                        JOptionPane.showMessageDialog(null, "Thank you, have a nice day ^_^");
                                        isValidInput = true;
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Invalid answer. Please enter 'yes' or 'no'.");
                                    }
                                }
                                continueExecution=false;
                                boolean isValidInput1 = false;
                                while (!isValidInput1) {
                                    String answer = JOptionPane.showInputDialog("Do you want to send it on Whatsapp 'yes' or 'no'?").toLowerCase();

                                    if (answer.equals("yes")) {
                                        Notificar whatsapp= new Notificar();
                                        whatsapp.Whatsapp(x.toString());


                                        isValidInput1 = true;
                                    } else if (answer.equals("no")) {
                                        JOptionPane.showMessageDialog(null, "Thank you, have a nice day ^_^");
                                        isValidInput1 = true;
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Invalid answer. Please enter 'yes' or 'no'.");
                                    }
                                }


                            } else {
                                showMessageDialog("Error: Please enter a number between 1 and 10.");
                            }
                        } catch (NumberFormatException e) {
                            showMessageDialog("Error: Please enter a valid number.");
                        }
                    }
                }
            }
        }
    }

    private static void showStartDialog() {
        ImageIcon customIcon = createImageIcon("src/pic/bag.png", 50, 50);
        int option = JOptionPane.showOptionDialog(
                null,
                "Click 'Start' to begin",
                "Noon Product bot",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                customIcon,
                new String[]{"Start", "Cancel"},
                null
        );

        if (option == 1) {
            System.exit(0);
        }
    }

    private static String showInputDialog(String message) {
        return JOptionPane.showInputDialog(message);
    }
    private static void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
    private static ImageIcon createImageIcon(String path, int width, int height) {
        ImageIcon originalIcon = new ImageIcon(path);
        Image resizedImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }



    }