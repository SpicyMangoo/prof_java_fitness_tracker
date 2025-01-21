package de.fh.albsig.id91997.fitness.model;

/**
 * Represents a workout session with details such as type, duration, and calories burned.
 * This class is used to track fitness activities of users.
 */
public class Workout {
  private String workoutType; // running, cycling, etc.
  private int duration; // in minutes
  private int caloriesBurned;

  /**
   * Default constructor for deserialization or initialization purposes.
   * Required for Jackson file handling.
   */
  public Workout() {}

  /**
   * Constructs a new Workout with the specified type, duration, and calories burned.
   *
   * @param workoutType    the type of workout (e.g., running, cycling)
   * @param duration       the duration of the workout in minutes
   * @param caloriesBurned the number of calories burned during the workout
   */
  public Workout(String workoutType, int duration, int caloriesBurned) {
    this.workoutType = workoutType;
    this.duration = duration;
    this.caloriesBurned = caloriesBurned;
  }

  public String getWorkoutType() {
    return workoutType;
  }

  public void setWorkoutType(String workoutType) {
    this.workoutType = workoutType;
  }

  public double getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public double getCaloriesBurned() {
    return caloriesBurned;
  }

  public void setCaloriesBurned(int caloriesBurned) {
    this.caloriesBurned = caloriesBurned;
  }

  @Override
  public String toString() {
    return String.format("%s: %d min, %d calories", workoutType, duration, caloriesBurned);
  }
}
