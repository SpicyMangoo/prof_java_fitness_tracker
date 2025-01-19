package de.fh.albsig._91997.fitness.model;

public class User {
    private String name;
    private int age;
    private double weight;  // in kg
    private double height;  // in cm

    public User(String name) {
        this.name = name;
        //this.age = age;
        //this.weight = weight;
        //this.height = height;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }
}
