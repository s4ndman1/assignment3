package za.ac.university.pretoria.node.mvc.model.Task;

import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author i
 */
public class Experiments {
    private int ID;
    private String Name;
    private int UserId;
    private int NumberOfIterations;
    private int Timeout;
    private ArrayList<Integer> AlgorithmId;
    private ArrayList<Integer> DatasetId;
    private ArrayList<String> Measurements;

    public Experiments() {
         AlgorithmId = new ArrayList<>();
         DatasetId = new ArrayList<>();
         Measurements = new ArrayList<>();
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    public int getNumberOfIterations() {
        return NumberOfIterations;
    }

    public void setNumberOfIterations(int NumberOfIterations) {
        this.NumberOfIterations = NumberOfIterations;
    }

    public int getTimeout() {
        return Timeout;
    }

    public void setTimeout(int Timeout) {
        this.Timeout = Timeout;
    }

    public ArrayList<Integer> getAlgorithmId() {
        return AlgorithmId;
    }

    public void setAlgorithmId(ArrayList<Integer> AlgorithmId) {
        this.AlgorithmId = AlgorithmId;
    }

    public ArrayList<Integer> getDatasetId() {
        return DatasetId;
    }

    public void setDatasetId(ArrayList<Integer> DatasetId) {
        this.DatasetId = DatasetId;
    }

    public ArrayList<String> getMeasurements() {
        return Measurements;
    }

    public void setMeasurements(ArrayList<String> Measurements) {
        this.Measurements = Measurements;
    }

    
    
    
}
