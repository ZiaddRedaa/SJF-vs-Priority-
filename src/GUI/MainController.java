package view;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import model.Process;
import scheduler.Priority;
import scheduler.Sjf;
import util.Validate;

import java.util.ArrayList;
import java.util.List;

public class MainController {

    @FXML private TextField pidField;
    @FXML private TextField arrivalField;
    @FXML private TextField burstField;
    @FXML private TextField priorityField;

    @FXML private TableView<Process> inputTable;
    @FXML private TableColumn<Process, String> inputPidCol;
    @FXML private TableColumn<Process, Number> inputArrivalCol;
    @FXML private TableColumn<Process, Number> inputBurstCol;
    @FXML private TableColumn<Process, Number> inputPriorityCol;

    @FXML private TableView<Process> sjfTable;
    @FXML private TableColumn<Process, String> sjfPidCol;
    @FXML private TableColumn<Process, Number> sjfArrivalCol;
    @FXML private TableColumn<Process, Number> sjfBurstCol;
    @FXML private TableColumn<Process, Number> sjfPriorityCol;
    @FXML private TableColumn<Process, Number> sjfStartCol;
    @FXML private TableColumn<Process, Number> sjfEndCol;
    @FXML private TableColumn<Process, Number> sjfWaitingCol;
    @FXML private TableColumn<Process, Number> sjfTurnaroundCol;
    @FXML private TableColumn<Process, Number> sjfResponseCol;

    @FXML private TableView<Process> priorityTable;
    @FXML private TableColumn<Process, String> priorityPidCol;
    @FXML private TableColumn<Process, Number> priorityArrivalCol;
    @FXML private TableColumn<Process, Number> priorityBurstCol;
    @FXML private TableColumn<Process, Number> priorityPriorityCol;
    @FXML private TableColumn<Process, Number> priorityStartCol;
    @FXML private TableColumn<Process, Number> priorityEndCol;
    @FXML private TableColumn<Process, Number> priorityWaitingCol;
    @FXML private TableColumn<Process, Number> priorityTurnaroundCol;
    @FXML private TableColumn<Process, Number> priorityResponseCol;

    @FXML private Label sjfAvgWaitingLabel;
    @FXML private Label sjfAvgTurnaroundLabel;
    @FXML private Label sjfAvgResponseLabel;

    @FXML private Label priorityAvgWaitingLabel;
    @FXML private Label priorityAvgTurnaroundLabel;
    @FXML private Label priorityAvgResponseLabel;

    @FXML private Label conclusionLabel;
    @FXML private Label statusLabel;

    @FXML private HBox sjfTimelineBox;
    @FXML private HBox priorityTimelineBox;

    private final ObservableList<Process> inputProcesses = FXCollections.observableArrayList();

    private Sjf lastSjfResult;
    private Priority lastPriorityResult;

    @FXML
    public void initialize() {
        setupInputTable();

        setupResultTable(
                sjfPidCol, sjfArrivalCol, sjfBurstCol, sjfPriorityCol,
                sjfStartCol, sjfEndCol, sjfWaitingCol, sjfTurnaroundCol, sjfResponseCol
        );

        setupResultTable(
                priorityPidCol, priorityArrivalCol, priorityBurstCol, priorityPriorityCol,
                priorityStartCol, priorityEndCol, priorityWaitingCol, priorityTurnaroundCol, priorityResponseCol
        );

        inputTable.setItems(inputProcesses);

        statusLabel.setText("Ready. Add processes then click Run Comparison.");
        conclusionLabel.setText("Run the comparison first, then choose a winner button.");
    }

