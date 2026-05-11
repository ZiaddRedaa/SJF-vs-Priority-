# SJF vs Priority CPU Scheduling Simulator

## Project Description

This project is an interactive **CPU Scheduling Simulator** that compares two fundamental scheduling algorithms used in operating systems:

- **SJF (Shortest Job First)**: A non-preemptive scheduling algorithm that executes processes with the shortest burst time first
- **Priority Scheduling**: A scheduling algorithm that executes processes based on their assigned priority levels

The application provides a visual comparison of how these two algorithms perform under various workload scenarios, helping students and developers understand the trade-offs between these scheduling strategies.

---

## Project Overview

### What Does It Do?

This simulator allows you to:

1. **Input Processes**: Define CPU processes with their characteristics:
   - Arrival Time
   - Burst Time (CPU execution time)
   - Priority Level

2. **Run Simulations**: Execute processes using both SJF and Priority scheduling algorithms simultaneously

3. **Compare Performance Metrics**:
   - **Waiting Time**: Total time a process waits before execution
   - **Turnaround Time**: Total time from arrival to completion
   - **Response Time**: Time from arrival to first execution
   - **Average Metrics**: Statistical analysis for both algorithms

4. **Visualize Results**: 
   - Gantt charts showing process execution timeline
   - Detailed metrics comparison
   - Process execution order and timing

### Key Features

 User-friendly JavaFX GUI  
 Real-time algorithm comparison  
 Multiple test scenarios included  
 Detailed performance metrics  
 Visual Gantt chart generation  
 Input validation and error handling  

---

## How to Use the Application

### Prerequisites

- Java 8 or higher
- JavaFX SDK (included in most JDK distributions)

### Running the Application

1. **Compile the project**:
   ```bash
   javac -d bin src/**/*.java
   ```

2. **Run the main application**:
   ```bash
   java -cp bin MainApp
   ```

### Using the GUI

1. **Enter Process Information**:
   - Input each process's Arrival Time, Burst Time, and Priority
   - You can add multiple processes

2. **Run Simulation**:
   - Click the "Schedule" or "Run" button to execute both algorithms
   - The application will calculate metrics for both SJF and Priority scheduling

3. **View Results**:
   - Examine the Gantt charts for process execution timeline
   - Compare average waiting time, turnaround time, and response time
   - Analyze which algorithm performs better for your workload

4. **Test Scenarios**:
   - Use pre-loaded test cases from `test-cases/Scenario.txt` to compare algorithm behavior

---

## Project Structure

```
SJF-vs-Priority/
├── README.md                      # Project documentation
├── screenshots/                   # Visual examples and test results
│   ├── Basic mixed workload/
│   ├── Conflict between burst time and priority/
│   ├── Fairness or starvation-sensitive case/
│   └── Validation/
├── src/                          # Source code
│   ├── MainApp.java              # Main application entry point
│   ├── metrics/
│   │   └── metrics.java          # Metrics calculation utilities
│   ├── model/
│   │   └── Process.java          # Process model class
│   ├── scheduler/
│   │   ├── Scheduler.java        # Abstract scheduler base class
│   │   ├── Sjf.java              # SJF scheduler implementation
│   │   └── Priority.java         # Priority scheduler implementation
│   ├── util/
│   │   ├── Compare.java          # Algorithm comparison utilities
│   │   └── Validate.java         # Input validation utilities
│   └── view/
│       ├── main_view.fxml        # JavaFX UI layout
│       ├── MainController.java   # UI controller logic
│       └── style.css             # Application styling
└── test-cases/
    └── Scenario.txt              # Pre-defined test scenarios
```

### Component Description

| Component | Purpose |
|-----------|---------|
| **MainApp.java** | Application entry point and window setup |
| **Process.java** | Data model representing a CPU process |
| **Scheduler.java** | Abstract base class for scheduling algorithms |
| **Sjf.java** | Shortest Job First scheduler implementation |
| **Priority.java** | Priority-based scheduler implementation |
| **metrics.java** | Calculates performance metrics (wait time, turnaround time, etc.) |
| **Compare.java** | Compares results between two scheduling algorithms |
| **Validate.java** | Validates user input and process data |
| **MainController.java** | Handles GUI interactions and updates |
| **main_view.fxml** | Defines the user interface layout |

---

## Test Scenarios

The project includes several pre-configured test scenarios in `test-cases/Scenario.txt`:

### Scenario A: Basic Mixed Workload
- Tests algorithms with processes arriving at different times
- Mix of short and long burst times

### Scenario B: Conflict Between Burst Time and Priority
- Examines how priority can override execution order
- Shows potential starvation issues

### Scenario C: Fairness or Starvation-Sensitive Case
- Tests scenarios where low-priority processes might be starved
- Evaluates fairness of the scheduling algorithm

---

## Algorithm Details

### Shortest Job First (SJF)
- **Type**: Non-preemptive
- **Selection**: Process with shortest burst time executes next
- **Advantage**: Minimizes average waiting time
- **Disadvantage**: Can cause starvation for longer processes

### Priority Scheduling
- **Type**: Non-preemptive
- **Selection**: Process with highest priority executes next
- **Advantage**: Allows important tasks to execute first
- **Disadvantage**: Low-priority processes may starve

---

## Development Team

This project was developed by:

- **Ziad Reda**
- **Amr Ahmed**
- **Diaa Adel**
- **Hussien Ahmed**
- **Omar Mohamed**
- **Youssef Hesham**

---

## Learning Objectives

By using this simulator, students can:

✓ Understand CPU scheduling algorithms  
✓ Compare algorithm performance metrics  
✓ Analyze scheduling trade-offs  
✓ Identify starvation and fairness issues  
✓ Learn Gantt chart interpretation  
✓ Apply OS concepts in a practical tool  

---

## Future Enhancements

Potential improvements for future versions:

- [ ] Add Round Robin (RR) scheduling
- [ ] Implement preemptive versions of algorithms
- [ ] Add time quantum configuration for RR
- [ ] Export results to CSV/PDF
- [ ] Add animation for process execution
- [ ] Create custom algorithm builder
- [ ] Add more performance metrics

---

## License

This project is created for educational purposes.

---

## Questions or Feedback?

For questions about the project or how to use it, please refer to the code comments or the test scenarios included in the `test-cases/` directory.

---

