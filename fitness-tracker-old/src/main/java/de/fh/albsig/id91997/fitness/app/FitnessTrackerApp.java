package de.fh.albsig.id91997.fitness.app;

import de.fh.albsig.id91997.fitness.model.User;
import de.fh.albsig.id91997.fitness.model.Workout;
import de.fh.albsig.id91997.fitness.service.CaloriesApi;
import de.fh.albsig.id91997.fitness.service.TrackerService;
import java.util.List;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main application class for the Fitness Tracker.
 * Provides functionality for user login, workout tracking, and saving data.
 */
public class FitnessTrackerApp {
  private static final Logger LOGGER = LogManager.getLogger(FitnessTrackerApp.class);
  private TrackerService trackerService;
  private Scanner scanner;
  private CaloriesApi caloriesApi;

  /**
   * Constructor to initialize the FitnessTrackerApp.
   *
   * @param trackerService the service responsible for managing workouts and users
   * @param caloriesApi the API used to calculate calories burned
   * @param scanner the input scanner for user interactions
   */
  public FitnessTrackerApp(TrackerService trackerService, CaloriesApi caloriesApi,
      Scanner scanner) {
    this.trackerService = trackerService;
    this.scanner = scanner;
    this.caloriesApi = caloriesApi;
  }

  /**
   * Logs in a user by prompting for a username.
   * Loads the user's existing workouts from the file system.
   */
  public void login() {
    System.out.println("Enter username to log in:");
    String name = scanner.nextLine();
    User user = new User(name);
    trackerService.setUser(user);
    trackerService.loadWorkoutsFromFile();
    LOGGER.info("User {} logged in", name);
    System.out.println("Hello, " + name + "!");
  }

  /**
   * Retrieves and displays the workouts of the logged-in user.
   *
   * @return a concatenated string of all workouts or a message if no workouts exist
   */
  public String getWorkouts() {
    List<Workout> workouts = trackerService.getWorkouts();
    if (workouts.isEmpty()) {
      LOGGER.info("No workouts found for user {}", trackerService.getUser().getName());
      return "You have no workouts yet.";
    } else {
      String workoutConcat = "";
      System.out.println("\nYour Workouts:");
      for (int i = 0; i < workouts.size(); i++) {
        workoutConcat += (i + 1) + ". " + workouts.get(i) + "\n";
      }
      LOGGER.info("Displayed {} workouts for user {}", workouts.size(),
          trackerService.getUser().getName());
      return workoutConcat;
    }
  }

  /**
   * Prompts the user to enter details of a workout, calculates calories burned, and adds it to the
   * user's workout list.
   */
  public void addWorkout() {
    System.out.print("Enter the type of workout: ");
    String workoutType = scanner.nextLine();

    System.out.print("Enter duration (in minutes): ");
    int workoutDuration = scanner.nextInt();
    scanner.nextLine();

    LOGGER.info("Adding workout: Type={}, Duration={} mins", workoutType, workoutDuration);

    System.out.println("Calculating calories burned...");
    int caloriesBurned = caloriesApi.getCaloriesBurned(workoutType, workoutDuration);
    if (caloriesBurned > 0) {
      Workout workout = new Workout(workoutType, workoutDuration, caloriesBurned);
      trackerService.addWorkout(workout);
      LOGGER.info("Workout added successfully: {}", workout);
      System.out.println("Workout burned " + caloriesBurned + " kcal!");
      System.out.println("Workout added successfully!");
    } else {
      LOGGER.error("Failed to fetch calories burned for workout: Type={}, Duration={} mins",
          workoutType, workoutDuration);
      System.out.println("Failed to fetch calories burned. Please try again.");
    }
  }

  /**
   * Saves the current user's workout data to the file system.
   */
  public void saveData() {
    System.out.println("Saving user data...");
    trackerService.saveWorkouts();
    LOGGER.info("User data saved for {}", trackerService.getUser().getName());
  }
}
