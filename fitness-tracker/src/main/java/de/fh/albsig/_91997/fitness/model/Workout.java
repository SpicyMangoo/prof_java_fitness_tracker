package de.fh.albsig._91997.fitness.model;

import java.time.LocalDate;

public class Workout {
    private String workoutType;  // running, cycling, etc.
    private double duration;     // in minutes
    private double caloriesBurned;
    
   // Empty constructor for Jackson filehandling use
    public Workout() {
    }

    public Workout(String workoutType, double duration, double caloriesBurned) {
        this.workoutType = workoutType;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
    }

    // Getters and setters
    public String getWorkoutType() { return workoutType; }
    public void setWorkoutType(String workoutType) { this.workoutType = workoutType; }

    public double getDuration() { return duration; }
    public void setDuration(double duration) { this.duration = duration; }

    public double getCaloriesBurned() { return caloriesBurned; }
    public void setCaloriesBurned(double caloriesBurned) { this.caloriesBurned = caloriesBurned; }

	
	@Override
	public String toString() {
        return String.format("%s: %.0f min, %.0f calories", workoutType, duration, caloriesBurned);
	}
}
