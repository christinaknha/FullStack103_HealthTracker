package HealthTracker.controller;

import HealthTracker.models.FoodItem;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CalorieTracker {

    List<FoodItem> foodData = new ArrayList<>();
    public void logMeal(String userName){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the name of what you ate: ");
        String foodEaten = scanner.nextLine();

        System.out.println("Enter the caloric value of what was eaten: ");
        int foodCalorieValue = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter the date the date of consumption if the format yyyy-mm-dd.");
        String dateString = scanner.nextLine();
        String [] dateData = dateString.split("-");

        int year = Integer.parseInt(dateData[0]);
        int month = Integer.parseInt(dateData[1]);
        int day = Integer.parseInt(dateData[2]);

        LocalDate logDate = LocalDate.of(year, month, day);

        FoodItem newFoodItem = new FoodItem(foodEaten, foodCalorieValue, logDate);

        foodData.add(newFoodItem);

        writeFoodLog(userName, newFoodItem);


    }

    public void writeFoodLog(String userName, FoodItem foodItem){

        String userDataPath = "src/main/java/HealthTracker/utils/"+userName+"healthData.txt";

        try{
            File outputFile = new File(userDataPath);
            if(outputFile.createNewFile()){
                System.out.println("File created: " + outputFile.getName());
            } else {
                System.out.println("File already exists. File will be updated.");
            }

            BufferedWriter writeUserNames = new BufferedWriter(new FileWriter(userDataPath, true));

            writeUserNames.write("Food Log Data; User: " + userName + "; Date: " + foodItem.getLogDate()
                    + "; Food Name: " + foodItem.getFoodName() + "; Caloric Intake: " + foodItem.getCaloriesInFood());
            writeUserNames.newLine();
            writeUserNames.close();
            System.out.println("New food item has been logged.");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
