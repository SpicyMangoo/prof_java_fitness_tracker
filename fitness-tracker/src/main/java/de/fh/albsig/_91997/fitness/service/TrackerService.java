package de.fh.albsig._91997.fitness.service;

import java.util.ArrayList;
import java.util.List;

import de.fh.albsig._91997.fitness.model.User;
import de.fh.albsig._91997.fitness.model.Workout;
import de.fh.albsig._91997.fitness.util.FileManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TrackerService {
    private static final Logger LOGGER = LogManager.getLogger(TrackerService.class);

    private User user;
    private List<Workout> workouts;
    private FileManager fileManager;

    public TrackerService() {
        workouts = new ArrayList<>();
        LOGGER.info("TrackerService initialized");
        fileManager = new FileManager(new ObjectMapper());
    }

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

    public void setUser(User user) {
        this.user = user;
        LOGGER.info("Set current user: {}", user.getName());
    }

    public void loadWorkoutsFromFile() {
        this.workouts = fileManager.loadWorkoutsFromFile(this.user.getName());
        LOGGER.info("Loaded {} workouts from file for user {}", workouts.size(), user.getName());
    }

    public void saveWorkouts() {
    	fileManager.saveWorkoutsToFile(this.user.getName(), workouts);
        LOGGER.info("Workouts saved to file for user {}", user.getName());
    }
}
