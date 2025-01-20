package de.fh.albsig._91997.fitness.app;

import de.fh.albsig._91997.fitness.model.User;
import de.fh.albsig._91997.fitness.model.Workout;
import de.fh.albsig._91997.fitness.service.CaloriesAPI;
import de.fh.albsig._91997.fitness.service.TrackerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FitnessTrackerAppTest {
	@Mock
	private TrackerService trackerService;
	@Mock
	private CaloriesAPI caloriesAPI;
	@Mock 
	private Scanner scanner;
	
	@Mock
	private User user;
	
	private FitnessTrackerApp app;
	 
	 @BeforeEach
	 void setup() {
		 MockitoAnnotations.openMocks(this);
		 app = new FitnessTrackerApp(trackerService, caloriesAPI, scanner);
	 }
	 
	 @Test
	 void testLogin_Successful() {
		 when(scanner.nextLine()).thenReturn("TestUser");
		 
		 app.login();
		 
		 verify(trackerService).setUser(any(User.class));
		 verify(trackerService).loadWorkoutsFromFile();
	 }
	 
	 @Test
	    void testGetWorkouts_WithWorkouts() {
	        // Prepare trackerService to return mock data	        
	        List<Workout> mockWorkouts = Arrays.asList(new Workout("Running", 30, 300));
	        when(trackerService.getWorkouts()).thenReturn(mockWorkouts);
	        when(trackerService.getUser()).thenReturn(user);
	        when(user.getName()).thenReturn("TestUser");

	        String result = app.getWorkouts();

	        // Verify the result
	        assertTrue(result.contains("Running"));
	        verify(trackerService).getWorkouts();  
	    }

	    @Test
	    void testGetWorkouts_NoWorkouts() {
	        // Prepare trackerService to return empty list
	        when(trackerService.getUser()).thenReturn(user);
	        when(user.getName()).thenReturn("TestUser");
	        when(trackerService.getWorkouts()).thenReturn(Arrays.asList());

	        // Call the method
	        String result = app.getWorkouts();

	        // Verify the result (No workouts available)
	        assertEquals("You have no workouts yet.", result);
	        verify(trackerService).getWorkouts();  // Verify the interaction with the service
	    }
	    
	    @Test
	    void testAddWorkout_SuccessfulCaloriesCall() {
	        // Prepare mock data
	        when(scanner.nextLine()).thenReturn("Running");  // Mock workout type input
	        when(scanner.nextInt()).thenReturn(30);  // Mock workout duration input
	        when(caloriesAPI.getCaloriesBurned("Running", 30)).thenReturn(300);  // Mock calories burned
	        
	        // Call the method
	        app.addWorkout();

	        // Verify that the workout was added to the service
	        verify(trackerService).addWorkout(any(Workout.class));  // Verify that addWorkout was called with a Workout object
	    }

	    @Test
	    void testAddWorkout_FailedCaloriesCall() {
	        // Prepare mock data
	        when(scanner.nextLine()).thenReturn("Running");
	        when(scanner.nextInt()).thenReturn(30);
	        when(caloriesAPI.getCaloriesBurned("Running", 30)).thenReturn(0);  // Simulate failure to fetch calories

	        // Call the method
	        app.addWorkout();

	        // Verify that the workout was not added because calories calculation failed
	        verify(trackerService, never()).addWorkout(any(Workout.class));  // Ensure addWorkout was not called
	    }
}

 