package za.ac.university.pretoria.node.mvc.model.Task;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author i
 */
public class Datasets {
    private int ID;
    private int ExperimentId;
    private String Name;
    private String Datapath;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getExperimentId() {
        return ExperimentId;
    }

    public void setExperimentId(int ExperimentId) {
        this.ExperimentId = ExperimentId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getDatapath() {
        return Datapath;
    }

    public void setDatapath(String Datapath) {
        this.Datapath = Datapath;
    }

   
}
