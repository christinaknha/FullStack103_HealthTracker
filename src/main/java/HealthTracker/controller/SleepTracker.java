package HealthTracker.controller;

import HealthTracker.models.Sleep;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SleepTracker {
    List<Sleep> sleepData = new ArrayList<>();
    public void logSleep(String userName){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the date you went to sleep in the format yyyy-mm-dd.");
        String sleepDateString = scanner.nextLine();

        String [] sleepDateData = sleepDateString.split("-");

        int sleepYear = Integer.parseInt(sleepDateData[0]);
        int sleepMonth = Integer.parseInt(sleepDateData[1]);
        int sleepDay = Integer.parseInt(sleepDateData[2]);

        System.out.println("Enter the time you went to sleep in military time format HH:mm.");
        String sleepTime = scanner.nextLine();

        String [] sleepTimeData = sleepTime.split(":");
        int sleepHour = Integer.parseInt(sleepTimeData[0]);
        int sleepMinute = Integer.parseInt(sleepTimeData[1]);

        LocalDateTime sleepInfo = LocalDateTime.of(sleepYear, sleepMonth, sleepDay,sleepHour, sleepMinute);
//        System.out.println(sleepInfo);

        System.out.println("Enter the date you woke up in the format yyyy-mm-dd.");
        String wakeDateString = scanner.nextLine();
        String [] wakeDateData = wakeDateString.split("-");

        int wakeYear = Integer.parseInt(wakeDateData[0]);
        int wakeMonth = Integer.parseInt(wakeDateData[1]);
        int wakeDay = Integer.parseInt(wakeDateData[2]);

        System.out.println("Enter the time you woke up in military time format HH:mm.");
        String wakeTime = scanner.nextLine();

        String [] wakeTimeData = wakeTime.split(":");
        int wakeHour = Integer.parseInt(wakeTimeData[0]);
        int wakeMinute = Integer.parseInt(wakeTimeData[1]);

        LocalDateTime wakeInfo = LocalDateTime.of(wakeYear, wakeMonth, wakeDay, wakeHour, wakeMinute);
//        System.out.println(wakeInfo);

        Sleep newSleepLog = new Sleep(sleepInfo, wakeInfo);

        sleepData.add(newSleepLog);

        writeSleepLog(userName, newSleepLog);

    }
    public void writeSleepLog(String userName, Sleep sleepLog){

        String userDataPath = "src/main/java/HealthTracker/utils/"+userName+"healthData.txt";

        try{
            File outputFile = new File(userDataPath);
            if(outputFile.createNewFile()){
                System.out.println("File created: " + outputFile.getName());
            } else {
                System.out.println("File already exists. File will be updated.");
            }

            BufferedWriter writeUserNames = new BufferedWriter(new FileWriter(userDataPath, true));

            writeUserNames.write("Sleep Log Data; User: " + userName + "; Went to Bed: " + sleepLog.getTimeToBed()
                    + "; Woke up: " + sleepLog.getTimeWakeUp());
            writeUserNames.newLine();
            writeUserNames.close();
            System.out.println("New sleep session has been logged.");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
