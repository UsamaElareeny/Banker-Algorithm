package com.example.bankeralgorithm;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.*;

public class BankerController implements Initializable {

    // UI controls
    @FXML private TextField processTextField;
    @FXML private TextField R1TextField, R2TextField, R3TextField;
    @FXML private TextField R1MaxTextField, R2MaxTextField, R3MaxTextField;
    @FXML private TextField availableTextField1, availableTextField2, availableTextField3;
    @FXML private TableView<Process> bankerTableView;

    // Table columns
    @FXML private TableColumn<Process, String> processColumn;
    @FXML private TableColumn<Process, Integer> aColumn, bColumn, cColumn;
    @FXML private TableColumn<Process, Integer> maxAColumn, maxBColumn, maxCColumn;
    @FXML private TableColumn<Process, Integer> needAColumn, needBColumn, needCColumn;
    @FXML private TableColumn<Process, Integer> availAColumn, availBColumn, availCColumn;
    @FXML private TableColumn<Process, String> safeSequenceColumn;

    // Process list
    private ObservableList<Process> processList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        processList = FXCollections.observableArrayList();
        bankerTableView.setItems(processList);

        // Setup columns
        processColumn.setCellValueFactory(new PropertyValueFactory<>("processName"));
        aColumn.setCellValueFactory(new PropertyValueFactory<>("resource1"));
        bColumn.setCellValueFactory(new PropertyValueFactory<>("resource2"));
        cColumn.setCellValueFactory(new PropertyValueFactory<>("resource3"));

        maxAColumn.setCellValueFactory(new PropertyValueFactory<>("resource1Max"));
        maxBColumn.setCellValueFactory(new PropertyValueFactory<>("resource2Max"));
        maxCColumn.setCellValueFactory(new PropertyValueFactory<>("resource3Max"));

        needAColumn.setCellValueFactory(new PropertyValueFactory<>("need1"));
        needBColumn.setCellValueFactory(new PropertyValueFactory<>("need2"));
        needCColumn.setCellValueFactory(new PropertyValueFactory<>("need3"));

        availAColumn.setCellValueFactory(new PropertyValueFactory<>("availA"));
        availBColumn.setCellValueFactory(new PropertyValueFactory<>("availB"));
        availCColumn.setCellValueFactory(new PropertyValueFactory<>("availC"));

        safeSequenceColumn.setCellValueFactory(new PropertyValueFactory<>("safeSequence"));
    }

    @FXML
    private void addProcess() {
        try {
            String process = processTextField.getText().trim();
            int a = Integer.parseInt(R1TextField.getText().trim());
            int b = Integer.parseInt(R2TextField.getText().trim());
            int c = Integer.parseInt(R3TextField.getText().trim());
            int maxA = Integer.parseInt(R1MaxTextField.getText().trim());
            int maxB = Integer.parseInt(R2MaxTextField.getText().trim());
            int maxC = Integer.parseInt(R3MaxTextField.getText().trim());

            int needA = maxA - a;
            int needB = maxB - b;
            int needC = maxC - c;

            if (needA < 0 || needB < 0 || needC < 0) {
                showAlert(Alert.AlertType.WARNING, "Validation Error",
                        "Allocated resources exceed maximum.",
                        "Ensure allocated values are less than or equal to their maximums.");
                return;
            }

            Process newProcess = new Process(process, a, b, c, maxA, maxB, maxC, needA, needB, needC);
            processList.add(newProcess);

            // Clear inputs
            processTextField.clear();
            R1TextField.clear();
            R2TextField.clear();
            R3TextField.clear();
            R1MaxTextField.clear();
            R2MaxTextField.clear();
            R3MaxTextField.clear();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error",
                    "Invalid input detected",
                    "Please enter valid integers for allocations and max values.");
        }
    }

    @FXML
    private void calculate() {
        try {
            int[] available = {
                    Integer.parseInt(availableTextField1.getText().trim()),
                    Integer.parseInt(availableTextField2.getText().trim()),
                    Integer.parseInt(availableTextField3.getText().trim())
            };

            int size = processList.size();
            int[][] allocated = new int[size][3];
            int[][] maximum = new int[size][3];
            int[][] need = new int[size][3];
            String[] processNames = new String[size];
            Process[] processes = new Process[size];

            for (int i = 0; i < size; i++) {
                Process p = processList.get(i);
                processes[i] = p;
                processNames[i] = p.getProcessName();
                allocated[i] = new int[]{p.getResource1(), p.getResource2(), p.getResource3()};
                maximum[i] = new int[]{p.getResource1Max(), p.getResource2Max(), p.getResource3Max()};
                need[i] = new int[]{p.getNeed1(), p.getNeed2(), p.getNeed3()};
            }

            List<String> safeSequence = new ArrayList<>();
            List<int[]> availableSequence = new ArrayList<>();
            boolean[] finish = new boolean[size];
            boolean progressMade;

            int[] work = Arrays.copyOf(available, 3);
            availableSequence.add(Arrays.copyOf(work, 3)); // Initial available

            do {
                progressMade = false;
                for (int i = 0; i < size; i++) {
                    if (!finish[i] && canProceed(i, work, need)) {
                        // Process executes
                        for (int j = 0; j < 3; j++) {
                            work[j] += allocated[i][j];
                        }
                        finish[i] = true;
                        safeSequence.add(processNames[i]);
                        availableSequence.add(Arrays.copyOf(work, 3));
                        progressMade = true;
                    }
                }
            } while (progressMade);

            if (safeSequence.size() == size) {
                // Reset all available values and safe sequence
                for (Process p : processList) {
                    p.setAvailA(0);
                    p.setAvailB(0);
                    p.setAvailC(0);
                    p.setSafeSequence("");
                }

                // Update available values in safe sequence order
                for (int i = 0; i < safeSequence.size(); i++) {
                    String processName = safeSequence.get(i);
                    int[] av = availableSequence.get(i + 1); // i+1 because first element is initial available

                    // Find the process and set its available values
                    for (Process p : processList) {
                        if (p.getProcessName().equals(processName)) {
                            p.setAvailA(av[0]);
                            p.setAvailB(av[1]);
                            p.setAvailC(av[2]);
                            break;
                        }
                    }
                }

                // Set safe sequence in the last process (for visual)
                String seq = String.join(" ", safeSequence);
                if (!processList.isEmpty()) {
                    processList.get(size - 1).setSafeSequence(seq);
                }

                // Reorder the table according to the safe sequence
                ObservableList<Process> orderedList = FXCollections.observableArrayList();
                for (String name : safeSequence) {
                    for (Process p : processList) {
                        if (p.getProcessName().equals(name)) {
                            orderedList.add(p);
                            break;
                        }
                    }
                }
                // Add any remaining processes that might not be in safe sequence (shouldn't happen)
                for (Process p : processList) {
                    if (!orderedList.contains(p)) {
                        orderedList.add(p);
                    }
                }

                bankerTableView.setItems(orderedList);
                bankerTableView.refresh();
            } else {
                showAlert(Alert.AlertType.ERROR, "Unsafe State",
                        "The system is in an unsafe state.",
                        "Deadlock is possible.");
            }

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error",
                    "Invalid input detected",
                    "Please enter valid integers for available resources.");
        }
    }




    private boolean canProceed(int processIndex, int[] available, int[][] need) {
        for (int i = 0; i < 3; i++) {
            if (need[processIndex][i] > available[i]) {
                return false;
            }
        }
        return true;
    }

    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
