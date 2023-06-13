package HealthTracker;

import HealthTracker.controller.UserLogin;
import HealthTracker.controller.*;


public class App {


    public static void main(String[] args){
        UserLogin loginUser = new UserLogin();
        CalorieTracker trackCalories = new CalorieTracker();
        ExerciseTracker trackExercise = new ExerciseTracker();
        SleepTracker trackSleep = new SleepTracker();
        HealthSummary report = new HealthSummary();


        loginUser.findUser(trackCalories, trackExercise, trackSleep, report);

    }
}
