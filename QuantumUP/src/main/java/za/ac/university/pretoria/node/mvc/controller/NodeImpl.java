package za.ac.university.pretoria.node.mvc.controller;

import com.google.gson.Gson;
import okhttp3.*;
import org.apache.log4j.Logger;
import za.ac.university.pretoria.node.api.NodeHandler;
import za.ac.university.pretoria.node.mvc.model.NodeException;
import za.ac.university.pretoria.node.mvc.model.Report.FinalReport;
import za.ac.university.pretoria.node.mvc.model.Report.Report;
import za.ac.university.pretoria.node.mvc.model.Report.ValueMetaData;
import za.ac.university.pretoria.node.mvc.model.Report.Value;
import za.ac.university.pretoria.node.mvc.model.Task.Task;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@Stateless(mappedName = "nodeImpl")
public class NodeImpl {

    private Random random;
    private NodeHandler nodeHandler;
    private Logger logger = Logger.getLogger(NodeImpl.class);
    private Gson gson;

    @Inject
    public void setNodeHandler(NodeHandler nodeHandler) {
        this.nodeHandler = nodeHandler;
    }

    public NodeImpl() {
        gson = new Gson();
        random = new Random();
    }

    public boolean startOperations(String id) {

        String nodeId = id;
        Boolean isNodeActive = true;
        if (nodeId != null)
            while (isNodeActive) {

                try {
                    logger.info("Checking to see if node " + id + " is active");
                    isNodeActive = nodeHandler.isNodeActive(nodeId);
                    if (isNodeActive) {
                        if (!nodeHandler.isNodeAlreadyActive(nodeId))
                            nodeHandler.setNodeActive(nodeId);

                        logger.info("Getting task for node " + id);
                        Task task = getTask(isNodeActive, nodeId);
                        if (task != null) {
                            logger.info("Got a task for node " + id);
                            nodeHandler.setNodeBusy(nodeId);
                            logger.info("Adding Task for node" + id);
                            nodeHandler.addTask(task, nodeId);
                            logger.info("Executing task for node " + id);
                            FinalReport reports = execute(task);
                            logger.info("Posting report for task in node " + id);
                            postReport(reports);
                            logger.info("Updating task to finished for node" + id);
                            nodeHandler.updateTask(task, nodeId);
                        }
                    }
                } catch (SQLException | NodeException e) {
                    logger.error("There was a problem checking to see if node " + nodeId + " is active ", e);
                }
            }
            return true;

    }

    public boolean postReport(FinalReport report) {
        String json = converReportToJson(report);
        try {
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, json);
            Request request = new Request.Builder()
                    .url("http://localhost:7001/Executions/webresources/executionPackage/postResult?result")
                    .put(body)
                    .addHeader("content-type", "application/json")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("postman-token", "08af0720-79cc-ff3d-2a7d-f208202e5ec0")
                    .build();

            Response response = client.newCall(request).execute();

        } catch (Exception e) {

            logger.error("There was a problem posting the response to the execution team ", e);
        }

