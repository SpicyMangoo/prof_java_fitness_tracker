package de.fh.albsig.id91997.fitness.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.fh.albsig.id91997.fitness.model.User;
import de.fh.albsig.id91997.fitness.model.Workout;
import de.fh.albsig.id91997.fitness.util.FileManager;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Service class responsible for managing workouts and users.
 * It handles workout operations such as adding, loading, saving, and retrieving workouts.
 */
public class TrackerService {

  private static final Logger LOGGER = LogManager.getLogger(TrackerService.class);

  private User user;
  private List<Workout> workouts;
  private FileManager fileManager;

  /**
   * Initializes a new TrackerService with an empty workout list and a FileManager.
   */
  public TrackerService() {
    workouts = new ArrayList<>();
    LOGGER.info("TrackerService initialized");
    fileManager = new FileManager(new ObjectMapper());
  }

  /**
   * Adds a new workout to the list of workouts.
   *
   * @param workout the workout to be added
   */
  public void addWorkout(Workout workout) {
    this.workouts.add(workout);
    LOGGER.info("Added workout for user {}: {}", user.getName(), workout);
  }

  public List<Workout> getWorkouts() {
    return this.workouts;
  }

  public User getUser() {
    return this.user;
  }

  /**
   * Sets the current user for the tracker.
   *
   * @param user the user to be set
   */
  public void setUser(User user) {
    this.user = user;
    LOGGER.info("Set current user to: {}", user.getName());
  }

  /**
   * Loads the user's workouts from a file.
   * The file is determined by the user's name.
   */
  public void loadWorkoutsFromFile() {
    this.workouts = fileManager.loadWorkoutsFromFile(this.user.getName());
    LOGGER.info("Loaded {} workouts from file for user {}", workouts.size(), user.getName());
  }

  /**
   * Saves the user's workouts to a file.
   * The file is determined by the user's name.
   */
  public void saveWorkouts() {
    fileManager.saveWorkoutsToFile(this.user.getName(), workouts);
    LOGGER.info("Workouts saved to file for user {}", user.getName());
  }
}
