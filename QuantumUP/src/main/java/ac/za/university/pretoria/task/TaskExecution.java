package ac.za.university.pretoria.task;

public class TaskExecution {

    public Report execute(Task task){

        Report report = new Report();
        report.setMetric(task.getMetric());
        report.setSingleMeasurement(task.execute());

        return report;
    }
}
