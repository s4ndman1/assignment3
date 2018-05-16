/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.university.pretoria.node.mvc.model.Task;

import java.util.ArrayList;

/**
 *
 * @author i
 */
public class Result {
    private Experiments experiment;
    private ArrayList<Datasets> dataset;
    private ArrayList<Algorithms> algorithm;
    private String status;

    public Experiments getExperiment() {
        return experiment;
    }

    public void setExperiment(Experiments experiment) {
        this.experiment = experiment;
    }

    public ArrayList<Datasets> getDataset() {
        return dataset;
    }

    public void setDataset(ArrayList<Datasets> dataset) {
        this.dataset = dataset;
    }

    public ArrayList<Algorithms> getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(ArrayList<Algorithms> algorithm) {
        this.algorithm = algorithm;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
