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

    // Newly added fields for Allocation and Available resources
    private int allocA, allocB, allocC; // Allocated resources
    private int availA, availB, availC; // Available resources
    private String safeSequence; // To store safe sequence

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

        // Initialize allocation and available resources
        this.allocA = 0;
        this.allocB = 0;
        this.allocC = 0;

        this.availA = 0;
        this.availB = 0;
        this.availC = 0;

        this.safeSequence = ""; // Default empty safe sequence
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

    // Setters for Max resources
    public void setMaxA(int maxA) { this.resource1Max = maxA; }
    public void setMaxB(int maxB) { this.resource2Max = maxB; }
    public void setMaxC(int maxC) { this.resource3Max = maxC; }

    // Setters for Need resources
    public void setNeedA(int needA) { this.need1 = needA; }
    public void setNeedB(int needB) { this.need2 = needB; }
    public void setNeedC(int needC) { this.need3 = needC; }

    // Setters for Allocated resources
    public void setAllocA(int allocA) { this.allocA = allocA; }
    public void setAllocB(int allocB) { this.allocB = allocB; }
    public void setAllocC(int allocC) { this.allocC = allocC; }

    // Setters for Available resources
    public void setAvailA(int availA) { this.availA = availA; }
    public void setAvailB(int availB) { this.availB = availB; }
    public void setAvailC(int availC) { this.availC = availC; }


    // Getter methods for Allocated and Available resources
    public int getAllocA() { return allocA; }
    public int getAllocB() { return allocB; }
    public int getAllocC() { return allocC; }

    public int getAvailA() { return availA; }
    public int getAvailB() { return availB; }
    public int getAvailC() { return availC; }

    public void setSafeSequence(String safeSequence) {
        this.safeSequence = safeSequence;
    }

    public String getSafeSequence() {
        return safeSequence;
    }
}
