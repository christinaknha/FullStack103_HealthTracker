package HealthTracker.controller;

import HealthTracker.models.User;

import java.io.*;
import java.util.Scanner;

public class UserLogin {

    String currentUser;

    public void findUser(CalorieTracker trackCalories, ExerciseTracker trackExercise, SleepTracker trackSleep, HealthSummary report){

        Scanner scanner = new Scanner(System.in);
        boolean accessingUserLogin = true;

        while(accessingUserLogin){
            System.out.println("Enter 1 if you are an existing user.");
            System.out.println("Enter 2 if you are a new user.");
            System.out.println("Enter 0 to close the application.");

            int optionSelected = Integer.parseInt(scanner.nextLine());

            switch (optionSelected) {
                case 1:

                    // ASK USER TO LOGIN
                    validateUsername(trackCalories, trackExercise, trackSleep, report);
                    break;

                case 2:

//                    // ASK USER FOR UNIQUE USERNAME
                    createNewUser();
                    break;

                default:
                    System.out.println("Closing the application. Goodbye.");
                    accessingUserLogin = false;
                    break;
            }

        }

    }

    public void validateUsername(CalorieTracker trackCalories, ExerciseTracker trackExercise, SleepTracker trackSleep, HealthSummary report){

        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter username: ");
        String enteredUserName = scanner.nextLine();

        String userNameFilePath = "src/main/java/HealthTracker/utils/userNames.txt";

//      READS USERNAMES FROM FILE
        try (BufferedReader reader = new BufferedReader(new FileReader(userNameFilePath))){
            String line;
            boolean foundUser = false;

            while((line = reader.readLine()) != null){

                if (line.equals(enteredUserName)){
                    foundUser = true;
                    currentUser = enteredUserName;
                    userOptions(trackCalories, trackExercise, trackSleep,report);
                    break;
                }
            }

            if(!foundUser){
                System.out.println("Username not found. Please try again.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

//  OPTIONS FOR EXISTING USERS
    public void userOptions(CalorieTracker trackCalories, ExerciseTracker trackExercise, SleepTracker trackSleep, HealthSummary report){

        Scanner scanner = new Scanner(System.in);
        boolean userAction = true;

        while(userAction){
            System.out.println("Welcome! What would you like to do today?");
            System.out.println("Enter 1 to log a meal.");
            System.out.println("Enter 2 to log exercise.");
            System.out.println("Enter 3 to log sleep.");
            System.out.println("Enter 4 to get your health summary.");
            System.out.println("Enter 0 to log out.");

            int optionSelected = Integer.parseInt(scanner.nextLine());

            switch (optionSelected) {
                case 1:

                    // LOG A MEAL
                    trackCalories.logMeal(currentUser);
                    break;

                case 2:

//                    // LOG EXERCISE
                    trackExercise.logExercise(currentUser);
                    break;

                case 3:

                    // LOG SLEEP
                    trackSleep.logSleep(currentUser);
                    break;

                case 4:

                    // GET HELP SUMMARY
                    report.generateSummary(currentUser);
                    break;

                default:
                    System.out.println("Logging off. Goodbye.");
                    userAction = false;
                    break;
            }

        }
    }

//  CREATE NEW USER

    public void createNewUser(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter a unique username: ");
        String newUserUserName = scanner.nextLine();

        String userNameFilePath = "src/main/java/HealthTracker/utils/userNames.txt";

//      READS USERNAMES FROM FILE
        try (BufferedReader reader = new BufferedReader(new FileReader(userNameFilePath))){
            String line;
            boolean foundUser = false;

            while((line = reader.readLine()) != null){
                if (line.equals(newUserUserName)){
                    foundUser = true;
                    System.out.println("Username already exists. Please try again.");
                    break;
                }
            }

            if(!foundUser){
                User newUser = new User(newUserUserName);
                storeUserNames(newUser);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

//  STORES USERNAMES INTO TXT FILE
    public void storeUserNames(User user){
        String userNameFilePath = "src/main/java/HealthTracker/utils/userNames.txt";

        try{
            File outputFile = new File(userNameFilePath);
            if(outputFile.createNewFile()){
                System.out.println("File created: " + outputFile.getName());
            } else {
                System.out.println("File already exists. File will be updated.");
            }

            BufferedWriter writeUserNames = new BufferedWriter(new FileWriter(userNameFilePath, true));

            writeUserNames.write(user.getUsername());
            writeUserNames.newLine();
            writeUserNames.close();
            System.out.println("New user has been added.");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
