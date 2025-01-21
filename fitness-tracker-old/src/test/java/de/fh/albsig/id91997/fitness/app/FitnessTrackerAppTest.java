package de.fh.albsig.id91997.fitness.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.fh.albsig.id91997.fitness.model.User;
import de.fh.albsig.id91997.fitness.model.Workout;
import de.fh.albsig.id91997.fitness.service.CaloriesApi;
import de.fh.albsig.id91997.fitness.service.TrackerService;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Unit tests for the FitnessTrackerApp class. This class tests the behavior of the
 * FitnessTrackerApp's methods to ensure the app functions as expected.
 */
public class FitnessTrackerAppTest {

  @Mock
  private TrackerService trackerService;

  @Mock
  private CaloriesApi caloriesApi;

  @Mock
  private Scanner scanner;

  @Mock
  private User user;

  private FitnessTrackerApp app;

  /**
   * Setup method to initialize mocks before each test.
   */
  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    app = new FitnessTrackerApp(trackerService, caloriesApi, scanner);
  }

  /**
   * Test for successful login. Verifies that the user is set and workouts are loaded after a
   * successful login.
   */
  @Test
  void testLoginShouldBeSuccessful() {
    when(scanner.nextLine()).thenReturn("TestUser");

    app.login();

    verify(trackerService).setUser(any(User.class));
    verify(trackerService).loadWorkoutsFromFile();
  }

  /**
   * Test for retrieving workouts when there are existing workouts. Verifies that the list of
   * workouts is displayed correctly.
   */
  @Test
  void testGetWorkoutsWithExistingWorkouts() {
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

  /**
   * Test for retrieving workouts when there are no workouts. Verifies that the "no workouts"
   * message is returned.
   */
  @Test
  void testGetWorkoutsWithoutExistingWorkouts() {
    // Prepare trackerService to return empty list
    when(trackerService.getUser()).thenReturn(user);
    when(user.getName()).thenReturn("TestUser");
    when(trackerService.getWorkouts()).thenReturn(Arrays.asList());

    String result = app.getWorkouts();

    // Verify the result (No workouts available)
    assertEquals("You have no workouts yet.", result);
    verify(trackerService).getWorkouts(); // Verify the interaction with the service
  }

  /**
   * Test for adding a workout when the calories burned are successfully calculated. Verifies that
   * the workout is added to the service when the calories are fetched successfully.
   */
  @Test
  void testAddWorkoutWithSuccessfulCaloriesCall() {
    // Prepare mock data
    when(scanner.nextLine()).thenReturn("Running"); // Mock workout type input
    when(scanner.nextInt()).thenReturn(30); // Mock workout duration input
    when(caloriesApi.getCaloriesBurned("Running", 30)).thenReturn(300); // Mock calories burned

    app.addWorkout();

    // Verify that the workout was added to the service
    verify(trackerService).addWorkout(any(Workout.class));
  }

  /**
   * Test for adding a workout when the calories burned cannot be fetched. Verifies that the workout
   * is not added if calories calculation fails.
   */
  @Test
  void testAddWorkoutWithFailedCaloriesCall() {
    // Prepare mock data
    when(scanner.nextLine()).thenReturn("Running");
    when(scanner.nextInt()).thenReturn(30);
    when(caloriesApi.getCaloriesBurned("Running", 30)).thenReturn(0);

    app.addWorkout();

    // Verify that the workout was not added because calories calculation failed
    verify(trackerService, never()).addWorkout(any(Workout.class));
  }
}
