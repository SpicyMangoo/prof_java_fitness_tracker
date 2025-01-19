package de.fh.albsig._91997.fitness.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.fh.albsig._91997.fitness.model.Meal;
import de.fh.albsig._91997.fitness.model.Workout;

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

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Save a list of workouts to a user-specific file.
     *
     * @param username the username of the user
     * @param workouts the list of workouts to save
     */
    public static void saveWorkoutsToFile(String username, List<Workout> workouts) {
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
    public static List<Workout> loadWorkoutsFromFile(String username) {
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

    /**
     * Save a list of meals to a user-specific file.
     *
     * @param username the username of the user
     * @param meals the list of meals to save
     */
    public static void saveMealsToFile(String username, List<Meal> meals) {
        String fileName = username + "_meals.json";
        try {
            objectMapper.writeValue(new File(fileName), meals);
            logger.info("Meals for user '{}' saved successfully to {}", username, fileName);
        } catch (IOException e) {
            logger.error("Error saving meals for user '{}' to file: {}", username, fileName, e);
        }
    }

    /**
     * Load meals from a user-specific file. If the file does not exist, it creates one.
     *
     * @param username the username of the user
     * @return a list of meals, or an empty list if the file is missing or an error occurs
     */
    public static List<Meal> loadMealsFromFile(String username) {
        String fileName = username + "_meals.json";
        File file = new File(fileName);

        // Create the file if it doesn't exist
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    logger.info("Meal file for user '{}' created: {}", username, fileName);
                }
                // Write an empty array to the new file to initialize it
                objectMapper.writeValue(file, Collections.emptyList());
            } catch (IOException e) {
                logger.error("Error creating meal file for user '{}': {}", username, fileName, e);
                return Collections.emptyList();
            }
        }

        // Load the meals from the file
        try {
            return objectMapper.readValue(file,
                objectMapper.getTypeFactory().constructCollectionType(List.class, Meal.class));
        } catch (IOException e) {
            logger.error("Error loading meals for user '{}' from file: {}", username, fileName, e);
            return Collections.emptyList();
        }
    }
}
