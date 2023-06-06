import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class OnlineExamSystem {
    private static String currentUser;
    private static int score;
    private static boolean isLoggedIn;
    private static Timer timer;

    public static void main(String[] args) {
        isLoggedIn = false;
        score = 0;
        currentUser = "";

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to the Online Exam System!");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    if (isLoggedIn) {
                        System.out.println("You are already logged in!");
                    } else {
                        login(scanner);
                    }
                    break;
                case 2:
                    if (isLoggedIn) {
                        System.out.println("You are already registered and logged in!");
                    } else {
                        register(scanner);
                    }
                    break;
                case 3:
                    System.out.println("Thank you for using the Online Exam System. Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static void login(Scanner scanner) {
        System.out.print("Enter your username: ");
        String username = scanner.next();
        System.out.print("Enter your password: ");
        String password = scanner.next();

        // Perform database validation for the username and password

        // Assuming validation is successful
        isLoggedIn = true;
        currentUser = username;

        System.out.println("Login successful. Welcome, " + currentUser + "!");
        showMainMenu(scanner);
    }

    private static void register(Scanner scanner) {
        System.out.print("Enter your desired username: ");
        String username = scanner.next();
        System.out.print("Enter your password: ");
        String password = scanner.next();

        // Perform database registration

        System.out.println("Registration successful. You can now log in.");
    }

    private static void showMainMenu(Scanner scanner) {
        while (isLoggedIn) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Update Profile");
            System.out.println("2. Start Exam");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    updateProfile(scanner);
                    break;
                case 2:
                    startExam(scanner);
                    break;
                case 3:
                    logout();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static void updateProfile(Scanner scanner) {
        System.out.println("Profile Update");
        // Code to update the user's profile in the database
        System.out.println("Profile updated successfully!");
    }

    private static void startExam(Scanner scanner) {
        score = 0;
        System.out.println("\nWelcome to the Exam!");

        // Sample MCQs
        String[] questions = {
                "1. What is the capital of France?",
                "2. Which planet is known as the Red Planet?",
                "3. Who painted the Mona Lisa?"
        };

        String[][] options = {
                {"A. London", "B. Paris", "C. Rome", "D. Madrid"},
                {"A. Venus", "B. Jupiter", "C. Mars", "D. Mercury"},
                {"A. Leonardo da Vinci", "B. Vincent van Gogh", "C. Pablo Picasso", "D. Michelangelo"}
        };

        int[] answers = {1, 3, 0};

        // Set the time limit for each question (in seconds)
        int timeLimitInSeconds = 10;

        // Display and process MCQs
        for (int i = 0; i < questions.length; i++) {
            displayQuestion(scanner, i, questions, options, answers, timeLimitInSeconds);
        }

        System.out.println("\nExam completed. Your score: " + score);
    }

    private static void displayQuestion(Scanner scanner, int questionIndex, String[] questions, String[][] options, int[] answers, int timeLimitInSeconds) {
        System.out.println("\n" + questions[questionIndex]);
        for (String option : options[questionIndex]) {
            System.out.println(option);
        }

        // Start the timer
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            int remainingTime = timeLimitInSeconds;

            @Override
            public void run() {
                if (remainingTime <= 0) {
                    System.out.println("\nTime's up! Automatically submitting your answer.");
                    submitAnswer(scanner, -1, answers[questionIndex]);
                    timer.cancel();
                } else {
                    System.out.println("\nTime remaining: " + remainingTime / 60 + " minutes, " + remainingTime % 60 + " seconds");
                    remainingTime--;
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, 1000, 1000);

        System.out.print("Enter your choice (A, B, C, D): ");
        String choice = scanner.next().toUpperCase();
        int selectedOption = choice.charAt(0) - 'A';

        // Cancel the timer as the user has submitted the answer within the time limit
        timer.cancel();

        submitAnswer(scanner, selectedOption, answers[questionIndex]);
    }

    private static void submitAnswer(Scanner scanner, int selectedOption, int correctAnswer) {
        if (selectedOption == -1) {
            System.out.println("No answer submitted. Skipping to the next question.");
        } else if (selectedOption == correctAnswer) {
            score++;
            System.out.println("Correct answer!");
        } else {
            System.out.println("Incorrect answer!");
        }
    }

    private static void logout() {
        isLoggedIn = false;
        currentUser = "";
        System.out.println("Logout successful.");
    }
}
