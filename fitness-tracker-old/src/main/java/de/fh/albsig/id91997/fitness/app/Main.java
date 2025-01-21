package de.fh.albsig.id91997.fitness.app;

import de.fh.albsig.id91997.fitness.service.CaloriesApi;
import de.fh.albsig.id91997.fitness.service.TrackerService;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The main entry point for the Fitness Tracker application. This class initializes the necessary
 * services and provides a menu-driven interface for the user.
 */
public class Main {
  private static final Logger LOGGER = LogManager.getLogger(Main.class);

  /**
   * The main method initializes the application and handles user interaction.
   *
   * @param args command-line arguments (not used in this application)
   */
  public static void main(String[] args) {
    TrackerService trackerservice = new TrackerService();
    CaloriesApi caloriesApi = new CaloriesApi();
    Scanner scanner = new Scanner(System.in);

    FitnessTrackerApp app = new FitnessTrackerApp(trackerservice, caloriesApi, scanner);

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
      String choice = scanner.nextLine();

      switch (choice) {
        case "1":
          String workouts = app.getWorkouts();
          System.out.println(workouts);
          break;
        case "2":
          app.addWorkout();
          break;
        case "3":
          app.saveData();
          break;
        case "4":
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
