package model;

public class Process {
    //input
    private String PID;
    private int startTime;
    private int EndTime;
    private int BurstTime;
    private int ArrivalTime;
    private int priority;
    //calc
    private int remainingTime;
    private int responseTime;
    private int turnaroundTime;
    private int waitingTime;
    private boolean started;


    public Process(String PID, int arrivalTime, int burstTime, int priority, int remainingTime, boolean started) {
        this.PID = PID;
        ArrivalTime = arrivalTime;
        BurstTime = burstTime;
        this.priority = priority;
        this.remainingTime = burstTime;
        this.started = false;
    }

    public boolean isStarted() {
        return started;
    }
    public void setStarted(boolean started) {
        this.started = started;
    }
    public int getStartTime() {
        return startTime;
    }
    public void setStartTime(int startTime) {

        this.startTime = startTime;
    }
    public int getEndTime() {

        return EndTime;
    }
    public void setEndTime(int endTime) {

        EndTime = endTime;
    }
    public int getTurnaroundTime() {

        return turnaroundTime;
    }
    public void setTurnaroundTime(int turnaroundTime) {

        this.turnaroundTime = turnaroundTime;
    }
    public int getWaitingTime() {

        return waitingTime;
    }
    public void setWaitingTime(int waitingTime) {

        this.waitingTime = waitingTime;
    }
    public String getPID() {
        return PID;
    }
    public int getBurstTime() {
        return BurstTime;
    }
    public int getArrivalTime() {

        return ArrivalTime;
    }
    public int getRemainingTime() {

        return remainingTime;
    }
    public int getPriority() {

        return priority;
    }
    public int getResponseTime() {

        return responseTime;
    }
    public void setID(String ID) {

        this.PID = ID;
    }
    public void setBurstTime(int burstTime) {

        BurstTime = burstTime;
    }
    public void setArrivalTime(int arrivalTime) {
        ArrivalTime = arrivalTime;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }
    public void setResponseTime(int responseTime) {

        this.responseTime = responseTime;
    }
}
