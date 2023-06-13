package HealthTracker.controller;
import HealthTracker.models.Workout;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ExerciseTracker {

    List<Workout> exerciseData = new ArrayList<>();
    public void logExercise(String userName) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the type of workout you did: ");
        System.out.println("Press 1 for Cardio.");
        System.out.println("Press 2 for Weight Lifting.");
        System.out.println("Press 3 for Swimming.");
        System.out.println("Press 4 for Pilates.");
        System.out.println("Press 5 for Yoga.");

        int optionSelected = Integer.parseInt(scanner.nextLine());
        String workoutType = null;

        switch (optionSelected) {
            case 1:

                workoutType = "CARDIO";
                break;

            case 2:
                workoutType = "WEIGHTLIFTING";

                break;

            case 3:
                workoutType = "SWIMMING";
                break;

            case 4:
                workoutType = "PILATES";
                break;

            case 5:
                workoutType = "YOGA";
                break;
        }

        System.out.println("Enter workout time in minutes: ");
        int exerciseTime = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter calories burned during workout: ");
        int caloriesBurned = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter the date of the exercise session in the format yyyy-mm-dd.");
        String dateString = scanner.nextLine();
        String [] dateData = dateString.split("-");

        int year = Integer.parseInt(dateData[0]);
        int month = Integer.parseInt(dateData[1]);
        int day = Integer.parseInt(dateData[2]);

        LocalDate logDate = LocalDate.of(year, month, day);

        Workout newWorkout = new Workout(workoutType, exerciseTime, caloriesBurned, logDate);

        exerciseData.add(newWorkout);

        writeExerciseLog(userName, newWorkout);
    }

    public void writeExerciseLog(String userName, Workout workout){

        String userDataPath = "src/main/java/HealthTracker/utils/"+userName+"healthData.txt";

        try{
            File outputFile = new File(userDataPath);
            if(outputFile.createNewFile()){
                System.out.println("File created: " + outputFile.getName());
            } else {
                System.out.println("File already exists. File will be updated.");
            }

            BufferedWriter writeUserNames = new BufferedWriter(new FileWriter(userDataPath, true));

            writeUserNames.write("Exercise Log Data; User: " + userName + "; Date: " + workout.getExerciseDate()
                    + "; Exercise Type: " + workout.getWorkOutType() + "; Exercise Time: " + workout.getWorkOutTime()
                    + "; Calories Burned: " + workout.getCaloriesBurned());
            writeUserNames.newLine();
            writeUserNames.close();
            System.out.println("New exercise session has been logged.");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
