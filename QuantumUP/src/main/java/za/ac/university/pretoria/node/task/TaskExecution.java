package za.ac.university.pretoria.node.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TaskExecution {

    Random random = new Random();

    public Report execute(Task task){

        Report report = new Report();

        report.setElapsedTime(random.nextInt(60));
        for(String metric: task.getMetrics())
        {

            if(metric.equalsIgnoreCase("energyGenerated"))
                report = executeEnergyGenerate(report);
            else if(metric.equalsIgnoreCase("memoryUsage"))
                report = executeMemeoryUsage(report);
            else if(metric.equalsIgnoreCase("cpuTime"))
                report = executeCPUTime(report);
            else if(metric.equalsIgnoreCase("cpuUsage"))
                report = executeCPUUsage(report);
            else if(metric.equalsIgnoreCase("heatGenerated"))
                report = executeHeatGenerated(report);

        }

        return report;
    }


    public Report executeHeatGenerated(Report report){

        List<Integer> cpuUsage = new ArrayList<>();
        for (int i = 0; i <= report.getElapsedTime(); i++){
            int randomUsage = random.nextInt(40)+ 30;
            cpuUsage.add(randomUsage) ;
        }
        report.setCpuUsage(cpuUsage);
        return report;
    }

    public Report executeCPUTime(Report report){

        List<Integer> cpuUsage = new ArrayList<>();
        for (int i = 0; i <= report.getElapsedTime(); i++){
            int randomUsage = random.nextInt(40)+ 30;
            cpuUsage.add(randomUsage) ;
        }
        report.setCpuUsage(cpuUsage);
        return report;
    }

    public Report executeMemeoryUsage(Report report){

        List<Integer> cpuUsage = new ArrayList<>();
        for (int i = 0; i <= report.getElapsedTime(); i++){
            int randomUsage = random.nextInt(40)+ 30;
            cpuUsage.add(randomUsage) ;
        }
        report.setCpuUsage(cpuUsage);
        return report;
    }
    public Report executeEnergyGenerate(Report report){
        //
        List<Integer> cpuUsage = new ArrayList<>();
        for (int i = 0; i <= report.getElapsedTime(); i++){
            int randomUsage = random.nextInt(40)+ 30;
            cpuUsage.add(randomUsage) ;
        }
        report.setCpuUsage(cpuUsage);
        return report;
    }

    public Report executeCPUUsage(Report report){

        List<Integer> cpuUsage = new ArrayList<>();
        for (int i = 0; i <= report.getElapsedTime(); i++){
            int randomUsage = random.nextInt(40)+ 30;
            cpuUsage.add(randomUsage) ;
        }
        report.setCpuUsage(cpuUsage);
        return report;
    }
}
