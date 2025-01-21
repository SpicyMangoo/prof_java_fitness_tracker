package de.fh.albsig.id91997.fitness.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.fh.albsig.id91997.fitness.model.Workout;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility class for managing file operations related to workouts and meals.
 */
public class FileManager {

  private static final Logger logger = LogManager.getLogger(FileManager.class);

  private ObjectMapper objectMapper = new ObjectMapper();

  /**
   * Constructor to initialize the FileManager with an ObjectMapper.
   *
   * @param objectMapper The ObjectMapper to be used for JSON processing.
   */
  public FileManager(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  /**
   * Save a list of workouts to a user-specific file.
   *
   * @param username the username of the user
   * @param workouts the list of workouts to save
   */
  public void saveWorkoutsToFile(String username, List<Workout> workouts) {
    String fileName = username + "_workouts.json";
    try {
      objectMapper.writeValue(new File(fileName), workouts);
      logger.info("Workouts for user '{}' saved successfully to {}", username, fileName);
    } catch (IOException e) {
      logger.error("Error saving workouts for user '{}' to file: {}", username, fileName, e);
    }
  }

  /**
   * Load workouts from a user-specific file. If the file does not exist, it creates one.
   *
   * @param username the username of the user
   * @return a list of workouts, or an empty list if the file is missing or an error occurs
   */
  public List<Workout> loadWorkoutsFromFile(String username) {
    String fileName = username + "_workouts.json";
    File file = new File(fileName);

    // Create the file if it doesn't exist
    if (!file.exists()) {
      try {
        if (file.createNewFile()) {
          logger.info("Workout file for user '{}' created: {}", username, fileName);
        }
        // Write an empty array to the new file to initialize it
        objectMapper.writeValue(file, Collections.emptyList());
      } catch (IOException e) {
        logger.error("Error creating workout file for user '{}': {}", username, fileName, e);
        return Collections.emptyList();
      }
    }

    // Load the workouts from the file
    try {
      return objectMapper.readValue(file,
          objectMapper.getTypeFactory().constructCollectionType(List.class, Workout.class));
    } catch (IOException e) {
      logger.error("Error loading workouts for user '{}' from file: {}", username, fileName, e);
      return Collections.emptyList();
    }
  }
}
