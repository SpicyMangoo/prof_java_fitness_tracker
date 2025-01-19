package de.fh.albsig._91997.fitness.model;

import java.util.Map;

public class Meal {
    private String mealName;
    private Map<String, Double> nutritionalInfo;  // <typeOfNutrition>, <amount> --> <protein>, <30g>

    // Constructor
    public Meal(String mealName, Map<String, Double> nutritionalInfo) {
        this.mealName = mealName;
        this.nutritionalInfo = nutritionalInfo;
    }

    // Getters and Setters
    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public Map<String, Double> getNutritionalInfo() {
        return nutritionalInfo;
    }

    public void setNutritionalInfo(Map<String, Double> nutritionalInfo) {
        this.nutritionalInfo = nutritionalInfo;
    }
}