        return true;
    }

    public Task getTask(Boolean isNodeActive, String nodeId) {
        Task task = null;
        if (isNodeActive == true) {
            try {

                URL url = new URL("http://localhost:7001/Executions/webresources/executionPackage/getTask");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + conn.getResponseCode());
                }

                BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

                String output;
                StringBuffer jsonBuffer = new StringBuffer();
                while ((output = br.readLine()) != null) {
                    jsonBuffer.append(output);
                }
                task = convertJsonToTask(jsonBuffer.toString());
                conn.disconnect();
                task.getExperiment().setID(random.nextInt(100000000));
            } catch (IOException e) {

                logger.error("There was a problem fetching a task from the execution team ", e);
            }
            if (task == null) {
                try {
                    Thread.sleep(10000);
                    isNodeActive = nodeHandler.isNodeActive(nodeId);
                    task = getTask(isNodeActive, nodeId);
                } catch (InterruptedException | NodeException | SQLException e) {
                    logger.error("There was a problem while waiting for a new task on node " + nodeId, e);
                }
            }
        }
        return task;
    }

    public FinalReport execute(Task task) {
        FinalReport finalReport = new FinalReport();
        ValueMetaData valueMetaData = new ValueMetaData();
        if (task.getExperiment().getNumberOfIterations() == 0)
            task.getExperiment().setNumberOfIterations(1);
        for (int i = 0; i < task.getExperiment().getNumberOfIterations(); i++) {


            Report report = new Report();
            report.setDispatcher(task.getExperiment().getName());
            report.setTaskId(task.getExperiment().getID() + "");
            LocalTime localTime = LocalTime.now();

            int loop = random.nextInt(30) + 30;

            Value value = new Value();
            value.setTimestamp(LocalTime.now().toString());
            value.setValue(loop + "");

            List<Value> values = new ArrayList<>();
            values.add(value);
            valueMetaData.setMeasurement("ElapsedTime");
            valueMetaData.setValues(values);

            report.getResult().add(valueMetaData);
            report.getResult().add(executeEnergyGenerate(loop, localTime));
            report.getResult().add(executeMemoryUsage(loop, localTime));
            report.getResult().add(executeCPUTime(loop, localTime));
            report.getResult().add(executeCPUUsage(loop, localTime));
            report.getResult().add(executeHeatGenerated(loop, localTime));


            finalReport.getResult().add(report);
        }

        return finalReport;
    }

    private String convertLocalTime(LocalTime localTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.US);
        LocalTime time = localTime;
        String f = formatter.format(time);
        return f;
    }

    public ValueMetaData executeHeatGenerated(int loop, LocalTime localTime) {
        ValueMetaData valueMetaData = new ValueMetaData();
        valueMetaData.setMeasurement("HeatGenerated");
        List<Value> values = new ArrayList<>();
        LocalTime newLocalTime = localTime;
        for (int i = 0; i < loop; i++) {
            newLocalTime = newLocalTime.plusSeconds(1);
            Value value = new Value();
            value.setTimestamp(convertLocalTime(newLocalTime));
            int randomUsage = random.nextInt(16) + 60;

            value.setValue(randomUsage + "");
            values.add(value);
        }
        valueMetaData.setValues(values);
        return valueMetaData;
    }

    public ValueMetaData executeCPUTime(int loop, LocalTime localTime) {

        ValueMetaData valueMetaData = new ValueMetaData();
        valueMetaData.setMeasurement("CpuTime");
        List<Value> values = new ArrayList<>();
        LocalTime newLocalTime = localTime;
        for (int i = 0; i < loop; i++) {
            newLocalTime = newLocalTime.plusSeconds(1);
            Value value = new Value();
            value.setTimestamp(convertLocalTime(newLocalTime));

            int randomUsage = random.nextInt(10);

            value.setValue(randomUsage + "");
            values.add(value);
        }
        valueMetaData.setValues(values);
        return valueMetaData;
    }

    public ValueMetaData executeMemoryUsage(int loop, LocalTime localTime) {

        ValueMetaData valueMetaData = new ValueMetaData();
        valueMetaData.setMeasurement("MemoryUsage");
        List<Value> values = new ArrayList<>();
        LocalTime newLocalTime = localTime;
        for (int i = 0; i < loop; i++) {
            newLocalTime = newLocalTime.plusSeconds(1);
            Value value = new Value();
            value.setTimestamp(convertLocalTime(newLocalTime));

            long randomUsage = random.nextInt(350000000) + 350000000;
            value.setValue(randomUsage + "");
            values.add(value);
        }
        valueMetaData.setValues(values);
        return valueMetaData;
    }

    public ValueMetaData executeEnergyGenerate(int loop, LocalTime localTime) {
        ValueMetaData valueMetaData = new ValueMetaData();
        valueMetaData.setMeasurement("EnergyGenerated");
        List<Value> values = new ArrayList<>();
        LocalTime newLocalTime = localTime;
        for (int i = 0; i < loop; i++) {
            newLocalTime = newLocalTime.plusSeconds(1);
            Value value = new Value();
            value.setTimestamp(convertLocalTime(newLocalTime));

            int randomUsage = random.nextInt(100) + 170;
            value.setValue(randomUsage + "");
            values.add(value);
        }
        valueMetaData.setValues(values);
        return valueMetaData;
    }

    public ValueMetaData executeCPUUsage(int loop, LocalTime localTime) {
        ValueMetaData valueMetaData = new ValueMetaData();
        valueMetaData.setMeasurement("CPU-Usage");
        List<Value> values = new ArrayList<>();

        LocalTime newLocalTime = localTime;
        for (int i = 0; i < loop; i++) {
            newLocalTime = newLocalTime.plusSeconds(1);
            Value value = new Value();
            value.setTimestamp(convertLocalTime(newLocalTime));

            int randomUsage = random.nextInt(40) + 30;

            value.setValue(randomUsage + "");
            values.add(value);
        }
        valueMetaData.setValues(values);
        return valueMetaData;
    }

    private Task convertJsonToTask(String json) {
        Task task = gson.fromJson(json, Task.class);
        return task;
    }

    private String converReportToJson(FinalReport valueMetaData) {
        String json = gson.toJson(valueMetaData);

        return json;
    }
}
