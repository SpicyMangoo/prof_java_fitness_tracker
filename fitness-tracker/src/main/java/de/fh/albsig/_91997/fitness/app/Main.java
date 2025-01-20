package de.fh.albsig._91997.fitness.app;

import de.fh.albsig._91997.fitness.service.TrackerService;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.fh.albsig._91997.fitness.service.CaloriesAPI;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

	public static void main(String[] args) {
		TrackerService trackerservice = new TrackerService();
		CaloriesAPI caloriesAPI = new CaloriesAPI();
		Scanner scanner = new Scanner(System.in);
		
		FitnessTrackerApp app = new FitnessTrackerApp(trackerservice, caloriesAPI, scanner);
		
        LOGGER.info("Starting Fitness Tracker Application");
		System.out.println("Welcome to the Fitness Tracker!");
        app.login();

        boolean running = true;
        while (running) {
            System.out.println("\nMain Menu:");
            System.out.println("[1] View Workouts");
            System.out.println("[2] Add Workout");
            System.out.println("[3] Save user data");
            System.out.println("[4] Exit");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    String workouts = app.getWorkouts();
                    System.out.println(workouts);
                    break;
                case 2:
                	app.addWorkout();
                    break;
                case 3:
                	app.saveData();
                    break;
                case 4:
                	LOGGER.info("Exiting the application");
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                	LOGGER.warn("Invalid menu choice: {}", choice);
                    System.out.println("Invalid choice. Try again.");
                    break;
            }
        }
	}

}
