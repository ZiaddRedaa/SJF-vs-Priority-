package util;
import java.util.*;
import model.Process;
public class Validate {
    private Process p;
    public Validate(Process p) {
        this.p = p;
    }
    public boolean validate() {
        if (p.getArrivalTime() < 0)
            return false;
        if (p.getBurstTime() <= 0)
            return false;
        if (p.getPriority() < 0)
            return false;
        return true;
    }
    public boolean validateID(List<Process> list) {
        Set<String> set = new HashSet<>();
        for (Process p : list) {
            if (set.contains(p.getPID().toLowerCase())) {
                return false;
            }
            set.add(p.getPID().toLowerCase());
        }
        return true;
    }

}
