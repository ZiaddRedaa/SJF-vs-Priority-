package scheduler;
import model.Process;
import java.util.*;

public class Priority extends Scheduler {

    private static int maxx = Integer.MAX_VALUE;
    private static int boost = 1;
    private static int minPrio = 0;
    private List<Process> processes;
    private List<Process> readyQueue = new ArrayList<>();
    private Set<String> arrived = new HashSet<>();
    private Map<String, Integer> waitTicks = new HashMap<>();
    private List<String> ganttChart = new ArrayList<>();
    private boolean agingEnabled;
    private int agingVal;
    private int agingBoost;
    public Priority(List<Process> processes) {
        this(processes, false, maxx, boost);
    }
    public Priority(List<Process> processes, boolean agingEnabled, int agingThreshold, int agingBoost) {
        super(processes);
        this.processes = new ArrayList<>(processes);
        this.agingEnabled = agingEnabled;
        this.agingVal = agingThreshold;
        this.agingBoost = agingBoost;
    }
    @Override
    public void schedule() {
        int currentTime = 0;
        Process running = null;
        while (!allFinished()) {
            addArrivedProcesses(currentTime);
            if (agingEnabled) {
                applyAging();
            }
            Process bestReady = pickBestReadyProcess();
            if (running == null) {
                if (bestReady != null) {
                    running = bestReady;
                    readyQueue.remove(running);
                    recordStart(running, currentTime);
                }
            } else if (bestReady != null && compare(bestReady, running) < 0) {
                readyQueue.add(running);
                running = bestReady;
                readyQueue.remove(running);
                recordStart(running, currentTime);
            }
            if (running == null) {
                ganttChart.add("Not found");
                currentTime++;
                continue;
            }
            ganttChart.add(running.getPID());
            running.setRemainingTime(running.getRemainingTime() - 1);
            if (running.getRemainingTime() == 0) {
                int endTime = currentTime + 1;
                running.setEndTime(endTime);
                running.setTurnaroundTime(endTime - running.getArrivalTime());
                running.setWaitingTime(
                        running.getTurnaroundTime() - running.getBurstTime()
                );
                running = null;
            }
            currentTime++;
        }
    }
    private void addArrivedProcesses(int currentTime) {
        for (Process p : processes) {
            if (p.getArrivalTime() <= currentTime
                    && p.getRemainingTime() > 0
                    && !arrived.contains(p.getPID())) {
                readyQueue.add(p);
                arrived.add(p.getPID());
                waitTicks.put(p.getPID(), 0);
            }
        }
    }
    private void applyAging() {
        for (Process p : readyQueue) {
            String id = p.getPID();
            int waited = waitTicks.getOrDefault(id, 0) + 1;
            waitTicks.put(id, waited);
            if (waited > 0 && waited % agingVal == 0) {
                int currentPriority = p.getPriority();
                int boostedPriority = Math.max(
                        minPrio,
                        currentPriority - agingBoost
                );
                if (boostedPriority < currentPriority) {
                    p.setPriority(boostedPriority);
                }
            }
        }
    }
    private Process pickBestReadyProcess() {
        if (readyQueue.isEmpty()) {
            return null;
        }
        Process best = readyQueue.get(0);
        for (int i = 1; i < readyQueue.size(); i++) {
            Process current = readyQueue.get(i);
            if (compare(current, best) < 0) {
                best = current;
            }
        }
        return best;
    }
    private int compare(Process a, Process b) {
        int pa = a.getPriority();
        int pb = b.getPriority();
        if (pa != pb)
            return Integer.compare(pa, pb);
        if (a.getArrivalTime() != b.getArrivalTime()) {
            return Integer.compare(a.getArrivalTime(), b.getArrivalTime());
        }
        if (a.getRemainingTime() != b.getRemainingTime()) {
            return Integer.compare(a.getRemainingTime(), b.getRemainingTime());
        }
        return a.getPID().compareTo(b.getPID());
    }
    private void recordStart(Process p, int currentTime) {
        if (!p.isStarted()) {
            p.setStarted(true);
            p.setStartTime(currentTime);
            p.setResponseTime(
                    currentTime - p.getArrivalTime()
            );
        }
        waitTicks.put(p.getPID(), 0);
    }
    private boolean allFinished() {
        for (Process p : processes) {
            if (p.getRemainingTime() != 0) {
                return false;
            }
        }
        return true;
    }
    @Override
    public void calculateAverages() {
        double totalWaiting = 0;
        double totalTurnaround = 0;
        double totalResponse = 0;
        int n = processes.size();
        for (Process p : processes) {
            totalWaiting += p.getWaitingTime();
            totalTurnaround += p.getTurnaroundTime();
            totalResponse += p.getResponseTime();
        }
        setAverageWaitingTime(totalWaiting / n);
        setAverageTurnaroundTime(totalTurnaround / n);
        setAverageResponseTime(totalResponse / n);
    }
    @Override
    public void makeChart() {
        // List<String> blocks = new ArrayList<>();
        // List<Integer> times = new ArrayList<>();
        // String prev = ganttChart.get(0);
        // int start = 0;
        // for (int i = 1; i < ganttChart.size(); i++) {
        //     if (!ganttChart.get(i).equals(prev)) {
        //         blocks.add(prev);
        //         times.add(start);
        //         prev = ganttChart.get(i);
        //         start = i;
        //     }
        // }
        // blocks.add(prev);
        // times.add(start);
        // times.add(ganttChart.size());
    }

    public List<String> getGanttChart() {
        return ganttChart;
    }
    public List<Integer> getGanttTimes() {
        List<Integer> times = new ArrayList<>();
        int start = 0;
        String prev = ganttChart.get(0);
        for (int i = 1; i < ganttChart.size(); i++) {
            if (!ganttChart.get(i).equals(prev)) {
                times.add(start);
                prev = ganttChart.get(i);
                start = i;
            }
        }
        times.add(start);
        times.add(ganttChart.size());
        return times ;
    }
}