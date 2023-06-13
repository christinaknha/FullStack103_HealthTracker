package HealthTracker.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HealthSummary {
    int calorieIntake;
    int caloriesBurned;
    int totalTimeSlept;
    String hoursSlept;

    public HealthSummary() {
        this.calorieIntake = 0;
        this.caloriesBurned = 0;
        this.totalTimeSlept = 0;
        this.hoursSlept = "0";
    }

    public void generateSummary(String username) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which type of health summary would you like to see?");

        boolean generateSummary = true;

        while (generateSummary) {
            System.out.println("Enter 1 to generate daily caloric intake.");
            System.out.println("Enter 2 to generate sleep analysis.");
            System.out.println("Enter 3 to generate an exercise log.");
            System.out.println("Enter 4 to generate a health summary log for the week.");
            System.out.println("Enter 0 to exit.");

            int optionSelected = Integer.parseInt(scanner.nextLine());

            switch (optionSelected) {
                case 1:
//                    generates daily caloric intake
                    dailyCaloricData(username);
                    break;
                case 2:
//                    generates sleep analysis
                    weeklySleepAnalysis(username);
                    break;
                case 3:
//                    generates exercise summary
                    weeklyExerciseLog(username);
                    break;
                case 4:
//                    generates total health summary
                    totalHealthSummary(username);
                    break;

                default:
                    System.out.println("Leaving health summary generator. Goodbye.");
                    generateSummary = false;
                    break;


            }
        }
    }


    public void dailyCaloricData(String userName) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the date (format yyyy-mm-dd) you would like to generate a summary for.");
        String date = scanner.nextLine();

        String userDataPath = "src/main/java/HealthTracker/utils/" + userName + "healthData.txt";
        List<String> foodEaten = new ArrayList<>();
        List<String> exercisesDone = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(userDataPath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(";");
                if (userData[0].equals("Food Log Data") && userData[2].split(": ")[1].equals(date)) {
                    String foodName = userData[3].split(": ")[1];
                    foodEaten.add(foodName);
                    int foodCalories = Integer.parseInt(userData[4].split(": ")[1]);
                    calorieIntake += foodCalories;

                } else if (userData[0].equals("Exercise Log Data") && userData[2].split(": ")[1].equals(date)) {
                    String exerciseType = userData[3].split(": ")[1];
                    exercisesDone.add(exerciseType);
                    int exerciseCalories = Integer.parseInt(userData[5].split(": ")[1]);
                    caloriesBurned += exerciseCalories;

                } else if (userData[0].equals("Sleep Log Data") && userData[2].split(": ")[1].split("T")[0].equals(date)) {
//
                    LocalDateTime bedDateTimeData = LocalDateTime.parse(userData[2].split(": ")[1]);
                    LocalDateTime wakeDateTimeData = LocalDateTime.parse(userData[3].split(": ")[1]);

                    Duration duration = Duration.between(wakeDateTimeData, bedDateTimeData);
                    hoursSlept = duration.toString();
                    System.out.println("Time Slept: " + hoursSlept.split("-")[1] + hoursSlept.split("-")[2]);

                }
            }
            System.out.println("On " + date + ":\n"
                    + "You ate " + foodEaten + ". \n"
                    + "This totaled to " + calorieIntake + " calories. \n"
                    + "You did " + exercisesDone + ", which burned " + caloriesBurned + " calories. \n"
                    + "Your net calorie intake total: " + (calorieIntake - caloriesBurned) + " calories.");


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void weeklySleepAnalysis(String userName) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the start date (format yyyy-mm-dd) for the week long sleep summary.");
        String startDate = scanner.nextLine();

        LocalDate start = LocalDate.parse(startDate);

        System.out.println("Enter the end date (format yyyy-mm-dd) for the sleep summary.");
        String endDate = scanner.nextLine();
        LocalDate end = LocalDate.parse(endDate);

