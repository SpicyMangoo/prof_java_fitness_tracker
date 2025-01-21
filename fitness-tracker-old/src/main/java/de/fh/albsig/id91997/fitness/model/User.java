package de.fh.albsig.id91997.fitness.model;

/**
 * Represents a user of the Fitness Tracker application.
 * Each user has a name that uniquely identifies them within the application.
 */
public class User {
  private String name;

  /**
   * Constructs a new User with the specified name.
   *
   * @param name the name of the user
   */
  public User(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  void setName(String name) {
    this.name = name;
  }
}
