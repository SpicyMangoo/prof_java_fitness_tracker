package de.fh.albsig._91997.fitness.app;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import de.fh.albsig._91997.fitness.model.User;
import de.fh.albsig._91997.fitness.model.Workout;
import de.fh.albsig._91997.fitness.service.CaloriesAPI;
import de.fh.albsig._91997.fitness.service.TrackerService;
import de.fh.albsig._91997.fitness.util.FileManager;

public class FitnessTrackerApp {
	private static User currentUser;
	private static final TrackerService trackerService = new TrackerService();
	private static final Scanner scanner = new Scanner(System.in);
	private static final CaloriesAPI caloriesAPI = new CaloriesAPI();

	public static void main(String[] args) {
        System.out.println("Welcome to the Fitness Tracker!");
        login();
        
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
                    viewWorkouts();
                    break;
                case 2:
                    addWorkout();
                    break;
                case 3:
                	saveData();
                	break;
                case 4:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
                    break;
            }    
        }
	}
	
	private static void login() {
		System.out.println("Enter username to log in");
		String name = scanner.nextLine();
		currentUser = new User(name);
		trackerService.setUser(currentUser);
		trackerService.loadWorkoutsFromFile();
		System.out.println("Hello, " + name + "!");
	}

	private static void viewWorkouts() {
		List<Workout> workouts = trackerService.getWorkouts();
		if (workouts.isEmpty()) {
            System.out.println("You have no workouts yet.");
        } else {
            System.out.println("\nYour Workouts:");
            for (int i = 0; i < workouts.size(); i++) {
                System.out.println((i + 1) + ". " + workouts.get(i));
            }
        }
	}
	
	private static void addWorkout() {
		System.out.print("Enter the type of workout: ");
        String workoutType = scanner.nextLine();

        System.out.print("Enter duration (in minutes): ");
        int workoutDuration = scanner.nextInt();
        scanner.nextLine(); 

        System.out.println("Calculating calories burned... ");
        int caloriesBurned = caloriesAPI.getCaloriesBurned(workoutType, workoutDuration);
        if (caloriesBurned > 0) {
            System.out.println("Workout burned " + caloriesBurned + " kcal!");
            Workout workout = new Workout(workoutType, workoutDuration, caloriesBurned);
            trackerService.addWorkout(workout);
            System.out.println("Workout added successfully!");
        } else {
            System.out.println("Failed to fetch calories burned. Please try again.");
        }
	}
	
	private static void saveData() {
		System.out.println("Saving user data");
		trackerService.saveWorkouts();
	}
	
}