//        List<String> sleepHoursAndMinutes = new ArrayList<>();
//        int weeklySleepTime = 0;
//        int avgMinutesSlept = 0;

        String userDataPath = "src/main/java/HealthTracker/utils/" + userName + "healthData.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(userDataPath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(";");
                for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {
                    if (userData[0].equals("Sleep Log Data") && userData[2].split(": ")[1].split("T")[0].equals(date.toString())) {
//
                        LocalDateTime bedDateTimeData = LocalDateTime.parse(userData[2].split(": ")[1]);
                        System.out.println("Date and time slept: " + bedDateTimeData);
                        LocalDateTime wakeDateTimeData = LocalDateTime.parse(userData[3].split(": ")[1]);
                        System.out.println("Date and time wake: " + wakeDateTimeData);

                        Duration duration;

                        duration = Duration.between(wakeDateTimeData, bedDateTimeData);
                        hoursSlept = duration.toString();


                        System.out.println("Duration slept: " + hoursSlept);
                        if (hoursSlept.split("-").length == 2) {
                            totalTimeSlept=0;
                            String totalSleep = hoursSlept.split("-")[1];
                            String[] separateTimeData = totalSleep.split("H");
                            int hoursToMinutesSlept = Integer.parseInt(separateTimeData[0]) * 60;
                            totalTimeSlept += hoursToMinutesSlept;
//                                sleepHoursAndMinutes.add(totalSleep);
                        } else if (hoursSlept.split("-").length == 3) {
                            String totalSleep = hoursSlept.split("-")[1] + hoursSlept.split("-")[2];
                            String[] separateTimeData = totalSleep.split("H");
                            int hoursToMinutesSlept = Integer.parseInt(separateTimeData[0]) * 60;
                            int minutesSlept = Integer.parseInt(separateTimeData[1].split("M")[0]);
                            totalTimeSlept += (hoursToMinutesSlept + minutesSlept);
//                                sleepHoursAndMinutes.add(totalSleep);

                        }

                    }

                }

            }
            System.out.println("Sleep in one week: " + totalTimeSlept + " minutes.");
            int averageMinutesPerDay = totalTimeSlept/7;
            int totalHoursSlept = averageMinutesPerDay/60;
//            System.out.println(totalHoursSlept);
            int remainderMinutesSlept = averageMinutesPerDay%60;
//            System.out.println(remainderMinutesSlept);

            System.out.println("On average, this is " + totalHoursSlept + "hours and " + remainderMinutesSlept + " minutes a day.");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void weeklyExerciseLog(String userName) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the start date (format yyyy-mm-dd) for the week long exercise summary.");
        String startDate = scanner.nextLine();

        LocalDate start = LocalDate.parse(startDate);
//        System.out.println(start);

        System.out.println("Enter the end date (format yyyy-mm-dd) for the exercise summary.");
        String endDate = scanner.nextLine();

        LocalDate end = LocalDate.parse(endDate);
