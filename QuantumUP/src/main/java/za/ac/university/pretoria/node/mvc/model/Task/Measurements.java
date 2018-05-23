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
public class Measurements {
    private int ID;
    private int ExperimentId;
    private String Name;
    private double CpuTime;
    private double CpuUsage;
    private double ElapsedTime;
    private double EnergyGenerated;
    private double HeatGenerated;
    private double MemoryUsage;

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

    public double getCpuTime() {
        return CpuTime;
    }

    public void setCpuTime(int CpuTime) {
        this.CpuTime = CpuTime;
    }

    public double getCpuUsage() {
        return CpuUsage;
    }

    public void setCpuUsage(int CpuUsage) {
        this.CpuUsage = CpuUsage;
    }

    public double getElapsedTime() {
        return ElapsedTime;
    }

    public void setElapsedTime(int ElapsedTime) {
        this.ElapsedTime = ElapsedTime;
    }

    public double getEnergyGenerated() {
        return EnergyGenerated;
    }

    public void setEnergyGenerated(int EnergyGenerated) {
        this.EnergyGenerated = EnergyGenerated;
    }

    public double getHeatGenerated() {
        return HeatGenerated;
    }

    public void setHeatGenerated(int HeatGenerated) {
        this.HeatGenerated = HeatGenerated;
    }

    public double getMemoryUsage() {
        return MemoryUsage;
    }

    public void setMemoryUsage(int MemoryUsage) {
        this.MemoryUsage = MemoryUsage;
    }
    
    
}
