import java.util.Random;
import java.util.Scanner;

public class GuessTheNumber {
    public static void main(String[] args) {
        int minRange = 1;
        int maxRange = 100;
        int maxAttempts = 5;
        int score = 0;
        int round = 1;

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("Welcome to Guess the Number!");

        while (true) {
            System.out.println("\nRound " + round + ":");
            int targetNumber = random.nextInt(maxRange - minRange + 1) + minRange;
            int attempts = 0;

            while (attempts < maxAttempts) {
                System.out.print("Enter your guess (between " + minRange + " and " + maxRange + "): ");
                int guess = scanner.nextInt();
                attempts++;

                if (guess == targetNumber) {
                    System.out.println("Congratulations! You guessed the number.");
                    score += (maxAttempts - attempts + 1) * 10;
                    break;
                } else if (guess < targetNumber) {
                    System.out.println("Too low. Try again.");
                } else {
                    System.out.println("Too high. Try again.");
                }
            }

            if (attempts == maxAttempts) {
                System.out.println("Oops! You reached the maximum number of attempts.");
                System.out.println("The number was: " + targetNumber);
            }

            System.out.println("Round " + round + " ended.");
            System.out.println("Your score: " + score);
            round++;

            System.out.print("Do you want to play again? (yes/no): ");
            String playAgain = scanner.next();
            if (!playAgain.equalsIgnoreCase("yes")) {
                System.out.println("Thanks for playing! Final score: " + score);
                break;
            }
        }

        scanner.close();
    }
}