//        System.out.println(end);

        String userDataPath = "src/main/java/HealthTracker/utils/" + userName + "healthData.txt";

        List<String> exercisesDone = new ArrayList<>();


        try (BufferedReader reader = new BufferedReader(new FileReader(userDataPath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(";");
                for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {
                    if (userData[0].equals("Exercise Log Data") && userData[2].split(": ")[1].equals(date.toString())) {
                        String exerciseType = userData[3].split(": ")[1];
                        exercisesDone.add(exerciseType);
                        int exerciseCalories = Integer.parseInt(userData[5].split(": ")[1]);
                        caloriesBurned += exerciseCalories;

                        System.out.println("Exercise Log: \n");
                        System.out.println("Date: " + date.toString());
                        System.out.println("Exercise done: " + exerciseType);
                        System.out.println("Exercise time (minutes): " + userData[4].split(": ")[1]);
                        System.out.println("Calories burned: " + userData[5].split(": ")[1]);

                    }
                }
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void totalHealthSummary(String userName) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the start date (format yyyy-mm-dd) for the week long exercise summary.");
        String startDate = scanner.nextLine();

        LocalDate start = LocalDate.parse(startDate);
//        System.out.println(start);

        System.out.println("Enter the end date (format yyyy-mm-dd) for the exercise summary.");
        String endDate = scanner.nextLine();
        LocalDate end = LocalDate.parse(endDate);
//        System.out.println(end);

        String userDataPath = "src/main/java/HealthTracker/utils/" + userName + "healthData.txt";

        List<String> foodEaten = new ArrayList<>();
        List<String> exercisesDone = new ArrayList<>();
//        List<String> sleepHoursAndMinutes = new ArrayList<>();
//        int weeklySleepTime = 0;
//        int avgMinutesSlept = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(userDataPath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(";");
                for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {

                    if (userData[0].equals("Food Log Data") && userData[2].split(": ")[1].equals(date.toString())) {
                        String foodName = userData[3].split(": ")[1];
                        foodEaten.add(foodName);
                        int foodCalories = Integer.parseInt(userData[4].split(": ")[1]);
                        calorieIntake += foodCalories;

                    } else if (userData[0].equals("Exercise Log Data") && userData[2].split(": ")[1].equals(date.toString())) {
                        String exerciseType = userData[3].split(": ")[1];
                        exercisesDone.add(exerciseType);
                        int exerciseCalories = Integer.parseInt(userData[5].split(": ")[1]);
                        caloriesBurned += exerciseCalories;

                    } else if (userData[0].equals("Sleep Log Data") && userData[2].split(": ")[1].split("T")[0].equals(date.toString())) {

                        LocalDateTime bedDateTimeData = LocalDateTime.parse(userData[2].split(": ")[1]);
//                        System.out.println("Date and time slept: " + bedDateTimeData);
                        LocalDateTime wakeDateTimeData = LocalDateTime.parse(userData[3].split(": ")[1]);
//                        System.out.println("Date and time wake: " + wakeDateTimeData);

                        Duration duration = Duration.between(wakeDateTimeData, bedDateTimeData);
                        hoursSlept = duration.toString();

//                        System.out.println("Duration slept: " + hoursSlept);
                        if (hoursSlept.split("-").length == 2) {
                            totalTimeSlept = 0;
                            String totalSleep = hoursSlept.split("-")[1];
                            String[] separateTimeData = totalSleep.split("H");
                            int hoursToMinutesSlept = Integer.parseInt(separateTimeData[0]) * 60;
                            totalTimeSlept += hoursToMinutesSlept;
//                                sleepHoursAndMinutes.add(totalSleep);
                        } else if (hoursSlept.split("-").length == 3) {
                            String totalSleep = hoursSlept.split("-")[1] + hoursSlept.split("-")[2];
                            String[] separateTimeData = totalSleep.split("H");
                            int hoursToMinutesSlept = Integer.parseInt(separateTimeData[0]) * 60;
                            int minutesSlept = Integer.parseInt(separateTimeData[1].split("M")[0]);
                            totalTimeSlept += (hoursToMinutesSlept + minutesSlept);
//                                sleepHoursAndMinutes.add(totalSleep);

                        }

                    }
                }
            }
            System.out.println("On the week of " + startDate + " through " + endDate + ":\n");
            System.out.println("Total Calories consumed: " + calorieIntake);
            System.out.println("Total Calories burned: " + caloriesBurned);
            System.out.println("Sleep in one week: " + totalTimeSlept + " minutes.");
            int averageMinutesPerDay = totalTimeSlept/7;
            int totalHoursSlept = averageMinutesPerDay/60;
//            System.out.println(totalHoursSlept);
            int remainderMinutesSlept = averageMinutesPerDay%60;
//            System.out.println(remainderMinutesSlept);

            System.out.println("On average, this is " + totalHoursSlept + "hours and " + remainderMinutesSlept + " minutes a day.");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}