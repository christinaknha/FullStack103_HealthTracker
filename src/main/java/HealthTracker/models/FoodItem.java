package HealthTracker.models;
import java.time.LocalDate;
public class FoodItem {
    String foodName;
    int caloriesInFood;
    LocalDate logDate;

    public FoodItem(String foodName, int caloriesInFood, LocalDate logDate) {
        this.foodName = foodName;
        this.caloriesInFood = caloriesInFood;
        this.logDate = logDate;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getCaloriesInFood() {
        return caloriesInFood;
    }

    public void setCaloriesInFood(int caloriesInFood) {
        this.caloriesInFood = caloriesInFood;
    }

    public LocalDate getLogDate() {
        return logDate;
    }

    public void setLogDate(int year, int month, int day) {
        this.logDate = LocalDate.of(year, month, day);
    }
}