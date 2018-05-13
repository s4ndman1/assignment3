package za.ac.university.pretoria.node.mvc.controller;

import za.ac.university.pretoria.node.api.Task;
import za.ac.university.pretoria.node.mvc.model.Report;
import za.ac.university.pretoria.node.mvc.model.TaskImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NodeImpl {

    Random random;
    DatabaseConnection connection;
    Boolean isNodeActive;
    public NodeImpl() throws SQLException, ClassNotFoundException {
        connection  = new DatabaseConnection();
        random = new Random();
        isNodeActive = true;
    }

    public void initNode(){

        while(!isNodeActive){

        }

    }

    public Task getTask(){

        try {

            URL url = new URL("http://localhost:8080/RESTfulExample/json/product/get");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
        return  new TaskImpl();
    }

    public Report execute(Task task){

        Report report = new Report();

        report.setElapsedTime(random.nextInt(30)+30);
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

        List<Integer> cpuHeatGenerated = new ArrayList<>();
        for (int i = 0; i <= report.getElapsedTime(); i++){
            int randomUsage = random.nextInt(16)+ 60;
            cpuHeatGenerated.add(randomUsage) ;
        }
        report.setHeatGenerated(cpuHeatGenerated);
        return report;
    }

    public Report executeCPUTime(Report report){

        List<Integer> cpuTimes = new ArrayList<>();
        for (int i = 0; i <= report.getElapsedTime(); i++){
            int randomUsage = random.nextInt(10);
            cpuTimes.add(randomUsage) ;
        }
        report.setCpuTime(cpuTimes);
        return report;
    }

    public Report executeMemeoryUsage(Report report){

        List<Long> memoryUsage = new ArrayList<>();
        for (int i = 0; i <= report.getElapsedTime(); i++){
            long randomUsage = random.nextInt(350000000)+ 350000000;
            memoryUsage.add(randomUsage) ;
        }
        report.setMemoryUsage(memoryUsage);
        return report;
    }
    public Report executeEnergyGenerate(Report report){
        //TODO: found out
        List<Integer> energyUsage = new ArrayList<>();
        for (int i = 0; i <= report.getElapsedTime(); i++){
            int randomUsage = random.nextInt(100)+ 170;
            energyUsage.add(randomUsage) ;
        }
        report.setEnergyGenerated(energyUsage);
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
