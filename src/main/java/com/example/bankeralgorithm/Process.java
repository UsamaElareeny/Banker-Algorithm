package com.example.bankeralgorithm;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class Process {
    private String processName;
    private int resource1, resource2, resource3;
    private int resource1Max, resource2Max, resource3Max;
    private int need1, need2, need3;

    // Constructor
    public Process(String processName, int resource1, int resource2, int resource3,
                   int resource1Max, int resource2Max, int resource3Max,
                   int need1, int need2, int need3) {
        this.processName = processName;
        this.resource1 = resource1;
        this.resource2 = resource2;
        this.resource3 = resource3;
        this.resource1Max = resource1Max;
        this.resource2Max = resource2Max;
        this.resource3Max = resource3Max;
        this.need1 = need1;
        this.need2 = need2;
        this.need3 = need3;
    }

    // Getter methods
    public String getProcessName() {
        return processName;
    }

    public int getResource1() {
        return resource1;
    }

    public int getResource2() {
        return resource2;
    }

    public int getResource3() {
        return resource3;
    }

    public int getResource1Max() {
        return resource1Max;
    }

    public int getResource2Max() {
        return resource2Max;
    }

    public int getResource3Max() {
        return resource3Max;
    }

    public int getNeed1() {
        return need1;
    }

    public int getNeed2() {
        return need2;
    }

    public int getNeed3() {
        return need3;
    }
}
