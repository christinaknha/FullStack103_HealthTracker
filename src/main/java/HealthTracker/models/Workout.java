package HealthTracker.models;

import java.time.LocalDate;

public class Workout {
    String workOutType;
    int workOutTime;
    int caloriesBurned;
    LocalDate exerciseDate;


    public Workout(String workOutType, int exerciseTime, int caloriesBurned, LocalDate exerciseDate){
        this.workOutType = workOutType;
        this.workOutTime = exerciseTime;
        this.caloriesBurned = caloriesBurned;
        this.exerciseDate=exerciseDate;
    }
    public String getWorkOutType() {
        return workOutType;
    }

    public void setWorkOutType(String workOutType) {
        this.workOutType = workOutType;
    }

    public int getWorkOutTime() {
        return workOutTime;
    }

    public void setWorkOutTime(int workOutTime) {
        this.workOutTime = workOutTime;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(int caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public LocalDate getExerciseDate() {
        return exerciseDate;
    }

    public void setExerciseDate(int year, int month, int day) {
        this.exerciseDate = LocalDate.of(year, month, day);
    }
}


