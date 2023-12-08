# Bot Automation with Java

This repository contains Java bots that automate tasks on online platforms such as Noon and AliExpress. These bots leverage Swing for graphical interfaces and Playwright for browser automation.

## Overview

### Main Bot (`Main.java`)
The `Main` class provides a menu interface to access different functionalities:
- Choose between "Noon Bot," "AliExpress Bot," or exit the program.
- Select a bot to perform automated tasks.

### Noon Bot (`Noonbot.java`)
The `Noonbot` class facilitates product search on Noon and provides options to receive details via email or WhatsApp:
- Search for a product and specify the quantity.
- Receive product details via email or send details through WhatsApp.

### AliExpress Bot (`AliExpressBot.java`)
The `AliExpressBot` class automates product searches on AliExpress and provides options for notifications:
- Search for a product, select items, and receive details via email or WhatsApp.
## Video Demonstration

Watch the bot automation in action:
![Bot Automation Video](Bot Automation Video.gif)
## How to Use

### Setup
- Ensure you have Java installed.
- Clone the repository to your local machine.

## Clone the Repository

To clone this repository and get a local copy on your machine, use the following command:

```bash
git clone https://github.com/MohammadThabetHassan/Java-Automated-Bots.git
```

### Running the Bots
- Compile and execute the desired Java file (`Main.java`, `Noonbot.java`, or `AliExpressBot.java`).
- Follow on-screen instructions to interact with the bots.


## Dependencies

- **javax.mail (1.6.2)**: For email functionalities.
- **com.microsoft.playwright (1.31.0)**: Enables browser automation.
- **org.apache.xbean (4.22)**: Provides reflection utilities.
- **org.openjfx (21-ea+17)**: Used for JavaFX-based graphical interfaces.
- **org.jline (3.23.0)**: Handles terminal input.

## Contributing
Feel free to contribute by forking the repository, making changes, and creating a pull request.

## Authors
**Mohammad Thabet Hassan**
