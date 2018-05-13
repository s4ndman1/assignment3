package za.ac.university.pretoria.node.mvc.model;

import java.util.List;

public class Report {


    List<Integer> cpuUsage;
    List<Integer> cpuTime;
    Integer elapsedTime;
    List<Long> memoryUsage;
    List<Integer> energyGenerated;
    List<Integer> heatGenerated;

    public List<Integer> getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(List<Integer> cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public List<Integer> getCpuTime() {
        return cpuTime;
    }

    public void setCpuTime(List<Integer> cpuTime) {
        this.cpuTime = cpuTime;
    }

    public Integer getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(Integer elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public List<Long> getMemoryUsage() {
        return memoryUsage;
    }

    public void setMemoryUsage(List<Long> memoryUsage) {
        this.memoryUsage = memoryUsage;
    }

    public List<Integer> getEnergyGenerated() {
        return energyGenerated;
    }

    public void setEnergyGenerated(List<Integer> energyGenerated) {
        this.energyGenerated = energyGenerated;
    }

    public List<Integer> getHeatGenerated() {
        return heatGenerated;
    }

    public void setHeatGenerated(List<Integer> heatGenerated) {
        this.heatGenerated = heatGenerated;
    }

}

