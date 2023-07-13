package main;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import objects.Map;

class BasicCommands {

    private List<String> commandList = Arrays.asList("help", "itemlist");

    void outputForBasicCommand(String command) {

        switch (command) {
        case "help":
            System.out.println("help");
            break;

        case "itemlist":
            System.out.println("itemlist");
            break;
        }

    }

    List<String> getCommandList() {
        return commandList;
    }
}

public class GameSettings {

    // 0 = easy, 1 = normal, 2 = hard
    private int difficulty;

    private boolean gameIsOver;

    // In this Method the user can choose the Map Size
    public void chooseMapSize(Map map) {
        @SuppressWarnings("resource")
        Scanner scan = new Scanner(System.in);

        String userInput;

        System.out.println("Type a number between 3 and and 9");
        userInput = scan.nextLine();
        try {
            int number = Integer.parseInt(userInput);
            if (number >= 3 && number <= 9) {
                map.setMapSize(number);
                System.out.println("The map has the size " + map.getMapSize() + "x" + map.getMapSize());
                return;
            } else {
                System.out.println("This isn't a number between 3 and 9");
                chooseMapSize(map);
            }

        } catch (NumberFormatException e) {
            System.out.println("This isn't a number between 3 and 9");
            chooseMapSize(map);
        }

    }

    // In this Method the user can choose the difficulty
    public void chooseDifficulty() {
        @SuppressWarnings("resource")
        Scanner scan = new Scanner(System.in);

        String userInput;

        userInput = scan.nextLine().toLowerCase().replace(" ", "");

        switch (userInput) {

        case "easy":
            difficulty = 0;
            System.out.println("You choose the difficulty easy");
            break;

        case "normal":
            difficulty = 1;
            System.out.println("You choose the difficulty normal");
            break;

        case "hard":
            difficulty = 2;
            System.out.println("You choose the difficulty hard");
            break;

        default:
            System.out.println("This isn't a difficulty");
            System.out.println("Choose easy, normal or hard");
            chooseDifficulty();
            break;

        }

    }

    public boolean calculateRandomNumberBasedOnDifficulty(int percentageForEasy, int percentageForNormal, int percentageForHard) {
        Random random = new Random();
        int randomNumber = random.nextInt(100);

        switch (difficulty) {

        case 0:
            if (randomNumber < percentageForEasy) {
                return true;
            } else {
                return false;
            }

        case 1:
            if (randomNumber < percentageForNormal) {
                return true;
            } else {
                return false;
            }
        case 2:
            if (randomNumber < percentageForHard) {
                return true;
            } else {
                return false;
            }
        default:
            System.err.println("The value of the difficulty is not valid at GameSettings:" + Thread.currentThread().getStackTrace()[0]
                    .getLineNumber());
            System.exit(0);
            return false;
        }
    }

    /*
     * percentChanceForEachItem parameters:
     * 
     * The first array contains the chances for each difficulty. In the first index are the chances for easy mode, in the second for normal
     * mode and in the third for hard mode.
     * 
     * The thrid array contains the number of items and the chances. If the size is 5, there are 5 items which can be generated
     */
    public int generateRandomNumber(int[][] percentChanceForEachItem) {
        Random random = new Random();
        int randomNumber = random.nextInt(100) + 1;
        int percentageBefore = 0;
        int areChancesValid = 0;

        for (int currentChance : percentChanceForEachItem[difficulty]) {
            areChancesValid += currentChance;
        }

        if (areChancesValid != 100) {
            System.err.println("The var areChancesValid is not 100 in generateRandomNumber GameSettings:" + Thread.currentThread()
                    .getStackTrace()[0].getLineNumber());
            System.exit(0);
        }
        for (int i = 0; i < percentChanceForEachItem[difficulty].length; i++) {
            if (randomNumber < percentageBefore + percentChanceForEachItem[difficulty][i]) {
                return i;
            } else {
                percentageBefore += percentChanceForEachItem[difficulty][i];
            }
        }

        System.err.println("The method generateRandomNumber is not working at GameSettings:" + Thread.currentThread().getStackTrace()[0]
                .getLineNumber());
        System.exit(0);
        return 404;
    }

    public void outputForBasicCommands(String command) {
        BasicCommands basicCommands = new BasicCommands();
        basicCommands.outputForBasicCommand(command);
    }

    public List<String> getCommandList() {
        BasicCommands basicCommands = new BasicCommands();
        return basicCommands.getCommandList();
    }

    public boolean isGameIsOver() {
        return gameIsOver;
    }

    public int getDifficulty() {
        return difficulty;
    }
}
