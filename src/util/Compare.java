package util;
import scheduler.*;
public class Compare {
    Scheduler sjf;
    Scheduler priority;
    public Compare(Scheduler s1, Scheduler s2) {
        this.sjf = s1;
        this.priority = s2;
    }
    public String publicCompare(){
        int WTSjf = 0 , WTPrio = 0;
        int TATSjf = 0 , TATPrio = 0;
        int RTSjf = 0 , RTPrio = 0;
        int totalSjf = 0, totalPrio = 0;
        if (sjf.getAverageWaitingTime() > priority.getAverageWaitingTime()){
            WTSjf++;
            totalSjf++;
        }
        else if (sjf.getAverageWaitingTime() < priority.getAverageWaitingTime()){
            WTPrio++;
            totalPrio++;
        }
        if (sjf.getAverageTurnaroundTime() > priority.getAverageTurnaroundTime()){
            TATSjf++;
            totalSjf++;
        }
        else if (sjf.getAverageTurnaroundTime() < priority.getAverageTurnaroundTime()){
            TATPrio++;
            totalPrio++;
        }
        if (sjf.getAverageResponseTime() > priority.getAverageResponseTime()){
            RTSjf++;
            totalSjf++;
        }
        else if (sjf.getAverageResponseTime() < priority.getAverageResponseTime()){
            RTPrio++;
            totalPrio++;
        }
        if (totalSjf > totalPrio){
            if (WTSjf == 0){
                return "SJF is better than Priority in Response Time and Turnaround Time, but Priority is better in Average Waiting Time.";
            }
            else if (TATSjf == 0){
                return "SJF is better than Priority in Waiting Time and Response Time, but Priority is better in Turnaround Time.";
            }
            else if (RTSjf == 0){
                return "SJF is better than Priority in Waiting Time and Turnaround Time, but Priority is better in Response Time.";
            }
            return "SJF is better than Priority in all metrics.";
        }
        else if (totalSjf < totalPrio){
            if (WTPrio == 0){
                return "Priority is better than SJF in Response Time and Turnaround Time, but SJF is better in Waiting Time.";
            }
            else if (TATPrio == 0){
                return "Priority is better than SJF in Waiting Time and Response Time, but SJF is better in Turnaround Time.";
            }
            else if (RTPrio == 0){
                return "Priority is better than SJF in Waiting Time and Turnaround Time, but SJF is better in Response Time.";
            }
            return "Priority is better than SJF";
        }
        else {
            return "SJF and Priority are equal in all metrics.";
        }
    }
    public String compareWT(){
        if (sjf.getAverageWaitingTime() > priority.getAverageWaitingTime()){
            return "Priority is better than SJF in Waiting Time.";
        }
        else if (sjf.getAverageWaitingTime() < priority.getAverageWaitingTime()){
            return "SJF is better than Priority in Waiting Time.";
        }
        else {
            return "SJF and Priority are equal in Waiting Time.";
        }
    }
    public String compareTAT(){
        if (sjf.getAverageTurnaroundTime() > priority.getAverageTurnaroundTime()){
            return "Priority is better than SJF in Turnaround Time.";
        }
        else if (sjf.getAverageTurnaroundTime() < priority.getAverageTurnaroundTime()){
            return "SJF is better than Priority in Turnaround Time.";
        }
        else {
            return "SJF and Priority are equal in Turnaround Time.";
        }
    }
    public String compareRT(){
        if (sjf.getAverageResponseTime() > priority.getAverageResponseTime()){
            return "Priority is better than SJF in Response Time.";
        }
        else if (sjf.getAverageResponseTime() < priority.getAverageResponseTime()){
            return "SJF is better than Priority in Response Time.";
        }
        else {
            return "SJF and Priority are equal in Response Time.";
        }
    }
}

