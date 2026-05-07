package metrics;
import model.Process;
import java.util.List;
public class metrics {
    public void calculate(List<Process> processes) {
        for (Process p : processes) {
            int tat = p.getEndTime() - p.getArrivalTime();
            p.setTurnaroundTime(tat);
            p.setWaitingTime(tat - p.getBurstTime());
            p.setResponseTime(p.getStartTime() - p.getArrivalTime());
        }
    }
}