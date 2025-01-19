package de.fh.albsig._91997.fitness.service;

import java.util.ArrayList;
import java.util.List;

import de.fh.albsig._91997.fitness.model.User;
import de.fh.albsig._91997.fitness.model.Workout;
import de.fh.albsig._91997.fitness.util.FileManager;

public class TrackerService {
	private User user;
	private List<Workout> workouts;
	
	public TrackerService() {
	}
	
	public void addWorkout(Workout workout) {
		
		this.workouts.add(workout);
		//LOGGING
	}
	
	public List<Workout> getWorkouts(){
		return this.workouts;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public void loadWorkoutsFromFile() {
		this.workouts = FileManager.loadWorkoutsFromFile(this.user.getName());
	}
	
	public void saveWorkouts() {
		FileManager.saveWorkoutsToFile(this.user.getName(), workouts);
	}
}
