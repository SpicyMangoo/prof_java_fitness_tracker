package de.fh.albsig._91997.fitness.app;

import java.util.List;
import java.util.Scanner;

import de.fh.albsig._91997.fitness.model.User;
import de.fh.albsig._91997.fitness.model.Workout;
import de.fh.albsig._91997.fitness.service.CaloriesAPI;
import de.fh.albsig._91997.fitness.service.TrackerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FitnessTrackerApp {
    private static final Logger LOGGER = LogManager.getLogger(FitnessTrackerApp.class);

    private final TrackerService trackerService;
    private final Scanner scanner;
    private final CaloriesAPI caloriesAPI;
    
    public FitnessTrackerApp(TrackerService trackerService, CaloriesAPI caloriesAPI, Scanner scanner) {
    	this.trackerService = trackerService;
    	this.scanner = scanner;
    	this.caloriesAPI = caloriesAPI;
    }

    public void login() {
        System.out.println("Enter username to log in:");
        String name = scanner.nextLine();
        User user = new User(name);
        trackerService.setUser(user);
        trackerService.loadWorkoutsFromFile();
        LOGGER.info("User {} logged in", name);
        System.out.println("Hello, " + name + "!");
    }

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
            LOGGER.info("Displayed {} workouts for user {}", workouts.size(), trackerService.getUser().getName());
            return workoutConcat;
        }
    }

    public void addWorkout() {
        System.out.print("Enter the type of workout: ");
        String workoutType = scanner.nextLine();

        System.out.print("Enter duration (in minutes): ");
        int workoutDuration = scanner.nextInt();
        scanner.nextLine();

        LOGGER.info("Adding workout: Type={}, Duration={} mins", workoutType, workoutDuration);

        System.out.println("Calculating calories burned...");
        int caloriesBurned = caloriesAPI.getCaloriesBurned(workoutType, workoutDuration);
        if (caloriesBurned > 0) {
            Workout workout = new Workout(workoutType, workoutDuration, caloriesBurned);
            trackerService.addWorkout(workout);
            LOGGER.info("Workout added successfully: {}", workout);
            System.out.println("Workout burned " + caloriesBurned + " kcal!");
            System.out.println("Workout added successfully!");
        } else {
            LOGGER.error("Failed to fetch calories burned for workout: Type={}, Duration={} mins", workoutType, workoutDuration);
            System.out.println("Failed to fetch calories burned. Please try again.");
        }
    }

    public void saveData() {
        System.out.println("Saving user data...");
        trackerService.saveWorkouts();
        LOGGER.info("User data saved for {}", trackerService.getUser().getName());
    }
}