    private void setupInputTable() {
        inputPidCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPID()));
        inputArrivalCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getArrivalTime()));
        inputBurstCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getBurstTime()));
        inputPriorityCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getPriority()));
    }

    private void setupResultTable(
            TableColumn<Process, String> pidCol,
            TableColumn<Process, Number> arrivalCol,
            TableColumn<Process, Number> burstCol,
            TableColumn<Process, Number> priorityCol,
            TableColumn<Process, Number> startCol,
            TableColumn<Process, Number> endCol,
            TableColumn<Process, Number> waitingCol,
            TableColumn<Process, Number> turnaroundCol,
            TableColumn<Process, Number> responseCol
    ) {
        pidCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPID()));
        arrivalCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getArrivalTime()));
        burstCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getBurstTime()));
        priorityCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getPriority()));
        startCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getStartTime()));
        endCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getEndTime()));
        waitingCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getWaitingTime()));
        turnaroundCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getTurnaroundTime()));
        responseCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getResponseTime()));
    }

    @FXML
    private void addProcess() {
        try {
            String pid = pidField.getText().trim();

            if (pid.isEmpty()) {
                showError("Process ID is required.");
                return;
            }

            int arrival = Integer.parseInt(arrivalField.getText().trim());
            int burst = Integer.parseInt(burstField.getText().trim());
            int priority = Integer.parseInt(priorityField.getText().trim());

            Process process = new Process(pid, arrival, burst, priority, burst, false);

            Validate validate = new Validate(process);
            if (!validate.validate()) {
                showError("Invalid input. Arrival must be >= 0, Burst must be > 0, Priority must be >= 0.");
                return;
            }

            for (Process p : inputProcesses) {
                if (p.getPID().equalsIgnoreCase(pid)) {
                    showError("This Process ID already exists.");
                    return;
                }
            }

            inputProcesses.add(process);
            clearInputFields();

            statusLabel.setText("Process added successfully.");
            conclusionLabel.setText("Process added. Run comparison to get winners.");

        } catch (NumberFormatException e) {
            showError("Arrival, Burst, and Priority must be numbers.");
        }
    }

    @FXML
    private void runComparison() {
        if (inputProcesses.isEmpty()) {
            showError("Add at least one process first.");
            return;
        }

        List<Process> sjfProcesses = copyProcesses(inputProcesses);
        List<Process> priorityProcesses = copyProcesses(inputProcesses);

        lastSjfResult = new Sjf(sjfProcesses);
        lastSjfResult.schedule();
        lastSjfResult.calculateAverages();

        lastPriorityResult = new Priority(priorityProcesses);
        lastPriorityResult.schedule();
        lastPriorityResult.calculateAverages();

        sjfTable.setItems(FXCollections.observableArrayList(sjfProcesses));
        priorityTable.setItems(FXCollections.observableArrayList(priorityProcesses));

        sjfAvgWaitingLabel.setText(format(lastSjfResult.getAverageWaitingTime()));
        sjfAvgTurnaroundLabel.setText(format(lastSjfResult.getAverageTurnaroundTime()));
        sjfAvgResponseLabel.setText(format(lastSjfResult.getAverageResponseTime()));

        priorityAvgWaitingLabel.setText(format(lastPriorityResult.getAverageWaitingTime()));
        priorityAvgTurnaroundLabel.setText(format(lastPriorityResult.getAverageTurnaroundTime()));
        priorityAvgResponseLabel.setText(format(lastPriorityResult.getAverageResponseTime()));

        drawTimelineFromSjf(lastSjfResult);
        drawTimelineFromPriority(lastPriorityResult);

        statusLabel.setText("Comparison completed.");
        conclusionLabel.setText("Comparison completed. Choose one of the winner buttons.");
    }

    @FXML
    private void clearAll() {
        inputProcesses.clear();

        sjfTable.getItems().clear();
        priorityTable.getItems().clear();

        sjfTimelineBox.getChildren().clear();
        priorityTimelineBox.getChildren().clear();

        sjfAvgWaitingLabel.setText("-");
        sjfAvgTurnaroundLabel.setText("-");
        sjfAvgResponseLabel.setText("-");

        priorityAvgWaitingLabel.setText("-");
        priorityAvgTurnaroundLabel.setText("-");
        priorityAvgResponseLabel.setText("-");

        lastSjfResult = null;
        lastPriorityResult = null;

        conclusionLabel.setText("Run the comparison first, then choose a winner button.");
        statusLabel.setText("Cleared.");
    }

    @FXML
    private void showTotalWinner() {
        if (!hasResults()) {
            return;
        }

        int sjfPoints = 0;
        int priorityPoints = 0;

        double sjfWT = lastSjfResult.getAverageWaitingTime();
        double priorityWT = lastPriorityResult.getAverageWaitingTime();

        double sjfTAT = lastSjfResult.getAverageTurnaroundTime();
        double priorityTAT = lastPriorityResult.getAverageTurnaroundTime();

        double sjfRT = lastSjfResult.getAverageResponseTime();
        double priorityRT = lastPriorityResult.getAverageResponseTime();

        if (sjfWT < priorityWT) {
            sjfPoints++;
        } else if (priorityWT < sjfWT) {
            priorityPoints++;
        }

        if (sjfTAT < priorityTAT) {
            sjfPoints++;
        } else if (priorityTAT < sjfTAT) {
            priorityPoints++;
        }

        if (sjfRT < priorityRT) {
            sjfPoints++;
        } else if (priorityRT < sjfRT) {
            priorityPoints++;
        }

        String winner;

        if (sjfPoints > priorityPoints) {
            winner = "SJF";
        } else if (priorityPoints > sjfPoints) {
            winner = "Priority";
        } else {
            winner = "Draw";
        }

        conclusionLabel.setText(
                "Winner in Total: " + winner + "\n\n" +
                        "SJF Points: " + sjfPoints + "\n" +
                        "Priority Points: " + priorityPoints + "\n\n" +
                        "The total winner is calculated using three metrics:\n" +
                        "Average Waiting Time, Average Turnaround Time, and Average Response Time.\n" +
                        "The algorithm with the lower value in each metric gets one point."
        );
    }

    @FXML
    private void showWaitingWinner() {
        if (!hasResults()) {
            return;
        }

        double sjfValue = lastSjfResult.getAverageWaitingTime();
        double priorityValue = lastPriorityResult.getAverageWaitingTime();

        String winner = getWinnerByLowerValue(sjfValue, priorityValue);

        conclusionLabel.setText(
                "Winner in WT: " + winner + "\n\n" +
                        "SJF Average Waiting Time = " + format(sjfValue) + "\n" +
                        "Priority Average Waiting Time = " + format(priorityValue) + "\n\n" +
                        "The lower average waiting time is better."
        );
    }

    @FXML
    private void showTurnaroundWinner() {
        if (!hasResults()) {
            return;
        }

        double sjfValue = lastSjfResult.getAverageTurnaroundTime();
        double priorityValue = lastPriorityResult.getAverageTurnaroundTime();

        String winner = getWinnerByLowerValue(sjfValue, priorityValue);

        conclusionLabel.setText(
                "Winner in TAT: " + winner + "\n\n" +
                        "SJF Average Turnaround Time = " + format(sjfValue) + "\n" +
                        "Priority Average Turnaround Time = " + format(priorityValue) + "\n\n" +
                        "The lower average turnaround time is better."
        );
    }

    @FXML
    private void showResponseWinner() {
        if (!hasResults()) {
            return;
        }

        double sjfValue = lastSjfResult.getAverageResponseTime();
        double priorityValue = lastPriorityResult.getAverageResponseTime();

        String winner = getWinnerByLowerValue(sjfValue, priorityValue);

        conclusionLabel.setText(
                "Winner in RT: " + winner + "\n\n" +
                        "SJF Average Response Time = " + format(sjfValue) + "\n" +
                        "Priority Average Response Time = " + format(priorityValue) + "\n\n" +
                        "The lower average response time is better."
        );
    }

    private boolean hasResults() {
        if (lastSjfResult == null || lastPriorityResult == null) {
            conclusionLabel.setText("Please click Run Comparison first.");
            statusLabel.setText("No comparison result yet.");
            return false;
        }

        return true;
    }

    private String getWinnerByLowerValue(double sjfValue, double priorityValue) {
        if (sjfValue < priorityValue) {
            return "SJF";
        } else if (priorityValue < sjfValue) {
            return "Priority";
        } else {
            return "Draw";
        }
    }

    private List<Process> copyProcesses(List<Process> original) {
        List<Process> copy = new ArrayList<>();

        for (Process p : original) {
            copy.add(new Process(
                    p.getPID(),
                    p.getArrivalTime(),
                    p.getBurstTime(),
                    p.getPriority(),
                    p.getBurstTime(),
                    false
            ));
        }

        return copy;
    }

    private void drawTimelineFromSjf(Sjf sjf) {
        sjfTimelineBox.getChildren().clear();

        List<String> names = sjf.getGanttProcess();
        List<Integer> times = sjf.getGantTime();

        drawTimeline(sjfTimelineBox, names, times);
    }

    private void drawTimelineFromPriority(Priority priority) {
        priorityTimelineBox.getChildren().clear();

        List<String> fullChart = priority.getGanttChart();

        if (fullChart == null || fullChart.isEmpty()) {
            return;
        }

        List<String> names = new ArrayList<>();
        List<Integer> times = new ArrayList<>();

        String current = fullChart.get(0);
        names.add(current);
        times.add(0);

        for (int i = 1; i < fullChart.size(); i++) {
            if (!fullChart.get(i).equals(current)) {
                current = fullChart.get(i);
                names.add(current);
                times.add(i);
            }
        }

        times.add(fullChart.size());

        drawTimeline(priorityTimelineBox, names, times);
    }

    private void drawTimeline(HBox container, List<String> names, List<Integer> times) {
        container.getChildren().clear();

        if (names == null || times == null || names.isEmpty() || times.size() < 2) {
            return;
        }

        for (int i = 0; i < names.size(); i++) {
            int start = times.get(i);
            int end = times.get(i + 1);

            VBox block = new VBox();
            block.getStyleClass().add("timeline-block");

            Label nameLabel = new Label(names.get(i));
            nameLabel.getStyleClass().add("timeline-name");

            Label timeLabel = new Label(start + " → " + end);
            timeLabel.getStyleClass().add("timeline-time");

            block.getChildren().addAll(nameLabel, timeLabel);

            int duration = end - start;
            block.setMinWidth(Math.max(75, duration * 28));

            container.getChildren().add(block);
        }
    }

    private void clearInputFields() {
        pidField.clear();
        arrivalField.clear();
        burstField.clear();
        priorityField.clear();
        pidField.requestFocus();
    }

    private void showError(String message) {
        statusLabel.setText(message);
    }

    private String format(double value) {
        return String.format("%.2f", value);
    }
}