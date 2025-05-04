package com.example.bankeralgorithm;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BankerController implements Initializable {

    @FXML private TextField processTextField;
    @FXML private TextField R1TextField, R2TextField, R3TextField;
    @FXML private TextField R1MaxTextField, R2MaxTextField, R3MaxTextField;
    @FXML private TableView<Process> bankerTableView;
    @FXML private TableColumn<Process, String> processColumn;
    @FXML private TableColumn<Process, Integer> aColumn, bColumn, cColumn;
    @FXML private TableColumn<Process, Integer> maxAColumn, maxBColumn, maxCColumn;
    @FXML private TableColumn<Process, Integer> needAColumn, needBColumn, needCColumn;

    @FXML private ObservableList<Process> processList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the ObservableList to hold Process objects
        processList = FXCollections.observableArrayList();

        // Set the ObservableList to the TableView
        bankerTableView.setItems(processList);

        // Setting up columns with PropertyValueFactory
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
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Validation Error");
                alert.setHeaderText("Allocated resources exceed maximum.");
                alert.setContentText("Ensure allocated values are less than or equal to their maximums.");
                alert.showAndWait();
                return;
            }

            // Create the new Process object
            Process newProcess = new Process(process, a, b, c, maxA, maxB, maxC, needA, needB, needC);

            // Add the new process to the ObservableList
            processList.add(newProcess);

            // Clear input fields
            processTextField.clear();
            R1TextField.clear();
            R2TextField.clear();
            R3TextField.clear();
            R1MaxTextField.clear();
            R2MaxTextField.clear();
            R3MaxTextField.clear();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Invalid input detected");
            alert.setContentText("Please enter valid integers for allocations and max values.");
            alert.showAndWait();
        }
    }




}
