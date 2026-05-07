package scheduler;
import model.Process;
import java.util.*;
public class Sjf extends Scheduler {
    List <Process> p1 ;
    private List <String> ganttProcess = new ArrayList<>();
    private List <Integer> gantTime = new ArrayList<>() ;

    public Sjf(List<Process> processes) {
        super(processes);
        this.p1 = new ArrayList<>(processes);
    }

    @Override
    public void schedule() {
        // Implement SJF scheduling algorithm here
        int currentTime = 0;
        int completed = 0 ;
        int n = p1.size();
        String lastProcess = "";
        while (completed < n){
            Process best = null;
            double bestScore = Double.MAX_VALUE;
            //choose the process
            for (Process pr : p1) {
                if (pr.getArrivalTime() <= currentTime && pr.getRemainingTime() > 0){
                    int actualTime = pr.getArrivalTime() - (pr.getBurstTime() - pr.getRemainingTime());
                    double effective = pr.getRemainingTime() - (actualTime * 0.5);
                    if (effective < bestScore){
                        bestScore = effective;
                        best = pr;
                    } else if (effective == bestScore) {
                        if (pr.getArrivalTime() < best.getArrivalTime()){
                            best = pr;
                        }
                    }
                }
            }
            if (best == null){
                currentTime++;
                continue;
            }
            //make gant
            if (!best.getPID().equals(lastProcess)){
                ganttProcess.add(best.getPID());
                gantTime.add(currentTime);
                lastProcess = best.getPID();
            }
            //process will start
            if (!best.isStarted()){
                best.setStartTime(currentTime);
                best.setResponseTime(currentTime - best.getArrivalTime());
                best.setStarted(true);
            }
            best.setRemainingTime(best.getRemainingTime() - 1);
            currentTime++;

            if (best.getRemainingTime() == 0){
                best.setEndTime(currentTime);
                best.setTurnaroundTime( best.getEndTime() - best.getArrivalTime());
                best.setWaitingTime(best.getTurnaroundTime() - best.getBurstTime());
                completed++;
            }
        }
        gantTime.add(currentTime);
    }
    @Override
    public void calculateAverages() {
        // Calculate average waiting time, turnaround time, and response time here
        double totalwt = 0;
        double totaltat = 0;
        double totalrt = 0;
        for (Process pr : p1) {
            totalwt += pr.getWaitingTime();
            totaltat += pr.getTurnaroundTime();
            totalrt += pr.getResponseTime();
        }
        setAverageWaitingTime(totalwt / p1.size());
        setAverageTurnaroundTime(totaltat / p1.size() );
        setAverageResponseTime(totalrt / p1.size() );
    }
    @Override
    public void makeChart() {
        // Create a chart to visualize the scheduling results here

    }

    public List<String> getGanttProcess() {
        return ganttProcess;
    }


    public List<Integer> getGantTime() {
        return gantTime;
    }

}


