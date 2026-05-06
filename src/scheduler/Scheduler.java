package scheduler;
import model.Process;
import java.util.*;
public abstract class Scheduler {
    protected double averageWaitingTime;
    protected double averageTurnaroundTime;
    protected double averageResponseTime;
    protected List<Process> p;
    public Scheduler(List<Process> p) {
        this.p = p;
    }
    public double getAverageWaitingTime() {
        return averageWaitingTime;
    }
    public double getAverageTurnaroundTime() {
        return averageTurnaroundTime;
    }
    public double getAverageResponseTime() {
        return averageResponseTime;
    }
    public void setAverageWaitingTime(double averageWaitingTime) {
        this.averageWaitingTime = averageWaitingTime;
    }
    public void setAverageTurnaroundTime(double averageTurnaroundTime) {
        this.averageTurnaroundTime = averageTurnaroundTime;
    }
    public void setAverageResponseTime(double averageResponseTime) {
        this.averageResponseTime = averageResponseTime;
    }
    public abstract void schedule();
    public abstract void calculateAverages() ;
    public abstract void makeChart() ;
}
