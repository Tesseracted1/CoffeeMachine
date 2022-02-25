package machine;

import java.util.Scanner;

enum states{
    ACTION, PURCHASING, FILLING_WATER, FILLING_MILK, FILLING_BEANS, FILLING_CUPS, LEAVING;
}

public class CoffeeMachine {
    private static states currentState = states.ACTION;
    private static boolean _running = true;

    static int[] quantities = new int[3];
    static int cups, money;

    final static int[][] options = {{250, 0, 16, 4}, {350, 75, 20, 7}, {200, 100, 12, 6}};

    /*private static void displaySteps(){
        System.out.print("Starting to make a coffee\n" +
                "Grinding coffee beans\n" +
                "Boiling water\n" +
                "Mixing boiled water with crushed coffee beans\n" +
                "Pouring coffee into the cup\n" +
                "Pouring some milk into the cup\n" +
                "Coffee is ready!");
    }*/

    private static void displayIngredients(){
        System.out.printf("The coffee machine has:\n" +
                "%d ml of water\n" +
                "%d ml of milk\n" +
                "%d g of coffee beans\n" +
                "%d disposable cups\n" +
                "$%d of money\n\n",
                quantities[0], quantities[1], quantities[2], cups, money);
    }

    private static void processInput(String input){
        switch(currentState.name()){
            case "ACTION":
                switch(input){
                    case "buy":
                        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                        currentState = states.PURCHASING;
                        break;
                    case "fill":
                        System.out.println("Write how many ml of water you want to add:");
                        currentState = states.FILLING_WATER;

                        break;
                    case "take":
                        System.out.printf("I gave you %d\n", money);
                        money = 0;
                        break;
                    case "remaining":
                        displayIngredients();
                        break;
                    case "exit":
                        currentState = states.LEAVING;
                        break;
                }
                break;
            case "PURCHASING":
                if(input.equals("back")){
                    System.out.println();
                    break;
                }
                buy(Integer.parseInt(input));
                break;
            case "LEAVING":
                _running = false;
                break;
            default:
                fill(input);
        }
    }

    /*private static void loop(Scanner scanner){
        boolean running = true;
        while (running) {
            System.out.println("Write action (buy, fill, take, remaining, exit):");
            String choice = scanner.next();
            switch(choice){
                case "buy":
                    System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                    String drinkChoice = scanner.next();
                    if(drinkChoice.equals("back")){
                        System.out.println();
                        break;
                    }
                    buy(Integer.parseInt(drinkChoice));
                    break;
                case "fill":
                   // fill(scanner);
                    break;
                case "take":
                    System.out.printf("I gave you %d\n", money);
                    money = 0;
                    break;
                case "remaining":
                    displayIngredients();
                    break;
                case "exit":
                    running = false;
                    break;
            }
        }
    }*/

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);

        quantities = new int[]{400, 540, 120};
        cups = 9;
        money = 550;

        while(_running){
            String input = scanner.next();
            processInput(input);
        }
    }

    private static void fill(String input) {
        switch(currentState.name()){
            case "FILLING_WATER":
                quantities[0] += Integer.parseInt(input);
                System.out.println("Write how many ml of milk you want to add:");
                currentState = states.FILLING_MILK;
                break;
            case "FILLING_MILK":
                quantities[1] += Integer.parseInt(input);
                System.out.println("Write how many grams of coffee beans you want to add");
                currentState = states.FILLING_BEANS;
                break;
            case "FILLING_BEANS":
                quantities[2] += Integer.parseInt(input);
                System.out.println("Write how many disposable cups of coffee you want to add:");
                currentState = states.FILLING_CUPS;
                break;
            case "FILLING_CUPS":
                cups += Integer.parseInt(input);
                currentState = states.ACTION;
                break;
        }
    }

    private static void buy(int choice){
        int[] purchase = options[choice - 1];

        for(int i = 0; i < 3; i++){
            if(purchase[i] > quantities[i]){
                String lacking[] = {"water", "milk", "coffee beans"};
                System.out.printf("Sorry, not enough %s!\n", lacking[i]);
                return;
            }
        }

        System.out.println("I have enough resources, making you a coffee!\n");

        for(int i = 0; i < 3; i++){
            quantities[i] -= purchase[i];
        }
        cups--;
        money += purchase[3];
    }

    private static void check(int water, int waterPC, int milk, int milkPC, int beansNr, int beansPC) {
        int nrMinim = water;
        for(int[] ingredient : new int[][]{{water, waterPC}, {milk, milkPC}, {beansNr, beansPC}}){
            if(ingredient[0] / ingredient[1] == 0){
                return;
            }
        }

    }
}
