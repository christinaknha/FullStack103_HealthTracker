package HealthTracker.models;
import java.time.LocalDateTime;
public class Sleep {
    LocalDateTime timeToBed;
    LocalDateTime timeWakeUp;

    public Sleep(LocalDateTime timeToBed, LocalDateTime timeWakeUp){
        this.timeToBed = timeToBed;
        this.timeWakeUp = timeWakeUp;
    }

    public LocalDateTime getTimeToBed() {
        return timeToBed;
    }

    public void setTimeToBed(int year, int month, int day, int hour, int minute) {
        this.timeToBed = LocalDateTime.of(year, month, day, hour, minute);
    }

    public LocalDateTime getTimeWakeUp() {
        return timeWakeUp;
    }

    public void setTimeWakeUp(int year, int month, int day, int hour, int minute) {
        this.timeWakeUp = LocalDateTime.of(year, month, day, hour, minute);
    }


}
